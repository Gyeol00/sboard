package kr.co.sboard.repository.impl;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.sboard.dto.PageRequestDTO;
import kr.co.sboard.entity.QArticle;
import kr.co.sboard.entity.QUser;
import kr.co.sboard.repository.custom.ArticleRepositoryCustom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class ArticleRepositoryImpl implements ArticleRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    private QArticle qArticle = QArticle.article;
    private QUser qUser = QUser.user;

    @Override
    public Page<Tuple> selectAllForList(Pageable pageable) {

        // select~ from~ join~ on~ limit 0,10(첫번째 값 offset, 두번째 값 size) orderby~
        List<Tuple> tupleList = queryFactory // 페이징 된 리스트
                                        .select(qArticle, qUser.nick)
                                        .from(qArticle)
                                        .join(qUser)
                                        .on(qArticle.user.uid.eq(qUser.uid))
                                        .offset(pageable.getOffset())
                                        .limit(pageable.getPageSize())
                                        .orderBy(qArticle.no.desc())
                                        .fetch();

        long total = queryFactory.select(qArticle.count()).from(qArticle).fetchOne();



        // 페이징 처리를 위한 페이지 객체 반환
        return new PageImpl<Tuple>(tupleList, pageable, total);
    }

    // 글 목록 검색 메서드
    @Override
    public Page<Tuple> selectAllForSearch(PageRequestDTO pageRequestDTO, Pageable pageable) {

        String searchType = pageRequestDTO.getSearchType();
        String keyword = pageRequestDTO.getKeyword();

        // 검색 조건에 따라서 where 조건 표현식 생성
        BooleanExpression expression = null;

        if(searchType.equals("title")){ // 제목 검색
            expression = qArticle.title.contains(keyword); // where title = contain 'keyword'
        }else if(searchType.equals("content")){
            expression = qArticle.content.contains(keyword);
        }else if(searchType.equals("writer")){
            expression = qUser.nick.contains(keyword);
        }

        List<Tuple> tupleList = queryFactory // 페이징 된 리스트
                .select(qArticle, qUser.nick)
                .from(qArticle)
                .join(qUser)
                .on(qArticle.user.uid.eq(qUser.uid))
                .where(expression)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(qArticle.no.desc())
                .fetch();

        long total = queryFactory
                            .select(qArticle.count())
                            .from(qArticle)
                            .join(qUser)
                            .on(qArticle.user.uid.eq(qUser.uid))
                            .where(expression)
                            .fetchOne();

        // 페이징 처리를 위한 페이지 객체 반환
        return new PageImpl<Tuple>(tupleList, pageable, total);
    }
}

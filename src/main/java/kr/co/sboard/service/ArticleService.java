package kr.co.sboard.service;

import com.querydsl.core.Tuple;
import jakarta.persistence.Entity;
import kr.co.sboard.dao.ArticleMapper;
import kr.co.sboard.dto.ArticleDTO;
import kr.co.sboard.dto.PageRequestDTO;
import kr.co.sboard.dto.PageResponseDTO;
import kr.co.sboard.entity.Article;
import kr.co.sboard.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component // @controller, @service, @repository 파생됨
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final ArticleMapper articleMapper;
    private final ModelMapper modelMapper;

    // 글 목록 조회
    public PageResponseDTO findAll(PageRequestDTO pageRequestDTO) {

        // 페이징 처리를 위한 pageable 객체 생성
        Pageable pageable = pageRequestDTO.getPageable("no"); // 최신글부터 정렬, 내림차순

        Page<Tuple> pageArticle = articleRepository.selectAllForList(pageable);

        log.info("pageArticle : {} ", pageArticle);

        // Article Entity 리스트를 ArticleDTO 리스트로 변환
        List<ArticleDTO> articleDTOList = pageArticle.getContent().stream().map(tuple -> { // 아티클 엔티티를 아티클DTO로 변환

            Article article = tuple.get(0, Article.class); // .select(qArticle, qUser.nick) 1번째 값
            String nick = tuple.get(1, String.class); // .select(qArticle, qUser.nick) 2번째 값

            ArticleDTO articleDTO = modelMapper.map(article, ArticleDTO.class);
            articleDTO.setNick(nick);

            return articleDTO;

        }).toList();

        // 전체 게시물 갯수
        int total = (int) pageArticle.getTotalElements();

        return PageResponseDTO
                .builder()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(articleDTOList)
                .total(total)
                .build();
    }

    public int register(ArticleDTO articleDTO) {

        // 엔티티 변환
        Article article = modelMapper.map(articleDTO, Article.class);

        // JPA 저장
        //Article savedArticle = articleRepository.save(article); // 저장하고 나서 정장된 걸 조회한 걸 가져옴
        // 저장한 글 번호 반환
        //return savedArticle.getNo();

        // Mybatis 저장
        articleMapper.insertArticle(articleDTO);

        // 매개변수로 전달되는 articleDTO의 no 속성에 Mybatis가 INSERT한 데이터의 PK값을 반환
        int no = articleDTO.getNo();

        return no;

    }


}

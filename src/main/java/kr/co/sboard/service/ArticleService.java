package kr.co.sboard.service;

import jakarta.persistence.Entity;
import kr.co.sboard.dao.ArticleMapper;
import kr.co.sboard.dto.ArticleDTO;
import kr.co.sboard.entity.Article;
import kr.co.sboard.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Component // @controller, @service, @repository 파생됨
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final ArticleMapper articleMapper;
    private final ModelMapper modelMapper;

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

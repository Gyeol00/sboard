package kr.co.sboard.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArticleDTO {

    private int no;
    private String cate;
    private String title;
    private String content;
    private int comment;
    private int file;
    private int hit;
    private String writer;
    private String regip;
    private String wdate;

    // 추가 필드
    private String nick;

    public String getWdate() {
        return wdate.substring(0, 10);
    }

    // 파일 첨부 객체
    private MultipartFile file1;
    private MultipartFile file2;

    public List<MultipartFile> getMultipartFiles() {

        return List.of(file1, file2);

    }

}

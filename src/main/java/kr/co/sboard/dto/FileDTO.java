package kr.co.sboard.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FileDTO {

    private int fno;
    private int ano; // 첨부하는 파일의 글 번호
    private String oName;
    private String sName;
    private int download;
    private String rdate;

}

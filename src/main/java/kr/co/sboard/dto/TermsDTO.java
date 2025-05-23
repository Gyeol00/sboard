package kr.co.sboard.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TermsDTO {

    private int no;
    private String terms;
    private String privacy;

}

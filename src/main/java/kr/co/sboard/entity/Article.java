package kr.co.sboard.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "Article")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
    private int no;

    @Column(nullable = false)
    private String cate;

    private String title;
    private String content;
    private int comment;
    private int file;
    private int hit;
    private String writer;
    private String regip;

    @CreationTimestamp
    private LocalDateTime wdate;

    // 추가 필드
    //@Transient // 엔티티 속성에서 테이블 매핑에서 제외하는 어노테이션. 테이블에 추가 안되게 하는 어노테이션.
    //private String nick;

    @PrePersist // persist가 db에 영구저장. 사전에 저장하기 전에 cate값 검사.
    public void prePersist() {
        if(this.cate == null) {
            this.cate = "free";
        }
    }

}

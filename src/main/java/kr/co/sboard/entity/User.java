package kr.co.sboard.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "User")
public class User {

    @Id
    private String uid;
    private String pass;
    private String name;
    private String nick;
    private String email;
    private String hp;

    @Column(nullable = false)
    private String role;

    private String zip;
    private String addr1;
    private String add2;
    private String regip;

    @CreationTimestamp // NOW() 엔티티에서 사용되는 값으로 DTO에서는 필요없음
    private LocalDateTime regDate;

    private String leaveDate;

    @PrePersist // persist가 db에 영구저장. 사전에 저장하기 전에 role값을 검사.
    public void prePersist() {
        if(this.role == null) {
            this.role = "USER";
        }
    }
}

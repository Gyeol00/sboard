package kr.co.sboard.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping(value = {"/", "/index"})
    public String index(Authentication auth) {

        if(auth != null && auth.isAuthenticated()) { // 인증(로그인)했을 경우
            // 로그인 성공 했을 경우
            return "forward:/article/list"; // 주소는 index 화면은 list
        }


        return "/index";
    }
}

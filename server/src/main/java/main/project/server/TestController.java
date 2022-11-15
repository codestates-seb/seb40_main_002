package main.project.server;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping ("/login/oauth2/kakao")
    public void kakao(String code, String state, String error, String error_description) {
        System.out.println(code);
        System.out.println();
    }


    @GetMapping ("/home")
    public void home() {
        System.out.println("KAKAO !!!!!!!!!!!!!!");

    }
}

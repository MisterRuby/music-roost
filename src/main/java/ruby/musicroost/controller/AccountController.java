package ruby.musicroost.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ruby.musicroost.domain.Account;
import ruby.musicroost.request.account.AccountSignUp;
import ruby.musicroost.service.AccountService;

@RestController
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @GetMapping("/")
    public String index() {
        return "ok";
    }

    /**
     * 계정 등록
     * @param accountSignUp
     */
    @PostMapping("/signUp")
    public void signUp( @RequestBody AccountSignUp accountSignUp) {
        accountService.signUp(accountSignUp.getName(), accountSignUp.getPassword());
    }

    /**
     * 로그인
     * @param accountSignUp
     * @return
     */
    @PostMapping("/login")
    public void login(@RequestBody AccountSignUp accountSignUp) {
        accountService.login(accountSignUp.getName(), accountSignUp.getPassword());
    }
}

package ruby.musicroost.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ruby.musicroost.domain.Account;
import ruby.musicroost.request.account.AccountSignUp;
import ruby.musicroost.service.AccountService;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @GetMapping("/")
    public String index() {
        return "ok";
    }

    @PostMapping("/signUp")
    public String signUp(@RequestBody AccountSignUp accountSignUp) {
        Account account = accountService.signUp(accountSignUp.getName(), accountSignUp.getPassword());
        log.info("name={} / password={}", account.getName(), account.getPassword());
        return "ok";
    }
}

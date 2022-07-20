package ruby.musicroost.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ruby.musicroost.domain.Account;
import ruby.musicroost.exception.account.UsernameNotFoundException;
import ruby.musicroost.exception.account.UsernameSameException;
import ruby.musicroost.repository.AccountRepository;
import ruby.musicroost.security.UserAccount;
import ruby.musicroost.service.AccountService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Account signUp(String name, String password) {
        // 같은 이름으로 등록된 계정이 있는지 확인.
        // 없다면 등록시키고 반환
        Optional<Account> accountOptional = accountRepository.findByName(name);

        if (accountOptional.isPresent()) throw new UsernameSameException();

        Account account = Account.builder()
                .name(name)
                .password(passwordEncoder.encode(password))
                .build();

        return accountRepository.save(account);
    }

    @Override
    @Transactional(readOnly = true)
    public void login(String email, String password) {
        Account account = accountRepository.findByName(email)
                .orElseThrow(UsernameNotFoundException::new);

        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(
                        new UserAccount(account), password, List.of(new SimpleGrantedAuthority("ROLE_USER")));

        SecurityContextHolder.getContext().setAuthentication(token);
    }
}

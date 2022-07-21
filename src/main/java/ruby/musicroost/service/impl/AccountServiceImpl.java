package ruby.musicroost.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ruby.musicroost.domain.Account;
import ruby.musicroost.domain.enums.AccountRole;
import ruby.musicroost.exception.account.UserBadCredentialsException;
import ruby.musicroost.exception.account.UserRoleWaitingException;
import ruby.musicroost.exception.account.UsernameNotFoundException;
import ruby.musicroost.exception.account.UsernameSameException;
import ruby.musicroost.repository.AccountRepository;
import ruby.musicroost.service.AccountService;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 계정 등록
     * @param name
     * @param password
     * @return
     */
    @Override
    public Account signUp(String name, String password) {
        boolean isFirst = accountRepository.count() == 0;
        if (!isFirst) {
            accountRepository.findByName(name)
                    .ifPresent(account -> {throw new UsernameSameException();});
        }

        Account account = Account.builder()
                .name(name)
                .password(passwordEncoder.encode(password))
                .build();

        account.addRole(isFirst ? AccountRole.ADMIN : AccountRole.WAITING);

        return accountRepository.save(account);
    }

    /**
     * 로그인
     * @param name
     * @param password
     */
    @Override
    @Transactional(readOnly = true)
    public void login(String name, String password) {
        Account account = accountRepository.findByName(name)
                .orElseThrow(UsernameNotFoundException::new);

        if (!passwordEncoder.matches(password, account.getPassword())) {
            throw new UserBadCredentialsException();
        }

        if (account.isWaiting()) {
            throw new UserRoleWaitingException();
        }

        setAuthentication(account);
    }

    private void setAuthentication(Account account) {
        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(
                        account.getName(), account.getPassword(), authorities(account.getRoles()));

        SecurityContextHolder.getContext().setAuthentication(token);
    }

    private Collection<? extends GrantedAuthority> authorities(Set<AccountRole> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
                .collect(Collectors.toSet());
    }
}

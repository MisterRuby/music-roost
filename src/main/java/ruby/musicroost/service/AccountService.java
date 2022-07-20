package ruby.musicroost.service;

import ruby.musicroost.domain.Account;

public interface AccountService {

    Account signUp(String name, String password);

    void login(String email, String password);
}

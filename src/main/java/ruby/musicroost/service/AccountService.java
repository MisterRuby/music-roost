package ruby.musicroost.service;

import ruby.musicroost.domain.Account;

public interface AccountService {

    /**
     * 계정 등록
     * @param name
     * @param password
     * @return
     */
    Account signUp(String name, String password);

    /**
     * 로그인
     * @param name
     * @param password
     */
    void login(String name, String password);
}

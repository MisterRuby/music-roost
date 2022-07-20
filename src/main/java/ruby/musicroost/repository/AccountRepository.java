package ruby.musicroost.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ruby.musicroost.domain.Account;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByName(String name);
}

package ruby.musicroost.domain;

import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import ruby.musicroost.domain.enums.AccountRole;

import javax.persistence.*;
import java.util.Set;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(value = AuditingEntityListener.class)
@EqualsAndHashCode(of = "id")
@Getter
public class Account {

    // 계정을 등록한다.
    // 등록된 계정은 곧바로 기능을 사용할 수 없음. 권한이 '대기중'
    // 대기중인 계정은 ADMIN 권한을 가진 계정으로 MANAGER 이상의 권한을 줘야 기능 사용 가능
    // 권한은 세 종류로 나뉨. ADMIN, MANAGER, WAITING
    // ADMIN -> 최종 관리자. 계정을 삭제하거나 권한 변경 가능. 애플리케이션 사용 가능
    // MANAGER -> ADMIN 에 의해 권한을 대리부여받은 권한. 애플리케이션 사용만 가능
    // WAITING -> 신청 대기중인 계정.

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(value = EnumType.STRING)
    private Set<AccountRole> roles;

    @Builder
    public Account(String name, String password, Set<AccountRole> roles) {
        this.name = name;
        this.password = password;
    }
}

package ruby.musicroost.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import ruby.musicroost.domain.enums.Course;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(value = AuditingEntityListener.class)
@Getter
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String phoneNumber;
    @Enumerated
    private Course course;
    @CreatedDate
    private LocalDate since;

    @Builder
    public Teacher(String name, String email, String phoneNumber, Course course) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.course = course;
    }
}

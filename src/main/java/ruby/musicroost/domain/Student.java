package ruby.musicroost.domain;

import lombok.*;
import ruby.musicroost.domain.enums.Course;

import javax.persistence.*;
import java.time.LocalDate;

import static javax.persistence.FetchType.LAZY;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter @Setter
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String phoneNumber;
    @Enumerated
    private Course course;
    private LocalDate since;

    @ManyToOne(fetch = LAZY)
    private Teacher teacher;

    @Builder
    public Student(String name, String email, String phoneNumber, Course course, LocalDate since) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.course = course;
        this.since = since;
    }
}

package ruby.musicroost.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 레슨
 *  - 레슨 시간
 *  - 수강생 정보
 *  - 선생님 정보
 */
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime lessonTime;

    @ManyToOne
    private Student student;
    @ManyToOne
    private Teacher teacher;
}

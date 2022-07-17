package ruby.musicroost.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ruby.musicroost.domain.enums.Course;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static javax.persistence.FetchType.LAZY;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime time;

    @ManyToOne(fetch = LAZY)
    private Teacher teacher;
    @ManyToOne(fetch = LAZY)
    private Student student;

    @Builder
    public Schedule(LocalDateTime time, Teacher teacher, Student student) {
        this.time = time;
        this.teacher = teacher;
        this.student = student;
    }

    /** 유틸리티 메서드 start */
    public boolean isPracticable() {
        return this.student.getCourse()
                .equals(this.teacher.getCourse());
    }

    public void setTimeByString(String time) {
        this.time = LocalDateTime.parse(time, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

    /** 유틸리티 메서드 end */
}

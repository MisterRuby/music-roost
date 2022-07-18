package ruby.musicroost.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ruby.musicroost.domain.editor.ScheduleEditor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static javax.persistence.FetchType.LAZY;
import static ruby.musicroost.domain.editor.ScheduleEditor.*;

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


    /** 비즈니스 메서드 start */

    /**
     * 수강생 과목과 선생님 과목 일치 여부 확인
     * @return
     */
    public boolean isPracticable() {
        return this.student.getCourse()
                .equals(this.teacher.getCourse());
    }

    /**
     * 스케쥴 정보 수정
     * @param scheduleEditor
     */
    public void edit(ScheduleEditor scheduleEditor) {
        this.teacher = scheduleEditor.getTeacher();
        this.time = scheduleEditor.getTime();
    }
    /** 비즈니스 메서드 end */



    /** 유틸리티 메서드 start */
    public ScheduleEditorBuilder toEditor() {
        return ScheduleEditor.builder()
                .teacher(this.teacher)
                .time(this.time);
    }

    /** 유틸리티 메서드 end */
}

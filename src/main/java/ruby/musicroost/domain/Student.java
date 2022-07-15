package ruby.musicroost.domain;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import ruby.musicroost.domain.editor.StudentEditor;
import ruby.musicroost.domain.enums.Course;

import javax.persistence.*;
import java.time.LocalDate;

import static javax.persistence.FetchType.LAZY;
import static ruby.musicroost.domain.editor.StudentEditor.*;

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
    @CreatedDate
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

    /** 비즈니스 메서드 start */

    /**
     * 선생님 배정
     * @param teacher
     */
    public void assignTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    /**
     * 수강생 정보 수정
     * @param editor
     */
    public void edit(StudentEditor editor) {
        this.phoneNumber = editor.getPhoneNumber();
        this.email = editor.getEmail();
        this.course = editor.getCourse();
        this.teacher = editor.getTeacher();
    }

    /** 비즈니스 메서드 end */


    /** 유틸리티 메서드 start */

    public StudentEditorBuilder toEditor() {
        return StudentEditor.builder()
                .phoneNumber(this.phoneNumber)
                .email(this.email)
                .course(this.course)
                .teacher(this.teacher);
    }

    /** 유틸리티 메서드 end */
}

package ruby.musicroost.domain;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import ruby.musicroost.domain.editor.StudentEditor;
import ruby.musicroost.domain.enums.Course;
import ruby.musicroost.domain.enums.Grade;

import javax.persistence.*;
import java.time.LocalDate;

import static ruby.musicroost.domain.editor.StudentEditor.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(value = AuditingEntityListener.class)
@EqualsAndHashCode(of = "id")
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
    @Enumerated
    private Grade grade;
    @CreatedDate
    private LocalDate since;

    @Builder
    public Student(String name, String email, String phoneNumber, Course course, Grade grade) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.course = course;
        this.grade = grade;
    }

    /** 비즈니스 메서드 start */

    /**
     * 수강생 정보 수정
     * @param editor
     */
    public void edit(StudentEditor editor) {
        this.name = editor.getName();
        this.email = editor.getEmail();
        this.phoneNumber = editor.getPhoneNumber();
        this.course = editor.getCourse();
        this.grade = editor.getGrade();
    }

    /** 비즈니스 메서드 end */


    /** 유틸리티 메서드 start */

    public StudentEditorBuilder toEditor() {
        return StudentEditor.builder()
                .name(this.name)
                .email(this.email)
                .phoneNumber(this.phoneNumber)
                .course(this.course)
                .grade(this.grade);
    }

    /** 유틸리티 메서드 end */
}

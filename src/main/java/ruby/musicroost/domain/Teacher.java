package ruby.musicroost.domain;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import ruby.musicroost.domain.editor.TeacherEditor;
import ruby.musicroost.domain.enums.Course;

import javax.persistence.*;
import java.time.LocalDate;

import static ruby.musicroost.domain.editor.TeacherEditor.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(value = AuditingEntityListener.class)
@EqualsAndHashCode(of = "id")
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

    /** 비즈니스 메서드 end */

    /**
     * 선생님 정보 수정
     * @param editor
     */
    public void edit(TeacherEditor editor) {
        this.name = editor.getName();
        this.phoneNumber = editor.getPhoneNumber();
        this.email = editor.getEmail();
        this.course = editor.getCourse();
    }

    /** 비즈니스 메서드 end */


    /** 유틸리티 메서드 start */
    public TeacherEditorBuilder toEditor() {
        return TeacherEditor.builder()
                .name(this.name)
                .phoneNumber(this.phoneNumber)
                .email(this.email)
                .course(this.course);
    }
    /** 유틸리티 메서드 end */
}

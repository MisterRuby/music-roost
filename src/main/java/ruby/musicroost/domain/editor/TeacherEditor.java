package ruby.musicroost.domain.editor;

import lombok.Builder;
import lombok.Getter;
import ruby.musicroost.domain.enums.Course;

@Getter
public class TeacherEditor {

    private String name;
    private String email;
    private String phoneNumber;
    private Course course;

    @Builder
    public TeacherEditor(String name, String email, String phoneNumber, Course course) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.course = course;
    }
}

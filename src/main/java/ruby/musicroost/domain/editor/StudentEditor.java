package ruby.musicroost.domain.editor;

import lombok.Builder;
import lombok.Getter;
import ruby.musicroost.domain.Teacher;
import ruby.musicroost.domain.enums.Course;

@Getter
public class StudentEditor {
    private String name;
    private String email;
    private String phoneNumber;
    private Course course;
    private Teacher teacher;

    @Builder
    public StudentEditor(String name, String email, String phoneNumber, Course course, Teacher teacher) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.course = course;
        this.teacher = teacher;
    }
}

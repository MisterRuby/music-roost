package ruby.musicroost.domain.editor;

import lombok.Builder;
import lombok.Getter;
import ruby.musicroost.domain.Teacher;
import ruby.musicroost.domain.enums.Course;
import ruby.musicroost.domain.enums.Grade;

@Getter
public class StudentEditor {
    private String name;
    private String email;
    private String phoneNumber;
    private Course course;
    private Grade grade;

    @Builder
    public StudentEditor(String name, String email, String phoneNumber, Course course, Grade grade) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.course = course;
        this.grade = grade;
    }
}

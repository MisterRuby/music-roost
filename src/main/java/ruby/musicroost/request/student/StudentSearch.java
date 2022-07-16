package ruby.musicroost.request.student;

import lombok.Getter;
import lombok.Setter;
import ruby.musicroost.valid.CoursePattern;
import ruby.musicroost.valid.GradePattern;
import ruby.musicroost.valid.NamePattern;

@Getter @Setter
public class StudentSearch {

    @CoursePattern
    private String course;
    @GradePattern
    private String grade;
    @NamePattern
    private String name;
    private int page = 1;
}

package ruby.musicroost.request.student;

import lombok.Getter;
import lombok.Setter;
import ruby.musicroost.valid.CourseSearchPattern;
import ruby.musicroost.valid.GradeSearchPattern;

@Getter @Setter
public class StudentSearch {

    @CourseSearchPattern
    private String course;
    @GradeSearchPattern
    private String grade;
    private String name;
    private int page = 1;
}

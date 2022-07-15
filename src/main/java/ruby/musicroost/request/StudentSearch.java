package ruby.musicroost.request;

import lombok.Getter;
import lombok.Setter;
import ruby.musicroost.valid.CoursePattern;
import ruby.musicroost.valid.NamePattern;

@Getter @Setter
public class StudentSearch {

    @CoursePattern
    private String course;
    @NamePattern
    private String name;
    private int page = 1;
}
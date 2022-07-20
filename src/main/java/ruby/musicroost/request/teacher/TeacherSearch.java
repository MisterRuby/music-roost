package ruby.musicroost.request.teacher;

import lombok.Getter;
import lombok.Setter;
import ruby.musicroost.valid.CourseSearchPattern;
import ruby.musicroost.valid.NamePattern;

@Getter @Setter
public class TeacherSearch {
    @CourseSearchPattern
    private String course;
    private String name;
    private int page = 1;
}

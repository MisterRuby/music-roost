package ruby.musicroost.repository.custom;

import org.springframework.data.domain.Pageable;
import ruby.musicroost.domain.Student;
import ruby.musicroost.domain.enums.Course;
import ruby.musicroost.domain.enums.Grade;

import java.util.List;

public interface StudentRepositoryCustom {

    /**
     * 수강생 목록 조회
     * @param course
     * @param grade
     * @param name
     * @param pageable
     * @return
     */
    List<Student> findByCourseAndGradeAndNameContains(Course course, Grade grade, String name, Pageable pageable);
}

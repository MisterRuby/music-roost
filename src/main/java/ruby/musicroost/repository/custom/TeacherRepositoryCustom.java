package ruby.musicroost.repository.custom;

import org.springframework.data.domain.Pageable;
import ruby.musicroost.domain.Teacher;
import ruby.musicroost.domain.enums.Course;

import java.util.List;

public interface TeacherRepositoryCustom {

    /**
     * 선생님 목록 조회
     * @param course
     * @param name
     * @param pageable
     * @return
     */
    List<Teacher> findByCourseAndNameContains(Course course, String name, Pageable pageable);
}

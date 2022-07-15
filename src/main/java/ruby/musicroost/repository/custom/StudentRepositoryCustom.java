package ruby.musicroost.repository.custom;

import org.springframework.data.domain.Pageable;
import ruby.musicroost.domain.Student;
import ruby.musicroost.domain.enums.Course;

import java.util.List;
import java.util.Optional;

public interface StudentRepositoryCustom {

    /**
     * 수강생 상세 조회
     * @param id
     * @return
     */
    Optional<Student> findDetailById(Long id);

    /**
     * 수강생 목록 조회
     * @param course
     * @param name
     * @param pageable
     * @return
     */
    List<Student> findByCourseAndNameContains(Course course, String name, Pageable pageable);
}

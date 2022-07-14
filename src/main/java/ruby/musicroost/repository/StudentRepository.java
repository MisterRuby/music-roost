package ruby.musicroost.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ruby.musicroost.domain.Student;
import ruby.musicroost.domain.enums.Course;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {

    /**
     * 수강생 목록 조회
     * @param course
     * @param name
     * @param pageable
     * @return
     */
    List<Student> findByCourseAndNameContains(Course course, String name, Pageable pageable);
}

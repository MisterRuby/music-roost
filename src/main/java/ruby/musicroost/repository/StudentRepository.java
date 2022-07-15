package ruby.musicroost.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ruby.musicroost.domain.Student;
import ruby.musicroost.repository.custom.StudentRepositoryCustom;

public interface StudentRepository extends JpaRepository<Student, Long>, StudentRepositoryCustom {

//    /**
//     * 수강생 목록 조회
//     * @param course
//     * @param name
//     * @param pageable
//     * @return
//     */
//    List<Student> findByCourseAndNameContains(Course course, String name, Pageable pageable);
}

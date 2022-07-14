package ruby.musicroost.service;

import org.springframework.data.domain.Pageable;
import ruby.musicroost.domain.Student;
import ruby.musicroost.domain.enums.Course;

import java.util.List;

/**
 * 수강생 서비스
 */
public interface StudentService {

    // 수강생 등록
    // 수강생 목록 조회 (악기별로 조회하는 기능 고려)
    // 수강생 상세 조회
    // 수강생 정보 변경
    // 수강생 삭제 (바로 삭제 X, 추후 배치를 통해 일정 기간동안 수강하지 않을 때 삭제를 고려)

    /**
     * 수강생 등록
     * @param newStudent
     */
    void signUp(Student newStudent);

    /**
     * 수강생 정보 상세 조회
     * @param studentId
     * @return
     */
    Student inquireDetail(Long studentId);

    /**
     * 수강생 목록 조회
     * @param course
     * @param name
     * @param pageable
     * @return
     */
    List<Student> getList(Course course, String name, Pageable pageable);
}

package ruby.musicroost.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ruby.musicroost.domain.Student;
import ruby.musicroost.domain.enums.Course;
import ruby.musicroost.repository.StudentRepository;
import ruby.musicroost.service.StudentService;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    /**
     * 수강생 등록
     * @param newStudent
     */
    @Override
    public void signUp(Student newStudent) {
        newStudent.setSince(LocalDate.now());
        studentRepository.save(newStudent);
    }

    /**
     * 수강생 정보 상세 조회
     * @param studentId
     * @return
     */
    @Override
    public Student inquireDetail(Long studentId) {
        return studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원의 정보를 찾을 수 없습니다."));
    }

    /**
     * 수강생 목록 조회
     * @param course
     * @param name
     * @param pageable
     * @return
     */
    @Override
    public List<Student> getList(Course course, String name, Pageable pageable) {

        return studentRepository.findByCourseAndNameContains(course, name, pageable);
    }
}

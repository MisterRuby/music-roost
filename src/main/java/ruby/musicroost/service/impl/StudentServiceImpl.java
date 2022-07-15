package ruby.musicroost.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ruby.musicroost.domain.Student;
import ruby.musicroost.domain.Teacher;
import ruby.musicroost.domain.editor.StudentEditor;
import ruby.musicroost.domain.enums.Course;
import ruby.musicroost.repository.StudentRepository;
import ruby.musicroost.repository.TeacherRepository;
import ruby.musicroost.request.StudentEdit;
import ruby.musicroost.request.StudentSearch;
import ruby.musicroost.service.StudentService;

import java.util.List;

import static java.lang.Math.max;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;

    /**
     * 수강생 등록
     * @param newStudent
     */
    @Override
    public void signUp(Student newStudent) {
        studentRepository.save(newStudent);
    }

    /**
     * 수강생 정보 상세 조회
     * @param studentId
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public Student inquireDetail(Long studentId) {
        return studentRepository.findDetailById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("해당 수강생의 정보를 찾을 수 없습니다."));
    }

    /**
     * 수강생 목록 조회
     * @param search
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public List<Student> getList(StudentSearch search) {
        Pageable pageable = PageRequest.of(max(0, search.getPage() - 1), 10);
        return studentRepository
                .findByCourseAndNameContains(Course.valueOf(search.getCourse()), search.getName(), pageable);
    }

    /**
     * 수강생 정보 수정
     * @param studentId
     * @param studentEdit
     */
    @Override
    public void edit(Long studentId, StudentEdit studentEdit) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("해당 수강생의 정보를 찾을 수 없습니다."));

        Teacher teacher = teacherRepository.findById(studentEdit.getTeacherId())
                .orElseThrow(() -> new IllegalArgumentException("해당 선생님의 정보를 찾을 수 없습니다."));

        StudentEditor studentEditor = student.toEditor()
                .phoneNumber(studentEdit.getPhoneNumber())
                .email(studentEdit.getEmail())
                .course(Course.valueOf(studentEdit.getCourse()))
                .teacher(teacher)
                .build();

        student.edit(studentEditor);
    }
}
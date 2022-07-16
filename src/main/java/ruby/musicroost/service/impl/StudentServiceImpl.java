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
import ruby.musicroost.domain.enums.Grade;
import ruby.musicroost.exception.teacher.TeacherNotFoundException;
import ruby.musicroost.exception.student.StudentNotFoundException;
import ruby.musicroost.repository.StudentRepository;
import ruby.musicroost.repository.TeacherRepository;
import ruby.musicroost.request.student.StudentEdit;
import ruby.musicroost.request.student.StudentSearch;
import ruby.musicroost.service.StudentService;

import java.util.List;

import static java.lang.Math.max;

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
    public void register(Student newStudent) {
        studentRepository.save(newStudent);
    }

    /**
     * 수강생 정보 상세 조회
     * @param studentId
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public Student getDetail(Long studentId) {
        return studentRepository.findDetailById(studentId)
                .orElseThrow(StudentNotFoundException::new);
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
        return studentRepository.findByCourseAndGradeAndNameContains
                (Course.valueOf(search.getCourse()), Grade.valueOf(search.getGrade()), search.getName(), pageable);
    }

    /**
     * 수강생 정보 수정
     * @param studentId
     * @param studentEdit
     */
    @Override
    public void edit(Long studentId, StudentEdit studentEdit) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(StudentNotFoundException::new);

        Teacher teacher = teacherRepository.findById(studentEdit.getTeacherId())
                .orElseThrow(TeacherNotFoundException::new);

        StudentEditor studentEditor = student.toEditor()
                .name(studentEdit.getName())
                .phoneNumber(studentEdit.getPhoneNumber())
                .email(studentEdit.getEmail())
                .course(Course.valueOf(studentEdit.getCourse()))
                .teacher(teacher)
                .build();

        student.edit(studentEditor);
    }

    /**
     * 수강생 정보 삭제
     * @param studentId
     */
    @Override
    public void delete(Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(StudentNotFoundException::new);

        studentRepository.delete(student);
    }
}

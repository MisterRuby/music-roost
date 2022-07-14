package ruby.musicroost.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ruby.musicroost.domain.Student;
import ruby.musicroost.domain.enums.Course;
import ruby.musicroost.infra.valid.NamePattern;
import ruby.musicroost.infra.valid.CoursePattern;
import ruby.musicroost.request.StudentSignUp;
import ruby.musicroost.response.student.StudentResponse;
import ruby.musicroost.service.StudentService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.data.domain.Sort.Direction.DESC;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/students")
@Validated
public class StudentController {

    private final StudentService studentService;
    private final ModelMapper mapper;

    /**
     * 수강생 신규 등록
     * @param studentSignUp
     */
    @PostMapping
    public void post(@RequestBody @Valid StudentSignUp studentSignUp) {
        // mapper 를 통해 String -> enum 으로 변환 가
        Student student = mapper.map(studentSignUp, Student.class);
        studentService.signUp(student);
    }

    /**
     * 수강생 목록 조회
     * @param course
     * @param name
     * @param pageable
     * @return
     */
    @GetMapping
    public List<StudentResponse> getList(
            @CoursePattern String course,
            @NamePattern String name,
            @PageableDefault(sort = "id",  direction = DESC) Pageable pageable) {
        List<Student> students = studentService.getList(Course.valueOf(course), name, pageable);
        return students.stream().map(student -> mapper.map(student, StudentResponse.class))
                .collect(Collectors.toList());
    }

    /**
     * 수강생 상세 조회
     * @param studentId
     * @return
     */
    @GetMapping("/{studentId}")
    public StudentResponse get(@PathVariable Long studentId) {
        Student student = studentService.inquireDetail(studentId);
        return mapper.map(student, StudentResponse.class);
    }
}

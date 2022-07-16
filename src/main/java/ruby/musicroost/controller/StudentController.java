package ruby.musicroost.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;
import ruby.musicroost.domain.Student;
import ruby.musicroost.request.student.StudentEdit;
import ruby.musicroost.request.student.StudentSearch;
import ruby.musicroost.request.student.StudentRegister;
import ruby.musicroost.response.student.StudentResponse;
import ruby.musicroost.service.StudentService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;
    private final ModelMapper mapper;

    /**
     * 수강생 신규 등록
     * @param studentRegister
     */
    @PostMapping
    public void post(@RequestBody @Valid StudentRegister studentRegister) {
        Student student = mapper.map(studentRegister, Student.class);
        studentService.register(student);
    }

    /**
     * 수강생 목록 조회
     * @param search
     * @return
     */
    @GetMapping
    public List<StudentResponse> getList(@Valid StudentSearch search) {
        List<Student> students = studentService.getList(search);
        return students.stream().map(StudentResponse::new)
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
        return new StudentResponse(student);
    }

    /**
     * 수강생 정보 수정
     * @param studentId
     * @param studentEdit
     */
    @PatchMapping("/{studentId}")
    public void edit(@PathVariable Long studentId, @RequestBody @Valid StudentEdit studentEdit) {
        studentService.edit(studentId, studentEdit);
        // 업무에 따라 수정된 정보를 응답으로 보내줄지는 클라이언트 담당 쪽과 협의하여 처리
    }

    /**
     * 수강생 정보 삭제
     * @param studentId
     */
    @DeleteMapping("/{studentId}")
    public void delete(@PathVariable Long studentId) {
        studentService.delete(studentId);
    }
}
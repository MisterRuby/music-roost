package ruby.musicroost.controller;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;
import ruby.musicroost.domain.Teacher;
import ruby.musicroost.request.teacher.TeacherEdit;
import ruby.musicroost.request.teacher.TeacherRegister;
import ruby.musicroost.request.teacher.TeacherSearch;
import ruby.musicroost.response.teacher.TeacherResponse;
import ruby.musicroost.service.TeacherService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/teachers")
public class TeacherController {

    private final TeacherService teacherService;
    private final ModelMapper mapper;

    /**
     * 선생님 정보 등록
     * @param teacherRegister
     */
    @PostMapping
    public void register(@RequestBody @Valid TeacherRegister teacherRegister) {
        Teacher teacher = mapper.map(teacherRegister, Teacher.class);
        teacherService.register(teacher);
    }

    /**
     * 선생님 목록 조회
     * @param search
     * @return
     */
    @GetMapping
    public List<TeacherResponse> getList(@Valid TeacherSearch search) {
        List<Teacher> teachers = teacherService.getList(search);
        return teachers.stream()
                .map(TeacherResponse::new)
                .collect(Collectors.toList());
    }

    /**
     * 선생님 상세 조회
     * @param teacherId
     * @return
     */
    @GetMapping("/{teacherId}")
    public TeacherResponse get(@PathVariable Long teacherId) {
        Teacher teacher = teacherService.getDetail(teacherId);
        return new TeacherResponse(teacher);
    }

    /**
     * 선생님 정보 수정
     * @param teacherId
     * @param teacherEdit
     */
    @PatchMapping("/{teacherId}")
    public void edit(@PathVariable Long teacherId, @RequestBody @Valid TeacherEdit teacherEdit) {
        teacherService.edit(teacherId, teacherEdit);
    }

    /**
     * 선생님 정보 삭제
     * @param teacherId
     */
    @DeleteMapping("/{teacherId}")
    public void delete(@PathVariable Long teacherId) {
        teacherService.delete(teacherId);
    }
}
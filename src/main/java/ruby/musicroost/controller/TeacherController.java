package ruby.musicroost.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;
import ruby.musicroost.domain.Teacher;
import ruby.musicroost.request.teacher.TeacherSearch;
import ruby.musicroost.request.teacher.TeacherRegister;
import ruby.musicroost.response.teacher.TeacherResponse;
import ruby.musicroost.service.TeacherService;

import javax.validation.Valid;
import java.util.List;

@Slf4j
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
    public void post(@RequestBody @Valid TeacherRegister teacherRegister) {
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
        return null;
    }

    /**
     * 선생님 상세 조회
     * @param teacherId
     * @return
     */
    @GetMapping("/{teacherId}")
    public TeacherResponse get(@PathVariable Long teacherId) {
        return null;
    }
}

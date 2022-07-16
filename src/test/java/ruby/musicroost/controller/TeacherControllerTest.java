package ruby.musicroost.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import ruby.musicroost.domain.Teacher;
import ruby.musicroost.domain.enums.Course;
import ruby.musicroost.domain.enums.Grade;
import ruby.musicroost.exception.student.StudentNotFoundException;
import ruby.musicroost.exception.teacher.TeacherNotFoundException;
import ruby.musicroost.repository.TeacherRepository;
import ruby.musicroost.request.student.StudentRegister;
import ruby.musicroost.request.teacher.TeacherEdit;
import ruby.musicroost.request.teacher.TeacherRegister;
import ruby.musicroost.valid.CoursePattern;
import ruby.musicroost.valid.EmailPattern;
import ruby.musicroost.valid.NamePattern;
import ruby.musicroost.valid.PhonePattern;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.*;
import static org.hamcrest.Matchers.is;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ruby.musicroost.controller.ExceptionController.BIND_EXCEPTION_MESSAGE;

@AutoConfigureMockMvc
@SpringBootTest
class TeacherControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private ObjectMapper mapper;

    @BeforeEach
    void clean() {
        teacherRepository.deleteAll();
    }

    /** 등록 테스트 start */
    @Test
    @DisplayName("선생님 이름이 공백일 때 등록 실패")
    void registerByEmptyName() throws Exception {
        TeacherRegister teacher = TeacherRegister.builder()
                .name("     ")
                .email("rubykim0723@gmail.com")
                .phoneNumber("010-1111-2222")
                .course(Course.FLUTE.name())
                .build();

        mockMvc.perform(post("/teachers")
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(teacher))
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.message").value(BIND_EXCEPTION_MESSAGE))
                .andExpect(jsonPath("$.validation.name").value(NamePattern.MESSAGE))
                .andDo(print());
    }

    @Test
    @DisplayName("선생님 이름이 잘못된 형식일 때 등록 실패")
    void registerByWrongName() throws Exception {
        TeacherRegister teacher = TeacherRegister.builder()
                .name("qwe$%^")
                .email("rubykim0723@gmail.com")
                .phoneNumber("010-1111-2222")
                .course(Course.FLUTE.name())
                .build();

        mockMvc.perform(post("/teachers")
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(teacher))
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.message").value(BIND_EXCEPTION_MESSAGE))
                .andExpect(jsonPath("$.validation.name").value(NamePattern.MESSAGE))
                .andDo(print());
    }

    @Test
    @DisplayName("선생님 이메일이 잘못된 형식일 때 등록 실패")
    void registerByWrongEmail() throws Exception {
        TeacherRegister teacher = TeacherRegister.builder()
                .name("teacher")
                .email("rubykim0723gmail.com")
                .phoneNumber("010-1111-2222")
                .course(Course.FLUTE.name())
                .build();

        mockMvc.perform(post("/teachers")
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(teacher))
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.message").value(BIND_EXCEPTION_MESSAGE))
                .andExpect(jsonPath("$.validation.email").value(EmailPattern.MESSAGE))
                .andDo(print());
    }

    @Test
    @DisplayName("선생님 핸드폰 번호가 잘못된 형식일 때 등록 실패")
    void registerByWrongPhoneNumber() throws Exception {
        TeacherRegister teacher = TeacherRegister.builder()
                .name("teacher")
                .email("rubykim0723@gmail.com")
                .phoneNumber("010-1111-22")
                .course(Course.FLUTE.name())
                .build();

        mockMvc.perform(post("/teachers")
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(teacher))
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.message").value(BIND_EXCEPTION_MESSAGE))
                .andExpect(jsonPath("$.validation.phoneNumber").value(PhonePattern.MESSAGE))
                .andDo(print());
    }

    @Test
    @DisplayName("선생님 과목이 잘못된 형식일 때 등록 실패")
    void registerByWrongCourse() throws Exception {
        TeacherRegister teacher = TeacherRegister.builder()
                .name("teacher")
                .email("rubykim0723@gmail.com")
                .phoneNumber("010-1111-2222")
                .course("wrong")
                .build();

        mockMvc.perform(post("/teachers")
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(teacher))
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.message").value(BIND_EXCEPTION_MESSAGE))
                .andExpect(jsonPath("$.validation.course").value(CoursePattern.MESSAGE))
                .andDo(print());
    }

    @Test
    @DisplayName("선생님의 여러 정보가 잘못된 형식일 때 등록 실패")
    void registerByWrongValues() throws Exception {
        TeacherRegister teacher = TeacherRegister.builder()
                .name("teacher#@$")
                .email("rubykim0723gmail.com")
                .phoneNumber("010-1111-22")
                .course("wrong")
                .build();

        mockMvc.perform(post("/teachers")
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(teacher))
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.message").value(BIND_EXCEPTION_MESSAGE))
                .andExpect(jsonPath("$.validation.name").value(NamePattern.MESSAGE))
                .andExpect(jsonPath("$.validation.email").value(EmailPattern.MESSAGE))
                .andExpect(jsonPath("$.validation.phoneNumber").value(PhonePattern.MESSAGE))
                .andExpect(jsonPath("$.validation.course").value(CoursePattern.MESSAGE))
                .andDo(print());
    }

    @Test
    @DisplayName("선생님 정보 등록")
    void register() throws Exception {
        TeacherRegister teacher = TeacherRegister.builder()
                .name("teacher")
                .email("rubykim0723@gmail.com")
                .phoneNumber("010-1111-2222")
                .course(Course.VIOLA.name())
                .build();

        mockMvc.perform(post("/teachers")
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(teacher))
                )
                .andExpect(status().isOk())
                .andDo(print());

        List<Teacher> teachers = teacherRepository.findAll();
        assertThat(teachers.size()).isEqualTo(1);
    }

    /** 등록 테스트 end */


    /** 상세조회 start */
    @Test
    @DisplayName("존재하지 않는 선생님 정보 조회")
    void getTeacherByWrongId() throws Exception {
        Teacher teacher = Teacher.builder()
                .name("teacher")
                .email("rubykim0723@gmail.com")
                .phoneNumber("010-1111-2222")
                .course(Course.VIOLA)
                .build();
        teacherRepository.save(teacher);

        mockMvc.perform(get("/teachers/{teacherId}", teacher.getId() + 1))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    @DisplayName("선생님 정보 조회")
    void getTeacher() throws Exception {
        Teacher teacher = Teacher.builder()
                .name("teacher")
                .email("rubykim0723@gmail.com")
                .phoneNumber("010-1111-2222")
                .course(Course.VIOLA)
                .build();
        teacherRepository.save(teacher);

        mockMvc.perform(get("/teachers/{teacherId}", teacher.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(teacher.getId()))
                .andExpect(jsonPath("$.name").value(teacher.getName()))
                .andExpect(jsonPath("$.email").value(teacher.getEmail()))
                .andExpect(jsonPath("$.phoneNumber").value(teacher.getPhoneNumber()))
                .andExpect(jsonPath("$.course").value(teacher.getCourse().name()))
                .andExpect(jsonPath("$.since").value(teacher.getSince().format(DateTimeFormatter.ISO_LOCAL_DATE)))
                .andDo(print());
    }
    /** 상세조회 end */

    /** 목록조회 start */
    private List<Teacher> getTeachers() {
        List<Teacher> teachers = IntStream.range(0, 30)
                .mapToObj(idx -> Teacher.builder()
                        .name("teacher" + idx)
                        .email(idx + "teacher@gmail.com")
                        .phoneNumber("010-11" + idx + "-2222")
                        .course(idx < 15 ? Course.FLUTE : Course.VIOLIN)
                        .build())
                .collect(Collectors.toList());

        return teacherRepository.saveAll(teachers);
    }

    @Test
    @DisplayName("선생님 목록 잘못된 과목으로 조회")
    void getListByWrongCourse() throws Exception {
        getTeachers();

        mockMvc.perform(get("/teachers")
                        .param("course", "asd!@#")
                        .param("name", "teacher")
                        .param("page", "1")
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.message").value(BIND_EXCEPTION_MESSAGE))
                .andExpect(jsonPath("$.validation.course").value(CoursePattern.MESSAGE))
                .andDo(print());
    }

    @Test
    @DisplayName("선생님 목록 잘못된 이름으로 조회")
    void getListByWrongName() throws Exception {
        getTeachers();

        mockMvc.perform(get("/teachers")
                        .param("course", Course.FLUTE.name())
                        .param("name", "!@#")
                        .param("page", "1")
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.message").value(BIND_EXCEPTION_MESSAGE))
                .andExpect(jsonPath("$.validation.name").value(NamePattern.MESSAGE))
                .andDo(print());
    }

    @Test
    @DisplayName("선생님 목록 존재하지 않는 이름으로 조회")
    void getListByNoneName() throws Exception {
        getTeachers();

        mockMvc.perform(get("/teachers")
                        .param("course", Course.FLUTE.name())
                        .param("name", "ruby")
                        .param("page", "1")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(0)))
                .andDo(print());
    }

    @Test
    @DisplayName("선생님 목록 존재하지 않는 페이지 조회")
    void getListByNonePage() throws Exception {
        getTeachers();

        mockMvc.perform(get("/teachers")
                        .param("course", Course.FLUTE.name())
                        .param("name", "teacher")
                        .param("page", "3")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(0)))
                .andDo(print());
    }

    @Test
    @DisplayName("선생님 목록 조회")
    void getList() throws Exception {
        getTeachers();

        mockMvc.perform(get("/teachers")
                        .param("course", Course.FLUTE.name())
                        .param("name", "teacher")
                        .param("page", "2")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(5)))
                .andDo(print());
    }
    /** 목록조회 end */

    /** 수정 start */
    Teacher createTeacher() {
        Teacher teacher = Teacher.builder()
                .name("teacher")
                .email("rubykim0723@gmail.com")
                .phoneNumber("010-1111-2222")
                .course(Course.VIOLA)
                .build();
        return teacherRepository.save(teacher);
    }

    @Test
    @DisplayName("선생님 정보 잘못된 이름으로 수정")
    void editByWrongName() throws Exception {
        Teacher teacher = createTeacher();

        TeacherEdit teacherEdit = TeacherEdit.builder()
                .name("teacher1$@!#")
                .email("rubykim0724@gmail.com")
                .phoneNumber("010-1111-3333")
                .course(Course.VIOLIN.name())
                .build();

        mockMvc.perform(patch("/teachers/{teacherId}", teacher.getId())
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(teacherEdit))
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.message").value(BIND_EXCEPTION_MESSAGE))
                .andExpect(jsonPath("$.validation.name").value(NamePattern.MESSAGE))
                .andDo(print());
    }

    @Test
    @DisplayName("선생님 정보 잘못된 이메일로 수정")
    void editByWrongEmail() throws Exception {
        Teacher teacher = createTeacher();

        TeacherEdit teacherEdit = TeacherEdit.builder()
                .name("teacher1")
                .email("rubykim0724gmail.com")
                .phoneNumber("010-1111-3333")
                .course(Course.VIOLIN.name())
                .build();

        mockMvc.perform(patch("/teachers/{teacherId}", teacher.getId())
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(teacherEdit))
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.message").value(BIND_EXCEPTION_MESSAGE))
                .andExpect(jsonPath("$.validation.email").value(EmailPattern.MESSAGE))
                .andDo(print());
    }

    @Test
    @DisplayName("선생님 정보 잘못된 핸드폰 번호로 수정")
    void editByWrongPhoneNumber() throws Exception {
        Teacher teacher = createTeacher();

        TeacherEdit teacherEdit = TeacherEdit.builder()
                .name("teacher1")
                .email("rubykim0724@gmail.com")
                .phoneNumber("010-11-3333")
                .course(Course.VIOLIN.name())
                .build();

        mockMvc.perform(patch("/teachers/{teacherId}", teacher.getId())
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(teacherEdit))
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.message").value(BIND_EXCEPTION_MESSAGE))
                .andExpect(jsonPath("$.validation.phoneNumber").value(PhonePattern.MESSAGE))
                .andDo(print());
    }

    @Test
    @DisplayName("선생님 정보 잘못된 과목으로 수정")
    void editByWrongCourse() throws Exception {
        Teacher teacher = createTeacher();

        TeacherEdit teacherEdit = TeacherEdit.builder()
                .name("teacher1")
                .email("rubykim0724@gmail.com")
                .phoneNumber("010-1111-3333")
                .course("WRONG")
                .build();

        mockMvc.perform(patch("/teachers/{teacherId}", teacher.getId())
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(teacherEdit))
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.message").value(BIND_EXCEPTION_MESSAGE))
                .andExpect(jsonPath("$.validation.course").value(CoursePattern.MESSAGE))
                .andDo(print());
    }

    @Test
    @DisplayName("선생님 정보 수정")
    void edit() throws Exception {
        Teacher teacher = createTeacher();

        TeacherEdit teacherEdit = TeacherEdit.builder()
                .name("teacher1")
                .email("rubykim0724@gmail.com")
                .phoneNumber("010-1111-3333")
                .course(Course.VIOLIN.name())
                .build();

        mockMvc.perform(patch("/teachers/{teacherId}", teacher.getId())
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(teacherEdit))
                )
                .andExpect(status().isOk())
                .andDo(print());

        Teacher editTeacher = teacherRepository.findById(teacher.getId()).get();
        assertThat(editTeacher.getName()).isEqualTo(teacherEdit.getName());
        assertThat(editTeacher.getEmail()).isEqualTo(teacherEdit.getEmail());
        assertThat(editTeacher.getPhoneNumber()).isEqualTo(teacherEdit.getPhoneNumber());
        assertThat(editTeacher.getCourse()).isEqualTo(Course.valueOf(teacherEdit.getCourse()));
    }
    /** 수정 end */


    /** 삭제 start */
    @Test
    @DisplayName("선생님 없는 정보 삭제")
    void deleteTeacherByWrongId() throws Exception {
        Teacher teacher = createTeacher();

        mockMvc.perform(delete("/teachers/{teacherId}", teacher.getId() + 1))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value(HttpStatus.NOT_FOUND.value()))
                .andExpect(jsonPath("$.message").value(TeacherNotFoundException.MESSAGE))
                .andDo(print());
    }

    @Test
    @DisplayName("선생님 정보 삭제")
    void deleteTeacher() throws Exception {
        Teacher teacher = createTeacher();

        mockMvc.perform(delete("/teachers/{teacherId}", teacher.getId()))
                .andExpect(status().isOk())
                .andDo(print());

        List<Teacher> teachers = teacherRepository.findAll();
        assertThat(teachers.size()).isEqualTo(0);
    }
    /** 삭제 end */
}
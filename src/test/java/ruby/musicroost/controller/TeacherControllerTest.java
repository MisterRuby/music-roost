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
import ruby.musicroost.repository.TeacherRepository;
import ruby.musicroost.request.student.StudentRegister;
import ruby.musicroost.request.teacher.TeacherRegister;
import ruby.musicroost.valid.CoursePattern;
import ruby.musicroost.valid.EmailPattern;
import ruby.musicroost.valid.NamePattern;
import ruby.musicroost.valid.PhonePattern;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
}
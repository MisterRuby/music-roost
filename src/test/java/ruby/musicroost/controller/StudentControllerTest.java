package ruby.musicroost.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import ruby.musicroost.domain.Student;
import ruby.musicroost.domain.enums.Course;
import ruby.musicroost.repository.StudentRepository;
import ruby.musicroost.request.StudentSignUp;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private ObjectMapper mapper;

    @BeforeEach
    void clean() {
        studentRepository.deleteAll();
    }

    /** 등록 테스트 start */

    @Test
    @DisplayName("수강생 이름이 공백일 때 등록 실패")
    void emptyNameSignUp() throws Exception {
        StudentSignUp student = StudentSignUp.builder()
                .name("     ")
                .email("rubykim0723@gmail.com")
                .phoneNumber("010-1111-2222")
                .course("FLUTE")
                .build();

        mockMvc.perform(post("/students")
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(student))
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.message").value("잘못된 요청입니다."))
                .andExpect(jsonPath("$.validation.name").value("이름은 2~20자 한글, 영문 대소문자만 입력할 수 있습니다."))
                .andDo(print());
    }

    @Test
    @DisplayName("이메일이 형식에 맞지 않을 때 등록 실패")
    void wrongPatternEmailSignUp() throws Exception {
        StudentSignUp student = StudentSignUp.builder()
                .name("ruby")
                .email("rubykim0723gmail")
                .phoneNumber("010-1111-2222")
                .course("FLUTE")
                .build();

        mockMvc.perform(post("/students")
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(student))
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.message").value("잘못된 요청입니다."))
                .andExpect(jsonPath("$.validation.email").value("이메일 입력 형식이 올바르지 않습니다."))
                .andDo(print());
    }

    @Test
    @DisplayName("전화번호가 형식에 맞지 않을 때 등록 실패")
    void wrongPatternPhoneNumberSignUp() throws Exception {
        StudentSignUp student = StudentSignUp.builder()
                .name("ruby")
                .email("rubykim0723@gmail.com")
                .phoneNumber("010-11-2222")
                .course("FLUTE")
                .build();

        mockMvc.perform(post("/students")
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(student))
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.message").value("잘못된 요청입니다."))
                .andExpect(jsonPath("$.validation.phoneNumber").value("핸드폰 번호 입력 형식이 올바르지 않습니다."))
                .andDo(print());
    }

    @Test
    @DisplayName("수강과목 입력 값이 형식에 맞지 않을 때 등록 실패")
    void wrongPatternCourseNameSignUp() throws Exception {
        StudentSignUp student = StudentSignUp.builder()
                .name("ruby")
                .email("rubykim0723@gmail.com")
                .phoneNumber("010-111-2222")
                .course("flute")
                .build();

        mockMvc.perform(post("/students")
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(student))
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.message").value("잘못된 요청입니다."))
                .andExpect(jsonPath("$.validation.course").value("수강 과목이 올바르지 않습니다."))
                .andDo(print());
    }

    @Test
    @DisplayName("수강생 등록 성공")
    void signUp() throws Exception {
        StudentSignUp student = StudentSignUp.builder()
                .name("ruby")
                .email("rubykim0723@gmail.com")
                .phoneNumber("010-1111-2222")
                .course("PIANO")
                .build();

        mockMvc.perform(post("/students")
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(student))
                )
                .andExpect(status().isOk())
                .andDo(print());

        assertThat(studentRepository.count()).isEqualTo(1);

        Student findStudent = studentRepository.findAll().get(0);
        assertThat(findStudent.getName()).isEqualTo("ruby");
        assertThat(findStudent.getEmail()).isEqualTo("rubykim0723@gmail.com");
        assertThat(findStudent.getPhoneNumber()).isEqualTo("010-1111-2222");
        assertThat(findStudent.getCourse()).isEqualTo(Course.PIANO);
    }

    /** 등록 테스트 end */


    /** 상세조회 start */
    @Test
    @DisplayName("존재하지 않는 ID 로 수강생 상세조회")
    void inquireDetailByWrongId() throws Exception {
        Student student = Student.builder()
                .name("ruby")
                .email("rubykim0723@gmail.com")
                .phoneNumber("010-1111-2222")
                .course(Course.FLUTE)
                .build();
        studentRepository.save(student);

        mockMvc.perform(get("/students/{studentId}", 24938L)
                        .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    // TODO - Teacher 등록 작업이 완료되면 추가 작업 필요
    @Test
    @DisplayName("수강생 상세조회")
    void inquireDetail() throws Exception {
        Student student = Student.builder()
                .name("ruby")
                .email("rubykim0723@gmail.com")
                .phoneNumber("010-1111-2222")
                .course(Course.FLUTE)
                .since(LocalDate.now())
                .build();
        studentRepository.save(student);

        mockMvc.perform(get("/students/{studentId}", student.getId())
                        .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(student.getId()))
                .andExpect(jsonPath("$.name").value(student.getName()))
                .andExpect(jsonPath("$.email").value(student.getEmail()))
                .andExpect(jsonPath("$.phoneNumber").value(student.getPhoneNumber()))
                .andExpect(jsonPath("$.course").value(student.getCourse().name()))
                .andExpect(jsonPath("$.since").value(student.getSince().format(DateTimeFormatter.ISO_LOCAL_DATE)))
                .andDo(print());
    }

    /** 상세조회 end */


    /** 목록조회 start */
    private List<Student> getStudents() {
        List<Student> students = IntStream.range(0, 30)
                .mapToObj(idx -> Student.builder()
                        .name("ruby" + idx)
                        .email(idx + "rubykim0723@gmail.com")
                        .phoneNumber("010-11" + idx + "-2222")
                        .course(idx < 15 ? Course.FLUTE : Course.VIOLIN)
                        .since(LocalDate.now())
                        .build())
                .collect(Collectors.toList());
        return students;
    }

    @Test
    @DisplayName("수강생 목록 수강 과목으로 없는 페이지 조회")
    void inquireListPagingByWrongPage() throws Exception {
        List<Student> students = getStudents();

        studentRepository.saveAll(students);

        mockMvc.perform(get("/students")
                        .param("course", "FLUTE")
                        .param("name", "ruby")
                        .param("page", "3")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(0)))
                .andDo(print());
    }

    @Test
    @DisplayName("수강생 목록 없는 이름으로 조회")
    void inquireListPagingByWrongName() throws Exception {
        List<Student> students = getStudents();

        studentRepository.saveAll(students);

        mockMvc.perform(get("/students")
                        .param("course", "FLUTE")
                        .param("name", "asdsadsa")
                        .param("page", "1")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(0)))
                .andDo(print());
    }

    @Test
    @DisplayName("수강생 목록 잘못된 옵션으로 조회")
    void inquireListPagingByWrongOption() throws Exception {
        List<Student> students = getStudents();

        studentRepository.saveAll(students);

        mockMvc.perform(get("/students")
                        .param("course", "WRONG")
                        .param("name", "rub")
                        .param("page", "1")
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.message").value("수강 과목이 올바르지 않습니다."))
                .andDo(print());
    }

    @Test
    @DisplayName("수강생 목록 수강 과목으로 페이징 조회")
    void inquireListPagingByCourse() throws Exception {
        List<Student> students = getStudents();

        studentRepository.saveAll(students);

        mockMvc.perform(get("/students")
                        .param("course", "FLUTE")
                        .param("name", "ruby")
                        .param("page", "2")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(5)))
                .andExpect(jsonPath("$[0].id").value(students.get(4).getId()))
                .andExpect(jsonPath("$[0].email").value(students.get(4).getEmail()))
                .andExpect(jsonPath("$[0].phoneNumber").value(students.get(4).getPhoneNumber()))
                .andExpect(jsonPath("$[0].course").value(students.get(4).getCourse().name()))
                .andExpect(jsonPath("$[0].since").value(students.get(4).getSince().format(DateTimeFormatter.ISO_LOCAL_DATE)))
                .andDo(print());
    }

    /** 목록조회 end */
}
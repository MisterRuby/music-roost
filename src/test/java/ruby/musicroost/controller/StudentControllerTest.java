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
import ruby.musicroost.domain.Teacher;
import ruby.musicroost.domain.enums.Course;
import ruby.musicroost.exception.TeacherNotFoundException;
import ruby.musicroost.exception.common.IllegalEmailException;
import ruby.musicroost.exception.common.IllegalNameException;
import ruby.musicroost.exception.common.IllegalPhoneException;
import ruby.musicroost.exception.student.IllegalCourseException;
import ruby.musicroost.exception.student.StudentNotFoundException;
import ruby.musicroost.repository.StudentRepository;
import ruby.musicroost.repository.TeacherRepository;
import ruby.musicroost.request.StudentEdit;
import ruby.musicroost.request.StudentSignUp;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
    private TeacherRepository teacherRepository;
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
                .course(Course.FLUTE.name())
                .build();

        mockMvc.perform(post("/students")
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(student))
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.message").value(ExceptionController.BAD_REQUEST_MESSAGE))
                .andExpect(jsonPath("$.validation.name").value(IllegalNameException.MESSAGE))
                .andDo(print());
    }

    @Test
    @DisplayName("이메일이 형식에 맞지 않을 때 등록 실패")
    void wrongPatternEmailSignUp() throws Exception {
        StudentSignUp student = StudentSignUp.builder()
                .name("ruby")
                .email("rubykim0723gmail")
                .phoneNumber("010-1111-2222")
                .course(Course.FLUTE.name())
                .build();

        mockMvc.perform(post("/students")
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(student))
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.message").value(ExceptionController.BAD_REQUEST_MESSAGE))
                .andExpect(jsonPath("$.validation.email").value(IllegalEmailException.MESSAGE))
                .andDo(print());
    }

    @Test
    @DisplayName("전화번호가 형식에 맞지 않을 때 등록 실패")
    void wrongPatternPhoneNumberSignUp() throws Exception {
        StudentSignUp student = StudentSignUp.builder()
                .name("ruby")
                .email("rubykim0723@gmail.com")
                .phoneNumber("010-11-2222")
                .course(Course.FLUTE.name())
                .build();

        mockMvc.perform(post("/students")
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(student))
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.message").value(ExceptionController.BAD_REQUEST_MESSAGE))
                .andExpect(jsonPath("$.validation.phoneNumber").value(IllegalPhoneException.MESSAGE))
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
                .andExpect(jsonPath("$.message").value(ExceptionController.BAD_REQUEST_MESSAGE))
                .andExpect(jsonPath("$.validation.course").value(IllegalCourseException.MESSAGE))
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

        mockMvc.perform(get("/students/{studentId}", student.getId() + 1)
                        .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

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

    // 테스트데이터 셋팅
    private List<Teacher> getTeachers() {
        List<Teacher> teachers = IntStream.range(0, 2)
                .mapToObj(idx -> Teacher.builder()
                        .name("teacher" + idx)
                        .build())
                .collect(Collectors.toList());

        return teacherRepository.saveAll(teachers);
    }
    private List<Student> getStudents() {
        List<Teacher> teachers = getTeachers();

        List<Student> students = IntStream.range(0, 30)
                .mapToObj(idx -> {
                    Student student = Student.builder()
                            .name("ruby" + idx)
                            .email(idx + "rubykim0723@gmail.com")
                            .phoneNumber("010-11" + idx + "-2222")
                            .course(idx < 15 ? Course.FLUTE : Course.VIOLIN)
                            .since(LocalDate.now())
                            .build();
                    student.assignTeacher(teachers.get(idx % 2));
                    return student;
                })
                .collect(Collectors.toList());

        return studentRepository.saveAll(students);
    }

    @Test
    @DisplayName("수강생 목록 수강 과목으로 없는 페이지 조회")
    void inquireListPagingByWrongPage() throws Exception {
        getStudents();

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
        getStudents();

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
        getStudents();

        mockMvc.perform(get("/students")
                        .param("course", "WRONG")
                        .param("name", "rub")
                        .param("page", "1")
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.message").value(ExceptionController.BAD_REQUEST_MESSAGE))
                .andExpect(jsonPath("$.validation.course").value(IllegalCourseException.MESSAGE))
                .andDo(print());
    }

    @Test
    @DisplayName("수강생 목록 수강 과목으로 페이징 조회")
    void inquireListPagingByCourse() throws Exception {
        List<Student> students = getStudents();

        mockMvc.perform(get("/students")
                        .param("course", "FLUTE")
                        .param("name", "ruby")
                        .param("page", "2")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(5)))
                .andExpect(jsonPath("$[0].id").value(students.get(4).getId()))
                .andExpect(jsonPath("$[0].name").value(students.get(4).getName()))
                .andExpect(jsonPath("$[0].email").value(students.get(4).getEmail()))
                .andExpect(jsonPath("$[0].phoneNumber").value(students.get(4).getPhoneNumber()))
                .andExpect(jsonPath("$[0].course").value(students.get(4).getCourse().name()))
                .andExpect(jsonPath("$[0].since").value(students.get(4).getSince().format(DateTimeFormatter.ISO_LOCAL_DATE)))
                .andExpect(jsonPath("$[0].teacherId").value(students.get(4).getTeacher().getId()))
                .andExpect(jsonPath("$[0].teacherName").value(students.get(4).getTeacher().getName()))
                .andDo(print());
   }

    @Test
    @DisplayName("수강생 목록 수강 과목 조회시 페이지 번호 없이 조회 - 첫 페이지 조회")
    void inquireListPagingByNonePage() throws Exception {
        List<Student> students = getStudents();

        mockMvc.perform(get("/students")
                        .param("course", "FLUTE")
                        .param("name", "ruby")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(10)))
                .andExpect(jsonPath("$[0].id").value(students.get(14).getId()))
                .andExpect(jsonPath("$[0].name").value(students.get(14).getName()))
                .andExpect(jsonPath("$[0].email").value(students.get(14).getEmail()))
                .andExpect(jsonPath("$[0].phoneNumber").value(students.get(14).getPhoneNumber()))
                .andExpect(jsonPath("$[0].course").value(students.get(14).getCourse().name()))
                .andExpect(jsonPath("$[0].since").value(students.get(14).getSince().format(DateTimeFormatter.ISO_LOCAL_DATE)))
                .andExpect(jsonPath("$[0].teacherId").value(students.get(14).getTeacher().getId()))
                .andExpect(jsonPath("$[0].teacherName").value(students.get(14).getTeacher().getName()))
                .andDo(print());
    }

    @Test
    @DisplayName("수강생 목록 수강 과목으로 조회시 페이지 번호 1 이하로 조회 - 첫 페이지 조회")
    void inquireListPagingByUnderPage() throws Exception {
        List<Student> students = getStudents();

        mockMvc.perform(get("/students")
                        .param("course", "FLUTE")
                        .param("name", "ruby")
                        .param("page", "0")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(10)))
                .andExpect(jsonPath("$[0].id").value(students.get(14).getId()))
                .andExpect(jsonPath("$[0].name").value(students.get(14).getName()))
                .andExpect(jsonPath("$[0].email").value(students.get(14).getEmail()))
                .andExpect(jsonPath("$[0].phoneNumber").value(students.get(14).getPhoneNumber()))
                .andExpect(jsonPath("$[0].course").value(students.get(14).getCourse().name()))
                .andExpect(jsonPath("$[0].since").value(students.get(14).getSince().format(DateTimeFormatter.ISO_LOCAL_DATE)))
                .andExpect(jsonPath("$[0].teacherId").value(students.get(14).getTeacher().getId()))
                .andExpect(jsonPath("$[0].teacherName").value(students.get(14).getTeacher().getName()))
                .andDo(print());
    }

    /** 목록조회 end */


    /** 수정 테스트 start */
    Teacher getTeacher() {
        Teacher teacher = Teacher.builder()
                .name("eun")
                .build();
        return teacherRepository.save(teacher);
    }

    Student getStudent() {
        Student student = Student.builder()
                .name("ruby")
                .email("rubykim0723@gmail.com")
                .phoneNumber("010-1111-2222")
                .course(Course.FLUTE)
                .since(LocalDate.now())
                .build();
        return studentRepository.save(student);
    }

    @Test
    @DisplayName("수강생 정보가 없는 id로 수정")
    void editStudentByWrongId() throws Exception {
        Teacher teacher = getTeacher();
        Student student = getStudent();

        StudentEdit studentEdit = StudentEdit.builder()
                .phoneNumber("010-2222-3333")
                .email("rubykim0724@gmail.com")
                .course(Course.VIOLIN.name())
                .teacherId(teacher.getId())
                .build();

        mockMvc.perform(patch("/students/{studentId}", student.getId() + 1)
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(studentEdit))
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.message").value(StudentNotFoundException.MESSAGE))
                .andDo(print());
    }

    @Test
    @DisplayName("수강생의 선생님을 존재하지 않는 선생님으로 수정")
    void editStudentByWrongTeacherId() throws Exception {
        getTeacher();
        Student student = getStudent();

        StudentEdit studentEdit = StudentEdit.builder()
                .phoneNumber("010-2222-3333")
                .email("rubykim0724@gmail.com")
                .course(Course.VIOLIN.name())
                .teacherId(123L)
                .build();

        mockMvc.perform(patch("/students/{studentId}", student.getId())
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(studentEdit))
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.message").value(TeacherNotFoundException.MESSAGE))
                .andDo(print());
    }

    @Test
    @DisplayName("수강생의 전화번호를 잘못된 형식으로 수정")
    void editStudentByWrongPhoneNumber() throws Exception {
        Teacher teacher = getTeacher();
        Student student = getStudent();

        StudentEdit studentEdit = StudentEdit.builder()
                .phoneNumber("421-222-3333")
                .email("rubykim0724@gmail.com")
                .course(Course.VIOLIN.name())
                .teacherId(teacher.getId())
                .build();

        mockMvc.perform(patch("/students/{studentId}", student.getId())
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(studentEdit))
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.message").value(ExceptionController.BAD_REQUEST_MESSAGE))
                .andExpect(jsonPath("$.validation.phoneNumber").value(IllegalPhoneException.MESSAGE))
                .andDo(print());
    }

    @Test
    @DisplayName("수강생의 이메일을 잘못된 형식으로 수정")
    void editStudentByWrongEmail() throws Exception {
        Teacher teacher = getTeacher();
        Student student = getStudent();

        StudentEdit studentEdit = StudentEdit.builder()
                .phoneNumber("010-222-3333")
                .email("rubykim0724gmail.com")
                .course(Course.VIOLIN.name())
                .teacherId(teacher.getId())
                .build();

        mockMvc.perform(patch("/students/{studentId}", student.getId())
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(studentEdit))
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.message").value(ExceptionController.BAD_REQUEST_MESSAGE))
                .andExpect(jsonPath("$.validation.email").value(IllegalEmailException.MESSAGE))
                .andDo(print());
    }

    @Test
    @DisplayName("수강생의 수강과목을 잘못된 값으로 수정")
    void editStudentByWrongCourse() throws Exception {
        Teacher teacher = getTeacher();
        Student student = getStudent();

        StudentEdit studentEdit = StudentEdit.builder()
                .phoneNumber("010-222-3333")
                .email("rubykim0724@gmail.com")
                .course("DRUM")
                .teacherId(teacher.getId())
                .build();

        mockMvc.perform(patch("/students/{studentId}", student.getId())
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(studentEdit))
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.message").value(ExceptionController.BAD_REQUEST_MESSAGE))
                .andExpect(jsonPath("$.validation.course").value(IllegalCourseException.MESSAGE))
                .andDo(print());
    }

    @Test
    @DisplayName("수강생 정보 수정")
    void editStudent() throws Exception {
        Teacher teacher = getTeacher();
        Student student = getStudent();

        StudentEdit studentEdit = StudentEdit.builder()
                        .phoneNumber("010-2222-3333")
                        .email("rubykim0724@gmail.com")
                        .course(Course.VIOLIN.name())
                        .teacherId(teacher.getId())
                        .build();

        mockMvc.perform(patch("/students/{studentId}", student.getId())
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(studentEdit))
                )
                .andExpect(status().isOk())
                .andDo(print());

        assertThat(studentRepository.count()).isEqualTo(1);

        Student findStudent = studentRepository.findDetailById(student.getId()).get();
        assertThat(findStudent.getName()).isEqualTo("ruby");
        assertThat(findStudent.getEmail()).isEqualTo(studentEdit.getEmail());
        assertThat(findStudent.getPhoneNumber()).isEqualTo(studentEdit.getPhoneNumber());
        assertThat(findStudent.getCourse()).isEqualTo(Course.valueOf(studentEdit.getCourse()));
        assertThat(findStudent.getTeacher().getId()).isEqualTo(teacher.getId());
        assertThat(findStudent.getTeacher().getName()).isEqualTo(teacher.getName());
    }

    /** 수정 테스트 end */

    /** 삭제 테스트 start */
    @Test
    @DisplayName("존재하지 않는 수강생 정보 삭제")
    void deleteStudentByWrongId() throws Exception {
        Student student = getStudent();

        mockMvc.perform(delete("/students/{studentId}", student.getId() + 1))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.message").value(StudentNotFoundException.MESSAGE))
                .andDo(print());

        assertThat(studentRepository.count()).isEqualTo(1);
    }

    @Test
    @DisplayName("수강생 정보 삭제")
    void deleteStudent() throws Exception {
        Student student = getStudent();

        mockMvc.perform(delete("/students/{studentId}", student.getId()))
                .andExpect(status().isOk())
                .andDo(print());

        assertThat(studentRepository.count()).isEqualTo(0);
    }
}
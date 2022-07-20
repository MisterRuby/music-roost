package ruby.musicroost.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import ruby.musicroost.domain.Student;
import ruby.musicroost.domain.enums.Course;
import ruby.musicroost.domain.enums.Grade;
import ruby.musicroost.exception.student.StudentNotFoundException;
import ruby.musicroost.request.student.StudentEdit;
import ruby.musicroost.request.student.StudentRegister;
import ruby.musicroost.valid.*;

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
import static ruby.musicroost.controller.ExceptionController.BIND_EXCEPTION_MESSAGE;

class StudentControllerTest extends ControllerTest {

    /** 등록 테스트 start */

    @Test
    @DisplayName("수강생 이름이 공백일 때 등록 실패")
    void emptyNameSignUp() throws Exception {
        StudentRegister student = StudentRegister.builder()
                .name("     ")
                .email("rubykim0723@gmail.com")
                .phoneNumber("010-1111-2222")
                .course(Course.FLUTE.name())
                .grade(Grade.BEGINNER.name())
                .build();

        mockMvc.perform(post("/students")
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(student))
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.message").value(BIND_EXCEPTION_MESSAGE))
                .andExpect(jsonPath("$.validation.name").value(NamePattern.MESSAGE))
                .andDo(print());
    }

    @Test
    @DisplayName("수강생 이름을 잘못된 형식으로 등록 실패")
    void wrongNameSignUp() throws Exception {
        StudentRegister student = StudentRegister.builder()
                .name("qwed$%")
                .email("rubykim0723@gmail.com")
                .phoneNumber("010-1111-2222")
                .course(Course.FLUTE.name())
                .grade(Grade.BEGINNER.name())
                .build();

        mockMvc.perform(post("/students")
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(student))
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.message").value(BIND_EXCEPTION_MESSAGE))
                .andExpect(jsonPath("$.validation.name").value(NamePattern.MESSAGE))
                .andDo(print());
    }

    @Test
    @DisplayName("이메일이 형식에 맞지 않을 때 등록 실패")
    void wrongPatternEmailSignUp() throws Exception {
        StudentRegister student = StudentRegister.builder()
                .name("ruby")
                .email("rubykim0723gmail")
                .phoneNumber("010-1111-2222")
                .course(Course.FLUTE.name())
                .grade(Grade.BEGINNER.name())
                .build();

        mockMvc.perform(post("/students")
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(student))
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.message").value(BIND_EXCEPTION_MESSAGE))
                .andExpect(jsonPath("$.validation.email").value(EmailPattern.MESSAGE))
                .andDo(print());
    }

    @Test
    @DisplayName("전화번호가 형식에 맞지 않을 때 등록 실패")
    void wrongPatternPhoneNumberSignUp() throws Exception {
        StudentRegister student = StudentRegister.builder()
                .name("ruby")
                .email("rubykim0723@gmail.com")
                .phoneNumber("010-11-2222")
                .course(Course.FLUTE.name())
                .grade(Grade.BEGINNER.name())
                .build();

        mockMvc.perform(post("/students")
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(student))
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.message").value(BIND_EXCEPTION_MESSAGE))
                .andExpect(jsonPath("$.validation.phoneNumber").value(PhonePattern.MESSAGE))
                .andDo(print());
    }

    @Test
    @DisplayName("수강과목 입력 값이 형식에 맞지 않을 때 등록 실패")
    void wrongPatternCourseNameSignUp() throws Exception {
        StudentRegister student = StudentRegister.builder()
                .name("ruby")
                .email("rubykim0723@gmail.com")
                .phoneNumber("010-111-2222")
                .course("flute")
                .grade(Grade.BEGINNER.name())
                .build();

        mockMvc.perform(post("/students")
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(student))
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.message").value(BIND_EXCEPTION_MESSAGE))
                .andExpect(jsonPath("$.validation.course").value(CoursePattern.MESSAGE))
                .andDo(print());
    }

    @Test
    @DisplayName("등급 입력 값이 형식에 맞지 않을 때 등록 실패")
    void wrongPatternGradeNameSignUp() throws Exception {
        StudentRegister student = StudentRegister.builder()
                .name("ruby")
                .email("rubykim0723@gmail.com")
                .phoneNumber("010-111-2222")
                .course(Course.VIOLA.name())
                .grade("asd")
                .build();

        mockMvc.perform(post("/students")
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(student))
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.message").value(BIND_EXCEPTION_MESSAGE))
                .andExpect(jsonPath("$.validation.grade").value(GradePattern.MESSAGE))
                .andDo(print());
    }

    @Test
    @DisplayName("수강생 등록 성공")
    void signUp() throws Exception {
        StudentRegister student = StudentRegister.builder()
                .name("ruby")
                .email("rubykim0723@gmail.com")
                .phoneNumber("010-1111-2222")
                .course(Course.PIANO.name())
                .grade(Grade.BEGINNER.name())
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
                .grade(Grade.BEGINNER)
                .build();
        studentRepository.save(student);

        mockMvc.perform(get("/students/{studentId}", student.getId() + 1))
                .andExpect(status().isNotFound())
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
                .grade(Grade.BEGINNER)
                .build();
        studentRepository.save(student);

        mockMvc.perform(get("/students/{studentId}", student.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(student.getId()))
                .andExpect(jsonPath("$.name").value(student.getName()))
                .andExpect(jsonPath("$.email").value(student.getEmail()))
                .andExpect(jsonPath("$.phoneNumber").value(student.getPhoneNumber()))
                .andExpect(jsonPath("$.course").value(student.getCourse().name()))
                .andExpect(jsonPath("$.grade").value(student.getGrade().name()))
                .andExpect(jsonPath("$.since").value(student.getSince().format(DateTimeFormatter.ISO_LOCAL_DATE)))
                .andDo(print());
    }

    /** 상세조회 end */


    /** 목록조회 start */

    // 테스트데이터 셋팅
    private List<Student> getStudents() {
        List<Student> students = IntStream.range(0, 30)
                .mapToObj(idx -> Student.builder()
                            .name("ruby" + idx)
                            .email(idx + "rubykim0723@gmail.com")
                            .phoneNumber("010-11" + idx + "-2222")
                            .course(idx < 15 ? Course.FLUTE : Course.VIOLIN)
                            .grade(idx < 15 ? Grade.BEGINNER : Grade.ADVANCED)
                            .build())
                .collect(Collectors.toList());

        return studentRepository.saveAll(students);
    }

    @Test
    @DisplayName("수강생 목록 수강 과목으로 없는 페이지 조회")
    void inquireListPagingByWrongPage() throws Exception {
        getStudents();

        mockMvc.perform(get("/students")
                        .param("course", Course.FLUTE.name())
                        .param("grade", Grade.BEGINNER.name())
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
                        .param("course", Course.FLUTE.name())
                        .param("grade", Grade.BEGINNER.name())
                        .param("name", "asdsadsa")
                        .param("page", "1")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(0)))
                .andDo(print());
    }

    @Test
    @DisplayName("수강생 목록 잘못된 수강과목으로 조회")
    void inquireListPagingByWrongCourse() throws Exception {
        getStudents();

        mockMvc.perform(get("/students")
                        .param("course", "WRONG")
                        .param("grade", Grade.BEGINNER.name())
                        .param("name", "rub")
                        .param("page", "1")
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.message").value(BIND_EXCEPTION_MESSAGE))
                .andExpect(jsonPath("$.validation.course").value(CoursePattern.MESSAGE))
                .andDo(print());
    }

    @Test
    @DisplayName("수강생 목록 잘못된 등급 조회")
    void inquireListPagingByWrongGrade() throws Exception {
        getStudents();

        mockMvc.perform(get("/students")
                        .param("course", Course.FLUTE.name())
                        .param("grade", "asdasd")
                        .param("name", "rub")
                        .param("page", "1")
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.message").value(BIND_EXCEPTION_MESSAGE))
                .andExpect(jsonPath("$.validation.grade").value(GradePattern.MESSAGE))
                .andDo(print());
    }

    @Test
    @DisplayName("수강생 목록 수강 과목으로 페이징 조회")
    void inquireListPagingByCourse() throws Exception {
        List<Student> students = getStudents();

        mockMvc.perform(get("/students")
                        .param("course", Course.FLUTE.name())
                        .param("grade", Grade.BEGINNER.name())
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
                .andExpect(jsonPath("$[0].grade").value(students.get(4).getGrade().name()))
                .andExpect(jsonPath("$[0].since").value(students.get(4).getSince().format(DateTimeFormatter.ISO_LOCAL_DATE)))
                .andDo(print());
    }

    @Test
    @DisplayName("수강생 목록 수강 과목 조회시 페이지 번호 없이 조회 - 첫 페이지 조회")
    void inquireListPagingByNonePage() throws Exception {
        List<Student> students = getStudents();

        mockMvc.perform(get("/students")
                        .param("course", Course.FLUTE.name())
                        .param("grade", Grade.BEGINNER.name())
                        .param("name", "ruby")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(10)))
                .andExpect(jsonPath("$[0].id").value(students.get(14).getId()))
                .andExpect(jsonPath("$[0].name").value(students.get(14).getName()))
                .andExpect(jsonPath("$[0].email").value(students.get(14).getEmail()))
                .andExpect(jsonPath("$[0].phoneNumber").value(students.get(14).getPhoneNumber()))
                .andExpect(jsonPath("$[0].course").value(students.get(14).getCourse().name()))
                .andExpect(jsonPath("$[0].grade").value(students.get(14).getGrade().name()))
                .andExpect(jsonPath("$[0].since").value(students.get(14).getSince().format(DateTimeFormatter.ISO_LOCAL_DATE)))
                .andDo(print());
    }

    @Test
    @DisplayName("수강생 목록 수강 과목으로 조회시 페이지 번호 1 이하로 조회 - 첫 페이지 조회")
    void inquireListPagingByUnderPage() throws Exception {
        List<Student> students = getStudents();

        mockMvc.perform(get("/students")
                        .param("course", Course.FLUTE.name())
                        .param("grade", Grade.BEGINNER.name())
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
                .andExpect(jsonPath("$[0].grade").value(students.get(14).getGrade().name()))
                .andExpect(jsonPath("$[0].since").value(students.get(14).getSince().format(DateTimeFormatter.ISO_LOCAL_DATE)))
                .andDo(print());
    }

    /** 목록조회 end */


    /** 수정 테스트 start */
    Student getStudent() {
        Student student = Student.builder()
                .name("ruby")
                .email("rubykim0723@gmail.com")
                .phoneNumber("010-1111-2222")
                .course(Course.FLUTE)
                .build();
        return studentRepository.save(student);
    }

    @Test
    @DisplayName("수강생 정보가 없는 id로 수정")
    void editStudentByWrongId() throws Exception {
        Student student = getStudent();

        StudentEdit studentEdit = StudentEdit.builder()
                .name("ruby")
                .phoneNumber("010-2222-3333")
                .email("rubykim0724@gmail.com")
                .course(Course.VIOLIN.name())
                .grade(Grade.ADVANCED.name())
                .build();

        mockMvc.perform(patch("/students/{studentId}", student.getId() + 1)
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(studentEdit))
                )
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value(HttpStatus.NOT_FOUND.value()))
                .andExpect(jsonPath("$.message").value(StudentNotFoundException.MESSAGE))
                .andDo(print());
    }

    @Test
    @DisplayName("수강생의 전화번호를 잘못된 형식으로 수정")
    void editStudentByWrongPhoneNumber() throws Exception {
        Student student = getStudent();

        StudentEdit studentEdit = StudentEdit.builder()
                .name("ruby")
                .phoneNumber("421-222-3333")
                .email("rubykim0724@gmail.com")
                .course(Course.VIOLIN.name())
                .grade(Grade.ADVANCED.name())
                .build();

        mockMvc.perform(patch("/students/{studentId}", student.getId())
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(studentEdit))
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.message").value(BIND_EXCEPTION_MESSAGE))
                .andExpect(jsonPath("$.validation.phoneNumber").value(PhonePattern.MESSAGE))
                .andDo(print());
    }

    @Test
    @DisplayName("수강생의 이메일을 잘못된 형식으로 수정")
    void editStudentByWrongEmail() throws Exception {
        Student student = getStudent();

        StudentEdit studentEdit = StudentEdit.builder()
                .name("ruby")
                .phoneNumber("010-222-3333")
                .email("rubykim0724gmail.com")
                .course(Course.VIOLIN.name())
                .grade(Grade.ADVANCED.name())
                .build();

        mockMvc.perform(patch("/students/{studentId}", student.getId())
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(studentEdit))
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.message").value(BIND_EXCEPTION_MESSAGE))
                .andExpect(jsonPath("$.validation.email").value(EmailPattern.MESSAGE))
                .andDo(print());
    }

    @Test
    @DisplayName("수강생의 수강과목을 잘못된 값으로 수정")
    void editStudentByWrongCourse() throws Exception {
        Student student = getStudent();

        StudentEdit studentEdit = StudentEdit.builder()
                .name("ruby")
                .phoneNumber("010-222-3333")
                .email("rubykim0724@gmail.com")
                .course("DRUM")
                .grade(Grade.BEGINNER.name())
                .build();

        mockMvc.perform(patch("/students/{studentId}", student.getId())
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(studentEdit))
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.message").value(BIND_EXCEPTION_MESSAGE))
                .andExpect(jsonPath("$.validation.course").value(CoursePattern.MESSAGE))
                .andDo(print());
    }

    @Test
    @DisplayName("수강생의 등급을 잘못된 값으로 수정")
    void editStudentByWrongGrade() throws Exception {
        Student student = getStudent();

        StudentEdit studentEdit = StudentEdit.builder()
                .name("ruby")
                .phoneNumber("010-222-3333")
                .email("rubykim0724@gmail.com")
                .course(Course.PIANO.name())
                .grade("asdasd")
                .build();

        mockMvc.perform(patch("/students/{studentId}", student.getId())
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(studentEdit))
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.message").value(BIND_EXCEPTION_MESSAGE))
                .andExpect(jsonPath("$.validation.grade").value(GradePattern.MESSAGE))
                .andDo(print());
    }

    @Test
    @DisplayName("수강생 정보 수정")
    void editStudent() throws Exception {
        Student student = getStudent();

        StudentEdit studentEdit = StudentEdit.builder()
                .name("ruby")
                .phoneNumber("010-2222-3333")
                .email("rubykim0724@gmail.com")
                .course(Course.VIOLIN.name())
                .grade(Grade.ADVANCED.name())
                .build();

        mockMvc.perform(patch("/students/{studentId}", student.getId())
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(studentEdit))
                )
                .andExpect(status().isOk())
                .andDo(print());

        assertThat(studentRepository.count()).isEqualTo(1);

        Student findStudent = studentRepository.findById(student.getId()).get();
        assertThat(findStudent.getName()).isEqualTo("ruby");
        assertThat(findStudent.getEmail()).isEqualTo(studentEdit.getEmail());
        assertThat(findStudent.getPhoneNumber()).isEqualTo(studentEdit.getPhoneNumber());
        assertThat(findStudent.getCourse()).isEqualTo(Course.valueOf(studentEdit.getCourse()));
    }

    /** 수정 테스트 end */

    /** 삭제 테스트 start */
    @Test
    @DisplayName("존재하지 않는 수강생 정보 삭제")
    void deleteStudentByWrongId() throws Exception {
        Student student = getStudent();

        mockMvc.perform(delete("/students/{studentId}", student.getId() + 1))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value(HttpStatus.NOT_FOUND.value()))
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
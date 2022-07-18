package ruby.musicroost.docs;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import ruby.musicroost.controller.ControllerTest;
import ruby.musicroost.domain.Student;
import ruby.musicroost.domain.Teacher;
import ruby.musicroost.domain.enums.Course;
import ruby.musicroost.domain.enums.Grade;
import ruby.musicroost.request.student.StudentEdit;
import ruby.musicroost.request.teacher.TeacherEdit;
import ruby.musicroost.request.teacher.TeacherRegister;
import ruby.musicroost.valid.validator.CourseValidator;
import ruby.musicroost.valid.validator.GradeValidator;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureRestDocs(uriScheme = "https", uriHost = "music-roost.com", uriPort = 443)
@ExtendWith(RestDocumentationExtension.class)
public class TeacherDocTest extends ControllerTest {

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
                        .accept(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(teacher))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("teacher-register",
                        requestFields(
                                fieldWithPath("name").description("이름"),
                                fieldWithPath("email").description("이메일"),
                                fieldWithPath("phoneNumber").description("핸드폰 번호"),
                                fieldWithPath("course").description("수강과목")
                                        .attributes(key("constraint").value(getCourseConstraintString()))
                        )
                ));
    }

    @Test
    @DisplayName("선생님 목록 조회")
    void getList() throws Exception {
        List<Teacher> teachers = IntStream.range(0, 30)
                .mapToObj(idx -> Teacher.builder()
                        .name("teacher" + idx)
                        .email(idx + "teacher@gmail.com")
                        .phoneNumber("010-11" + idx + "-2222")
                        .course(idx < 15 ? Course.FLUTE : Course.VIOLIN)
                        .build())
                .collect(Collectors.toList());

        teacherRepository.saveAll(teachers);

        mockMvc.perform(get("/teachers")
                        .param("course", Course.FLUTE.name())
                        .param("name", "teacher")
                        .param("page", "1")
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("teacher-inquiryList",
                        requestParameters(
                                parameterWithName("course").description("수강과목")
                                        .attributes(key("constraint").value(getCourseConstraintString())),
                                parameterWithName("name").description("이름"),
                                parameterWithName("page").description("페이지").optional()
                        ),
                        responseFields(
                                fieldWithPath("[].id").description("순번"),
                                fieldWithPath("[].name").description("이름"),
                                fieldWithPath("[].email").description("이메일"),
                                fieldWithPath("[].phoneNumber").description("핸드폰 번호"),
                                fieldWithPath("[].course").description("수강과목"),
                                fieldWithPath("[].since").description("등록일")
                        )
                ));
    }


    @Test
    @DisplayName("선생님 조회")
    void getTeacher() throws Exception {
        Teacher teacher = Teacher.builder()
                .name("teacher")
                .email("rubykim0723@gmail.com")
                .phoneNumber("010-1111-2222")
                .course(Course.VIOLA)
                .build();
        teacherRepository.save(teacher);

        this.mockMvc.perform(get("/teachers/{teacherId}", teacher.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("teacher-inquiry",
                        pathParameters(
                                parameterWithName("teacherId").description("선생님 ID")
                        ),
                        responseFields(
                                fieldWithPath("id").description("순번"),
                                fieldWithPath("name").description("이름"),
                                fieldWithPath("email").description("이메일"),
                                fieldWithPath("phoneNumber").description("핸드폰 번호"),
                                fieldWithPath("course").description("수강과목"),
                                fieldWithPath("since").description("등록일")
                        )
                ));
    }

    @Test
    @DisplayName("선생님 정보 수정")
    void editTeacher() throws Exception {
        Teacher teacher = Teacher.builder()
                .name("teacher")
                .email("rubykim0723@gmail.com")
                .phoneNumber("010-1111-2222")
                .course(Course.VIOLA)
                .build();
        teacherRepository.save(teacher);

        TeacherEdit teacherEdit = TeacherEdit.builder()
                .name("teacher12")
                .email("rubykim0724@gmail.com")
                .phoneNumber("010-1111-3333")
                .course(Course.VIOLIN.name())
                .build();

        mockMvc.perform(patch("/teachers/{teacherId}", teacher.getId())
                        .contentType(APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(teacherEdit))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("teacher-edit",
                        pathParameters(
                                parameterWithName("teacherId").description("선생님 ID")
                        ),
                        requestFields(
                                fieldWithPath("name").description("이름"),
                                fieldWithPath("email").description("이메일"),
                                fieldWithPath("phoneNumber").description("핸드폰 번호"),
                                fieldWithPath("course").description("수강과목")
                                        .attributes(key("constraint").value(getCourseConstraintString()))
                        )));
    }

    @Test
    @DisplayName("선생님 정보 삭제")
    void deleteTeacher() throws Exception {
        Teacher teacher = Teacher.builder()
                .name("teacher")
                .email("rubykim0723@gmail.com")
                .phoneNumber("010-1111-2222")
                .course(Course.VIOLA)
                .build();
        teacherRepository.save(teacher);

        mockMvc.perform(delete("/teachers/{teacherId}", teacher.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("teacher-delete",
                        pathParameters(
                                parameterWithName("teacherId").description("선생님 ID")
                        )
                ));
    }

    private String getCourseConstraintString() {
        return CourseValidator.getRegexpCourse().replaceAll("\\|", " / ");
    }

    private String getGradeConstraintString() {
        return GradeValidator.getRegexpGrade().replaceAll("\\|", " / ");
    }
}

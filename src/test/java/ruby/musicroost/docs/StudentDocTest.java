package ruby.musicroost.docs;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ruby.musicroost.domain.Student;
import ruby.musicroost.domain.enums.Course;
import ruby.musicroost.domain.enums.Grade;
import ruby.musicroost.repository.StudentRepository;
import ruby.musicroost.request.student.StudentRegister;
import ruby.musicroost.valid.validator.CourseValidator;
import ruby.musicroost.valid.validator.GradeValidator;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.hamcrest.Matchers.is;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs(uriScheme = "https", uriHost = "music-roost.com", uriPort = 443)
@ExtendWith(RestDocumentationExtension.class)
public class StudentDocTest {

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    protected ObjectMapper mapper;

//    @BeforeEach
//    public void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
//        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
//                .apply(documentationConfiguration(restDocumentation))
//                .build();
//    }

    @Test
    @DisplayName("수강생 조회")
    void testMethod() throws Exception {
        Student student = Student.builder()
                .name("ruby")
                .email("rubykim0723@gmail.com")
                .phoneNumber("010-1111-2222")
                .course(Course.FLUTE)
                .grade(Grade.BEGINNER)
                .build();
        studentRepository.save(student);

        this.mockMvc.perform(get("/students/{studentId}", 1L).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("student-inquiry",
                        pathParameters(
                                parameterWithName("studentId").description("수강생 ID")
                        ),
                        responseFields(
                                fieldWithPath("id").description("순번"),
                                fieldWithPath("name").description("이름"),
                                fieldWithPath("email").description("이메일"),
                                fieldWithPath("phoneNumber").description("핸드폰 번호"),
                                fieldWithPath("course").description("수강과목"),
                                fieldWithPath("grade").description("수강등급"),
                                fieldWithPath("since").description("등록일")
                        )
                ));
    }

    @Test
    @DisplayName("수강생 등록")
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
                        .accept(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(student))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("student-register",
                        requestFields(
                                fieldWithPath("name").description("이름"),
                                fieldWithPath("email").description("이메일"),
                                fieldWithPath("phoneNumber").description("핸드폰 번호"),
                                fieldWithPath("course").description("수강과목")
                                        .attributes(key("constraint").value(getCourseConstraintString())),
                                fieldWithPath("grade").description("수강등급")
                                        .attributes(key("constraint").value(getGradeConstraintString()))
//                                fieldWithPath("grade").description("수강생 수강등급").optional()   // optional 여부 추가
                        )
                ));
    }

    @Test
    @DisplayName("수강생 목록 조회")
    void inquiryList() throws Exception {
        List<Student> students = IntStream.range(0, 30)
                .mapToObj(idx -> Student.builder()
                        .name("ruby" + idx)
                        .email(idx + "rubykim0723@gmail.com")
                        .phoneNumber("010-11" + idx + "-2222")
                        .course(idx < 15 ? Course.FLUTE : Course.VIOLIN)
                        .grade(idx < 15 ? Grade.BEGINNER : Grade.ADVANCED)
                        .build())
                .collect(Collectors.toList());

        studentRepository.saveAll(students);

        mockMvc.perform(get("/students")
                        .param("course", Course.FLUTE.name())
                        .param("grade", Grade.BEGINNER.name())
                        .param("name", "ruby")
                        .param("page", "1")
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("student-inquiryList",
                        requestParameters(
                                parameterWithName("course").description("수강과목")
                                        .attributes(key("constraint").value(getCourseConstraintString())),
                                parameterWithName("grade").description("수강등급")
                                        .attributes(key("constraint").value(getGradeConstraintString())),
                                parameterWithName("name").description("이름"),
                                parameterWithName("page").description("페이지").optional()
                        ),
                        responseFields(
                                fieldWithPath("[].id").description("순번"),
                                fieldWithPath("[].name").description("이름"),
                                fieldWithPath("[].email").description("이메일"),
                                fieldWithPath("[].phoneNumber").description("핸드폰 번호"),
                                fieldWithPath("[].course").description("수강과목"),
                                fieldWithPath("[].grade").description("수강등급"),
                                fieldWithPath("[].since").description("등록일")
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

package ruby.musicroost.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class LessonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("/lessons 요청시 lesson controller 출력")
    void test() throws Exception {
        mockMvc.perform(post("/lessons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"studentId\" : 1, \"teacherId\" : 2," +
                                " \"lessonTime\" : \"2022-07-13 20:00\"}")
                )
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("/lessons 요청시 학생 ID가 null 일 때 에러 발생")
    void emptyStudentNameWhenLessonPost() throws Exception {
        mockMvc.perform(post("/lessons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"studentId\" : null, \"teacherId\" : 2," +
                                " \"lessonTime\" : \"2022-07-13 20:00\"}")
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.message").value("잘못된 요청입니다."))
                .andExpect(jsonPath("$.validation.studentId").value("수강생을 선택해주세요."))
                .andDo(print());
    }

    @Test
    @DisplayName("/lessons 요청시 선생님 Id가 null 일 때 에러 발생")
    void emptyTeacherNameWhenLessonPost() throws Exception {
        mockMvc.perform(post("/lessons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"studentId\" : 1, \"teacherId\" : null," +
                                " \"lessonTime\" : \"2022-07-13 20:00\"}")
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.message").value("잘못된 요청입니다."))
                .andExpect(jsonPath("$.validation.teacherId").value("선생님을 선택해주세요."))
                .andDo(print());
    }

    @Test
    @DisplayName("/lessons 요청시 레슨 시간이 null 일 때 에러 발생")
    void emptyStartDateWhenLessonPost() throws Exception {
        mockMvc.perform(post("/lessons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"studentId\" : 1, \"teacherId\" : 2," +
                                " \"lessonTime\" : null}")
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.message").value("잘못된 요청입니다."))
                .andExpect(jsonPath("$.validation.lessonTime").value("레슨 시간이 입력되지 않았습니다."))
                .andDo(print());
    }

    @Test
    @DisplayName("/lessons 모든 필드의 검증 에러 발생")
    void wrongAllFieldWhenLessonPost() throws Exception {
        mockMvc.perform(post("/lessons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"studentId\" : null, \"teacherId\" : null," +
                                " \"lessonTime\" : \"2022-07-13 20:99\"}")
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.message").value("잘못된 요청입니다."))
                .andExpect(jsonPath("$.validation.studentId").value("수강생을 선택해주세요."))
                .andExpect(jsonPath("$.validation.teacherId").value("선생님을 선택해주세요."))
                .andExpect(jsonPath("$.validation.lessonTime").value("레슨 시간의 날짜 형식이 올바르지 않습니다."))
                .andDo(print());
    }
}
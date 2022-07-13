package ruby.musicroost.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
class ScheduleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("/schedules 요청시 schedule controller 출력")
    void test() throws Exception {
        mockMvc.perform(post("/schedules")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"studentName\" : \"ruby\", \"teacherName\" : \"eun\"," +
                                " \"startTime\" : \"2022-07-13 20:00\", \"endTime\" : \"2022-07-13 21:00\"}")
                )
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("/schedules 요청시 학생 이름이 빈 공백일 때 에러 발생")
    void emptyStudentNameWhenSchedulePost() throws Exception {
        mockMvc.perform(post("/schedules")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"studentName\" : \"    \", \"teacherName\" : \"eun\"," +
                                " \"startTime\" : \"2022-07-13 20:00\", \"endTime\" : \"2022-07-13 21:00\"}")
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.message").value("잘못된 요청입니다."))
                .andExpect(jsonPath("$.validation.studentName").value("수강생 이름이 비어있습니다."))
                .andDo(print());
    }

    @Test
    @DisplayName("/schedules 요청시 선생님 이름이 null 일 때 에러 발생")
    void emptyTeacherNameWhenSchedulePost() throws Exception {
        mockMvc.perform(post("/schedules")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"studentName\" : \"ruby\", \"teacherName\" : null," +
                                " \"startTime\" : \"2022-07-13 20:00\", \"endTime\" : \"2022-07-13 21:00\"}")
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.message").value("잘못된 요청입니다."))
                .andExpect(jsonPath("$.validation.teacherName").value("선생님 이름이 비어있습니다."))
                .andDo(print());
    }

    @Test
    @DisplayName("/schedules 요청시 시작시간이 null 일 때 에러 발생")
    void emptyStartDateWhenSchedulePost() throws Exception {
        mockMvc.perform(post("/schedules")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"studentName\" : \"ruby\", \"teacherName\" : \"eun\"," +
                                " \"startTime\" : null, \"endTime\" : \"2022-07-13 21:00\"}")
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.message").value("잘못된 요청입니다."))
                .andExpect(jsonPath("$.validation.startTime").value("레슨 시작 시간이 입력되지 않았습니다."))
                .andDo(print());
    }

    @Test
    @DisplayName("/schedules 요청시 종료 시간이 날짜타입에 맞지 않을 때 에러 발생")
    void emptyEndDateWhenSchedulePost() throws Exception {
        mockMvc.perform(post("/schedules")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"studentName\" : \"ruby\", \"teacherName\" : \"eun\"," +
                                " \"startTime\" : \"2022-07-13 20:00\", \"endTime\" : \"2022-07-44 21:92\"}")
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.message").value("잘못된 요청입니다."))
                .andExpect(jsonPath("$.validation.endTime").value("레슨 종료 시간의 날짜 형식이 올바르지 않습니다."))
                .andDo(print());
    }

    @Test
    @DisplayName("/schedules 모든 필드의 검증 에러 발생")
    void wrongAllFieldWhenSchedulePost() throws Exception {
        mockMvc.perform(post("/schedules")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"studentName\" : \"ruby\", \"teacherName\" : \"eun\"," +
                                " \"startTime\" : \"2022-07-13 20:00\", \"endTime\" : \"2022-07-44 21:92\"}")
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.message").value("잘못된 요청입니다."))
                .andExpect(jsonPath("$.validation.endTime").value("레슨 종료 시간의 날짜 형식이 올바르지 않습니다."))
                .andDo(print());
    }
}
package ruby.musicroost.docs;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import ruby.musicroost.controller.ControllerTest;
import ruby.musicroost.domain.Schedule;
import ruby.musicroost.domain.Student;
import ruby.musicroost.domain.Teacher;
import ruby.musicroost.domain.enums.Course;
import ruby.musicroost.domain.enums.Grade;
import ruby.musicroost.request.schedule.ScheduleEdit;
import ruby.musicroost.request.schedule.ScheduleRegister;
import ruby.musicroost.request.schedule.enums.ScheduleOption;
import ruby.musicroost.valid.validator.CourseValidator;
import ruby.musicroost.valid.validator.GradeValidator;
import ruby.musicroost.valid.validator.ScheduleOptionValidator;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureRestDocs(uriScheme = "https", uriHost = "music-roost.com", uriPort = 443)
@ExtendWith(RestDocumentationExtension.class)
public class ScheduleDocTest extends ControllerTest {

    @Test
    @DisplayName("스케쥴 등록")
    void registerSchedule() throws Exception {
        Student student = getStudent();
        Teacher teacher = getTeacher();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String time = LocalDateTime.of(2022, 7, 18, 14, 0).format(formatter);

        ScheduleRegister scheduleRegister = ScheduleRegister.builder()
                .studentId(student.getId())
                .teacherId(teacher.getId())
                .time(time)
                .build();

        mockMvc.perform(post("/schedules")
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(scheduleRegister))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("schedule-register",
                        requestFields(
                                fieldWithPath("studentId").description("수강생 ID"),
                                fieldWithPath("teacherId").description("선생님 ID"),
                                fieldWithPath("time").description("수강시간")
                                        .attributes(key("constraint").value("yyyy-MM-dd HH:mm"))
                        )
                ));
    }

    @Test
    @DisplayName("스케쥴 조회")
    void getListSchedulesByStudent() throws Exception {
        getSchedules();

        mockMvc.perform(get("/schedules")
                        .param("option", ScheduleOption.STUDENT_NAME.name())
                        .param("name", "ruby0")
                        .param("page", "1")
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("schedule-inquiryList",
                        requestParameters(
                                parameterWithName("option").description("검색종류")
                                        .attributes(key("constraint").value(getScheduleOptionConstraintString())).optional(),
                                parameterWithName("name").description("이름").optional(),
                                parameterWithName("page").description("페이지").optional()
                        ),
                        responseFields(
                                fieldWithPath("[].id").description("순번"),
                                fieldWithPath("[].time").description("수강시간"),
                                fieldWithPath("[].studentId").description("수강생 ID"),
                                fieldWithPath("[].studentName").description("수강생 이름"),
                                fieldWithPath("[].teacherId").description("선생님 ID"),
                                fieldWithPath("[].teacherName").description("선생님 이름")
                        )
                ));

    }

    @Test
    @DisplayName("스케쥴 수정")
    void editSchedule() throws Exception {
        Schedule schedule = getSchedule();
        Teacher newTeacher = getTeacher();

        ScheduleEdit scheduleEdit = ScheduleEdit.builder()
                .teacherId(newTeacher.getId())
                .time("2022-07-18 11:00")
                .build();

        mockMvc.perform(patch("/schedules/{scheduleId}", schedule.getId())
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(scheduleEdit))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("schedule-edit",
                        pathParameters(
                                parameterWithName("scheduleId").description("스케쥴 ID")
                        ),
                        requestFields(
                                fieldWithPath("teacherId").description("선생님 ID"),
                                fieldWithPath("time").description("수강시간")
                                        .attributes(key("constraint").value("yyyy-MM-dd HH:mm"))
                        )));
    }

    @Test
    @DisplayName("스케쥴 삭제")
    void deleteSchedule() throws Exception {
        Schedule schedule = getSchedule();

        mockMvc.perform(delete("/schedules/{scheduleId}", schedule.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("schedule-delete",
                        pathParameters(
                                parameterWithName("scheduleId").description("스케쥴 ID")
                        )
                ));
    }

    Student getStudent() {
        Student student = Student.builder()
                .name("ruby")
                .email("rubykim0723@gmail.com")
                .phoneNumber("010-1111-2222")
                .course(Course.FLUTE)
                .grade(Grade.BEGINNER)
                .build();
        return studentRepository.save(student);
    }
    Teacher getTeacher() {
        Teacher teacher = Teacher.builder()
                .name("teacher1")
                .email("rubykim0111@gmail.com")
                .phoneNumber("010-1111-3333")
                .course(Course.FLUTE)
                .build();
        return teacherRepository.save(teacher);
    }
    Schedule getSchedule() {
        Student student = getStudent();
        Teacher teacher = getTeacher();
        Schedule schedule = Schedule.builder()
                .time(LocalDateTime.of(2022, 7, 18, 13, 0))
                .teacher(teacher)
                .student(student)
                .build();

        return scheduleRepository.save(schedule);
    }
    List<Student> getStudents() {
        List<Student> students = IntStream.range(0, 2)
                .mapToObj(idx -> Student.builder()
                        .name("ruby" + idx)
                        .email("rubykim0723@gmail.com")
                        .phoneNumber("010-1111-2222")
                        .course(Course.FLUTE)
                        .grade(Grade.BEGINNER)
                        .build())
                .collect(Collectors.toList());
        return studentRepository.saveAll(students);
    }
    List<Teacher> getTeachers() {
        List<Teacher> teachers = IntStream.range(0, 2)
                .mapToObj(idx -> Teacher.builder()
                        .name("teacher" + idx)
                        .email("rubykim0723@gmail.com")
                        .phoneNumber("010-1111-2222")
                        .course(Course.FLUTE)
                        .build())
                .collect(Collectors.toList());

        return teacherRepository.saveAll(teachers);
    }
    List<Schedule> getSchedules() {
        List<Student> students = getStudents();
        List<Teacher> teachers = getTeachers();

        List<Schedule> schedules = IntStream.range(0, 12)
                .mapToObj(idx -> Schedule.builder()
                        .student(idx <= 10 ? students.get(0) : students.get(1))
                        .teacher(idx <= 10 ? teachers.get(0) : teachers.get(1))
                        .time(LocalDateTime.of(2022, 7, 17, idx + 10, 0))
                        .build())
                .collect(Collectors.toList());
        return scheduleRepository.saveAll(schedules);
    }

    private String getScheduleOptionConstraintString() {
        return ScheduleOptionValidator.getRegexpOption().replaceAll("\\|", " / ");
    }

    private String getCourseConstraintString() {
        return CourseValidator.getRegexpCourse().replaceAll("\\|", " / ");
    }

    private String getGradeConstraintString() {
        return GradeValidator.getRegexpGrade().replaceAll("\\|", " / ");
    }
}

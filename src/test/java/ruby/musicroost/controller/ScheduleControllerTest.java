package ruby.musicroost.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import ruby.musicroost.domain.Schedule;
import ruby.musicroost.domain.Student;
import ruby.musicroost.domain.Teacher;
import ruby.musicroost.domain.enums.Course;
import ruby.musicroost.domain.enums.Grade;
import ruby.musicroost.exception.schedule.ScheduleDifferentCourseException;
import ruby.musicroost.exception.schedule.ScheduleNotFoundException;
import ruby.musicroost.exception.student.StudentNotFoundException;
import ruby.musicroost.exception.teacher.TeacherNotFoundException;
import ruby.musicroost.request.schedule.ScheduleEdit;
import ruby.musicroost.request.schedule.ScheduleRegister;
import ruby.musicroost.request.schedule.enums.ScheduleOption;
import ruby.musicroost.valid.NamePattern;
import ruby.musicroost.valid.ScheduleOptionPattern;
import ruby.musicroost.valid.ScheduleTimePattern;
import ruby.musicroost.valid.message.ValidMessage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ruby.musicroost.controller.ExceptionController.BIND_EXCEPTION_MESSAGE;

class ScheduleControllerTest extends ControllerTest {

    /** 등록 테스트 start */
    Student getFluteStudent() {
        Student student = Student.builder()
                .name("ruby")
                .email("rubykim0723@gmail.com")
                .phoneNumber("010-1111-2222")
                .course(Course.FLUTE)
                .grade(Grade.BEGINNER)
                .build();
        return studentRepository.save(student);
    }
    Teacher getFluteTeacher() {
        Teacher teacher = Teacher.builder()
                .name("teacher1")
                .email("rubykim0111@gmail.com")
                .phoneNumber("010-1111-3333")
                .course(Course.FLUTE)
                .build();
        return teacherRepository.save(teacher);
    }
    Teacher getViolinTeacher() {
        Teacher teacher = Teacher.builder()
                .name("teacher2")
                .email("rubykim0111@gmail.com")
                .phoneNumber("010-1111-3333")
                .course(Course.VIOLIN)
                .build();
        return teacherRepository.save(teacher);
    }


    @Test
    @DisplayName("스케쥴 등록시 존재하지 않는 수강생 id로 등록")
    void registerScheduleNoneStudentId() throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        Student student = getFluteStudent();
        Teacher teacher = getFluteTeacher();
        String time = LocalDateTime.of(2022, 7, 18, 14, 0).format(formatter);

        ScheduleRegister scheduleRegister = ScheduleRegister.builder()
                .studentId(student.getId() + 1)
                .teacherId(teacher.getId())
                .time(time)
                .build();

        mockMvc.perform(post("/schedules")
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(scheduleRegister))
                )
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value(HttpStatus.NOT_FOUND.value()))
                .andExpect(jsonPath("$.message").value(StudentNotFoundException.MESSAGE))
                .andDo(print());
    }

    @Test
    @DisplayName("스케쥴 등록시 존재하지 않는 선생님 id로 등록")
    void registerScheduleNoneTeacherId() throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        Student student = getFluteStudent();
        Teacher teacher = getFluteTeacher();
        String time = LocalDateTime.of(2022, 7, 18, 14, 0).format(formatter);

        ScheduleRegister scheduleRegister = ScheduleRegister.builder()
                .studentId(student.getId())
                .teacherId(teacher.getId() + 1)
                .time(time)
                .build();

        mockMvc.perform(post("/schedules")
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(scheduleRegister))
                )
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value(HttpStatus.NOT_FOUND.value()))
                .andExpect(jsonPath("$.message").value(TeacherNotFoundException.MESSAGE))
                .andDo(print());
    }

    @Test
    @DisplayName("스케쥴 등록시 잘못된 시간 형식으로 등록")
    void registerScheduleWrongTime() throws Exception {
        Student student = getFluteStudent();
        Teacher teacher = getFluteTeacher();

        ScheduleRegister scheduleRegister = ScheduleRegister.builder()
                .studentId(student.getId())
                .teacherId(teacher.getId())
                .time("2022-07-17 9:55")
                .build();

        mockMvc.perform(post("/schedules")
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(scheduleRegister))
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.message").value(BIND_EXCEPTION_MESSAGE))
                .andExpect(jsonPath("$.validation.time").value(ScheduleTimePattern.MESSAGE))
                .andDo(print());
    }



    @Test
    @DisplayName("스케쥴 등록시 수강생을 지정하지 않고 등록")
    void registerScheduleByNullStudent() throws Exception {
        Teacher teacher = getFluteTeacher();

        ScheduleRegister scheduleRegister = ScheduleRegister.builder()
                .teacherId(teacher.getId())
                .time("2022-07-17 10:55")
                .build();

        mockMvc.perform(post("/schedules")
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(scheduleRegister))
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.message").value(BIND_EXCEPTION_MESSAGE))
                .andExpect(jsonPath("$.validation.studentId").value(ValidMessage.NOT_NULL))
                .andDo(print());
    }

    @Test
    @DisplayName("스케쥴 등록시 선생님을 지정하지 않고 등록")
    void registerScheduleByNullTeacher() throws Exception {
        Student student = getFluteStudent();

        ScheduleRegister scheduleRegister = ScheduleRegister.builder()
                .studentId(student.getId())
                .time("2022-07-17 10:55")
                .build();

        mockMvc.perform(post("/schedules")
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(scheduleRegister))
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.message").value(BIND_EXCEPTION_MESSAGE))
                .andExpect(jsonPath("$.validation.teacherId").value(ValidMessage.NOT_NULL))
                .andDo(print());
    }

    @Test
    @DisplayName("스케쥴 등록시 수강생과 선생님의 과목이 다른 경우")
    void registerScheduleDifferentCourse() throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        Student student = getFluteStudent();
        Teacher teacher = getViolinTeacher();
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
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.message").value(ScheduleDifferentCourseException.MESSAGE))
                .andDo(print());
    }

    @Test
    @DisplayName("스케쥴 등록")
    void registerSchedule() throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        Student student = getFluteStudent();
        Teacher teacher = getFluteTeacher();
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
                .andExpect(status().isOk())
                .andDo(print());

        Schedule schedule = scheduleRepository.findAll().get(0);
        assertThat(schedule.getStudent().getId()).isEqualTo(student.getId());
        assertThat(schedule.getTeacher().getId()).isEqualTo(teacher.getId());
        assertThat(schedule.getTime().format(formatter)).isEqualTo(time);
    }
    /** 등록 테스트 end */

    /** 목록 조회 테스트 start */
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

    @Test
    @DisplayName("스케쥴 목록 잘못된 옵션으로 검색")
    void getListSchedulesByWrongOption() throws Exception {
        getSchedules();

        mockMvc.perform(get("/schedules")
                        .param("option", "WRONG")
                        .param("name", "ruby0")
                        .param("page", "1")
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.message").value(BIND_EXCEPTION_MESSAGE))
                .andExpect(jsonPath("$.validation.option").value(ScheduleOptionPattern.MESSAGE))
                .andDo(print());
    }

    @Test
    @DisplayName("스케쥴 목록 1 페이지 미만으로 검색 시 1페이지 검색")
    void getListSchedulesByUnderPage() throws Exception {
        getSchedules();

        mockMvc.perform(get("/schedules")
                        .param("option", ScheduleOption.STUDENT_NAME.name())
                        .param("name", "ruby")
                        .param("page", "0")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(10))
                .andDo(print());
    }

    @Test
    @DisplayName("스케쥴 목록 없는 페이지로 검색")
    void getListSchedulesByNonePage() throws Exception {
        getSchedules();

        mockMvc.perform(get("/schedules")
                        .param("option", ScheduleOption.STUDENT_NAME.name())
                        .param("name", "ruby")
                        .param("page", "3")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0))
                .andDo(print());
    }

    @Test
    @DisplayName("스케쥴 목록 수강생 이름으로 조회")
    void getListSchedulesByStudent() throws Exception {
        getSchedules();

        mockMvc.perform(get("/schedules")
                        .param("option", ScheduleOption.STUDENT_NAME.name())
                        .param("name", "ruby0")
                        .param("page", "1")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(10))
                .andDo(print());

        mockMvc.perform(get("/schedules")
                        .param("option", ScheduleOption.STUDENT_NAME.name())
                        .param("name", "ruby0")
                        .param("page", "2")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
               .andDo(print());
    }

    @Test
    @DisplayName("스케쥴 목록 선생님 이름으로 조회")
    void getListSchedulesByTeacher() throws Exception {
        getSchedules();

        mockMvc.perform(get("/schedules")
                        .param("option", ScheduleOption.TEACHER_NAME.name())
                        .param("name", "teacher0")
                        .param("page", "1")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(10))
                .andDo(print());

        mockMvc.perform(get("/schedules")
                        .param("option", ScheduleOption.TEACHER_NAME.name())
                        .param("name", "ruby0")
                        .param("page", "1")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0))
                .andDo(print());
    }
    /** 목록 조회 테스트 end */


    /** 수정 테스트 start */
    Schedule getSchedule() {
        Student student = getFluteStudent();
        Teacher teacher = getFluteTeacher();
        Schedule schedule = Schedule.builder()
                .time(LocalDateTime.of(2022, 7, 18, 13, 0))
                .teacher(teacher)
                .student(student)
                .build();

        return scheduleRepository.save(schedule);
    }

    @Test
    @DisplayName("존재하지 않는 스케쥴 수정")
    void editScheduleByNoneSchedule() throws Exception {
        Schedule schedule = getSchedule();
        Teacher newTeacher = getFluteTeacher();

        ScheduleEdit scheduleEdit = ScheduleEdit.builder()
                .teacherId(newTeacher.getId())
                .time("2022-07-18 11:00")
                .build();

        mockMvc.perform(patch("/schedules/{scheduleId}", schedule.getId() + 1)
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(scheduleEdit))
                )
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value(HttpStatus.NOT_FOUND.value()))
                .andExpect(jsonPath("$.message").value(ScheduleNotFoundException.MESSAGE))
                .andDo(print());
    }

    @Test
    @DisplayName("스케쥴 담당 선생님을 존재하지 않는 선생님으로 수정")
    void editScheduleByNoneTeacher() throws Exception {
        Schedule schedule = getSchedule();
        Teacher newTeacher = getFluteTeacher();

        ScheduleEdit scheduleEdit = ScheduleEdit.builder()
                .teacherId(newTeacher.getId() + 1)
                .time("2022-07-18 11:00")
                .build();

        mockMvc.perform(patch("/schedules/{scheduleId}", schedule.getId())
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(scheduleEdit))
                )
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value(HttpStatus.NOT_FOUND.value()))
                .andExpect(jsonPath("$.message").value(TeacherNotFoundException.MESSAGE))
                .andDo(print());
    }

    @Test
    @DisplayName("스케쥴 잘못된 선생님으로 수정")
    void editScheduleByWrongTeacher() throws Exception {
        Schedule schedule = getSchedule();
        Teacher newTeacher = getViolinTeacher();

        ScheduleEdit scheduleEdit = ScheduleEdit.builder()
                .teacherId(newTeacher.getId())
                .time("2022-07-18 11:00")
                .build();

        mockMvc.perform(patch("/schedules/{scheduleId}", schedule.getId())
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(scheduleEdit))
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.message").value(ScheduleDifferentCourseException.MESSAGE))
                .andDo(print());
    }

    @Test
    @DisplayName("스케쥴 수정")
    void editSchedule() throws Exception {
        Schedule schedule = getSchedule();
        Teacher newTeacher = getFluteTeacher();

        ScheduleEdit scheduleEdit = ScheduleEdit.builder()
                .teacherId(newTeacher.getId())
                .time("2022-07-18 11:00")
                .build();

        mockMvc.perform(patch("/schedules/{scheduleId}", schedule.getId())
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(scheduleEdit))
                )
                .andExpect(status().isOk())
                .andDo(print());

        mockMvc.perform(get("/schedules")
                        .param("option", ScheduleOption.STUDENT_NAME.name())
                        .param("name", schedule.getStudent().getName())
                        .param("page", "1")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$.[0].studentId").value(schedule.getStudent().getId()))
                .andExpect(jsonPath("$.[0].teacherId").value(newTeacher.getId()))
                .andDo(print());
    }

    /** 수정 테스트 end */

    /** 삭제 테스트 start */
    @Test
    @DisplayName("존재하지 않는 스케쥴 삭제")
    void deleteScheduleByNoneSchedule() throws Exception {
        Schedule schedule = getSchedule();

        mockMvc.perform(delete("/schedules/{scheduleId}", schedule.getId() + 1)
                )
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value(HttpStatus.NOT_FOUND.value()))
                .andExpect(jsonPath("$.message").value(ScheduleNotFoundException.MESSAGE))
                .andDo(print());
    }

    @Test
    @DisplayName("스케쥴 삭제")
    void deleteSchedule() throws Exception {
        Schedule schedule = getSchedule();

        mockMvc.perform(delete("/schedules/{scheduleId}", schedule.getId())
                )
                .andExpect(status().isOk())
                .andDo(print());

        assertThat(scheduleRepository.findAll().size()).isEqualTo(0);
    }
    /** 삭제 테스트 end */
}
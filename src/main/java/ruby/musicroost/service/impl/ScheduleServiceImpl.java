package ruby.musicroost.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ruby.musicroost.domain.Schedule;
import ruby.musicroost.domain.Student;
import ruby.musicroost.domain.Teacher;
import ruby.musicroost.domain.editor.ScheduleEditor;
import ruby.musicroost.exception.schedule.ScheduleDifferentCourseException;
import ruby.musicroost.exception.schedule.ScheduleNotFoundException;
import ruby.musicroost.exception.student.StudentNotFoundException;
import ruby.musicroost.exception.teacher.TeacherNotFoundException;
import ruby.musicroost.repository.ScheduleRepository;
import ruby.musicroost.repository.StudentRepository;
import ruby.musicroost.repository.TeacherRepository;
import ruby.musicroost.request.schedule.ScheduleEdit;
import ruby.musicroost.request.schedule.ScheduleRegister;
import ruby.musicroost.request.schedule.ScheduleSearch;
import ruby.musicroost.request.schedule.enums.ScheduleOption;
import ruby.musicroost.service.ScheduleService;

import java.util.List;

import static java.lang.Math.max;

@Service
@RequiredArgsConstructor
@Transactional
public class ScheduleServiceImpl implements ScheduleService {

    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final ScheduleRepository scheduleRepository;

    /**
     * 스케쥴 등록
     * @param scheduleRegister
     */
    @Override
    public void register(ScheduleRegister scheduleRegister) {
        Student student = studentRepository.findById(scheduleRegister.getStudentId())
                .orElseThrow(StudentNotFoundException::new);
        Teacher teacher = teacherRepository.findById(scheduleRegister.getTeacherId())
                .orElseThrow(TeacherNotFoundException::new);

        Schedule schedule = Schedule.builder()
                .student(student)
                .teacher(teacher)
                .time(scheduleRegister.getParseTime())
                .build();

        if (!schedule.isPracticable()) throw new ScheduleDifferentCourseException();

        scheduleRepository.save(schedule);
    }

    /**
     * 스케쥴 목록 조회
     * @param search
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public List<Schedule> getList(ScheduleSearch search) {
        Pageable pageable = PageRequest.of(max(0, search.getPage() - 1), 10);
        return scheduleRepository.findByNameContains(ScheduleOption.valueOf(search.getOption()), search.getName(), pageable);
    }

    /**
     * 스케쥴 수정
     * @param scheduleId
     * @param scheduleEdit
     */
    @Override
    public void edit(Long scheduleId, ScheduleEdit scheduleEdit) {
        Schedule schedule = scheduleRepository.findByIdFetchTeacher(scheduleId)
                .orElseThrow(ScheduleNotFoundException::new);

        Teacher teacher = teacherRepository.findById(scheduleEdit.getTeacherId())
                .orElseThrow(TeacherNotFoundException::new);

        ScheduleEditor scheduleEditor = schedule.toEditor()
                .teacher(teacher)
                .time(scheduleEdit.getParseTime())
                .build();

        schedule.edit(scheduleEditor);

        // 최종 update 되기전에 수정된 선생님과 수강생의 과목이 같은지 확인
        if (!schedule.isPracticable()) throw new ScheduleDifferentCourseException();
    }
}

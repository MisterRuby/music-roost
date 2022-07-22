package ruby.musicroost.repository.custom;

import org.springframework.data.domain.Pageable;
import ruby.musicroost.domain.Schedule;
import ruby.musicroost.domain.Student;
import ruby.musicroost.request.schedule.enums.ScheduleOption;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ScheduleRepositoryCustom{

    /**
     * 스케쥴 목록 조회
     * @param option
     * @param name
     * @param pageable
     * @return
     */
    List<Schedule> findByNameContains(ScheduleOption option, String name, Pageable pageable);

    /**
     * 스케쥴 정보 조회
     * @param scheduleId
     * @return
     */
    Optional<Schedule> findByIdFetchTeacher(Long scheduleId);


    boolean existsByStudentAndTime(Student student, LocalDateTime time);
}

package ruby.musicroost.repository.custom;

import org.springframework.data.domain.Pageable;
import ruby.musicroost.domain.Schedule;
import ruby.musicroost.request.schedule.enums.ScheduleOption;

import java.util.List;

public interface ScheduleRepositoryCustom{

    /**
     * 스케쥴 목록 조회
     * @param option
     * @param name
     * @param pageable
     * @return
     */
    List<Schedule> findByNameContains(ScheduleOption option, String name, Pageable pageable);
}

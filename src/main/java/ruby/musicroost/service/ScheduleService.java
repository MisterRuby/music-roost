package ruby.musicroost.service;

import ruby.musicroost.domain.Schedule;
import ruby.musicroost.request.schedule.ScheduleEdit;
import ruby.musicroost.request.schedule.ScheduleRegister;
import ruby.musicroost.request.schedule.ScheduleSearch;

import java.util.List;

public interface ScheduleService {

    /**
     * 스케쥴 등록
     * @param scheduleRegister
     */
    void register(ScheduleRegister scheduleRegister);

    /**
     * 스케쥴 목록 조회
     * @param search
     * @return
     */
    List<Schedule> getList(ScheduleSearch search);

    /**
     * 스케쥴 수정
     * @param scheduleId
     * @param scheduleEdit
     */
    void edit(Long scheduleId, ScheduleEdit scheduleEdit);

    /**
     * 스케쥴 삭제
     * @param scheduleId
     */
    void delete(Long scheduleId);
}

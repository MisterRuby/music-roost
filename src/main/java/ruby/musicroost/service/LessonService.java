package ruby.musicroost.service;

import ruby.musicroost.request.Schedule;

/**
 * 레슨 서비스
 */
public interface LessonService {

    // 레슨 예약
    // 레슨 조회
    // 레슨 상세조회 - 레슨시 특이사항, 진도 등을 메모하여 조회
    // 레슨 변경
    // 레슨 진행
    // 레슨 종료
    // 레슨 취소

    /**
     * 레슨 예약
     * @param schedule
     */
    void schedule(Schedule schedule);
//    void reschedule();
//    void proceedSchedule();
//    void finishSchedule();
//    void cancelSchedule();

}

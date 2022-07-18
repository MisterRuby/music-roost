package ruby.musicroost.repository.impl;

import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import ruby.musicroost.domain.Schedule;
import ruby.musicroost.repository.custom.ScheduleRepositoryCustom;
import ruby.musicroost.request.schedule.enums.ScheduleOption;

import java.util.List;
import java.util.Optional;

import static ruby.musicroost.domain.QSchedule.schedule;
import static ruby.musicroost.domain.QStudent.student;
import static ruby.musicroost.domain.QTeacher.teacher;

@RequiredArgsConstructor
public class ScheduleRepositoryImpl implements ScheduleRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    /**
     * 스케쥴 목록 조회
     * @param option
     * @param name
     * @param pageable
     * @return
     */
    @Override
    public List<Schedule> findByNameContains(ScheduleOption option, String name, Pageable pageable) {
        return jpaQueryFactory.selectFrom(schedule)
                .leftJoin(schedule.teacher, teacher).fetchJoin()
                .leftJoin(schedule.student, student).fetchJoin()
                .where(searchCondition(option, name))
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .orderBy(schedule.id.desc())
                .fetch();
    }

    /**
     * 스케쥴 정보 조회
     * @param scheduleId
     * @return
     */
    @Override
    public Optional<Schedule> findByIdFetchTeacher(Long scheduleId) {
        Schedule findSchedule = jpaQueryFactory.selectFrom(schedule)
                .leftJoin(schedule.student, student).fetchJoin()
                .where(schedule.id.eq(scheduleId))
                .fetchOne();

        return Optional.ofNullable(findSchedule);
    }

    /**
     * 스케쥴 검색 조건
     * @param option
     * @param name
     * @return
     */
    private Predicate searchCondition(ScheduleOption option, String name) {
        if (option.equals(ScheduleOption.STUDENT_NAME)) return student.name.contains(name);

        return teacher.name.contains(name);
    }


}

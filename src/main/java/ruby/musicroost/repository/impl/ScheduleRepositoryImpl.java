package ruby.musicroost.repository.impl;

import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import ruby.musicroost.domain.QSchedule;
import ruby.musicroost.domain.QStudent;
import ruby.musicroost.domain.QTeacher;
import ruby.musicroost.domain.Schedule;
import ruby.musicroost.repository.custom.ScheduleRepositoryCustom;
import ruby.musicroost.request.schedule.enums.ScheduleOption;

import java.util.List;

import static ruby.musicroost.domain.QSchedule.*;
import static ruby.musicroost.domain.QStudent.*;
import static ruby.musicroost.domain.QTeacher.*;

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
        // 스케쥴에 연관되어 있는 수강생, 선생님 정보도 같이 조회되어야 함 -> 화면에서 보여줄 때 id와 이름도 팔요하므로
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

package ruby.musicroost.repository.impl;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import ruby.musicroost.domain.Teacher;
import ruby.musicroost.domain.enums.Course;
import ruby.musicroost.repository.custom.TeacherRepositoryCustom;

import java.util.List;

import static ruby.musicroost.domain.QTeacher.teacher;

@RequiredArgsConstructor
public class TeacherRepositoryImpl implements TeacherRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    /**
     * 선생님 목록 조회 - TODO : 페이징 처리를 위해 전체 페이지 개수를 계산해서 클라이언트에게 전달해줘야함
     * @param course
     * @param name
     * @param pageable
     * @return
     */
    @Override
    public List<Teacher> findByCourseAndNameContains(Course course, String name, Pageable pageable) {
        return jpaQueryFactory.selectFrom(teacher)
                .where(searchCondition(course, name))
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .orderBy(teacher.id.desc())
                .fetch();
    }

    /**
     * 선생님 검색 조건
     * @param course
     * @param name
     * @return
     */
    private Predicate searchCondition(Course course, String name) {
        if (name == null) name = "";
        BooleanExpression condition = teacher.name.contains(name);
        if (course != null) condition = condition.and(teacher.course.eq(course));
        return condition;
    }
}

package ruby.musicroost.repository.impl;

import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import ruby.musicroost.domain.Student;
import ruby.musicroost.domain.enums.Course;
import ruby.musicroost.domain.enums.Grade;
import ruby.musicroost.repository.custom.StudentRepositoryCustom;

import java.util.List;
import java.util.Optional;

import static ruby.musicroost.domain.QStudent.student;
import static ruby.musicroost.domain.QTeacher.teacher;


@RequiredArgsConstructor
public class StudentRepositoryImpl implements StudentRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    /**
     * 수강생 상세 조회
     * @param id
     * @return
     */
    @Override
    public Optional<Student> findDetailById(Long id) {
        return Optional.ofNullable(jpaQueryFactory.selectFrom(student)
                .leftJoin(student.teacher, teacher).fetchJoin()
                .where(student.id.eq(id))
                .fetchOne());
    }

    /**
     * 수강생 목록 조회 - TODO : 페이징 처리를 위해 전체 페이지 개수를 계산해서 클라이언트에게 전달해줘야함
     * @param course
     * @param grade
     * @param name
     * @param pageable
     * @return
     */
    @Override
    public List<Student> findByCourseAndGradeAndNameContains(Course course, Grade grade, String name, Pageable pageable) {
        return jpaQueryFactory.selectFrom(student)
                .leftJoin(student.teacher, teacher).fetchJoin()
                .where(searchCondition(course, grade, name))
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .orderBy(student.id.desc())
                .fetch();
    }

    /**
     * 수강생 검색 조건
     * @param course
     * @param grade
     * @param name
     * @return
     */
    private Predicate searchCondition(Course course, Grade grade, String name) {
        return student.course.eq(course)
                .and(student.grade.eq(grade))
                .and(student.name.contains(name));
    }
}

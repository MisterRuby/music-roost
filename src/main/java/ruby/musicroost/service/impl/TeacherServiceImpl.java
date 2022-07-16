package ruby.musicroost.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ruby.musicroost.domain.Teacher;
import ruby.musicroost.domain.enums.Course;
import ruby.musicroost.exception.teacher.TeacherNotFoundException;
import ruby.musicroost.repository.TeacherRepository;
import ruby.musicroost.request.teacher.TeacherSearch;
import ruby.musicroost.service.TeacherService;

import java.util.List;

import static java.lang.Math.max;

@Service
@RequiredArgsConstructor
@Transactional
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teacherRepository;

    /**
     * 선생님 정보 등
     * @param teacher
     */
    @Override
    public void register(Teacher teacher) {
        teacherRepository.save(teacher);
    }

    /**
     * 선생님 목록 조회
     * @param search
     */
    @Override
    public List<Teacher> getList(TeacherSearch search) {
        Pageable pageable = PageRequest.of(max(0, search.getPage() - 1), 10);
        return teacherRepository.findByCourseAndNameContains
                (Course.valueOf(search.getCourse()), search.getName(), pageable);
    }

    /**
     * 선생님 정보 상세조회
     * @param teacherId
     */
    @Override
    @Transactional(readOnly = true)
    public Teacher getDetail(Long teacherId) {
        return teacherRepository.findById(teacherId)
                .orElseThrow(TeacherNotFoundException::new);
    }
}

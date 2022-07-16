package ruby.musicroost.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ruby.musicroost.domain.Teacher;
import ruby.musicroost.repository.TeacherRepository;
import ruby.musicroost.request.teacher.TeacherSearch;
import ruby.musicroost.service.TeacherService;

import java.util.List;

@Service
@RequiredArgsConstructor
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
        return null;
    }
}

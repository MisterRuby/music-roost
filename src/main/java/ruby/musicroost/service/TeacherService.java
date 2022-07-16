package ruby.musicroost.service;

import ruby.musicroost.domain.Teacher;
import ruby.musicroost.request.teacher.TeacherSearch;

import java.util.List;

public interface TeacherService {

    /**
     * 선생님 등록
     * @param teacher
     */
    void register(Teacher teacher);

    /**
     * 선생님 목록 조회
     * @param search
     */
    List<Teacher> getList(TeacherSearch search);
}

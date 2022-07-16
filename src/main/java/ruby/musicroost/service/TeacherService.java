package ruby.musicroost.service;

import ruby.musicroost.domain.Teacher;
import ruby.musicroost.request.teacher.TeacherEdit;
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

    /**
     * 선생님 정보 상세조회
     * @param teacherId
     */
    Teacher getDetail(Long teacherId);

    /**
     * 선생님 정보 수정
     * @param teacherId
     * @param teacherEdit
     */
    void edit(Long teacherId, TeacherEdit teacherEdit);

    /**
     * 선생님 정보 삭제
     * @param teacherId
     */
    void delete(Long teacherId);
}

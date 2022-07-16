package ruby.musicroost.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ruby.musicroost.domain.Teacher;
import ruby.musicroost.repository.custom.TeacherRepositoryCustom;

public interface TeacherRepository extends JpaRepository<Teacher, Long>, TeacherRepositoryCustom {
}

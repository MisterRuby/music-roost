package ruby.musicroost.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ruby.musicroost.domain.Teacher;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
}

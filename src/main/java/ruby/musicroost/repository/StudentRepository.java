package ruby.musicroost.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ruby.musicroost.domain.Student;
import ruby.musicroost.repository.custom.StudentRepositoryCustom;

public interface StudentRepository extends JpaRepository<Student, Long>, StudentRepositoryCustom {
}

package ruby.musicroost.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ruby.musicroost.domain.Schedule;
import ruby.musicroost.domain.Student;
import ruby.musicroost.repository.custom.ScheduleRepositoryCustom;

import java.time.LocalDateTime;

public interface ScheduleRepository extends JpaRepository<Schedule, Long>, ScheduleRepositoryCustom {
}

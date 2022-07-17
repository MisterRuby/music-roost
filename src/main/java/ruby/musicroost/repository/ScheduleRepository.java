package ruby.musicroost.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ruby.musicroost.domain.Schedule;
import ruby.musicroost.repository.custom.ScheduleRepositoryCustom;

public interface ScheduleRepository extends JpaRepository<Schedule, Long>, ScheduleRepositoryCustom {
}

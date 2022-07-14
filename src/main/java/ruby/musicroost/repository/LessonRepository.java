package ruby.musicroost.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ruby.musicroost.domain.Lesson;

public interface LessonRepository extends JpaRepository<Lesson, Long> {
}

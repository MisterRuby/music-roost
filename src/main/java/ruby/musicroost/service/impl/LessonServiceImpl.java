package ruby.musicroost.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ruby.musicroost.repository.LessonRepository;
import ruby.musicroost.request.Schedule;
import ruby.musicroost.service.LessonService;

@Slf4j
@Service
@RequiredArgsConstructor
public class LessonServiceImpl implements LessonService {

    private final LessonRepository lessonRepository;

    @Override
    public void schedule(Schedule schedule) {
        // schedule 에 있는 선생님ID 와 수강생 ID 가 존재하는지 검증
        // 검증이 된다면 레슨 등록



//        lessonRepository.save()
    }
}

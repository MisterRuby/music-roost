package ruby.musicroost.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ruby.musicroost.request.Schedule;

import javax.validation.Valid;

@Slf4j
@RestController
public class LessonController {

    // 스케쥴 등록
    @PostMapping("/lessons")
    public String post(@RequestBody @Valid Schedule schedule) {
        log.info("lessonPost={}", schedule);
        return "lesson controller";
    }
}

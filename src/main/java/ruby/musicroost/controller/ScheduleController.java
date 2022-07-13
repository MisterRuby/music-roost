package ruby.musicroost.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ruby.musicroost.request.SchedulePostDto;

import javax.validation.Valid;

@Slf4j
@RestController
public class ScheduleController {

    // 스케쥴 등록
    @PostMapping("/schedules")
    public String post(@RequestBody @Valid SchedulePostDto schedulePost) {
//        if (bindingResult.hasErrors()) {
//            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
//            fieldErrors.forEach(fieldError -> log.info("message={}", fieldError.getDefaultMessage()));
//        }

        log.info("schedulePost={}", schedulePost);
        return "schedule controller";
    }
}

package ruby.musicroost.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ruby.musicroost.domain.Schedule;
import ruby.musicroost.request.schedule.ScheduleEdit;
import ruby.musicroost.request.schedule.ScheduleRegister;
import ruby.musicroost.request.schedule.ScheduleSearch;
import ruby.musicroost.response.schedule.ScheduleResponse;
import ruby.musicroost.service.ScheduleService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    /**
     * 스케쥴 등록
     * @param scheduleRegister
     */
    @PostMapping
    public void register(@RequestBody @Valid ScheduleRegister scheduleRegister) {
        scheduleService.register(scheduleRegister);
    }

    /**
     * 스케쥴 목록 조회
     * @param search
     */
    @GetMapping
    public List<ScheduleResponse> getList(@Valid ScheduleSearch search){
        List<Schedule> schedules = scheduleService.getList(search);
        return schedules.stream().map(ScheduleResponse::new).collect(Collectors.toList());
    }

    /**
     * 스케쥴 수정
     * @param scheduleId
     * @param scheduleEdit
     */
    @PatchMapping("/{scheduleId}")
    public void edit(@PathVariable Long scheduleId, @RequestBody @Valid ScheduleEdit scheduleEdit) {
        scheduleService.edit(scheduleId, scheduleEdit);
    }

    /**
     * 스케쥴 삭제
     * @param scheduleId
     */
    @DeleteMapping("/{scheduleId}")
    public void delete(@PathVariable Long scheduleId){
        scheduleService.delete(scheduleId);
    }
}

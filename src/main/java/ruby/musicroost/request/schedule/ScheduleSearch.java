package ruby.musicroost.request.schedule;

import lombok.Getter;
import lombok.Setter;
import ruby.musicroost.valid.NamePattern;
import ruby.musicroost.valid.ScheduleOptionPattern;

@Getter @Setter
public class ScheduleSearch {
    @ScheduleOptionPattern
    private String option;
    @NamePattern
    private String name;
    private int page = 1;
}

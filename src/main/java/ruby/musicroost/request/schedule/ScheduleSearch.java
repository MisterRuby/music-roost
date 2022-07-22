package ruby.musicroost.request.schedule;

import lombok.Getter;
import lombok.Setter;
import ruby.musicroost.request.schedule.enums.ScheduleOption;
import ruby.musicroost.valid.NamePattern;
import ruby.musicroost.valid.ScheduleOptionPattern;

@Getter @Setter
public class ScheduleSearch {
    @ScheduleOptionPattern
    private String option;
    private String name;
    private int page = 1;

    public boolean isOptionEmpty() {
        return this.option == null || this.option.isEmpty();
    }
}

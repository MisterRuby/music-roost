package ruby.musicroost.response.student;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
public class StudentResponse {

    private Long id;
    private String name;
    private String email;
    private String phoneNumber;
    private String course;
    private LocalDate since;

    // TODO - 담당 선생닝 정보(ID, 이름)도 같이 필요함
}

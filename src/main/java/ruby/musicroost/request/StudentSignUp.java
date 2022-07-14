package ruby.musicroost.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ruby.musicroost.infra.valid.CoursePattern;
import ruby.musicroost.infra.valid.NamePattern;
import ruby.musicroost.infra.valid.PhonePattern;

import javax.validation.constraints.Email;

@Getter @Setter
@NoArgsConstructor
public class StudentSignUp {

    @NamePattern
    private String name;
    @Email(message = "이메일 입력 형식이 올바르지 않습니다.")
    private String email;
    @PhonePattern
    private String phoneNumber;
    @CoursePattern
    private String course;

    @Builder
    public StudentSignUp(String name, String email, String phoneNumber, String course) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.course = course;
    }
}

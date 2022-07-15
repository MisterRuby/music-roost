package ruby.musicroost.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ruby.musicroost.exception.common.IllegalEmailException;
import ruby.musicroost.valid.CoursePattern;
import ruby.musicroost.valid.NamePattern;
import ruby.musicroost.valid.PhonePattern;

import javax.validation.constraints.Email;

@Getter @Setter
@NoArgsConstructor
public class StudentSignUp {

    @NamePattern
    private String name;
    @Email(message = IllegalEmailException.MESSAGE)
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

package ruby.musicroost.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ruby.musicroost.exception.common.IllegalEmailException;
import ruby.musicroost.valid.CoursePattern;
import ruby.musicroost.valid.PhonePattern;

import javax.validation.constraints.Email;

@Getter @Setter
@NoArgsConstructor
public class StudentEdit {

    @PhonePattern
    private String phoneNumber;
    @Email(message = IllegalEmailException.MESSAGE)
    private String email;
    @CoursePattern
    private String course;
    private Long teacherId;

    @Builder
    public StudentEdit(String phoneNumber, String email, String course, Long teacherId) {
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.course = course;
        this.teacherId = teacherId;
    }
}

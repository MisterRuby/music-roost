package ruby.musicroost.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import ruby.musicroost.repository.ScheduleRepository;
import ruby.musicroost.repository.StudentRepository;
import ruby.musicroost.repository.TeacherRepository;

@AutoConfigureMockMvc
@SpringBootTest
@WithMockUser(username = "ruby", roles = "ADMIN")       // security 적용으로 인해 테스트시 MockUser 적용
@Transactional
public class ControllerTest {

    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    protected StudentRepository studentRepository;
    @Autowired
    protected TeacherRepository teacherRepository;
    @Autowired
    protected ScheduleRepository scheduleRepository;
    @Autowired
    protected ObjectMapper mapper;

    @BeforeEach
    void clean() {
        scheduleRepository.deleteAll();
        studentRepository.deleteAll();
        teacherRepository.deleteAll();
    }
}

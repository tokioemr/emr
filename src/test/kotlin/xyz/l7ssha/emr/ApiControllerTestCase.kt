package xyz.l7ssha.emr

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.test.web.servlet.MockMvc

@Import(EmrTestConfiguration::class)
@AutoConfigureMockMvc
@SpringBootTest
class ApiControllerTestCase {
    @Autowired
    protected lateinit var mockMvc: MockMvc
}

package xyz.l7ssha.emr

import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.Import
import org.springframework.test.web.servlet.MockMvc
import xyz.l7ssha.emr.controller.AuthController
import xyz.l7ssha.emr.service.AuthService

@Import(TestConfiguration::class)
@WebMvcTest(controllers = [AuthController::class])
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
open class ApiControllerTestCase {
    @MockBean
    protected lateinit var authService: AuthService

    @Autowired
    protected lateinit var mockMvc: MockMvc
}

package xyz.l7ssha.emr.controller

import com.fasterxml.jackson.databind.ObjectMapper
import org.hamcrest.CoreMatchers.containsString
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.post
import xyz.l7ssha.emr.ApiControllerTestCase

class AuthControllerTest : ApiControllerTestCase() {
    @Test
    fun loginEmptyBody() {
        mockMvc.post("/api/auth/login").andExpect { status { isEqualTo(400) } }
    }

    @Test
    fun loginInvalidData() {
        val json = ObjectMapper().writeValueAsString(
            mapOf(
                "email" to "test",
                "password" to "password"
            )
        )

        mockMvc.post("/api/auth/login") {
            content = json
            contentType = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isUnprocessableEntity() }
            content { string(containsString("must be a well-formed email address")) }
        }
    }
}

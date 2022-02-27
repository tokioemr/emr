package xyz.l7ssha.emr.controller

import org.hamcrest.Matchers
import org.junit.jupiter.api.Test
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.get
import xyz.l7ssha.emr.ApiControllerTestCase

class UserControllerTest : ApiControllerTestCase() {
    @Test
    @WithMockUser(username = "admin@example.com", authorities = ["VIEW_USERS"])
    fun getUsersTest() {
        mockMvc.get("/api/users").andExpect {
            status { isOk() }
            content { jsonPath("$.data.size()", Matchers.equalTo(0)) }
        }
    }

    @Test
    @WithMockUser(username = "admin@example.com", authorities = [])
    fun getUsersWithoutPermissionsTest() {
        mockMvc.get("/api/users").andExpect {
            status { isForbidden() }
        }
    }
}

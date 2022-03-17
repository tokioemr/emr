package xyz.l7ssha.emr.controller

import org.hamcrest.Matchers
import org.junit.jupiter.api.Test
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.get
import xyz.l7ssha.emr.ApiControllerTestCase

class UserControllerTest : ApiControllerTestCase() {
    @Test
    @WithMockUser(authorities = ["VIEW_USERS"])
    fun getUsersTest() {
        mockMvc.get("/api/users").andExpect {
            status { isOk() }
            content { jsonPath("$.data.size()", Matchers.equalTo(1)) }
        }
    }

    @Test
    @WithMockUser
    fun getUsersWithoutPermissionsTest() {
        mockMvc.get("/api/users").andExpect {
            status { isForbidden() }
        }
    }
}

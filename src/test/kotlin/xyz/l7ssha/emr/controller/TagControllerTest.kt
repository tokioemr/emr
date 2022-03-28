package xyz.l7ssha.emr.controller

import org.hamcrest.Matchers
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import xyz.l7ssha.emr.ApiControllerTestCase

class TagControllerTest : ApiControllerTestCase() {
    @Test
    @WithMockUser
    fun createTagMissingPermissions() {
        mockMvc.post("/api/tags") {
            content = "{\"name\": \"test tag\"}"
            contentType = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isForbidden() }
        }
    }

    @Test
    @WithMockUser(authorities = ["CREATE_TAGS"])
    fun createTag() {
        mockMvc.post("/api/tags") {
            content = "{\"name\": \"test tag\"}"
            contentType = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isCreated() }
            content { jsonPath("$.name", Matchers.equalTo("test tag")) }
        }

        mockMvc.get("/api/tags").andExpect {
            status { isOk() }
            content { jsonPath("$.data.size()", Matchers.equalTo(1)) }
        }
    }
}

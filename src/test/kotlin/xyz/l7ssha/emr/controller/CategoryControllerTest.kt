package xyz.l7ssha.emr.controller

import org.hamcrest.Matchers
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.delete
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import xyz.l7ssha.emr.ApiControllerTestCase

class CategoryControllerTest : ApiControllerTestCase() {
    @Test
    @WithMockUser
    fun getCategories() {
        mockMvc.get("/api/categories").andExpect {
            status { isOk() }
            content { jsonPath("$.data.size()", Matchers.equalTo(0)) }
        }
    }

    @Test
    @WithMockUser
    fun createCategoryMissingPermissions() {
        mockMvc.post("/api/categories") {
            content = "{ \"name\": \"test\"}"
            contentType = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isForbidden() }
        }
    }

    @Test
    @WithMockUser(authorities = ["CREATE_CATEGORIES"])
    fun createCategorySuccess() {
        mockMvc.post("/api/categories") {
            content = "{ \"name\": \"test\"}"
            contentType = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isCreated() }
            content {
                jsonPath("$.name", Matchers.equalTo("test"))
                jsonPath("$.parent", Matchers.nullValue())
            }
        }
    }

    @Test
    @WithMockUser(authorities = ["REMOVE_CATEGORIES"])
    fun removeCategorySuccess() {
        mockMvc.delete("/api/categories/1").andExpect {
            status { isOk() }
        }
    }
}

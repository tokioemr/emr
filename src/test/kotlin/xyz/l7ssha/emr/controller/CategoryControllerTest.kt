package xyz.l7ssha.emr.controller

import org.hamcrest.Matchers
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.delete
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.patch
import org.springframework.test.web.servlet.post
import xyz.l7ssha.emr.ApiControllerTestCase

class CategoryControllerTest : ApiControllerTestCase() {
    @Test
    @WithMockUser
    fun getCategories() {
        mockMvc.get("/api/categories").andExpect {
            status { isOk() }
            content { jsonPath("$.data.size()", Matchers.equalTo(2)) }
        }
    }

    @Test
    @WithMockUser
    fun createCategoryMissingPermissions() {
        mockMvc.post("/api/categories") {
            content = "{\"name\": \"test\"}"
            contentType = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isForbidden() }
        }
    }

    @Test
    @WithMockUser(authorities = ["CREATE_CATEGORIES"])
    fun createCategorySuccess() {
        mockMvc.post("/api/categories") {
            content = "{\"name\": \"test\"}"
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
    @WithMockUser(authorities = ["CREATE_CATEGORIES"])
    fun patchCategory() {
        mockMvc.patch("/api/categories/1") {
            content = "{\"name\": \"test1\"}"
            contentType = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isOk() }
            content {
                jsonPath("$.name", Matchers.equalTo("test1"))
                jsonPath("$.parent", Matchers.nullValue())
            }
        }

        mockMvc.patch("/api/categories/1") {
            content = "{\"parent\": 2}"
            contentType = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isOk() }
            content {
                jsonPath("$.name", Matchers.equalTo("test1"))
                jsonPath("$.parent.name", Matchers.equalTo("test2"))
            }
        }
    }

    @Test
    @WithMockUser(authorities = ["REMOVE_CATEGORIES"])
    @Disabled("Broken controller advice handling")
    fun removeCategorySuccess() {
        mockMvc.get("/api/categories/3") { accept = MediaType.ALL}.andExpect {
            status { isOk() }
        }

        mockMvc.delete("/api/categories/3").andExpect {
            status { isOk() }
        }

        mockMvc.get("/api/categories/3").andExpect {
            status { isNotFound() }
        }
    }
}

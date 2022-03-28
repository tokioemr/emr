package xyz.l7ssha.emr.controller

import org.hamcrest.Matchers
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import xyz.l7ssha.emr.ApiControllerTestCase

class FeatureGroupControllerTest : ApiControllerTestCase() {
    @Test
    @WithMockUser
    fun createFeatureGroupMissingPermissions() {
        mockMvc.post("/api/product_feature_groups") {
            content = "{\"name\": \"test group\", \"description\": \"test description\"}"
            contentType = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isForbidden() }
        }
    }

    @Test
    @WithMockUser
    fun createFeatureGroupMissingDescription() {
        mockMvc.post("/api/product_feature_groups") {
            content = "{\"name\": \"test tag\"}"
            contentType = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isUnprocessableEntity() }
        }
    }

    @Test
    @WithMockUser(authorities = ["CREATE_FEATURES"])
    fun createFeatureGroup() {
        mockMvc.post("/api/product_feature_groups") {
            content = "{\"name\": \"test group\", \"description\": \"test description\"}"
            contentType = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isCreated() }
            content {
                jsonPath("$.name", Matchers.equalTo("test group"))
                jsonPath("$.description", Matchers.equalTo("test description"))
            }
        }

        mockMvc.get("/api/product_feature_groups").andExpect {
            status { isOk() }
            content {
                jsonPath("$.data.size()", Matchers.equalTo(1))
                jsonPath("$.data[0].name", Matchers.equalTo("test group"))
                jsonPath("$.data[0].description", Matchers.equalTo("test description"))
            }
        }
    }
}

package xyz.l7ssha.emr.configuration

import com.fasterxml.jackson.annotation.JsonInclude
import org.openapitools.jackson.nullable.JsonNullableModule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder

@Configuration
class JacksonConfiguration {
    @Bean
    fun objectMapperBuilder(): Jackson2ObjectMapperBuilder {
        return Jackson2ObjectMapperBuilder().apply {
            serializationInclusion(JsonInclude.Include.ALWAYS)
                .modulesToInstall(JsonNullableModule())
        }
    }
}

package xyz.l7ssha.emr.configuration

import org.springframework.context.annotation.Configuration
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import xyz.l7ssha.emr.configuration.resolver.RSQLSpecificationResolver

@Configuration
internal class WebConfiguration : WebMvcConfigurer {
    override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver>) {
        resolvers.add(RSQLSpecificationResolver())
    }
}

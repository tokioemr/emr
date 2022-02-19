package xyz.l7ssha.emr.validation

import javax.validation.Constraint
import kotlin.reflect.KClass

@Constraint(validatedBy = [NullOrNotBlankValidator::class])
@Target(AnnotationTarget.FIELD, AnnotationTarget.TYPE)
@Retention(AnnotationRetention.RUNTIME)
annotation class NullOrNotBlank(
    val message: String = "must not be blank",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<*>> = []
)

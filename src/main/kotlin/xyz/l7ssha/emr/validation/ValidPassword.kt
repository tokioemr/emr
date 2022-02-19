package xyz.l7ssha.emr.validation

import javax.validation.Constraint
import kotlin.reflect.KClass

@Constraint(validatedBy = [ValidPasswordValidator::class])
@Target(AnnotationTarget.FIELD, AnnotationTarget.PROPERTY, AnnotationTarget.TYPE)
@Retention(AnnotationRetention.RUNTIME)
annotation class ValidPassword(
    val message: String = "Invalid Password",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<*>> = []
)

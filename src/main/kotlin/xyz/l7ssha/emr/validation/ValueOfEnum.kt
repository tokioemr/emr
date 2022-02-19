package xyz.l7ssha.emr.validation

import javax.validation.Constraint
import kotlin.reflect.KClass

@Target(AnnotationTarget.FIELD, AnnotationTarget.TYPE)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [ValueOfEnumValidator::class])
annotation class ValueOfEnum(
    val enumClass: KClass<out Enum<*>>,

    val message: String = "must be any of enum {enumClass}",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<*>> = []
)

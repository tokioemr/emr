package xyz.l7ssha.emr.validation

import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

class ValueOfEnumValidator : ConstraintValidator<ValueOfEnum, CharSequence?> {
    lateinit var acceptedValues: List<String>

    override fun initialize(annotation: ValueOfEnum) {
        acceptedValues = annotation.enumClass.java.enumConstants
            .map { it.name }
    }

    override fun isValid(value: CharSequence?, context: ConstraintValidatorContext): Boolean {
        return if (value == null) {
            true
        } else acceptedValues.contains(value.toString())
    }
}

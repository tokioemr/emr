package xyz.l7ssha.emr.validation

import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

class NullOrNotBlankValidator : ConstraintValidator<NullOrNotBlank, String?> {
    override fun isValid(value: String?, constraintValidatorContext: ConstraintValidatorContext): Boolean {
        return value == null || value.trim().isNotEmpty()
    }
}

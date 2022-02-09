package xyz.l7ssha.emr.validation

import org.passay.*
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext


class ValidPasswordValidator: ConstraintValidator<ValidPassword, String> {
    override fun isValid(value: String, context: ConstraintValidatorContext): Boolean {
        val validator = PasswordValidator(
            LengthRule(8, 16),
            CharacterRule(EnglishCharacterData.LowerCase, 1),
            CharacterRule(EnglishCharacterData.UpperCase, 1),
            CharacterRule(EnglishCharacterData.Digit, 1),
            WhitespaceRule(),
        )

        val result = validator.validate(PasswordData(value))
        if (result.isValid) {
            return true
        }

        context.buildConstraintViolationWithTemplate(validator.getMessages(result).joinToString(","))
            .addConstraintViolation()
            .disableDefaultConstraintViolation()

        return false
    }
}

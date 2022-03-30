package xyz.l7ssha.emr.validation

import org.passay.*
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

private const val MIN_PASSWORD_LENGTH = 8
private const val MAX_PASSWORD_LENGTH = 16

class ValidPasswordValidator : ConstraintValidator<ValidPassword, String> {
    override fun isValid(value: String, context: ConstraintValidatorContext): Boolean {
        val passwordValidator = PasswordValidator(
            LengthRule(MIN_PASSWORD_LENGTH, MAX_PASSWORD_LENGTH),
            CharacterRule(EnglishCharacterData.LowerCase, 1),
            CharacterRule(EnglishCharacterData.UpperCase, 1),
            CharacterRule(EnglishCharacterData.Digit, 1),
            WhitespaceRule(),
        )

        val result = passwordValidator.validate(PasswordData(value))
        if (result.isValid) {
            return true
        }

        context.buildConstraintViolationWithTemplate(passwordValidator.getMessages(result).joinToString(","))
            .addConstraintViolation()
            .disableDefaultConstraintViolation()

        return false
    }
}

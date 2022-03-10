package xyz.l7ssha.emr.validation

import org.passay.PasswordData
import org.passay.PasswordValidator
import org.springframework.beans.factory.annotation.Autowired
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

class ValidPasswordValidator : ConstraintValidator<ValidPassword, String> {
    @Autowired
    lateinit var passwordValidator: PasswordValidator

    override fun isValid(value: String, context: ConstraintValidatorContext): Boolean {
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

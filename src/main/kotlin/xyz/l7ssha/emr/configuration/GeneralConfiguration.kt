package xyz.l7ssha.emr.configuration

import org.passay.*
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

private const val MIN_PASSWORD_LENGTH = 8
private const val MAX_PASSWORD_LENGTH = 16

@Configuration
class GeneralConfiguration {
    @Bean
    fun passwordValidator(): PasswordValidator = PasswordValidator(
        LengthRule(MIN_PASSWORD_LENGTH, MAX_PASSWORD_LENGTH),
        CharacterRule(EnglishCharacterData.LowerCase, 1),
        CharacterRule(EnglishCharacterData.UpperCase, 1),
        CharacterRule(EnglishCharacterData.Digit, 1),
        WhitespaceRule(),
    )
}

package xyz.l7ssha.emr.events.commands

data class ConfirmForgotPasswordCommand(val email: String, val password: String, val token: String)

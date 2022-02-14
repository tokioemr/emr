package xyz.l7ssha.emr.events.commands

data class SendEmailCommand(val email: String, val subject: String, val body: String)

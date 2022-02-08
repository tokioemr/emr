package xyz.l7ssha.emr.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class ApiIndexController {
    @GetMapping
    fun indexAction() = "OK"
}

package xyz.l7ssha.emr.controller.api

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import xyz.l7ssha.emr.dto.user.UserOutputDto
import xyz.l7ssha.emr.dto.user.UserPatchInputDto
import xyz.l7ssha.emr.mapper.UserMapper
import xyz.l7ssha.emr.repositories.UserRepository

@RestController
@RequestMapping("/api/users")
@PreAuthorize("hasAuthority('VIEW_USERS')")
class UserController(@Autowired val userMapper: UserMapper, @Autowired val userRepository: UserRepository) {
    @GetMapping("/{id}")
    fun getUserAction(@PathVariable id: Long): UserOutputDto = userMapper.userToUserOutputDto(userRepository.getById(id))

    @GetMapping
    fun getUsersAction(pageable: Pageable) = userRepository.findAll(pageable).map { userMapper.userToUserOutputDto(it) }

    @PatchMapping("/{id}")
    @PreAuthorize("#id == authentication.principal.id or hasAuthority('ADMIN')")
    fun patchUser(@PathVariable id: Long, @RequestBody patchDto: UserPatchInputDto): UserOutputDto {
        val user = userRepository.getById(id)

        return userMapper.userToUserOutputDto(
            userRepository.save(userMapper.updateUserFromPatchDto(user, patchDto))
        )
    }
}

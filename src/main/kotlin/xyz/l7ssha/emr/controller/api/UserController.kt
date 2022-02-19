package xyz.l7ssha.emr.controller.api

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import xyz.l7ssha.emr.dto.user.UserCreateInputDto
import xyz.l7ssha.emr.dto.user.UserOutputDto
import xyz.l7ssha.emr.dto.user.UserPatchInputDto
import xyz.l7ssha.emr.entities.User
import xyz.l7ssha.emr.mapper.UserMapper
import xyz.l7ssha.emr.repositories.UserRepository
import javax.validation.Valid

@RestController
@RequestMapping("/api/users")
@PreAuthorize("hasAuthority('VIEW_USERS')")
class UserController(@Autowired val userMapper: UserMapper, @Autowired val userRepository: UserRepository) {
    @GetMapping("/{id}")
    fun getUserAction(@PathVariable id: Long): UserOutputDto = userMapper.userToUserOutputDto(userRepository.getById(id))

    @GetMapping
    fun getUsersAction(pageable: Pageable) = userRepository.findAll(pageable).map { userMapper.userToUserOutputDto(it) }

    @PostMapping
    @PreAuthorize("hasAuthority('CREATE_USERS')")
    @ResponseStatus(HttpStatus.CREATED)
    fun postUser(@RequestBody @Valid createDto: UserCreateInputDto): User {
        val user = userMapper.createUserFromDto(createDto)

        return userRepository.save(user)
    }

    @PatchMapping("/{id}")
    @PreAuthorize("#id == authentication.principal.id or hasAuthority('CREATE_USERS')")
    fun patchUser(@PathVariable id: Long, @RequestBody @Valid patchDto: UserPatchInputDto): UserOutputDto {
        val user = userRepository.getById(id)

        return userMapper.userToUserOutputDto(
            userRepository.save(userMapper.updateUserFromPatchDto(user, patchDto))
        )
    }
}

package xyz.l7ssha.emr.service.entity

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Component
import xyz.l7ssha.emr.entities.user.User
import xyz.l7ssha.emr.repositories.EmrRepository
import javax.persistence.EntityNotFoundException

@Component
abstract class EntityService<T : Any> {
    @Autowired
    protected lateinit var repository: EmrRepository<T>

    @Suppress("UnusedPrivateMember")
    fun delete(entity: T, user: User) = repository.delete(entity)

    fun save(entity: T) = repository.save(entity)

    fun findById(id: Long) = repository.findById(id)

    fun getById(id: Long): T = findById(id).orElseThrow { EntityNotFoundException() }

    fun findAll(specification: Specification<T>?, pageable: Pageable) = repository.findAll(
        specification,
        pageable
    )
}

package xyz.l7ssha.emr.service.entity

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Component
import xyz.l7ssha.emr.entities.AbstractSoftDelete
import xyz.l7ssha.emr.entities.user.User
import xyz.l7ssha.emr.repositories.EmrRepository
import java.time.Instant
import javax.persistence.EntityNotFoundException

@Component
abstract class SoftDeletableEntityService<T : AbstractSoftDelete> {
    @Autowired
    protected lateinit var repository: EmrRepository<T>

    fun delete(entity: T, user: User) {
        entity.deletedAt = Instant.now()
        entity.updatedAt = entity.deletedAt
        entity.updatedBy = user

        repository.save(entity)
    }

    fun save(entity: T) = repository.save(entity)

    fun findById(id: Long) = repository.findOne { root, _, criteriaBuilder ->
        criteriaBuilder.and(
            criteriaBuilder.equal(root.get<Long>("id"), id),
            criteriaBuilder.isNull(root.get<Any>("deletedAt"))
        )
    }

    fun getById(id: Long): T = findById(id).orElseThrow { EntityNotFoundException() }

    fun findAll(specification: Specification<T>?, pageable: Pageable) = repository.findAll(
        Specification.where<T> { root, _, criteriaBuilder -> criteriaBuilder.isNull(root.get<Any>("deletedAt")) }
            .and(specification),
        pageable
    )
}

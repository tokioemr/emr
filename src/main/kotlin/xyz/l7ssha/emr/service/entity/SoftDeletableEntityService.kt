package xyz.l7ssha.emr.service.entity

import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Component
import xyz.l7ssha.emr.entities.AbstractSoftDelete
import xyz.l7ssha.emr.entities.user.User
import java.time.Instant

@Component
abstract class SoftDeletableEntityService<T : AbstractSoftDelete>: EntityService<T>() {
    protected val notDeletedSpec by lazy {
        Specification.where<T> { root, _, criteriaBuilder -> criteriaBuilder.isNull(root.get<Any>("deletedAt")) }
    }

    override fun delete(entity: T, user: User) {
        entity.deletedAt = Instant.now()
        entity.updatedAt = entity.deletedAt
        entity.updatedBy = user

        repository.save(entity)
    }

    override fun findById(id: Long) = repository.findOne(
        notDeletedSpec.and { root, _, criteriaBuilder ->
            criteriaBuilder.equal(root.get<Long>("id"), id)
        }
    )

    override fun findAll(specification: Specification<T>?, pageable: Pageable) = repository.findAll(
        notDeletedSpec.and(specification),
        pageable
    )
}

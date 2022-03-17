package xyz.l7ssha.emr.repositories

import org.springframework.data.repository.NoRepositoryBean
import xyz.l7ssha.emr.entities.AbstractSoftDelete
import xyz.l7ssha.emr.entities.user.User
import java.time.Instant
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

interface SoftDeletableRepository<T : AbstractSoftDelete> {
    fun softDelete(entity: T, user: User)
}

@NoRepositoryBean
class SoftDeletableRepositoryImpl<T : AbstractSoftDelete> : SoftDeletableRepository<T> {
    @PersistenceContext
    lateinit var entityManager: EntityManager

    override fun softDelete(entity: T, user: User) {
        entity.deletedAt = Instant.now()
        entity.updatedAt = entity.deletedAt
        entity.updatedBy = user

        entityManager.persist(entity)
    }
}

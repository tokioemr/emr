package xyz.l7ssha.emr.repositories

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.repository.NoRepositoryBean
import xyz.l7ssha.emr.entities.AbstractSoftDelete
import xyz.l7ssha.emr.entities.user.User
import java.time.Instant

@NoRepositoryBean
interface SoftDeletableRepository<T : AbstractSoftDelete> : JpaRepository<T, Long>, JpaSpecificationExecutor<T> {
    fun softDelete(entity: T, user: User) {
        entity.deletedAt = Instant.now()
        entity.updatedAt = entity.deletedAt
        entity.updatedBy = user

        save(entity)
    }
}

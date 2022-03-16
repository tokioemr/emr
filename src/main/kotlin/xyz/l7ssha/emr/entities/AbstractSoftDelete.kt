package xyz.l7ssha.emr.entities

import java.time.Instant
import javax.persistence.Column
import javax.persistence.MappedSuperclass

@MappedSuperclass
open class AbstractSoftDelete : AbstractAuditableEntity() {
    @Column(name = "deleted_at", nullable = true)
    open var deletedAt: Instant? = null

    /** True of entity is soft deleted */
    fun isDeleted() = deletedAt != null
}

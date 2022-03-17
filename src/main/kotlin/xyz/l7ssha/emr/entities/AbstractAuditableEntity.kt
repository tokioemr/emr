package xyz.l7ssha.emr.entities

import org.hibernate.annotations.Fetch
import org.hibernate.annotations.FetchMode
import xyz.l7ssha.emr.entities.user.User
import java.time.Instant
import javax.persistence.*

@Suppress("UnnecessaryAbstractClass")
@MappedSuperclass
abstract class AbstractAuditableEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    @Fetch(FetchMode.JOIN)
    open var createdBy: User? = null

    @Column(name = "created_at", updatable = false, nullable = false)
    open val createdAt: Instant = Instant.now()

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updated_by")
    @Fetch(FetchMode.JOIN)
    open var updatedBy: User? = null

    @Column(name = "updated_at", nullable = true)
    open var updatedAt: Instant? = null
}

package xyz.l7ssha.emr.entities.products

import xyz.l7ssha.emr.entities.AbstractAuditableEntity
import javax.persistence.*

@Entity
@Table(name = "product_tags")
open class Tag(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    open val id: Long,

    @Column(name = "name", nullable = false, unique = true)
    open var name: String
) : AbstractAuditableEntity()

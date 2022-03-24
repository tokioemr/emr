package xyz.l7ssha.emr.entities.products

import org.hibernate.annotations.Fetch
import org.hibernate.annotations.FetchMode
import xyz.l7ssha.emr.entities.AbstractAuditableEntity
import javax.persistence.*

@Entity
@Table(name = "product_prices")
open class Price(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    open val id: Long,

    @Column(name = "value", nullable = false)
    open var value: Double,

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "currencies", referencedColumnName = "id")
    @Fetch(FetchMode.JOIN)
    open val currency: Currency,
) : AbstractAuditableEntity()

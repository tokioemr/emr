package xyz.l7ssha.emr.entities.products.feature

import org.hibernate.annotations.Fetch
import org.hibernate.annotations.FetchMode
import xyz.l7ssha.emr.entities.AbstractAuditableEntity
import javax.persistence.*

@Entity
@Table(name = "product_feature_values")
open class FeatureValue(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    open val id: Long,

    @Column(name = "name", nullable = false, length = 30)
    open var value: String,

    @ManyToOne
    @JoinColumn(name = "product_feature_groups", referencedColumnName = "id")
    @Fetch(FetchMode.JOIN)
    open var feature: Feature,
) : AbstractAuditableEntity()

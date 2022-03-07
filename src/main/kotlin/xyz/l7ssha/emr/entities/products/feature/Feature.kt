package xyz.l7ssha.emr.entities.products.feature

import org.hibernate.annotations.Fetch
import org.hibernate.annotations.FetchMode
import xyz.l7ssha.emr.entities.AbstractAuditableEntity
import javax.persistence.*

@Entity
@Table(name = "product_features")
open class Feature(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    open val id: Long,

    @Column(name = "name", nullable = false, unique = true)
    open var name: String,

    @Column(name = "title", nullable = true)
    open var description: String?,

    @ManyToOne
    @JoinColumn(name = "product_feature_groups", referencedColumnName = "id")
    @Fetch(FetchMode.JOIN)
    open var group: FeatureGroup,
) : AbstractAuditableEntity()

package xyz.l7ssha.emr.entities.products.feature

import org.hibernate.annotations.Fetch
import org.hibernate.annotations.FetchMode
import xyz.l7ssha.emr.entities.AbstractAuditableEntity
import javax.persistence.*

@Entity
@Table(name = "product_feature_groups")
open class FeatureGroup(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    open val id: Long,

    @Column(name = "name", nullable = false, unique = true)
    open var name: String,

    @Column(name = "title", nullable = false)
    open var description: String,

    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JoinColumn(name = "product_features", referencedColumnName = "id")
    @Fetch(FetchMode.SUBSELECT)
    open val features: List<Feature>,
) : AbstractAuditableEntity()

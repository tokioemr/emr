package xyz.l7ssha.emr.entities.products

import org.hibernate.annotations.Fetch
import org.hibernate.annotations.FetchMode
import xyz.l7ssha.emr.entities.AbstractSoftDelete
import xyz.l7ssha.emr.entities.products.feature.FeatureValue
import javax.persistence.*

@Entity
@Table(name = "products", indexes = [Index(columnList = "sku", unique = true)])
open class Product(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = true)
    open val id: Long,

    @Column(name = "name", nullable = false, unique = true, length = 30)
    open var name: String,

    @Column(name = "description", nullable = false, length = 16384)
    open var description: String,

    @Column(name = "sku", nullable = false, unique = true, updatable = false, length = 30)
    open val sku: String,

    @Column(name = "stock", nullable = false)
    open val stock: Int = 0,

    @Column(name = "producer", nullable = false, length = 30)
    open val producer: String,

    @Column(name = "model", nullable = false, length = 30)
    open val model: String,

    @Column(name = "producer_product_number", nullable = false, length = 30)
    open val producerProductNumber: String,

    @Column(name = "condition", nullable = false)
    @Enumerated(EnumType.STRING)
    open val condition: ProductCondition,

    @Column(name = "ean", nullable = false, length = 15)
    open val ean: String,

    @ManyToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JoinColumn(name = "product_tags", referencedColumnName = "id")
    @Fetch(FetchMode.SUBSELECT)
    open val tags: List<Tag> = mutableListOf(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_categories", referencedColumnName = "id")
    @Fetch(FetchMode.JOIN)
    open val category: Category,

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_feature_values", referencedColumnName = "id")
    @Fetch(FetchMode.SUBSELECT)
    open val featuresValues: List<FeatureValue> = mutableListOf(),

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_prices", referencedColumnName = "id")
    @Fetch(FetchMode.SUBSELECT)
    open val prices: List<Price> = mutableListOf(),

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_images", referencedColumnName = "id")
    @Fetch(FetchMode.SUBSELECT)
    open val images: List<Image> = mutableListOf(),
) : AbstractSoftDelete()

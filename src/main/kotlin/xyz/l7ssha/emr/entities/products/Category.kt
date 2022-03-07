package xyz.l7ssha.emr.entities.products

import org.hibernate.annotations.Fetch
import org.hibernate.annotations.FetchMode
import xyz.l7ssha.emr.entities.AbstractSoftDelete
import javax.persistence.*

@Entity
@Table(name = "product_categories", indexes = [Index(columnList = "parent")])
open class Category(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    open val id: Long,

    @Column(name = "name", nullable = false, unique = true)
    open var name: String,

    @ManyToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "parent", referencedColumnName = "id")
    @Fetch(FetchMode.JOIN)
    open var parent: Category? = null,

    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
    open val children: List<Category> = mutableListOf(),

    @Column(name = "assignable", nullable = false)
    open var assignable: Boolean = false
) : AbstractSoftDelete()

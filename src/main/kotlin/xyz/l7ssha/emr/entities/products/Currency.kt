package xyz.l7ssha.emr.entities.products

import xyz.l7ssha.emr.entities.AbstractSoftDelete
import javax.persistence.*

@Entity
@Table(name = "currencies")
open class Currency(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = true)
    open val id: Long,

    @Column(name = "name", nullable = false, unique = true, length = 10)
    open var name: String,

    @Column(name = "symbol", nullable = false, unique = true, length = 3)
    open var symbol: String,

    @Column(name = "alternative_symbol", nullable = false, unique = true, length = 3)
    open var alternativeSymbol: String,
) : AbstractSoftDelete()

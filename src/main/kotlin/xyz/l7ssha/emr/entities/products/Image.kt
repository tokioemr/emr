package xyz.l7ssha.emr.entities.products

import java.util.*
import javax.persistence.*

@Entity
@Table(name = "product_images")
open class Image(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = true)
    open val id: Long,

    @Column(name = "extension", nullable = false, length = 5)
    @Enumerated(EnumType.STRING)
    open val extension: ImageExtension,

    @Column(name = "name", nullable = false, unique = true, length = 32)
    open var hash: String,

    @Column(name = "external_id", nullable = false, unique = true)
    open var externalId: UUID,
)

package xyz.l7ssha.emr.repositories

import org.springframework.stereotype.Repository
import xyz.l7ssha.emr.entities.products.Image
import java.util.*

@Repository
interface ImageRepository : EmrRepository<Image> {
    fun getByExternalId(id: UUID): Image
    fun getByHash(hash: String): Image
}

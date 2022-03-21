package xyz.l7ssha.emr.service.image

import org.springframework.stereotype.Component
import java.util.*

@Component
interface ImageStorageAdapter {
    fun getImageBytesForId(id: UUID): ByteArray
    fun saveBytesForImage(id: UUID, imageData: ByteArray)
}

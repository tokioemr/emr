package xyz.l7ssha.emr.service.image

import org.springframework.stereotype.Component
import java.io.File
import java.io.FileOutputStream
import java.util.*

@Component
class LocalImageStorageAdapter : ImageStorageAdapter {
    override fun getImageBytesForId(id: UUID): ByteArray {
        return File("/tmp/$id").readBytes()
    }

    override fun saveBytesForImage(id: UUID, imageData: ByteArray) {
        File("/tmp/$id").apply {
            FileOutputStream(this).use { outputStream ->
                outputStream.write(imageData)
                outputStream.flush()
            }
        }
    }
}

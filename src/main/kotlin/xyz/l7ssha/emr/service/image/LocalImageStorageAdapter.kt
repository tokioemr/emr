package xyz.l7ssha.emr.service.image

import org.springframework.stereotype.Component
import xyz.l7ssha.emr.configuration.exception.EmrNotFoundException
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.util.*

@Component
class LocalImageStorageAdapter : ImageStorageAdapter {
    override fun getImageBytesForId(id: UUID): ByteArray {
        return try {
            File("/tmp/$id").readBytes()
        } catch (_: FileNotFoundException) {
            throw EmrNotFoundException("Image with id: $id does not exist")
        }
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

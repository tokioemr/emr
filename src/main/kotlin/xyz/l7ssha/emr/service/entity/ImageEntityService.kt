package xyz.l7ssha.emr.service.entity

import javassist.NotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils
import org.springframework.web.multipart.MultipartFile
import xyz.l7ssha.emr.entities.products.Image
import xyz.l7ssha.emr.entities.products.ImageExtension
import xyz.l7ssha.emr.repositories.ImageRepository
import xyz.l7ssha.emr.service.image.ImageStorageAdapter
import xyz.l7ssha.emr.valueobject.ImageDataTransportValueObject
import java.math.BigInteger
import java.security.MessageDigest
import java.util.*

@Service
class ImageEntityService(
    @Autowired val imageStorageAdapter: ImageStorageAdapter,
    @Autowired val imageRepository: ImageRepository
) {
    fun getMetadataById(id: UUID) = imageRepository.getByExternalId(id)

    fun saveImage(multipartFile: MultipartFile): Image {
        val extension = StringUtils.cleanPath(multipartFile.originalFilename ?: "").let {
            StringUtils.getFilenameExtension(it)
        }!!

        val dataHash = md5(multipartFile.bytes)
        val externalId = UUID.randomUUID()

        try {
            return imageRepository.getByHash(dataHash)
        } catch (_: NotFoundException) {}

        imageStorageAdapter.saveBytesForImage(externalId, multipartFile.bytes)

        return Image(
            0L,
            ImageExtension.valueOf(extension.uppercase()),
            dataHash,
            externalId
        ).apply {
            imageRepository.save(this)
        }
    }

    fun retrieveImageData(id: UUID): ImageDataTransportValueObject {
        val imageMetadata = getMetadataById(id)

        val imageData = imageStorageAdapter.getImageBytesForId(id)
        val contentType = when (imageMetadata.extension) {
            ImageExtension.JPG, ImageExtension.JPEG -> MediaType.IMAGE_JPEG
            ImageExtension.PNG -> MediaType.IMAGE_PNG
            ImageExtension.WEBP, ImageExtension.GIF -> MediaType.IMAGE_GIF
            ImageExtension.UNKNOWN -> MediaType.APPLICATION_OCTET_STREAM
        }

        return ImageDataTransportValueObject(contentType, imageData)
    }

    @Suppress("MagicNumber")
    private fun md5(input: ByteArray): String {
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest(input)).toString(16).padStart(32, '0')
    }
}

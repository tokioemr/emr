package xyz.l7ssha.emr.valueobject

import org.springframework.http.MediaType

data class ImageDataTransportValueObject(val mediaType: MediaType, val imageData: ByteArray) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ImageDataTransportValueObject

        if (mediaType != other.mediaType) return false
        if (!imageData.contentEquals(other.imageData)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = mediaType.hashCode()
        result = 31 * result + imageData.contentHashCode()
        return result
    }
}

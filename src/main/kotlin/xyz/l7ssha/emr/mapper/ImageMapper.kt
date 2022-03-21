package xyz.l7ssha.emr.mapper

import org.springframework.stereotype.Component
import xyz.l7ssha.emr.dto.image.ImageOutputDto
import xyz.l7ssha.emr.entities.products.Image

@Component
class ImageMapper {
    fun imageToOutputDto(image: Image): ImageOutputDto =
        ImageOutputDto(image.externalId, image.hash, image.extension.value)
}

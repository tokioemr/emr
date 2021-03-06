package xyz.l7ssha.emr.controller.api.product

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import xyz.l7ssha.emr.configuration.security.AuthenticationFacade
import xyz.l7ssha.emr.mapper.ImageMapper
import xyz.l7ssha.emr.service.entity.ImageEntityService
import java.util.*

@RestController
@RequestMapping("/api/images")
class ImageController(
    @Autowired val imageService: ImageEntityService,
    @Autowired val imageMapper: ImageMapper
) {
    @GetMapping("/{id}/metadata")
    fun getMetadataByExternalIdAction(@PathVariable id: UUID) =
        imageService.getMetadataById(id)
            .let { imageMapper.imageToOutputDto(it) }

    @GetMapping("/{id}")
    fun getItemAction(@PathVariable id: UUID): ResponseEntity<ByteArray> {
        val (mediaType, imageData) = imageService.retrieveImageData(id)

        return ResponseEntity.ok().contentType(mediaType).body(imageData)
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('CREATE_PRODUCTS')")
    fun postItemAction(@RequestParam("file") multipartFile: MultipartFile) =
        imageService.saveImage(multipartFile)
            .let { imageMapper.imageToOutputDto(it) }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('CREATE_PRODUCTS')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteItemAction(@PathVariable id: Long, authenticationFacade: AuthenticationFacade) =
        imageService.delete(imageService.getById(id), authenticationFacade.loggedInUser)
}

package xyz.l7ssha.emr.controller.api.product

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.*
import xyz.l7ssha.emr.configuration.security.AuthenticationFacade
import xyz.l7ssha.emr.dto.RSQLSpecification
import xyz.l7ssha.emr.dto.tag.TagCreatePatchInputDto
import xyz.l7ssha.emr.entities.products.Tag
import xyz.l7ssha.emr.mapper.TagMapper
import xyz.l7ssha.emr.service.entity.TagEntityService
import javax.validation.Valid

@RestController
@RequestMapping("/api/tags")
class TagController(
    @Autowired val tagService: TagEntityService,
    @Autowired val tagMapper: TagMapper,
    @Autowired val authenticationFacade: AuthenticationFacade
) {
    @GetMapping
    fun getAllAction(spec: RSQLSpecification<Tag>, pageable: Pageable) =
        tagService
            .findAll(spec.getFiltersAndSpecification(), pageable)
            .map { tagMapper.tagToOutputDto(it) }

    @GetMapping("/{id}")
    fun getItemAction(@PathVariable id: Long) =
        tagService.getById(id).let { tagMapper.tagToOutputDto(it) }

    @PostMapping
    fun postItemAction(@RequestBody @Valid inputDto: TagCreatePatchInputDto) =
        tagMapper.inputDtoToTag(inputDto)
            .let { tagService.save(it) }
            .let { tagMapper.tagToOutputDto(it) }

    @PatchMapping("/{id}")
    fun patchItemAction(@PathVariable id: Long, @RequestBody @Valid inputDto: TagCreatePatchInputDto) =
        tagMapper.updateTagFromInputDto(tagService.getById(id), inputDto)
            .let { tagService.save(it) }
            .let { tagMapper.tagToOutputDto(it) }

    @DeleteMapping("/{id}")
    fun deleteItemAction(@PathVariable id: Long) =
        tagService.delete(tagService.getById(id), authenticationFacade.loggedInUser)
}

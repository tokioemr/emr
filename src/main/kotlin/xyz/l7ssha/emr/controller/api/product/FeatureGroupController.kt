package xyz.l7ssha.emr.controller.api.product

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import xyz.l7ssha.emr.configuration.security.AuthenticationFacade
import xyz.l7ssha.emr.dto.RSQLSpecification
import xyz.l7ssha.emr.dto.product.feature.FeatureGroupCreateInputDto
import xyz.l7ssha.emr.dto.product.feature.FeatureGroupPatchInputDto
import xyz.l7ssha.emr.entities.products.feature.FeatureGroup
import xyz.l7ssha.emr.mapper.FeatureGroupMapper
import xyz.l7ssha.emr.service.entity.FeatureGroupEntityService
import javax.validation.Valid

@RestController
@RequestMapping("/api/product_feature_groups")
class FeatureGroupController(
    @Autowired val featureGroupService: FeatureGroupEntityService,
    @Autowired val featureMapper: FeatureGroupMapper,
    @Autowired val authenticationFacade: AuthenticationFacade
) {
    @GetMapping
    fun getAllAction(specification: RSQLSpecification<FeatureGroup>, pageable: Pageable) =
        featureGroupService.findAll(specification.getFiltersAndSpecification(), pageable)
            .map { featureMapper.featureGroupToOutputDto(it) }

    @GetMapping("/{id}")
    fun getItemAction(@PathVariable id: Long) =
        featureGroupService.getById(id)
            .let { featureMapper.featureGroupToOutputDto(it) }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('CREATE_FEATURES')")
    fun postItemAction(@RequestBody @Valid inputDto: FeatureGroupCreateInputDto) =
        featureMapper.featureGroupCreateInputDtoToFeatureGroup(inputDto)
            .let { featureGroupService.save(it) }
            .let { featureMapper.featureGroupToOutputDto(it) }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('CREATE_FEATURES')")
    fun patchItemAction(@PathVariable id: Long, @RequestBody @Valid inputDto: FeatureGroupPatchInputDto) =
        featureGroupService.getById(id)
            .let { featureMapper.featureGroupPatchInputDtoToFeatureGroup(it, inputDto) }
            .let { featureGroupService.save(it) }
            .let { featureMapper.featureGroupToOutputDto(it) }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('CREATE_FEATURES')")
    fun putItemAction(@PathVariable id: Long, @RequestBody @Valid inputDto: FeatureGroupCreateInputDto) =
        featureGroupService.getById(id)
            .let { featureMapper.featureGroupCreateInputDtoToFeatureGroup(it, inputDto) }
            .let { featureGroupService.save(it) }
            .let { featureMapper.featureGroupToOutputDto(it) }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('REMOVE_FEATURES')")
    fun deleteItem(@PathVariable id: Long) =
        featureGroupService.getById(id)
            .let { featureGroupService.delete(it, authenticationFacade.loggedInUser) }
}

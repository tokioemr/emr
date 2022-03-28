package xyz.l7ssha.emr.mapper

import org.springframework.stereotype.Component
import xyz.l7ssha.emr.configuration.security.AuthenticationFacade
import xyz.l7ssha.emr.dto.tag.TagCreatePatchInputDto
import xyz.l7ssha.emr.dto.tag.TagOutputDto
import xyz.l7ssha.emr.entities.products.Tag
import java.time.Instant

@Component
class TagMapper(val authenticationFacade: AuthenticationFacade) {
    fun tagToOutputDto(tag: Tag): TagOutputDto = TagOutputDto(tag.id, tag.name)

    fun inputDtoToTag(inputDto: TagCreatePatchInputDto): Tag = Tag(0L, inputDto.name).apply {
        createdBy = authenticationFacade.loggedInUser
    }

    fun updateTagFromInputDto(tag: Tag, dto: TagCreatePatchInputDto): Tag {
        return tag.apply {
            this.name = dto.name

            updatedBy = authenticationFacade.loggedInUser
            updatedAt = Instant.now()
        }
    }
}

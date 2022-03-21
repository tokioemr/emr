package xyz.l7ssha.emr.mapper

import org.springframework.stereotype.Component
import xyz.l7ssha.emr.dto.tag.TagCreatePatchInputDto
import xyz.l7ssha.emr.dto.tag.TagOutputDto
import xyz.l7ssha.emr.entities.products.Tag

@Component
class TagMapper {
    fun tagToOutputDto(tag: Tag): TagOutputDto = TagOutputDto(tag.id, tag.name)

    fun inputDtoToTag(inputDto: TagCreatePatchInputDto): Tag = Tag(0L, inputDto.name)

    fun updateTagFromInputDto(tag: Tag, dto: TagCreatePatchInputDto): Tag {
        return tag.apply {
            this.name = dto.name
        }
    }
}

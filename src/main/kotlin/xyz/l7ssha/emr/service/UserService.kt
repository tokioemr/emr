package xyz.l7ssha.emr.service

import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import xyz.l7ssha.emr.dto.pagination.FilteringSortingInputDto
import xyz.l7ssha.emr.entities.User
import xyz.l7ssha.emr.mapper.FilterSortMapper
import xyz.l7ssha.emr.repositories.UserRepository

@Service
class UserService(val userRepository: UserRepository, private val filterSortMapper: FilterSortMapper) {
    fun findAllByCustomFilters(filteringSortingInputDto: FilteringSortingInputDto): Page<User> {
        val specification = filterSortMapper.filtersToQuerySpecification<User>(filteringSortingInputDto)
        val sort = filterSortMapper.sortDtoToSort(filteringSortingInputDto)

        return userRepository.findAll(
            specification,
            PageRequest.of(filteringSortingInputDto.page, filteringSortingInputDto.perPage, sort)
        )
    }
}

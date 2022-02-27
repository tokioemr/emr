package xyz.l7ssha.emr.mapper

import org.springframework.data.domain.Sort
import org.springframework.data.domain.Sort.Direction
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Component
import xyz.l7ssha.emr.dto.pagination.FilteringOperator
import xyz.l7ssha.emr.dto.pagination.FilteringSortingInputDto
import xyz.l7ssha.emr.dto.pagination.SortingOperator
import javax.persistence.criteria.Expression

@Suppress("UNCHECKED_CAST")
@Component
class FilterSortMapper {
    fun <T> filtersToQuerySpecification(filteringSortingDto: FilteringSortingInputDto): Specification<T> {
        return Specification { root, _, criteriaBuilder ->
            if (filteringSortingDto.filters.isEmpty()) {
                return@Specification criteriaBuilder.conjunction()
            }

            val predicates = filteringSortingDto.filters.map {
                val field = root.get<T>(it.field)

                val filteringOperator = when (it.filteringOperator) {
                    null, FilteringOperator.EQ -> criteriaBuilder.equal(field, it.value)
                    FilteringOperator.PART -> criteriaBuilder.like(field as Expression<String>, "%${it.value}%")
                }

                return@map criteriaBuilder.and(filteringOperator)
            }

            return@Specification criteriaBuilder.and(*predicates.toTypedArray())
        }
    }

    fun sortDtoToSort(filteringSortingDto: FilteringSortingInputDto): Sort {
        val orders = filteringSortingDto.sorting.map {
            val order = when (it.value) {
                SortingOperator.DESC -> Direction.DESC
                SortingOperator.ASC -> Direction.ASC
            }

            return@map Sort.Order(order, it.field)
        }

        return Sort.by(orders)
    }
}

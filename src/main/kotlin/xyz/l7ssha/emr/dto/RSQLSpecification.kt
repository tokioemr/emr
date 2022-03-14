package xyz.l7ssha.emr.dto

import org.springframework.data.jpa.domain.Specification

data class RSQLSpecification<T>(val filters: Specification<T>?, val sorting: Specification<T>?) {
    fun getFiltersAndSpecification(): Specification<T>? {
        if (filters != null) {
            return filters.and(sorting)
        }

        return sorting
    }
}

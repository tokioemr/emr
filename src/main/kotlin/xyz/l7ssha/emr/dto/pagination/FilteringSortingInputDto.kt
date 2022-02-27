package xyz.l7ssha.emr.dto.pagination

data class FilteringSortingInputDto(
    val perPage: Int = 10,
    val page: Int = 1,
    val filters: List<FilterInputDto> = listOf(),
    val sorting: List<SortingInputDto> = listOf()
)

data class SortingInputDto(val field: String, val value: SortingOperator)
data class FilterInputDto(val field: String, val value: String, val filteringOperator: FilteringOperator?)

enum class SortingOperator(val operatorName: String) {
    ASC("asc"),
    DESC("desc");

    companion object {
        fun fromValue(value: String) = values().first { it.operatorName == value }
    }
}

enum class FilteringOperator(val operatorName: String) {
    PART("part"),
    EQ("eq");
//    LT("lt"),
//    GT("gt")

    companion object {
        fun fromValue(value: String) = values().first { it.operatorName == value }
    }
}

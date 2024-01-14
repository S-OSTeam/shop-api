package sosteam.deamhome.domain.item.handler.request

import jakarta.validation.constraints.Min

data class ItemPageRequest (
    @field: Min(0)
    val page: Int,
    @field: Min(1)
    val size: Int,
    val orderBy: OrderBy
)
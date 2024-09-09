package sosteam.deamhome.domain.item.handler.response

data class ItemSearchResponse(
	val items: List<QuestionResponse>,
	val totalCount: Long
)

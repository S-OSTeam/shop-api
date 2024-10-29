package sosteam.deamhome.domain.question.service

import kotlinx.coroutines.flow.toList
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import sosteam.deamhome.domain.item.handler.request.QuestionFilterRequest
import sosteam.deamhome.domain.item.handler.request.QuestionRequest
import sosteam.deamhome.domain.item.handler.request.QuestionUpdateRequest
import sosteam.deamhome.domain.question.exception.QuestionNotFoundException
import sosteam.deamhome.domain.question.handler.response.QuestionResponse
import sosteam.deamhome.domain.question.repository.QuestionCategoryRepository
import sosteam.deamhome.domain.question.repository.QuestionRepository
import sosteam.deamhome.global.category.exception.CategoryNotFoundException
import sosteam.deamhome.global.image.provider.ImageProvider

@Service
class QuestionService(
	private val questionRepository: QuestionRepository,
	private val questionCategoryRepository: QuestionCategoryRepository,
	private val imageProvider: ImageProvider
) {
	
	@Transactional
	suspend fun createQuestion(request: QuestionRequest): QuestionResponse {
		// 카테고리가 존재하는지 확인
		questionCategoryRepository.findByPublicId(request.categoryPublicId)
			?: throw CategoryNotFoundException()
		
		// TODO storeId 가 존재하는지 확인?
		
		val question = request.asDomain()
		val saveQuestion = questionRepository.save(question)
		return QuestionResponse.fromQuestion(saveQuestion)
	}
	
	@Transactional
	suspend fun deleteQuestionByPublicId(publicId: String): Long {
		// 존재하는 아이템인지 확인
		val question = questionRepository.findByPublicId(publicId)
			?: throw QuestionNotFoundException()
		// 아이템의 이미지들 삭제
		for (imageUrl in question.imageUrls) {
			imageProvider.deleteImageByUrl(imageUrl)
		}
		//아이템 삭제
		return questionRepository.deleteByPublicId(publicId)
	}
	
	@Transactional
	suspend fun updateQuestion(request: QuestionUpdateRequest): QuestionResponse {
		// 기존 아이템을 가져옵니다
		val existingQuestion = questionRepository.findByPublicId(request.publicId)
			?: throw QuestionNotFoundException()
		
		// request 에서 null 이 아닌 값만 사용하여 새 객체 생성
		val updatedQuestion = existingQuestion.copy(
			categoryPublicId = request.categoryPublicId?.let { categoryId ->
				questionCategoryRepository.findByPublicId(categoryId)
					?: throw CategoryNotFoundException()
				categoryId
			} ?: existingQuestion.categoryPublicId,
			title = request.title ?: existingQuestion.title,
			content = request.content ?: existingQuestion.content,
			summary = request.summary ?: existingQuestion.summary,
			userId = request.userId ?: existingQuestion.userId,
			postId = request.postId ?: existingQuestion.postId,
			itemId = request.itemId ?: existingQuestion.itemId,
			questionStatus = request.questionStatus ?: existingQuestion.questionStatus,
			questionType = request.questionType ?: existingQuestion.questionType,
			questionIsCompleted = request.questionIsCompleted ?: existingQuestion.questionIsCompleted,
			storeId = request.storeId ?: existingQuestion.storeId,
			imageUrls = request.imageUrls ?: existingQuestion.imageUrls
		)
		val savedQuestion = questionRepository.save(updatedQuestion)
		return QuestionResponse.fromQuestion(savedQuestion)
	}
	
	suspend fun findQuestions(request: QuestionFilterRequest): List<QuestionResponse> {
		val parentIds: MutableList<String> = mutableListOf()
		if (request.questionCategoryFilter != null && !request.questionCategoryFilter.publicId.isNullOrBlank()) {
			request.questionCategoryFilter.publicId.let { parentIds.add(it) }
			
			val childIds =
				request.questionCategoryFilter.publicId.let {
					questionCategoryRepository.findByParentPublicId(it).toList()
						.onEach { parentIds.add(it.publicId) }
						.map { it.publicId }
				}
			
			parentIds.addAll(questionCategoryRepository.findByParentPublicIdIn(childIds).toList().map { it.publicId })
		}
		
		return questionRepository.findAllQuestionsByFilter(request, parentIds).toList()
			.map { QuestionResponse.fromQuestion(it) }
	}
}
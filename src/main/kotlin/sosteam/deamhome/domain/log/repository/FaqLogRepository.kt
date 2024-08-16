package sosteam.deamhome.domain.log.repository

import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import sosteam.deamhome.domain.log.entity.QuestionLog

interface QuestionLogRepository : CoroutineCrudRepository<QuestionLog, Long>
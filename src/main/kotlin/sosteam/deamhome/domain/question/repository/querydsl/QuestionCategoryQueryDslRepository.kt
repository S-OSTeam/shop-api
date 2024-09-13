package sosteam.deamhome.domain.question.repository.querydsl

import com.infobip.spring.data.r2dbc.QuerydslR2dbcRepository
import sosteam.deamhome.domain.question.entity.QuestionCategory

interface QuestionCategoryQueryDslRepository : QuerydslR2dbcRepository<QuestionCategory, Long>
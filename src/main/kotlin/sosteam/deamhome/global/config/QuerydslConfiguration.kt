package sosteam.deamhome.global.config

import com.querydsl.sql.PostgreSQLTemplates
import com.querydsl.sql.SQLTemplates
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class QuerydslConfiguration {

    @Bean
    fun sqlTemplates(): SQLTemplates {
        return PostgreSQLTemplates()  // 적절한 데이터베이스에 맞는 SQLTemplates를 선택
    }
}
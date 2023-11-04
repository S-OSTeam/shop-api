package sosteam.deamhome.global.config

import lombok.RequiredArgsConstructor
import org.springframework.boot.autoconfigure.mongo.MongoProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.ReactiveMongoDatabaseFactory
import org.springframework.data.mongodb.ReactiveMongoTransactionManager
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration
import org.springframework.data.mongodb.config.EnableReactiveMongoAuditing
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories


@Configuration
@RequiredArgsConstructor
@EnableReactiveMongoAuditing
@EnableReactiveMongoRepositories
class ReactiveMongoConfig(private val mongoProperties: MongoProperties) : AbstractMongoClientConfiguration() {
	
	@Bean
	fun transactionManager(dbFactory: ReactiveMongoDatabaseFactory): ReactiveMongoTransactionManager {
		return ReactiveMongoTransactionManager(dbFactory)
	}
	
	override fun getDatabaseName(): String {
		return mongoProperties.database
	}
	
}
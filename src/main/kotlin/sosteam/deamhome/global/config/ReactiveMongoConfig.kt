//package sosteam.deamhome.global.config
//
//import lombok.RequiredArgsConstructor
//import org.springframework.boot.autoconfigure.mongo.MongoProperties
//import org.springframework.context.annotation.Bean
//import org.springframework.context.annotation.Configuration
//import org.springframework.data.mongodb.ReactiveMongoDatabaseFactory
//import org.springframework.data.mongodb.ReactiveMongoTransactionManager
//import org.springframework.data.mongodb.config.EnableReactiveMongoAuditing
//
//
//@Configuration
//@RequiredArgsConstructor
//@EnableReactiveMongoAuditing
//class ReactiveMongoConfig(private val mongoProperties: MongoProperties) {
//
//	@Bean
//	fun transactionManager(dbFactory: ReactiveMongoDatabaseFactory): ReactiveMongoTransactionManager {
//		return ReactiveMongoTransactionManager(dbFactory)
//	}
//}
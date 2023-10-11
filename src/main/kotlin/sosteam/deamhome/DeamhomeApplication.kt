package sosteam.deamhome

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories

@SpringBootApplication
@EnableReactiveMongoRepositories
class DeamhomeApplication

fun main(args: Array<String>) {
	runApplication<DeamhomeApplication>(*args)
}

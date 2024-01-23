package sosteam.deamhome

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
//@EnableR2dbcRepositories
@EnableAsync
class DeamhomeApplication

fun main(args: Array<String>) {
	runApplication<DeamhomeApplication>(*args)
}

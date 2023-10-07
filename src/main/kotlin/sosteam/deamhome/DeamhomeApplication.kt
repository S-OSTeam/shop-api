package sosteam.deamhome

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@EnableScheduling
@SpringBootApplication
class DeamhomeApplication

fun main(args: Array<String>) {
	runApplication<DeamhomeApplication>(*args)
}

package sosteam.deamhome.global.entity

import com.github.f4b6a3.ulid.UlidCreator
import org.springframework.data.annotation.Id

abstract class EmbeddedEntity : Domain {
    @Id
    var id: String = UlidCreator.getMonotonicUlid().toString().replace("-", "")
        private set
}
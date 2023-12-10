package sosteam.deamhome.global

import lombok.RequiredArgsConstructor
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@DataMongoTest
@RequiredArgsConstructor
abstract class RepositoryBaseTest
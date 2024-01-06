
package sosteam.deamhome.global.sequence.provider

import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.data.mongodb.core.FindAndModifyOptions.options
import org.springframework.data.mongodb.core.ReactiveMongoOperations
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Update
import org.springframework.stereotype.Service
import sosteam.deamhome.global.sequence.entity.DatabaseSequence


@Service
class SequenceGenerator (
    private val mongoOperations: ReactiveMongoOperations
) {

    suspend fun generateSequence(sequenceName: String): Long {
        val counter = mongoOperations.findAndModify(
            Query(Criteria.where("_id").`is`(sequenceName)),
            Update().inc("sequence", 1),
            options().returnNew(true).upsert(true),
            DatabaseSequence::class.java
        ).awaitSingle()
        return counter.sequence
    }
}
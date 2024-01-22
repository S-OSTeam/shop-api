package sosteam.deamhome.global.config

import io.r2dbc.postgresql.PostgresqlConnectionConfiguration
import io.r2dbc.postgresql.PostgresqlConnectionFactory
import io.r2dbc.postgresql.codec.EnumCodec
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories
import sosteam.deamhome.global.attribute.SNS
import sosteam.deamhome.global.attribute.Status

@Configuration
@EnableR2dbcRepositories
class R2DBCConfiguration {

    @Value("\${spring.r2dbc.host}")
    private lateinit var host: String

    @Value("\${spring.r2dbc.port}")
    private lateinit var port: String

    @Value("\${spring.r2dbc.username}")
    private lateinit var username: String

    @Value("\${spring.r2dbc.password}")
    private lateinit var password: String

    @Value("\${spring.r2dbc.database}")
    private lateinit var databaseName: String

    @Bean
    fun connectionFactory(): PostgresqlConnectionFactory {
        return PostgresqlConnectionFactory(
            io.r2dbc.postgresql.PostgresqlConnectionConfiguration.builder()
                .host(host)
                .port(port.toInt()) // PostgreSQL의 포트 번호
                .username(username)
                .password(password)
                .database(databaseName) // 연결할 데이터베이스 이름
                .build()
        )
    }
}
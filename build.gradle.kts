import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "3.1.4"
	id("io.spring.dependency-management") version "1.1.3"
	id("org.jetbrains.kotlin.plugin.noarg") version "1.8.22"
	kotlin("jvm") version "1.8.22"
	kotlin("plugin.spring") version "1.8.22"
	kotlin("kapt") version "1.8.22"
}

noArg {
	annotation("jakarta.persistence.Document")
}

allOpen {
	annotation("jakarta.persistence.Document")
	annotation("jakarta.persistence.Embeddable")
}

group = "sosteam"
version = "0.1.0"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-mail")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-webflux")
	
	implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")

	implementation("org.postgresql:postgresql")
	implementation("org.postgresql:r2dbc-postgresql")

	implementation("com.infobip:infobip-spring-data-r2dbc-querydsl-boot-starter:8.1.2")
	kapt("com.infobip:infobip-spring-data-jdbc-annotation-processor:8.1.2")

	implementation("org.springframework.boot:spring-boot-starter-data-redis")
	
	implementation("org.springframework.boot:spring-boot-starter-graphql")
	implementation("name.nkonev.multipart-spring-graphql:multipart-spring-graphql:1.1.3")
	implementation("com.graphql-java:graphql-java-extended-scalars:21.0")
	
	compileOnly("org.projectlombok:lombok")
	annotationProcessor("org.projectlombok:lombok")
	testCompileOnly("org.projectlombok:lombok")
	testAnnotationProcessor("org.projectlombok:lombok")
	
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.1")
	runtimeOnly("org.jetbrains.kotlinx:kotlinx-coroutines-core-jvm:1.7.1")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:1.7.1")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.security:spring-security-test")
	testImplementation("org.mockito:mockito-core:5.2.0")
	testImplementation("org.springframework.graphql:spring-graphql-test")
	testImplementation("io.kotest:kotest-runner-junit5-jvm:5.6.2")
	testImplementation("io.kotest:kotest-assertions-core:5.6.2")
	
	runtimeOnly("org.mariadb.jdbc:mariadb-java-client")
	
	implementation("io.jsonwebtoken:jjwt-api:0.11.5")
	implementation("com.github.f4b6a3:ulid-creator:5.2.0")
	runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.5")
	runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.5")
	
	testImplementation("org.springframework.graphql:spring-graphql-test:1.2.3")
	
	testImplementation("it.ozimov:embedded-redis:0.7.2")
	testImplementation("ru.yandex.qatools.embed:postgresql-embedded:2.10")
	testImplementation("io.kotest:kotest-runner-junit5:5.4.2")
	testImplementation("io.kotest.extensions:kotest-extensions-spring:1.1.2")
	testImplementation("io.kotest:kotest-assertions-core:5.4.2")
	testImplementation("io.mockk:mockk:1.13.2")
	testImplementation("com.ninja-squad:springmockk:3.1.1")
	
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.security:spring-security-test")
	testImplementation("org.mockito:mockito-core:5.2.0")
	
	implementation("io.netty:netty-resolver-dns-native-macos:4.1.68.Final:osx-aarch_64")
	
	implementation("com.graphql-java:graphql-java-extended-validation:21.0")
}

tasks.withType<Jar> {
	duplicatesStrategy = DuplicatesStrategy.INCLUDE
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs += "-Xjsr305=strict"
		jvmTarget = "17"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

val querydslDir = "$buildDir/generated/querydsl"

sourceSets.getByName("main") {
	java.srcDir(querydslDir)
}


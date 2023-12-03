import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.openapitools.generator.gradle.plugin.tasks.GenerateTask

plugins {
	id("org.springframework.boot") version "2.7.17"
	id("io.spring.dependency-management") version "1.1.4"
	id("org.openapi.generator") version "6.6.0"
	kotlin("jvm") version "1.8.22"
	kotlin("plugin.spring") version "1.8.22"
}

group = "com.kantarix"
version = "0.0.1"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
	mavenCentral()
}

dependencies {
	/**
	 * Spring
	 */
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-security")

	testImplementation("org.springframework.boot:spring-boot-starter-test")

	/**
	 * Other
	 */
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("com.auth0:java-jwt:4.4.0")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("io.github.microutils:kotlin-logging:3.0.5")
}

tasks.register("generateUserClient", GenerateTask::class) {
	group = "openapi tools"
	input = project.file("$projectDir/src/main/resources/openapi/user/api-docs.yaml").path
	outputDir.set("$buildDir/generated")
	modelPackage.set("com.kantarix.user.client.model")
	apiPackage.set("com.kantarix.user.client.api")
	generatorName.set("kotlin")
	modelNameSuffix.set("Gen")
	templateDir.set("$projectDir/src/main/resources/openapi/templates")
	configOptions.set(
		mapOf(
			"dateLibrary" to "java8-localdatetime",
			"useTags" to "true",
			"enumPropertyNaming" to "UPPERCASE",
			"serializationLibrary" to "jackson",
			"useCoroutines" to "true"
		)
	)
	additionalProperties.set(
		mapOf(
			"removeEnumValuePrefix" to "false"
		)
	)
}

tasks.register("generateHomeClient", GenerateTask::class) {
	group = "openapi tools"
	input = project.file("$projectDir/src/main/resources/openapi/home/api-docs.yaml").path
	outputDir.set("$buildDir/generated")
	modelPackage.set("com.kantarix.home.client.model")
	apiPackage.set("com.kantarix.home.client.api")
	generatorName.set("kotlin")
	modelNameSuffix.set("Gen")
	templateDir.set("$projectDir/src/main/resources/openapi/templates")
	configOptions.set(
		mapOf(
			"dateLibrary" to "java8-localdatetime",
			"useTags" to "true",
			"enumPropertyNaming" to "UPPERCASE",
			"serializationLibrary" to "jackson",
			"useCoroutines" to "true"
		)
	)
	additionalProperties.set(
		mapOf(
			"removeEnumValuePrefix" to "false"
		)
	)
}

tasks.register("generateDeviceClient", GenerateTask::class) {
	group = "openapi tools"
	input = project.file("$projectDir/src/main/resources/openapi/device/api-docs.yaml").path
	outputDir.set("$buildDir/generated")
	modelPackage.set("com.kantarix.device.client.model")
	apiPackage.set("com.kantarix.device.client.api")
	generatorName.set("kotlin")
	modelNameSuffix.set("Gen")
	templateDir.set("$projectDir/src/main/resources/openapi/templates")
	configOptions.set(
		mapOf(
			"dateLibrary" to "java8-localdatetime",
			"useTags" to "true",
			"enumPropertyNaming" to "UPPERCASE",
			"serializationLibrary" to "jackson",
			"useCoroutines" to "true"
		)
	)
	additionalProperties.set(
		mapOf(
			"removeEnumValuePrefix" to "false"
		)
	)
}

allOpen {
	annotations(
		"javax.persistence.Entity",
		"javax.persistence.MappedSuperclass",
		"javax.persistence.Embeddable"
	)
}

tasks.withType<KotlinCompile> {
	dependsOn("generateUserClient", "generateHomeClient", "generateDeviceClient")
	kotlinOptions {
		freeCompilerArgs += "-Xjsr305=strict"
		jvmTarget = "17"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

sourceSets {
	main {
		java {
			srcDir("${buildDir.absoluteFile}/generated/src/main/kotlin")
		}
	}
}
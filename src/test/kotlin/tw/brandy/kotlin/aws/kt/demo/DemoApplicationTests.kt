package tw.brandy.kotlin.aws.kt.demo

import aws.sdk.kotlin.services.s3.S3Client
import aws.sdk.kotlin.services.s3.model.CreateBucketRequest
import aws.sdk.kotlin.services.s3.model.PutObjectRequest
import aws.sdk.kotlin.services.s3.putObject
import aws.smithy.kotlin.runtime.content.asByteStream
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Bean
import org.testcontainers.junit.jupiter.Testcontainers
import java.io.File

@SpringBootTest
@Testcontainers
class DemoApplicationTests(@Autowired val demoRest: DemoRest) : TestContainersSetup() {

    @Autowired
    lateinit var s3: S3Client

    @Test
    fun contextLoads() = runBlocking {
        demoRest.demo().collect { println(it) }
        println(demoRest.fetch())
    }


}

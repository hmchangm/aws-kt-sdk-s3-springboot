package tw.brandy.kotlin.aws.kt.demo

import aws.sdk.kotlin.services.s3.S3Client
import aws.sdk.kotlin.services.s3.model.CreateBucketRequest
import aws.sdk.kotlin.services.s3.model.PutObjectRequest
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


    @BeforeEach
    fun putSampleFile() {
        runBlocking {
            val request = CreateBucketRequest {
                bucket = "test"
            }
            s3.createBucket(request)
            println("bucket test is ready")


            val req = PutObjectRequest {
                bucket = "test"
                key = "Iris.parquet"
                body = File("src/test/resources/Iris.parquet").asByteStream()
            }

            val response = s3.putObject(req)
            println("Tag information is ${response.eTag}")

        }


    }

    @Test
    fun contextLoads() {
        runBlocking {
            demoRest.demo().collect { println(it) }
        }

    }


}

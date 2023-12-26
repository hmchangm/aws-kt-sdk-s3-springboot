package tw.brandy.kotlin.aws.kt.demo

import aws.sdk.kotlin.services.s3.S3Client
import aws.sdk.kotlin.services.s3.model.CreateBucketRequest
import aws.sdk.kotlin.services.s3.model.PutObjectRequest
import aws.smithy.kotlin.runtime.content.asByteStream
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.io.File

@Configuration
class BeforeAllTestRunner {

    @Autowired
    lateinit var s3: S3Client
    @Bean
    fun putSampleFile() = runBlocking {
        CreateBucketRequest {
            bucket = "test"
        }.let { req ->
            s3.createBucket(req)
            println("bucket test is ready")
        }

        PutObjectRequest {
            bucket = "test"
            key = "Iris.parquet"
            body = File("src/test/resources/Iris.parquet").asByteStream()
        }.let { req ->
            s3.putObject(req)
        }.let { resp ->
            "Tag information is ${resp.eTag}"
        }
    }



}
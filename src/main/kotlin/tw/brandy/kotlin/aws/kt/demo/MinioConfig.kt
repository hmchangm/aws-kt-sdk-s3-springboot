package tw.brandy.kotlin.aws.kt.demo

import aws.sdk.kotlin.runtime.auth.credentials.StaticCredentialsProvider
import aws.sdk.kotlin.services.s3.S3Client
import aws.smithy.kotlin.runtime.net.url.Url
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment

@Configuration
class MinioConfig(val env:Environment) {

    @Bean
    fun s3Client() = S3Client {
        region = "minio"
        endpointUrl = Url.parse( env.getRequiredProperty("minio.url"))
        credentialsProvider = StaticCredentialsProvider {
            accessKeyId = env.getRequiredProperty("minio.accessKeyId")
            secretAccessKey = env.getRequiredProperty("minio.secretAccessKey")
        }
        forcePathStyle = true

    }
}
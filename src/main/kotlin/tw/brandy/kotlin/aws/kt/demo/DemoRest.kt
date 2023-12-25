package tw.brandy.kotlin.aws.kt.demo

import aws.sdk.kotlin.services.s3.S3Client
import aws.sdk.kotlin.services.s3.model.GetObjectRequest
import aws.sdk.kotlin.services.s3.model.ListObjectsRequest
import aws.smithy.kotlin.runtime.content.writeToFile
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import org.springframework.core.env.Environment
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController


@RestController
class DemoRest(val s3: S3Client, val env: Environment) {

    val targetBucket by lazy { env.getRequiredProperty("minio.bucket") }

    @GetMapping("/list")
    suspend fun demo() = flow {
        val req = ListObjectsRequest {
            bucket = targetBucket
        }
        s3.listObjects(req)
                .contents?.forEach { emit(it) }

    }.map {
        "The name of the key is ${it.key}\n<BR>"
    }

    @GetMapping("/fetch")
    suspend fun fetch(): String {
        val keyName = "Iris.parquet"
        val request = GetObjectRequest {
            key = keyName
            bucket = targetBucket
        }
        val tempFile = kotlin.io.path.createTempFile("Iris", ".parquet")
        s3.getObject(request) { resp ->
            resp.body?.writeToFile(tempFile)
        }

        return "Successfully read $keyName to $tempFile "
    }


}


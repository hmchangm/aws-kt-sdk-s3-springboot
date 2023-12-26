package tw.brandy.kotlin.aws.kt.demo

import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.MinIOContainer
import org.testcontainers.junit.jupiter.Container

abstract class TestContainersSetup {

    companion object {
        @Container
        private val minio: MinIOContainer =
            MinIOContainer("minio/minio:RELEASE.2023-12-23T07-19-11Z")
            .withUserName("testuser")
            .withPassword("testpassword")

        @DynamicPropertySource
        @JvmStatic
        fun registerDynamicProperties(registry: DynamicPropertyRegistry) {
            registry.add("minio.url") { minio.s3URL.also { println("testcontainers minio $it") } }
            registry.add("minio.accessKeyId", minio::getUserName)
            registry.add("minio.secretAccessKey", minio::getPassword)
        }

    }

}

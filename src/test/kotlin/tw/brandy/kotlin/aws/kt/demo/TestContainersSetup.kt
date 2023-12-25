package tw.brandy.kotlin.aws.kt.demo


import org.junit.jupiter.api.BeforeAll
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.MinIOContainer
import org.testcontainers.junit.jupiter.Container

abstract class TestContainersSetup {

    companion object {
        @Container
        val minio = MinIOContainer("minio/minio:latest")
            .withUserName("testuser")
            .withPassword("testpassword")

        @DynamicPropertySource
        @JvmStatic
        fun registerDynamicProperties(registry: DynamicPropertyRegistry) {
            registry.add("minio.url") { minio.s3URL.also { println(it) } }
            registry.add("minio.accessKeyId", minio::getUserName)
            registry.add("minio.secretAccessKey", minio::getPassword)
        }


        @JvmStatic
        @BeforeAll
        fun start(): Unit {
            minio.start()
        }

    }

}

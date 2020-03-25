package com.maironpasha.dell.task

import com.firefly.codec.http2.model.HttpMethod
import com.firefly.kotlin.ext.http.HttpServer
import com.maironpasha.dell.task.containers.ContainersManager
import com.maironpasha.dell.task.sql.Database
import com.maironpasha.dell.task.sql.migrations.MigrationManager
import java.util.*

class HandlerProperties(
    val serverHost: String,
    val serverPort: Int,
    val postgresHost: String
) {
    constructor(properties: Properties): this(
        properties.getProperty("server.host") ?: "localhost",
        properties.getProperty("server.port")?.toInt() ?: 8081,
        properties.getProperty("postgres.host") ?: "jdbc:postgresql://localhost:5432/object_storage"
    )
}

class Handler(
    private val properties: HandlerProperties
) {
    private val database = Database(properties.postgresHost)
    private val containers = ContainersManager(database)
    private val server: HttpServer

    init {
        server = HttpServer {
            router {
                httpMethod = HttpMethod.GET
                asyncHandler {
                    end("Hello world!")
                }
            }
            router {
                httpMethod = HttpMethod.POST
                path = "/:container"
                asyncHandler {
                    val name = getPathParameter("container")
                    containers.add(name)
                    end(containers.getByName(name)?.name ?: "not found")
                }
            }
        }
    }

    suspend fun listen() {
        MigrationManager.instance.migrate(database)
        server.listen(
            properties.serverHost,
            properties.serverPort
        )
    }
}
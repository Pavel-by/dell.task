package com.maironpasha.dell.task.sql

import com.firefly.db.SQLClient
import com.firefly.db.SQLConnection
import com.firefly.db.jdbc.JDBCClient
import com.firefly.kotlin.ext.db.execSQL
import com.maironpasha.dell.task.sql.migrations.MigrationManager
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import kotlinx.coroutines.future.await

class Database(
    url: String
) {
    private val sqlClient: SQLClient

    init {
        val config = HikariConfig()
        config.jdbcUrl = url
        //config.driverClassName = "org.h2.Driver"
        config.isAutoCommit = false
        config.username = "postgres"
        config.password = "12345678"
        val ds = HikariDataSource(config)
        sqlClient = JDBCClient(ds)
    }

    suspend fun <T> exec(handler: suspend (conn: SQLConnection) -> T): T =
        sqlClient.connection.await().execSQL(handler = handler)
}
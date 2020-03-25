package com.maironpasha.dell.task.sql.migrations

import com.firefly.kotlin.ext.db.asyncInsert
import com.firefly.kotlin.ext.db.asyncQueryForObject
import com.firefly.kotlin.ext.db.asyncQueryForSingleColumn
import com.firefly.kotlin.ext.db.asyncUpdate
import com.maironpasha.dell.task.sql.Database

class MigrationManager {
    private val VERSION = 1
    val migrations = listOf<Migration>(
        Migration1()
    )

    suspend fun migrate(db: Database) {
        var curVersion = validateVersion(db)

        for (version in curVersion until VERSION) {
            migrations[curVersion].migrate(db)
            db.exec {
                it.asyncInsert<Int>(
                    """
                    INSERT INTO migrations (version)
                    VALUES(?)
                """,
                    curVersion + 1
                )
            }
        }
    }

    private suspend fun validateVersion(db: Database): Int {
        return db.exec { conn ->
            val tableValidation = """
                CREATE TABLE IF NOT EXISTS migrations (
                    version INTEGER PRIMARY KEY ,
                    migration_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
                );
            """
            conn.asyncUpdate(tableValidation)

            val valueValidation = """
                INSERT INTO migrations (version)
                VALUES(0)
                ON CONFLICT DO NOTHING;
            """
            conn.asyncUpdate(valueValidation)
            return@exec conn.asyncQueryForSingleColumn<Int>("select max(version) from migrations;")
        }
    }

    companion object {
        private var _instance: MigrationManager? = null
        val instance: MigrationManager
            get() {
                _instance = _instance
                    ?: MigrationManager()
                return _instance!!
            }
    }
}
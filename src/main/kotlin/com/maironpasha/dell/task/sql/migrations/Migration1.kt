package com.maironpasha.dell.task.sql.migrations

import com.maironpasha.dell.task.sql.Database

class Migration1 : Migration() {
    override suspend fun migrate(db: Database) {
        db.exec {conn ->
            conn.update(javaClass.getResource("/sql/migrations/migration1.sql").readText())
        }
    }
}
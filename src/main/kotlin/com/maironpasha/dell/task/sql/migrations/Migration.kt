package com.maironpasha.dell.task.sql.migrations

import com.maironpasha.dell.task.sql.Database

abstract class Migration {
    abstract suspend fun migrate(db: Database)
}
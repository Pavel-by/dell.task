package com.maironpasha.dell.task.sql.migrations

import com.firefly.db.annotation.Column
import com.firefly.db.annotation.Id
import com.firefly.db.annotation.Table

@Table(value = "migrations")
class Version (
    @Id(value = "version")
    var version: Int,
    @Column(value = "migration_time")
    val migration_time: String?
)
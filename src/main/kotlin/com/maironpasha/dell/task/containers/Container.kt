package com.maironpasha.dell.task.containers

import com.firefly.db.annotation.Column
import com.firefly.db.annotation.Id
import com.firefly.db.annotation.Table
import java.sql.Date

@Table(value = "containers")
class Container(
    @Id(value = "hash")
    var hash: String?,
    @Column(value = "creation_time")
    var creationTime: Date?,
    @Column(value = "last_modified_time")
    var lastModifiedTime: Date?,
    @Column(value = "objects_count")
    var objectsCount: Int?,
    @Column(value = "container_name")
    var name: String?
)
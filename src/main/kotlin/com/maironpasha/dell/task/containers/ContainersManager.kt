package com.maironpasha.dell.task.containers

import com.firefly.kotlin.ext.db.*
import com.maironpasha.dell.task.hash
import com.maironpasha.dell.task.sql.Database

class ContainersManager(private val db: Database) {
    suspend fun getByName(name: String): Container? {
        return try {
            val result = db.exec {
                it.asyncNamedQueryForObject<Container>(
                    """
                    SELECT * FROM containers 
                    WHERE hash=:hash;
                """,
                    mapOf(
                        "hash" to name.hash()
                    )
                )
            }
            result
        } catch (e: Exception) {
            println(e)
            null
        }
    }

    suspend fun add(name: String): Boolean = db.exec {
        try {
            val count = it.asyncNamedInsert<Int>(
                """
                INSERT INTO containers(container_name, hash, objects_count)
                VALUES(:name,:hash,:count) 
                ON CONFLICT DO NOTHING;
                """,
                mapOf(
                    "name" to name,
                    "hash" to name.hash(),
                    "count" to 0
                )
            )
            return@exec count > 0
        } catch (e: Exception) {
            return@exec false
        }
    }

}
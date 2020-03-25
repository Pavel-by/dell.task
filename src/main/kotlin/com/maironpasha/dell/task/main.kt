package com.maironpasha.dell.task

import com.firefly.kotlin.ext.http.HttpServer
import kotlinx.coroutines.runBlocking
import org.apache.log4j.BasicConfigurator
import java.io.File
import java.io.Reader
import java.lang.Exception
import java.util.*

fun main(args: Array<String>) = runBlocking {
    BasicConfigurator.configure()
    val properties = Properties()

    try {
        properties.load(File(System.getProperty("config")).inputStream())
    } catch (e: Exception) {
        properties.load(File("D:\\University\\Dell\\project\\conf.properties").inputStream())
    }

    Handler(HandlerProperties(properties)).listen()
}

package com.maironpasha.dell.task

import java.security.MessageDigest
import java.text.SimpleDateFormat
import java.util.*
import javax.xml.bind.DatatypeConverter

fun String.hash(type: String = "SHA-256"): String {
    val bytes = MessageDigest.getInstance(type).digest(this.toByteArray())
    return DatatypeConverter.printHexBinary(bytes)
}

fun Date.toString(format: String = "dd.MM.yyyy hh:mm:ss aaa", locale: Locale = Locale.getDefault()): String {
    val formatter = SimpleDateFormat(format, locale)
    return formatter.format(this)
}

fun getCurrentDateTime(): Date {
    return Calendar.getInstance().time
}
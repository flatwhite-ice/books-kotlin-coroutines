package com.study.book.bookskotlincoroutines.ch2

import kotlinx.coroutines.*
import kotlin.random.Random


class Page13AsyncExample {


}

suspend fun delayedResponseFromExternService(serviceName: String): List<Triple<String, String, Long>> {
    val delayTime = Random.nextLong(3000L)
    delay(delayTime)
    val value = Random.nextInt(10).toString().also {
        println("[${Thread.currentThread().name}][service-name : $serviceName] response value : $it (delayTime : $delayTime)")
    }
    return listOf(Triple(serviceName, value, delayTime))
}

fun main(args: Array<String>) {

    val externServices = arrayListOf("wallet", "retail", "point", "stock", "payment", "admin")

    println("[${Thread.currentThread().name}]start.")
    runBlocking {
        println("[${Thread.currentThread().name}]run blocking start.")
        val allOf = externServices.indices
            .map { seed -> async { delayedResponseFromExternService(externServices[seed]) } }
            .flatMap { it.await() }

        println("[${Thread.currentThread().name}]all : ${allOf.sortedBy { it.third }}")
        println("[${Thread.currentThread().name}]run blocking end.")
    }
    println("[${Thread.currentThread().name}]end.")

}

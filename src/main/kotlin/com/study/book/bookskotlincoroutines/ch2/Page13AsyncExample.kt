package com.study.book.bookskotlincoroutines.ch2

import kotlinx.coroutines.*
import kotlin.random.Random


class Page13AsyncExample {


}

suspend fun delayedReturn(seed: Int): List<Triple<Int, String, Long>> {
    val delayTime = Random.nextLong(3000L)
    delay(delayTime)
    val value = Random.nextInt(10).toString().also {
        println("[${Thread.currentThread().name}][$seed] returnValue : $it (delayTime : $delayTime)")
    }
    return listOf(Triple(seed, value, delayTime))
}

fun main(args: Array<String>) {

    println("[${Thread.currentThread().name}]start.")
    runBlocking {
        println("[${Thread.currentThread().name}]run blocking start.")
        val allNews = (0 until 10)
            .map { seed -> async {
                delayedReturn(seed)
            } }
            .flatMap { it.await() }

        println("[${Thread.currentThread().name}]allNews: ${allNews.sortedBy { it.third }}")
        println("[${Thread.currentThread().name}]run blocking end.")
    }
    println("[${Thread.currentThread().name}]end.")

}

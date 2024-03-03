package com.study.book.bookskotlincoroutines.ch2


class Page18Sequence {


}

val seq = sequence {
    yield(1)
    yield(2)
    yield(3)
}

fun main(args: Array<String>) {
    for (num in seq) {
        print(num)
    } // 123
}

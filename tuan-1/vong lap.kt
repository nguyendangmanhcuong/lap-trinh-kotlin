package com.example.tuan1

fun main() {
    // bai 1
    println("bai 1")
    for (i in 1..10) {
        println(i)
    }

    // bai 2
    println("bai 2")
    var tong = 0
    for (i in 1..100) {
        tong += i
    }
    println("tong: $tong")

    // bai 3
    println("bai 3")
    for (i in 2..20 step 2) {
        println(i)
    }
}
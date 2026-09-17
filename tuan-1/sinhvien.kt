package com.example.tuan1

import java.util.Scanner

fun main() {
    val scanner = Scanner(System.`in`)
    print("Nhap diem math: ")
    val math = scanner.nextDouble()
    print("Nhap diem programming: ")
    val programming = scanner.nextDouble()
    print("Nhap diem database: ")
    val database = scanner.nextDouble()
    val tongDiem = math + programming + database
    val diemTB = tongDiem / 3.0
    val diemCaoNhat = maxOf(math, programming, database)
    val trangThai = if (diemTB >= 5.0) "Dat" else "Khong dat"

    println("\n--- thong tin va ket qua ---")
    println("Ten sinh vien: Nguyen Dang Manh Cuong\nMSV: 2415053122303")
    println("Tong diem: $tongDiem")
    println("Diem trung binh: $diemTB")
    println("Diem cao nhat: $diemCaoNhat")
    println("Ket qua: $trangThai")
}
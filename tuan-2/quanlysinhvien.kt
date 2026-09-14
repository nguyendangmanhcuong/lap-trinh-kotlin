package com.example.bai1

import java.util.Scanner

// Class bieu dien thong tin Sinh vien
data class Student(
    val studentId: String,
    var fullName: String,
    var age: Int,
    var major: String,
    var gpa: Double
) {
    fun showDetails() {
        println("[%s] %-22s | Tuoi: %-2d | Nganh: %-18s | GPA: %.2f".format(studentId, fullName, age, major, gpa))
    }
}

class AcademicManager {
    // 5 Sinh vien
    private val studentList = mutableListOf(
        Student("STD101", "Tran Quoc An", 21, "Khoa hoc may tinh", 8.85),
        Student("STD102", "Nguyen Dang Manh Cuong", 20, "He thong thong tin", 7.40),
        Student("STD103", "Nguyen Huu Gia Bao", 23, "Khoa hoc may tinh", 4.80),
        Student("STD104", "Hoang Nguyen Bao Huy", 19, "Thuong mai dien tu", 8.10),
        Student("STD105", "Tran Van Trung Hieu", 22, "He thong thong tin", 7.15)
    )

    fun registerNewStudent(scanner: Scanner) {
        println("\n---> NHAP THONG TIN SINH VIEN MOI <---")
        print("Nhap Ma SV: ")
        val id = scanner.nextLine().trim()

        // Kiem tra trung lap ID
        if (studentList.any { it.studentId.equals(id, ignoreCase = true) }) {
            println("Loi: Ma sinh vien nay da ton tai tren he thong!")
            return
        }

        print("Nhap Ho va Ten: ")
        val name = scanner.nextLine().trim()

        print("Nhap Tuoi: ")
        val age = scanner.nextLine().toIntOrNull() ?: run {
            println("Tuoi khong hop le! Dat mac dinh la 18.")
            18
        }

        print("Nhap Nganh hoc: ")
        val major = scanner.nextLine().trim()

        print("Nhap Diem GPA (0.0 - 10.0): ")
        val gpa = scanner.nextLine().toDoubleOrNull() ?: run {
            println("GPA khong hop le! Dat mac dinh la 0.0.")
            0.0
        }

        studentList.add(Student(id, name, age, major, gpa))
        println("=> Da them sinh vien $name vao danh sach thanh cong!")
    }

    fun printStudentList(customList: List<Student> = studentList) {
        if (customList.isEmpty()) {
            println("=> Khong tim thay sinh vien nao phu hop.")
            return
        }
        println("\n=========================================================================================")
        customList.forEach { it.showDetails() }
        println("=========================================================================================")
    }

    fun deleteStudent(scanner: Scanner) {
        print("\nNhap Ma SV can xoa khoi he thong: ")
        val targetId = scanner.nextLine().trim()
        val isRemoved = studentList.removeIf { it.studentId.equals(targetId, ignoreCase = true) }

        if (isRemoved) {
            println("=> Da xoa thanh cong sinh vien co ma: $targetId")
        } else {
            println("=> Khong tim thay ma sinh vien: $targetId")
        }
    }

    fun processSearchAndStats(scanner: Scanner) {
        println("\n---------------- MENU TIM KIEM & THONG KE ----------------")
        println("1. Dem sinh vien co GPA >= 8.0")
        println("2. Dem sinh vien co GPA < 5.0")
        println("3. Tinh diem GPA trung binh theo nganh")
        println("4. Tim sinh vien dat GPA cao nhat")
        println("5. Tim sinh vien lon tuoi nhat")
        println("6. Tra cuu sinh vien co GPA trong doan [7.0 - 8.5]")
        println("7. Loc sinh vien theo nganh")
        println("8. Tim kiem sinh vien theo mot phan ten")
        print("Moi chon chuc nang (1-8): ")

        when (scanner.nextLine().trim()) {
            "1" -> {
                val total = studentList.count { it.gpa >= 8.0 }
                println("=> Tong so sinh vien co GPA >= 8.0 la: $total")
            }
            "2" -> {
                val total = studentList.count { it.gpa < 5.0 }
                println("=> Tong so sinh vien co GPA < 5.0 la: $total")
            }
            "3" -> {
                print("Nhap ten nganh can tinh: ")
                val majorQuery = scanner.nextLine().trim()
                val averageGpa = studentList
                    .filter { it.major.equals(majorQuery, ignoreCase = true) }
                    .map { it.gpa }
                    .average()

                if (averageGpa.isNaN()) {
                    println("=> Khong tim thay sinh vien nao thuoc nganh '$majorQuery'.")
                } else {
                    println("=> Diem GPA trung binh nganh '$majorQuery': %.2f".format(averageGpa))
                }
            }
            "4" -> {
                println("=> Sinh vien co GPA cao nhat:")
                studentList.maxByOrNull { it.gpa }?.showDetails() ?: println("Danh sach trong.")
            }
            "5" -> {
                println("=> Sinh vien lon tuoi nhat:")
                studentList.maxByOrNull { it.age }?.showDetails() ?: println("Danh sach trong.")
            }
            "6" -> {
                val filtered = studentList.filter { it.gpa in 7.0..8.5 }
                println("=> Danh sach sinh vien co GPA tu 7.0 den 8.5:")
                printStudentList(filtered)
            }
            "7" -> {
                print("Nhap ten nganh can xem: ")
                val majorQuery = scanner.nextLine().trim()
                val filtered = studentList.filter { it.major.equals(majorQuery, ignoreCase = true) }
                println("=> Danh sach sinh vien thuoc nganh '$majorQuery':")
                printStudentList(filtered)
            }
            "8" -> {
                print("Nhap tu khoa tim kiem trong ten: ")
                val nameKeyword = scanner.nextLine().trim()
                val filtered = studentList.filter { it.fullName.contains(nameKeyword, ignoreCase = true) }
                println("=> Ket qua tim kiem voi tu khoa '$nameKeyword':")
                printStudentList(filtered)
            }
            else -> println("Lua chon khong hop le!")
        }
    }

    fun processSorting(scanner: Scanner) {
        println("\n---------------- MENU SAP XEP ----------------")
        println("1. Sap xep danh sach theo GPA giam dan")
        println("2. Hien thi TOP 3 sinh vien diem GPA cao nhat")
        println("3. Sap xep danh sach theo tuoi tang dan")
        println("4. Sap xep danh sach theo ten (A-Z)")
        print("Moi chon chuc nang (1-4): ")

        when (scanner.nextLine().trim()) {
            "1" -> {
                val sortedList = studentList.sortedByDescending { it.gpa }
                println("=> Danh sach sau khi sap xep GPA giam dan:")
                printStudentList(sortedList)
            }
            "2" -> {
                val top3List = studentList.sortedByDescending { it.gpa }.take(3)
                println("=> Top 3 sinh vien xuat sac nhat:")
                printStudentList(top3List)
            }
            "3" -> {
                val sortedList = studentList.sortedBy { it.age }
                println("=> Danh sach sau khi sap xep theo tuoi tang dan:")
                printStudentList(sortedList)
            }
            "4" -> {
                val sortedList = studentList.sortedBy { it.fullName.trim().split("\\s+".toRegex()).last() }
                println("=> Danh sach sau khi sap xep theo ten (A-Z):")
                printStudentList(sortedList)
            }
            else -> println("Lua chon khong hop le!")
        }
    }

    fun computeOverallAverage() {
        val avgGpa = studentList.map { it.gpa }.average()
        if (avgGpa.isNaN()) {
            println("=> He thong chua co du lieu sinh vien.")
        } else {
            println("=> GPA trung binh toan bo sinh vien: %.2f".format(avgGpa))
        }
    }
}

fun main() {
    val inputScanner = Scanner(System.`in`)
    val manager = AcademicManager()

    while (true) {
        println("\n========== STUDENT MANAGEMENT ==========")
        println("1. Them sinh vien")
        println("2. Hien thi tat ca sinh vien")
        println("3. Tim kiem & Loc sinh vien")
        println("4. Tinh GPA trung binh")
        println("5. Sap xep sinh vien")
        println("6. Xoa sinh vien")
        println("0. Thoat")
        println("========================================")
        print("Choose: ")

        when (inputScanner.nextLine().trim()) {
            "1" -> manager.registerNewStudent(inputScanner)
            "2" -> manager.printStudentList()
            "3" -> manager.processSearchAndStats(inputScanner)
            "4" -> manager.computeOverallAverage()
            "5" -> manager.processSorting(inputScanner)
            "6" -> manager.deleteStudent(inputScanner)
            "0" -> {
                println("Cam on ban da su dung chuong trinh. Tam biet!")
                break
            }
            else -> println("Lua chon khong hop le. Vui long thu lai!")
        }
    }
}
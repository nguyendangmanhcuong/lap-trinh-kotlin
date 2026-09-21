package com.ute.studentprofile

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ute.studentprofile.databinding.ActivityMainBinding
import com.ute.studentprofile.model.Student
import com.ute.studentprofile.utils.toAcademicRanking
import com.ute.studentprofile.utils.toast

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var currentStudent = Student(
        id = "2415053122303",
        name = "Nguyen Dang Manh Cuong",
        className = "DD2026",
        email = "ndmc@ute.udn.vn",
        gpa = 3.8
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Gán dữ liệu ban đầu lên các Views
        bindStudentData(currentStudent)

        // Cài đặt sự kiện tương tác nút bấm
        setupListeners()
    }

    private fun bindStudentData(student: Student) {
        with(binding) {
            tvName.text = student.name
            tvStudentId.text = "MSSV: ${student.id} • Lớp: ${student.className}"
            tvGpaBadge.text = "${student.gpa} GPA (${student.gpa.toAcademicRanking()})"
            edtNewGpa.setText(student.gpa.toString())
        }
    }

    private fun setupListeners() {
        binding.btnUpdateGpa.setOnClickListener {
            val inputGpaText = binding.edtNewGpa.text.toString().trim()

            // Kiểm tra xem người dùng đã nhập dữ liệu hay chưa
            if (inputGpaText.isEmpty()) {
                toast("Vui lòng nhập điểm GPA mới!")
                return@setOnClickListener
            }

            val newGpa = inputGpaText.toDoubleOrNull()

            // Kiểm tra tính hợp lệ của điểm GPA (từ 0.0 đến 4.0)
            if (newGpa == null || newGpa < 0.0 || newGpa > 4.0) {
                toast("Điểm GPA không hợp lệ! Vui lòng nhập từ 0.0 - 4.0")
                return@setOnClickListener
            }

            // Cập nhật lại đối tượng student bằng hàm copy()
            currentStudent = currentStudent.copy(gpa = newGpa)

            // Cập nhật lại giao diện hiển thị
            bindStudentData(currentStudent)

            // Thông báo thành công
            toast("Cập nhật GPA thành công!")
        }
    }
}
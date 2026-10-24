package com.example.registerapp

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout

class MainActivity : AppCompatActivity() {

    private lateinit var tilName: TextInputLayout
    private lateinit var tilEmail: TextInputLayout
    private lateinit var tilPhone: TextInputLayout
    private lateinit var tilMsv: TextInputLayout
    private lateinit var tilClass: TextInputLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tilName = findViewById(R.id.tilName)
        tilEmail = findViewById(R.id.tilEmail)
        tilPhone = findViewById(R.id.tilPhone)
        tilMsv = findViewById(R.id.tilMsv)
        tilClass = findViewById(R.id.tilClass)

        findViewById<Button>(R.id.btnRegister).setOnClickListener {
            if (validate()) {
                // Lưu thông tin đăng ký
                getSharedPreferences("register", MODE_PRIVATE).edit()
                    .putString("name", tilName.text())
                    .putString("email", tilEmail.text())
                    .putString("phone", tilPhone.text())
                    .putString("msv", tilMsv.text())
                    .putString("lop", tilClass.text())
                    .apply()

                Snackbar.make(
                    findViewById(android.R.id.content),
                    "Đăng ký thành công!",
                    Snackbar.LENGTH_INDEFINITE
                ).setAction("Xem chi tiết") {
                    openDetail(withData = true)
                }.show()
            }
        }

        // Nút chuyển sang trang Chi tiết (không cần validate)
        findViewById<Button>(R.id.btnGoDetail).setOnClickListener {
            openDetail(withData = false)
        }
    }

    private fun openDetail(withData: Boolean) {
        val intent = Intent(this, DetailActivity::class.java)
        if (withData) {
            intent.putExtra("name", tilName.text())
            intent.putExtra("email", tilEmail.text())
            intent.putExtra("phone", tilPhone.text())
            intent.putExtra("msv", tilMsv.text())
            intent.putExtra("lop", tilClass.text())
        }
        startActivity(intent)
    }

    private fun TextInputLayout.text(): String = editText?.text.toString().trim()

    private fun validate(): Boolean {
        var ok = true
        val name = tilName.text()
        val email = tilEmail.text()
        val phone = tilPhone.text()
        val msv = tilMsv.text()
        val lop = tilClass.text()

        // Họ tên: chỉ chữ cái (có dấu) và khoảng trắng, tối thiểu 2 từ
        if (name.isEmpty()) {
            tilName.error = "Vui lòng nhập họ tên"; ok = false
        } else if (!name.matches(Regex("^\\p{L}+(\\s\\p{L}+)+$"))) {
            tilName.error = "Họ tên chỉ gồm chữ cái, ít nhất 2 từ"; ok = false
        } else tilName.error = null

        // Email
        if (email.isEmpty()) {
            tilEmail.error = "Vui lòng nhập email"; ok = false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            tilEmail.error = "Email không hợp lệ"; ok = false
        } else tilEmail.error = null

        // SĐT: 10 số, bắt đầu bằng 0
        if (phone.isEmpty()) {
            tilPhone.error = "Vui lòng nhập số điện thoại"; ok = false
        } else if (!phone.matches(Regex("^0\\d{9}$"))) {
            tilPhone.error = "SĐT gồm 10 số, bắt đầu bằng 0"; ok = false
        } else tilPhone.error = null

        // MSV: chữ + số, 8-12 ký tự
        if (msv.isEmpty()) {
            tilMsv.error = "Vui lòng nhập mã sinh viên"; ok = false
        } else if (!msv.matches(Regex("^[A-Za-z0-9]{8,12}$"))) {
            tilMsv.error = "MSV gồm 8-12 ký tự chữ/số"; ok = false
        } else tilMsv.error = null

        // Lớp: chữ, số, dấu gạch, 3-20 ký tự
        if (lop.isEmpty()) {
            tilClass.error = "Vui lòng nhập lớp"; ok = false
        } else if (!lop.matches(Regex("^[A-Za-z0-9\\-]{3,20}$"))) {
            tilClass.error = "Lớp gồm 3-20 ký tự chữ/số/dấu -"; ok = false
        } else tilClass.error = null

        return ok
    }
}
package com.example.registerapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val prefs = getSharedPreferences("register", MODE_PRIVATE)

        fun show(id: Int, label: String, key: String) {
            val value = intent.getStringExtra(key)
                ?: prefs.getString(key, null)
                ?: "(chưa có)"
            findViewById<TextView>(id).text = "$label: $value"
        }

        show(R.id.tvName, "Họ tên", "name")
        show(R.id.tvEmail, "Email", "email")
        show(R.id.tvPhone, "SĐT", "phone")
        show(R.id.tvMsv, "MSV", "msv")
        show(R.id.tvClass, "Lớp", "lop")

        // Dùng Intent chuyển về trang Đăng ký
        findViewById<Button>(R.id.btnBack).setOnClickListener {
            val intent = Intent(this, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            }
            startActivity(intent)
            finish()
        }
    }
}
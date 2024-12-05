package com.example.mdmapp

import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var devicePolicyManager: DevicePolicyManager
    private lateinit var adminComponent: ComponentName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Инициализация DevicePolicyManager и ComponentName
        devicePolicyManager = getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager
        adminComponent = ComponentName(this, MyDeviceAdminReceiver::class.java)

        // Кнопки из разметки
        val enableAdminButton: Button = findViewById(R.id.enable_admin)
        val lockDeviceButton: Button = findViewById(R.id.lock_device)

        // Обработчики нажатий кнопок
        enableAdminButton.setOnClickListener { enableAdmin() }
        lockDeviceButton.setOnClickListener { lockDevice() }
    }

    private fun enableAdmin() {
        val intent = Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN).apply {
            putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, adminComponent)
            putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "Administrator access is required.")
        }
        startActivity(intent)
    }

    private fun lockDevice() {
        if (devicePolicyManager.isAdminActive(adminComponent)) {
            devicePolicyManager.lockNow()
        } else {
            Toast.makeText(this, "Admin permission not enabled", Toast.LENGTH_SHORT).show()
        }
    }
}
package com.example.ultimateflashlight

import android.content.Context
import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraManager
import android.os.Build

class FlashlightController(private val context: Context) {
    private val cameraManager: CameraManager = context.getSystemService(Context.CAMERA_SERVICE) as CameraManager
    private var cameraId: String? = null

    init {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                cameraId = cameraManager.cameraIdList[0]
            }
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }
    }

    fun turnOnFlashlight() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                cameraManager.setTorchMode(cameraId!!, true)
            }
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }
    }

    fun turnOffFlashlight() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                cameraManager.setTorchMode(cameraId!!, false)
            }
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }
    }
}

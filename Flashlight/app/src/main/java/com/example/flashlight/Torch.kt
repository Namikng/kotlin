package com.example.flashlight

import android.content.Context
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraManager

class Torch(context: Context) {
    private var cameraId: String? = null
    private val cameraManager = context.getSystemService(Context.CAMERA_SERVICE) as CameraManager

    // 초기화
    init {
        cameraId
    }

    // 손전등 켜기
    fun flashOn() {
        cameraManager.setTorchMode(cameraId, true)
    }
    
    // 손전등 끄기
    fun flashOff() {
        cameraManager.setTorchMode(cameraId, false)
    }

    private fun getCameraId(): String? {
        val cameraIds = cameraManager.cameraIdList
        for (id in cameraIds) {
            val info = cameraManager.getCameraCharacteristics(id)
            val flashAvailable = info.get(CameraCharacteristics.FLASH_INFO_AVAILABLE)
        }
        return null
    }
}
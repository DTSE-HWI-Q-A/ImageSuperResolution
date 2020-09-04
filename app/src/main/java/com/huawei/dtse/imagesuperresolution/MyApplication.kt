package com.huawei.dtse.imagesuperresolution

import android.app.Application
import android.util.Log
import com.huawei.hiai.vision.common.ConnectionCallback
import com.huawei.hiai.vision.common.VisionBase

class MyApplication : Application() {

    companion object {
        private const val TAG = "ImageSuperResolution"
    }

    override fun onCreate() {
        super.onCreate()
        initHiAi()
    }

    private fun initHiAi() {
        VisionBase.init(applicationContext, object : ConnectionCallback {
            override fun onServiceConnect() {
                Log.i(TAG, "onServiceConnect")
            }

            override fun onServiceDisconnect() {
                Log.i(TAG, "onServiceDisconnect")
            }
        })
    }
}
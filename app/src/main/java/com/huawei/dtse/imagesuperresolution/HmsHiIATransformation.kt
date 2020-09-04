package com.huawei.dtse.imagesuperresolution

import android.content.Context
import android.graphics.Bitmap
import com.huawei.hiai.vision.common.ConnectionCallback
import com.huawei.hiai.vision.common.VisionBase
import com.huawei.hiai.vision.image.sr.ImageSuperResolution
import com.huawei.hiai.vision.visionkit.common.Frame
import com.huawei.hiai.vision.visionkit.image.sr.SuperResolutionConfiguration
import com.squareup.picasso.Transformation

class HmsHiIATransformation(private val context: Context) : Transformation {

    init {
        VisionBase.init(context, object : ConnectionCallback {
            override fun onServiceConnect() {}

            override fun onServiceDisconnect() {}
        })
    }

    override fun key(): String {
        return "HiIAImageSuperResolution"
    }

    override fun transform(source: Bitmap?): Bitmap? {
        if (source != null) {
            source.let {
                return if (it.width <= 800 && it.height <= 600) {
                    return@let Thread {
                        setHiAi(it)
                    }.start()
                } else {
                    it
                }
            }
        } else {
            return source
        }
    }

    private fun setHiAi(mBitmap: Bitmap): Bitmap {
        val superResolution = ImageSuperResolution(context)
        val frame = Frame().apply {
            bitmap = mBitmap
        }
        val paras = SuperResolutionConfiguration(
            SuperResolutionConfiguration.SISR_SCALE_3X,
            SuperResolutionConfiguration.SISR_QUALITY_HIGH
        )
        superResolution.setSuperResolutionConfiguration(paras)
        val result = superResolution.doSuperResolution(frame, null)

        return result.bitmap
    }
}
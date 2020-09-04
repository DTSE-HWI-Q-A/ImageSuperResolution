package com.huawei.dtse.imagesuperresolution

import android.content.Context
import android.graphics.Bitmap
import com.huawei.hiai.vision.image.sr.ImageSuperResolution
import com.huawei.hiai.vision.visionkit.common.Frame
import com.huawei.hiai.vision.visionkit.image.sr.SuperResolutionConfiguration
import java.util.concurrent.Executors

class HiAiImageSuperResolutionManager(applicationContext: Context) {

    private var imageSuperResolution: ImageSuperResolution =
        ImageSuperResolution(applicationContext)

    companion object {
        private const val THREADS_COUNT = 10
        private const val MAX_WIDTH = 800
        private const val MAX_HEIGHT = 600
    }

    private val executor = Executors.newFixedThreadPool(THREADS_COUNT)


    fun doSuperResolutionAsync(bitmap: Bitmap, onImageListener: (Bitmap?) -> Unit) {
        executor.execute {
            onImageListener.invoke(doSuperResolution(bitmap))
        }
    }

    private fun doSuperResolution(source: Bitmap): Bitmap? {
        if (imageSuperResolution.prepare() != 0) return source
        return if (source.width <= MAX_WIDTH && source.height <= MAX_HEIGHT) {
            getSuperResolutionBitmap(source)
        } else {
            source
        }
    }

    private fun getSuperResolutionBitmap(source: Bitmap): Bitmap? {
        val frame = Frame().apply {
            bitmap = source
        }
        val result = getSuperResolutionInstance().doSuperResolution(frame, null).bitmap
        return result
    }

    private fun getSuperResolutionInstance(): ImageSuperResolution {
        val params = SuperResolutionConfiguration(
            SuperResolutionConfiguration.SISR_SCALE_3X,
            SuperResolutionConfiguration.SISR_QUALITY_HIGH
        )
        imageSuperResolution.setSuperResolutionConfiguration(params)

        return imageSuperResolution
    }
}
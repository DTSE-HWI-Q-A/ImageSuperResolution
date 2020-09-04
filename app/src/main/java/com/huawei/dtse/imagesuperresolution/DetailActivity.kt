package com.huawei.dtse.imagesuperresolution

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import kotlinx.android.synthetic.main.activity_detail_gallery.*

@Suppress("DEPRECATION")
class DetailActivity : AppCompatActivity() {

    companion object {
        private const val EXTRA_PHOTO_CACHE = "ExtraPhotoCache"
        private const val TAG = "UiAiDetailActivity"
    }

    private lateinit var imageUrl: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_gallery)
        imageUrl = intent.getStringExtra(EXTRA_PHOTO_CACHE).toString()
    }

    override fun onStart() {
        super.onStart()
        Picasso.get()
            .load(imageUrl)
            .placeholder(R.drawable.logo)
            .error(R.drawable.logo_black)
            .into(object : Target {
                override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
                    Log.i(TAG, "onPrepareLoad")
                }

                override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
                    Log.i(TAG, "onBitmapFailed")
                }

                override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                    Log.i(TAG, "onBitmapLoaded")
                    if (bitmap != null) {
                        imageNoResolution.setImageBitmap(bitmap)
                        HiAiImageSuperResolutionManager(applicationContext).doSuperResolutionAsync(
                            bitmap
                        ) {
                            imageResolution.setImageBitmap(it)
                        }
                    }
                }
            })
    }
}
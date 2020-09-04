package com.huawei.dtse.imagesuperresolution

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val images = resources.getStringArray(R.array.url_resources)

        galleryRecyclerView.apply {
            this.setHasFixedSize(true)
            this.layoutManager = GridLayoutManager(this@MainActivity, 2)
            adapter = GalleryAdapter(images) { url ->
                val intent = Intent(this@MainActivity, DetailActivity::class.java).apply {
                    this.putExtra("ExtraPhotoCache", url)
                }
                startActivity(intent)
            }
        }
    }
}
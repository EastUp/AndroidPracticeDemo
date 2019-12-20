package com.east.customview.material_design_1

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.east.customview.R
import kotlinx.android.synthetic.main.activity_fruit.*
import kotlinx.android.synthetic.main.activity_simple_practice.toolbar

class FruitActivity : AppCompatActivity() {

    companion object{
        const val FRUIT_NAME = "fruit_name"

        const val FRUIT_IMAGE_ID = "fruit_image_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fruit)

        val intent = intent
        val fruitName = intent.getStringExtra(FRUIT_NAME)
        val fruitImageId = intent.getIntExtra(FRUIT_IMAGE_ID, 0)

        setSupportActionBar(toolbar)
        if(supportActionBar!=null)
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        collapsing_toolbar.title = fruitName
        Glide.with(this).load(fruitImageId).into(fruit_image_view)
        val fruitContent = generateFruitContent(fruitName)
        fruit_content_text.text = fruitContent
    }

    private fun generateFruitContent(fruitName: String): String {
        val fruitContent = StringBuilder()
        for (i in 0..499) {
            fruitContent.append(fruitName)
        }
        return fruitContent.toString()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home){
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

}

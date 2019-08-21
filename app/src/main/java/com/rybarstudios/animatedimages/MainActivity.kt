package com.rybarstudios.animatedimages

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val drawableIds = listOf(R.drawable.colorful_space, R.drawable.space)
    var pointer = 0

    private fun incrementPointer() {
        ++pointer
        if (pointer >= drawableIds.size) {
            pointer = 0
        }
    }

    private fun decrementPointer() {
        --pointer
        if (pointer < 0) {
            pointer = drawableIds.size - 1
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        displayImage()

        next_button.setOnClickListener {
            incrementPointer()
            displayImage()
        }

        previous_button.setOnClickListener {
            decrementPointer()
            displayImage()
        }
    }

    private fun displayImage() {
        image_display.setImageDrawable(ContextCompat.getDrawable(this, drawableIds[pointer]))
    }

}

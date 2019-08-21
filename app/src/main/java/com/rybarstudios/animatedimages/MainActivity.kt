package com.rybarstudios.animatedimages

import android.graphics.drawable.Animatable
import android.graphics.drawable.AnimatedImageDrawable
import android.graphics.drawable.AnimationDrawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val drawableIds = listOf(R.drawable.colorful_space, R.drawable.space)
    private var pointer = 0

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

        play_animation_button.setOnClickListener { view ->
            when (pointer) {
                0 -> animateAnimationDrawable(drawableIds[pointer], image_display)
                1 -> animateGif(drawableIds[pointer], image_display)
            }
            animateVectorDrawable(R.drawable.avd_play_to_pause, view as ImageView)
        }
    }

    private fun displayImage() {
        image_display.setImageDrawable(ContextCompat.getDrawable(this, drawableIds[pointer]))
    }

    private fun animateAnimationDrawable(id: Int, view: ImageView) {
        val animation = ContextCompat.getDrawable(this, id)
        view.setImageDrawable(animation)
        (animation as AnimationDrawable).start()
    }

    private fun animateVectorDrawable(id: Int, view: ImageView) {
        val animation = ContextCompat.getDrawable(this, id)
        view.setImageDrawable(animation)
        (animation as Animatable).start()
    }

    private fun animateGif(id: Int, view: ImageView) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val animation = ContextCompat.getDrawable(this, id)
            view.setImageDrawable(animation)
            (animation as AnimatedImageDrawable).start()
        }
    }

}

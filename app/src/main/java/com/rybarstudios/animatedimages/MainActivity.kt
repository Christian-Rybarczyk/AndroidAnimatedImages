package com.rybarstudios.animatedimages

import android.graphics.ImageDecoder
import android.graphics.drawable.Animatable
import android.graphics.drawable.AnimatedImageDrawable
import android.graphics.drawable.AnimatedVectorDrawable
import android.graphics.drawable.AnimationDrawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val drawableIds = listOf(R.drawable.colorful_space,
        R.drawable.space,
        R.drawable.space_clouds,
        R.drawable.astronaut,
        R.drawable.shooting_spaceship)
    private var pointer = 0
    private var isPlaying = false

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
            isPlaying = false
            incrementPointer()
            playPausePosition(play_animation_button)
            displayImage()
        }

        previous_button.setOnClickListener {
            isPlaying = false
            decrementPointer()
            playPausePosition(play_animation_button)
            displayImage()
        }

        play_animation_button.setOnClickListener {
            imageSelector(pointer)
            playPausePosition(play_animation_button)
        }
    }

    private fun playPausePosition(view: View?) {
        if (!isPlaying) {
            animateVectorDrawable(R.drawable.avd_pause_to_play, view as ImageButton)
        } else {
            animateVectorDrawable(R.drawable.avd_play_to_pause, view as ImageButton)
        }
    }

    private fun displayImage() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            image_display.setImageDrawable(ContextCompat.getDrawable(this, drawableIds[pointer]))
        } else {
            image_display.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.space))
        }
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
            val animation = ImageDecoder.decodeDrawable(ImageDecoder.createSource(resources, id))
            view.setImageDrawable(animation)
            (animation as AnimatedImageDrawable).start()
        }
    }

    private fun stopAanimateAnimationDrawable(id: Int, view: ImageView) {
        val animation = ContextCompat.getDrawable(this, id)
        view.setImageDrawable(animation)
        (animation as AnimationDrawable).stop()
    }

    private fun stopAnimateGif(id: Int, view: ImageView) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val animation = ContextCompat.getDrawable(this, id)
            view.setImageDrawable(animation)
            (animation as AnimatedImageDrawable).stop()
        }
    }

    private fun imageSelector(pointer: Int) {
        when (pointer) {
            0 -> isPlaying = if (!isPlaying) {
                animateAnimationDrawable(drawableIds[pointer], image_display)
                true
            } else {
                stopAanimateAnimationDrawable(drawableIds[pointer], image_display)
                false
            }
            1, 2, 3, 4 -> isPlaying = if (!isPlaying) {
                animateGif(drawableIds[pointer], image_display)
                true
            } else {
                stopAnimateGif(drawableIds[pointer], image_display)
                false
            }
        }
    }

}

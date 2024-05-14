package com.example.ultimateflashlight

import android.Manifest
import android.animation.Animator
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.ToggleButton
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.airbnb.lottie.LottieAnimationView
import com.example.ultimateflashlight.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var flashlightController: FlashlightController
    private lateinit var toggleAnimationView: LottieAnimationView
    private val CAMERA_PERMISSION_REQUEST_CODE = 1001 // You can use any integer value here
    private var flashlightControl : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupBind()
        checkPermission()
        setupListener()
    }

    private fun setupBind() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        flashlightController = FlashlightController(this)
        toggleAnimationView = binding.animationToogle
        // Definir a animação para iniciar na metade
        toggleAnimationView.progress = 0.5f
    }

    private fun checkPermission() {
        if (
            ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) !=
            PackageManager.PERMISSION_GRANTED
        )
        {
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.CAMERA),
                CAMERA_PERMISSION_REQUEST_CODE
            )
        }
    }

    private fun setupListener() {
        // Adicionar um listener para pausar a animação quando atingir a metade
        toggleAnimationView.addAnimatorUpdateListener { animation ->
            val fraction = animation.animatedFraction
            val frameToPause = toggleAnimationView.frame
            if (frameToPause == 239) {
                toggleAnimationView.pauseAnimation()
                toggleAnimationView.progress = 0.5f
            }
            //Log.d("FRAME", "Fraction: $fraction e Frame: $frameToPause")
        }



        toggleAnimationView.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {
                //Nothing to do
            }

            override fun onAnimationEnd(animation: Animator) {
                toggleAnimationView.pauseAnimation()
            }

            override fun onAnimationCancel(animation: Animator) {
                //Nothing to do
            }

            override fun onAnimationRepeat(animation: Animator) {
                //Nothing to do
            }
        })

        // Definir o OnClickListener para retomar a animação
        toggleAnimationView.setOnClickListener {
            toggleAnimationView.resumeAnimation()
            if (!flashlightControl) {
                flashlightController.turnOnFlashlight()
                flashlightControl = !flashlightControl
            } else {
                flashlightController.turnOffFlashlight()
                flashlightControl = !flashlightControl
            }
        }
    }
}
package com.platzi.admin.view.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.platzi.admin.R
import kotlinx.android.synthetic.main.activity_splashscreen.*

class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)


        //cargamos la animacion y la asignamos a la imagen
        val animation = AnimationUtils.loadAnimation(this,R.anim.animation)
        ivLogoPlatziConf.startAnimation(animation)
        val intent = Intent(this,MainActivity::class.java)


        animation.setAnimationListener(object: Animation.AnimationListener{
            override fun onAnimationRepeat(animation: Animation?) {
            }

            override fun onAnimationEnd(animation: Animation?) {
                startActivity(intent)

                //una vez que termine de ejecutarse la animacion se va a destuir esta activity
                finish()
            }

            override fun onAnimationStart(animation: Animation?) {
            }

        })

    }
}

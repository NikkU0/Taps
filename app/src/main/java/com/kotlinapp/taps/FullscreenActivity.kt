package com.kotlinapp.taps

import android.animation.Animator
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_fullscreen.*

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class FullscreenActivity : AppCompatActivity() {
        private lateinit var sharedPref:SharedPreferences
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth= Firebase.auth
      sharedPref = this?.getSharedPreferences("HighScore", Context.MODE_PRIVATE)
//        with(sharedPref.edit()){
//            putInt("high",0)
//            commit()
//        }
        setContentView(R.layout.activity_fullscreen)
        with(animationView) {
            setMinAndMaxFrame(0, 40)
            addAnimatorListener(object : Animator.AnimatorListener {
                override fun onAnimationRepeat(animation: Animator?) {
                    //TODO("Not yet implemented")
                }

                override fun onAnimationEnd(animation: Animator?) {
                    // TODO("Not yet implemented")
                    setMinAndMaxFrame(10, 40)
                    playAnimation()
                }

                override fun onAnimationCancel(animation: Animator?) {
                    // TODO("Not yet implemented")
                }

                override fun onAnimationStart(animation: Animator?) {
                    //   TODO("Not yet implemented")
                }

            })
        }
//        val intent = Intent(this, LoginActivity::class.java).apply {
//        }

      Handler().postDelayed( Runnable {
        startActivity(Intent(this, LoginActivity::class.java));
      },2000)
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    private fun updateUI(currentUser: FirebaseUser?) {


    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.

    }


}
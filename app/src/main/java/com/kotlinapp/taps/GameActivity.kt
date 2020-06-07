package com.kotlinapp.taps

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.CountDownTimer
import android.preference.PreferenceManager
import android.util.DisplayMetrics
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_game.*
import java.util.*
import kotlin.math.min
import kotlin.random.Random


class GameActivity : AppCompatActivity() {

    private lateinit var database : FirebaseDatabase
   private lateinit var sharedPref:SharedPreferences
    private lateinit var auth :FirebaseAuth
    var counter = 10
    lateinit var timer :CountDownTimer
        val viewModel : GameViewModel by viewModels()
    private lateinit var timertask:TimerTask
    private lateinit var displayMetrics: DisplayMetrics
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        displayMetrics = DisplayMetrics()
        auth = Firebase.auth
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        sharedPref = this?.getSharedPreferences("HighScore", Context.MODE_PRIVATE)
        val edit = PreferenceManager.getDefaultSharedPreferences(this)
        val editor = edit.edit()
        database =FirebaseDatabase.getInstance()
     //   val ref = database.getReference("users").child(auth.currentUser!!.uid).child("high")
//        ref.addValueEventListener(object: ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                val post = dataSnapshot as Int
//                Log.d("On Data Change","high = "+post.toString())
//                editor.putInt("high",post).apply()
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                //print error.message
//            }
//        })

        object: CountDownTimer(10000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                textView_timer.text="Time : "+counter.toString()
                counter--
            }

            override fun onFinish() {

            showGameOverDialog()
            }
        }.start()
        fab_press.setOnClickListener {
            Log.d("FAB", " Pressed")
            gameProceeds()
            viewModel.updateScore(true)
            text_field_score.text = "Score : " + viewModel.getCurScore().toString()

        }

    }
    private fun gameProceeds() {

        Log.d("Game Proceeds", "Executed")
        Log.d("Run block", "Executed")
        val width = constraint_layout_gameArea.width.toFloat()
        val height = constraint_layout_gameArea.height.toFloat()
        var r = Random
       var curx =   fab_press.pivotX
        var cury = fab_press.pivotY
        var dx = r.nextFloat() * (width - 60) + 60
        var dy = r.nextFloat() * (height - 60) + 60
        while ((dx+dy-curx-curx) <= 200) {
            dx = r.nextFloat() * (width - 60) + 60
            dy = r.nextFloat() * (height - 60) + 60
        }
        dx = min(dx, width - 150)
        dy = min(dy, height - 150)
        fab_press.animate().x(dx).y(dy).setDuration(0).start()

    }
    fun showGameOverDialog() {
        // Create an instance of the dialog fragment and show it
    //    val dialog = CustomDialogFragment()
       // dialog.show(supportFragmentManager, "Game Over")


//      var alert = AlertDialog.Builder(applicationContext)
//        var mview = layoutInflater.inflate(R.layout.popup_game_finish,null)
//        var textView = mview.findViewById<TextView>(R.id.textView_score)
//        var playagain = mview.findViewById<Button>(R.id.button_playAgain)
//        alert.setView(mview)
//        var alertDialog = alert.create()
//        alertDialog.setCanceledOnTouchOutside(false)
//        textView.text="Score : "+viewModel.getCurScore().toString()
//        playagain.setOnClickListener {
//            startActivity(Intent(this,GameActivity::class.java))
//        }

        val dialog = MaterialDialog(this)
            .noAutoDismiss()
            .cancelable(false)
            .customView(R.layout.popup_game_finish)
        dialog.findViewById<TextView>(R.id.textView_score).text="Score : "+viewModel.getCurScore()

        dialog.findViewById<Button>(R.id.button_playAgain).setOnClickListener {
            startActivity(Intent(this,GameActivity::class.java))
        }
        dialog.findViewById<Button>(R.id.button_quit).setOnClickListener {
            startActivity(Intent(this,LoginActivity::class.java))
        }

       var imageView = dialog.findViewById<ShapeableImageView>(R.id.imageView_popup_user_pic)
        auth.currentUser?.let {
        Glide.with(this).load(auth.currentUser?.photoUrl.toString()).into( imageView)
        }
        val edit = PreferenceManager.getDefaultSharedPreferences(this)
        val editor = edit.edit()

        var highScore:Int = edit.getInt("high",0)

        Log.d("highscore",highScore.toString())
        if(viewModel.getCurScore().toInt()>=highScore)
        {
            dialog.findViewById<TextView>(R.id.textView_score).text="New High Score : "+viewModel.getCurScore()
            editor.putInt("high",viewModel.getCurScore().toInt()).apply()
   //         val ref = database.getReference("users").child(auth.currentUser!!.uid).child("high").setValue(viewModel.getCurScore())
        }

        dialog.show()
    }

    override fun onBackPressed() {

    }

}
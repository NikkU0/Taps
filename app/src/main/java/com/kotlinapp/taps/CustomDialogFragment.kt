package com.kotlinapp.taps

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.kotlinapp.taps.R.layout.popup_game_finish
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.popup_game_finish.*
import java.time.LocalDate

class CustomDialogFragment : DialogFragment() {
    lateinit var viewModel: GameViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = activity?.run {
            ViewModelProviders.of(this)[GameViewModel::class.java]
        } ?: throw Exception("Invalid Activity")
        Log.d("Pop", viewModel.getCurScore().toString())
        //textView_score.text ="Score : " + viewModel.getCurScore().toString()
}


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

      return  inflater.inflate(R.layout.popup_game_finish,container,false)
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onDismiss(dialog: DialogInterface) {

    }

    override fun onCancel(dialog: DialogInterface) {

    }
}
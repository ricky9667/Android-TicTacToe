package com.example.ooxxbattle

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_tictactoe.*

class TictactoeActivity : AppCompatActivity(), View.OnClickListener {

    var currentPlayer = (1..2).random()
    var lastClicked: View? = null
    var player1: String? = null
    var player2: String? = null

    override fun onClick(v: View) {
        val clickedId = v.id
        when(clickedId) {
            btn_restart.id -> Toast.makeText(this, "Restart: Not implemented", Toast.LENGTH_SHORT).show()
            btn_leave.id -> Toast.makeText(this, "Leave: Not implemented", Toast.LENGTH_SHORT).show()
            btn_confirm.id -> {
                lastClicked?.setBackgroundResource(android.R.drawable.btn_default)

                if(currentPlayer == 1) {
                    currentPlayer = 2
                    player2?.let { changeTitle(currentPlayer, it) }
                }
                else {
                    currentPlayer = 1
                    player1?.let { changeTitle(currentPlayer, it) }
                }
            }

            else -> {
                lastClicked?.setBackgroundResource(android.R.drawable.btn_default)
                if(currentPlayer == 1)
                    v.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.p1_color))
                else
                    v.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.p2_color))
                lastClicked = v
            }
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tictactoe)
        btn_confirm.setOnClickListener(this)
        btn_restart.setOnClickListener(this)
        btn_leave.setOnClickListener(this)
        btn_lu.setOnClickListener(this)
        btn_lm.setOnClickListener(this)
        btn_ld.setOnClickListener(this)
        btn_mu.setOnClickListener(this)
        btn_mm.setOnClickListener(this)
        btn_md.setOnClickListener(this)
        btn_ru.setOnClickListener(this)
        btn_rm.setOnClickListener(this)
        btn_rd.setOnClickListener(this)

        intent?.extras?.let {
            player1 = it.getString("p1_name")
            player2 = it.getString("p2_name")

            if(currentPlayer == 1 && player1 != null) {
                changeTitle(currentPlayer, player1!!)
            }
            else if(player2 != null) {
                changeTitle(currentPlayer, player2!!)
            }


        }
    }

    private fun changeTitle(current_player: Int, name: String) {

        val topMessage: TextView = findViewById(R.id.top_message)
        val yt: TextView = findViewById(R.id.your_turn)
        topMessage.text = name

        if(current_player == 1) {
            topMessage.setTextColor(ContextCompat.getColor(applicationContext, R.color.p1_color))
            yt.setTextColor(ContextCompat.getColor(applicationContext, R.color.p1_color))
        }
        else {
            topMessage.setTextColor(ContextCompat.getColor(applicationContext, R.color.p2_color))
            yt.setTextColor(ContextCompat.getColor(applicationContext, R.color.p2_color))
        }
    }
}
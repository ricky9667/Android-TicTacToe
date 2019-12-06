package com.example.ooxxbattle

import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.Button
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
    var grid: MutableList<String> = mutableListOf("","","","","","","","","")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tictactoe)
    }

    override fun onStart() {
        super.onStart()
        getInfo()
        startGame()
    }

    override fun onClick(v: View) {
        val clickedId = v.id
        when(clickedId) {
            // when click leave btn
            btn_leave.id ->
                finish()

            // when click restart btn (not fully implemented)
            btn_restart.id -> {
                Toast.makeText(this, "Restart: Not implemented", Toast.LENGTH_SHORT).show()
                startGame()
            }

            // when click put here btn -> change player title/color
            btn_confirm.id -> {

                if(lastClicked == null)
                    Toast.makeText(this, "Choose a block first!", Toast.LENGTH_SHORT).show()
                else {
                    lastClicked?.setBackgroundResource(android.R.drawable.btn_default)
                    if (currentPlayer == 1) {
                        (lastClicked as Button).text = "O"
                        //                    markGrid(v,"O")   // with error
                        currentPlayer = 2
                        player2?.let { changeTitle(currentPlayer, it) }
                    } else {
                        (lastClicked as Button).text = "X"
                        //                    markGrid(v,"X")   // with error
                        currentPlayer = 1
                        player1?.let { changeTitle(currentPlayer, it) }
                    }
                }
            }

            // when click grid btn
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

    private fun startGame() {
        initialize()
        chooseStarter()

    }

    private fun getInfo() {
        intent?.extras?.let {
            player1 = it.getString("p1_name")
            player2 = it.getString("p2_name")
        }
    }

    private fun initialize() {
        // init buttons
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

        // init grid
        grid = mutableListOf("","","","","","","","","")
        btn_ld.text = ""
        btn_lm.text = ""
        btn_lu.text = ""
        btn_md.text = ""
        btn_mm.text = ""
        btn_mu.text = ""
        btn_rd.text = ""
        btn_rm.text = ""
        btn_ru.text = ""
    }

    private fun chooseStarter() {
        currentPlayer = (1..2).random()
        if(currentPlayer == 1 && player1 != null) {
            changeTitle(currentPlayer, player1!!)
        }
        else if(player2 != null) {
            changeTitle(currentPlayer, player2!!)
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

    private fun markGrid(v: View, player: String) {
        val index = when (v) {
            btn_lu -> 0
            btn_mu -> 1
            btn_ru -> 2
            btn_lm -> 3
            btn_mm -> 4
            btn_rm -> 5
            btn_ld -> 6
            btn_md -> 7
            btn_rd -> 8
            else -> -1
        }
        grid[index] = player
    }
}
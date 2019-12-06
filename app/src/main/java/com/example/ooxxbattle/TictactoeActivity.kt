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

    var player1: String? = null
    var player2: String? = null
    var currentPlayer = (1..2).random()
    var winner: String? = null
    var lastClicked: View? = null
    var gridFilled: Int = 0
    var grid: MutableList<String> =
        mutableListOf("","","","","","","","","")

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
            btn_leave.id -> finish()

            // when click restart btn (not fully implemented)
            btn_restart.id -> startGame()

            // when click put here btn -> change player title/color
            btn_confirm.id -> {
                if(lastClicked == null)
                    Toast.makeText(this, "Choose a block first!", Toast.LENGTH_SHORT).show()
                else {
                    lastClicked?.setBackgroundResource(android.R.drawable.btn_default)
                    if (currentPlayer == 1) {
                        (lastClicked as Button).text = "O"
                        markGrid(lastClicked as Button,"O")   // with error
                        currentPlayer = 2
                        player2?.let { changeTitle(currentPlayer, it) }

                    } else {
                        (lastClicked as Button).text = "X"
                        markGrid(lastClicked as Button,"X")   // with error
                        currentPlayer = 1
                        player1?.let { changeTitle(currentPlayer, it) }
                    }
                }
                lastClicked = null
                gridFilled++
                findWinner()
            }

            // when click grid btn
            else -> {
                if(winner == null && (v as Button).text == "") {
                    lastClicked?.setBackgroundResource(android.R.drawable.btn_default)
                    if(currentPlayer == 1)
                        v.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.p1_color))
                    else
                        v.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.p2_color))
                    lastClicked = v
                }
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
        gridFilled = 0
        winner = null

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
        yt.text = your_turn.text

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
        val index = when (v.id) {
            btn_lu.id -> 0
            btn_mu.id -> 1
            btn_ru.id -> 2
            btn_lm.id -> 3
            btn_mm.id -> 4
            btn_rm.id -> 5
            btn_ld.id -> 6
            btn_md.id -> 7
            btn_rd.id -> 8
            else -> -1
        }
        if(index != -1)
            grid[index] = player
        else
            Toast.makeText(this, "What?", Toast.LENGTH_SHORT).show()
    }

    private fun findWinner() {
        val topMessage: TextView = findViewById(R.id.top_message)
        val yt: TextView = findViewById(R.id.your_turn)

        if(findLine("O")) {
            winner = player1
            topMessage.text = player1
            yt.text = "You Win!"
            topMessage.setTextColor(ContextCompat.getColor(applicationContext, R.color.p1_color))
            yt.setTextColor(ContextCompat.getColor(applicationContext, R.color.p1_color))
//            Toast.makeText(this, "O Wins", Toast.LENGTH_SHORT).show()
        }
        else if(findLine("X")) {
            winner = player2
            topMessage.text = player2
            yt.text = "You Win!"
            topMessage.setTextColor(ContextCompat.getColor(applicationContext, R.color.p2_color))
            yt.setTextColor(ContextCompat.getColor(applicationContext, R.color.p2_color))
//            Toast.makeText(this, "X Wins", Toast.LENGTH_SHORT).show()
        }
        else if(gridFilled >= 9) {
            topMessage.text = "Tie"
            yt.text = "Play Again!"
//            Toast.makeText(this, "Tie", Toast.LENGTH_SHORT).show()
        }
    }

    private fun findLine(p: String): Boolean {
        if(grid[0]==p && grid[1]==p && grid[2]==p) return true
        if(grid[3]==p && grid[4]==p && grid[5]==p) return true
        if(grid[6]==p && grid[7]==p && grid[8]==p) return true

        if(grid[0]==p && grid[3]==p && grid[6]==p) return true
        if(grid[1]==p && grid[4]==p && grid[7]==p) return true
        if(grid[2]==p && grid[5]==p && grid[8]==p) return true

        if(grid[0]==p && grid[4]==p && grid[8]==p) return true
        if(grid[2]==p && grid[4]==p && grid[6]==p) return true

        return false
    }


}
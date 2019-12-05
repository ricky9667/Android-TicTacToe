package com.example.ooxxbattle

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_fight.setOnClickListener {
            if(player_1_input.length()<1 || player_2_input.length()<1)
                Toast.makeText(this, "You didn't input a name", Toast.LENGTH_SHORT).show()
            else {
                val bundle = Bundle()
                bundle.putString("p1_name", player_1_input.text.toString())
                bundle.putString("p2_name", player_2_input.text.toString())
            //    Toast.makeText(this, "p1: ${player_1_input.text}, p2: ${player_2_input.text}", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, TictactoeActivity::class.java)
                intent.putExtras(bundle)
                startActivityForResult(intent, 1)
            }                
        }
    }
}

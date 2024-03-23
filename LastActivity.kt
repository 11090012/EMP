package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import java.lang.Math.round

class LastActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_last)

        //находим на XML странице все элементы с которыми будем работать
        val forName: TextView = findViewById(R.id.kostya)
        val forLastName: TextView = findViewById(R.id.dedov)
        val length: TextView = findViewById(R.id.length)
        val price: TextView = findViewById(R.id.price)
        val time: TextView = findViewById(R.id.time)
        val callNewTaxi: Button = findViewById(R.id.callNewTaxi)


        //достаем из этого intent имя, фамилию, дистанцию
        val distance = intent.getSerializableExtra("distance") as String
        val userName = intent.getSerializableExtra("userName") as String
        val userLastName = intent.getSerializableExtra("userLastName") as String
        //заполняем наши текст вью
        forName.text = userName
        forLastName.text = userLastName
        length.text = distance
        price.text = round((distance.toFloat())/950).toString()
        time.text = round((distance.toFloat())/750).toString()


        //при нажатии на кнопку возвращаемся обратно ко второй активити с картой
        callNewTaxi.setOnClickListener {
            val intent = Intent(this, Taxi::class.java)
            startActivity(intent)
        }
    }
}
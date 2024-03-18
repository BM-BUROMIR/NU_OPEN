package com.example.helloworld

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

// главный класс, отвечающий за само приложение
class MainActivity : AppCompatActivity() {
    private var counter = 0 // счетчик нажатий на кнопку
    private val colors = listOf(0xFFFF0000.toInt(), 0xFF00FF00.toInt(), 0xFF0000FF.toInt(), 0xFFFFFF00.toInt(), 0xFFFF00FF.toInt(), 0xFF00FFFF.toInt())

    // метод onCreate - это точка входа в приложение (в нашем случае)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) // после это строки можем добавлять свою логику

        // находим текстовое поле
        val textField = findViewById<TextView>(R.id.myTextView)
        // записываем в него значение счетчика (преобразовав его в строку)
        textField.text = counter.toString()

        // находим кнопку
        val button = findViewById<Button>(R.id.button2)
        // добавляем обработчик нажатий, который будет вызываться при каждом нажатии кнопки
        button.setOnClickListener{
            // увеличиваем счетчик
            counter++
            // записываем новое значение счетчика в текстовое поле
            textField.text = counter.toString()
            val randomColor = colors.random()
            textField.setTextColor(randomColor)
        }
    }
}
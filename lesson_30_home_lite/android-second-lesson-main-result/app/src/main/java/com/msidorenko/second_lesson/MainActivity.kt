package com.msidorenko.second_lesson

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException
import java.util.concurrent.TimeUnit

const val API_URL = "http://10.0.2.2:5000/api/get_answer/"
const val API_TIMEOUT: Long = 30

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // ----------------------------
        // находим наши элементы
        val etQuestion = findViewById<EditText>(R.id.etQuestion)
        val tvQuestion = findViewById<TextView>(R.id.tvQuestion)
        val tvResponse = findViewById<TextView>(R.id.tvResponse)
        val button = findViewById<Button>(R.id.button)

        // ----------------------------
        // задаем обработчик нажатия кнопки 'send'
        button.setOnClickListener {
            // считываем вопрос пользователя
            val question = etQuestion.text.toString().trim()

            // проверяем, пустой ли вопрос пользователя
            if (question.isNotEmpty()) {
                // выводим уведомление на экран
                Toast.makeText(this, question, Toast.LENGTH_SHORT).show()
                // меняем текст в полях сверху
                tvQuestion.text = question
                tvResponse.text = "Please wait..."

                // делаем запрос и задаем callback прямо здесь
                getResponse(question) { response ->
                    /* делаем действия на основном потоке, на котором же
                       работает отображение пользовательского UI (фактически мы
                       фризим его на время запроса) */
                    runOnUiThread {
                        tvResponse.text = response
                        println(response)
                    }
                }
            } else {
                // выводим предупреждение, если текст от пользователя пустой
                Toast.makeText(this, "Введите текст!", Toast.LENGTH_SHORT).show()
            }

        }
    }
}




//------------------------------------------------------------
// Объект клиента для отправки и обработки запросов к API
//------------------------------------------------------------
private val client = OkHttpClient.Builder()
    .connectTimeout(API_TIMEOUT, TimeUnit.SECONDS) // Устанавливаем таймаут соединения
    .readTimeout(API_TIMEOUT, TimeUnit.SECONDS)    // Устанавливаем таймаут чтения
    .writeTimeout(API_TIMEOUT, TimeUnit.SECONDS)   // Устанавливаем таймаут записи
    .build()




/* ------------------------------------------------------------------------------------
                Функция для отправки и обработки запроса API
    На вход принимает:
        - queston: строка, в которой содержится вопрос от пользователя
        - callback: лямбда функция, которая будет вызвана, когда мы успешно
                    обработаем запрос (у этой функции тоже есть строковый
                    параметр -- ответ от API в текстовом формате)
------------------------------------------------------------------------------------*/
private fun getResponse(question: String, callback: (String) -> Unit) {
    // ----------------------------
    // Формируем тело запроса
    val requestBody = """
            {
                "text":"$question"
            }
        """

    // ----------------------------
    // Собираем запрос
    val request = Request.Builder()
        .url(API_URL) // добавляем адрес
        .addHeader("Accept", "application/json") // хедер
        .addHeader("Content-Type", "application/json") // хедер
        .post(requestBody.toRequestBody()) // задаем тип запроса (post) и передаем туда тело
        .build() // билдим запрос


    // ----------------------------
    // Работаем с OkHttp
    client.newCall(request).enqueue(object : Callback {
        // функция, которая будет вызвана в случае ошибки отправки запроса
        override fun onFailure(call: Call, e: IOException) {
            // пишем в лог ошибку
            Log.e("error", "API failed", e)
        }

        // функция, которая будет вызвана в случае получения ответа
        override fun onResponse(call: Call, response: Response) {
            // считываем тело ответа
            val responseBody = response.body

            // если ответ не пустой
            if (responseBody != null) {
                // формируем Json объект из тела ответа (прерватив его в строку)
                val jsonObject = JSONObject(responseBody.string())
                // ищем тег "message" и считываем его значение
                val textResult = jsonObject.getString("message")
                // пишем в лог
                Log.i("data", textResult)
                // вызываем функцию callback и передаем туда ответ от нейронки
                callback(textResult)
            } else {
                // если ответ пустой, просто пишем в лог
                Log.i("data", "empty")
            }
        }
    })
}
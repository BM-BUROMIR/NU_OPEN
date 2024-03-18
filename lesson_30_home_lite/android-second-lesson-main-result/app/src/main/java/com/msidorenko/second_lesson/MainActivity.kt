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
const val API_TIMEOUT: Long = 5

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val etQuestion = findViewById<EditText>(R.id.etQuestion)
        val tvQuestion = findViewById<TextView>(R.id.tvQuestion)
        val tvResponse = findViewById<TextView>(R.id.tvResponse)
        val button = findViewById<Button>(R.id.button)

        button.setOnClickListener {
            val question = etQuestion.text.toString().trim()
            if (question.isNotEmpty()) {
                Toast.makeText(this, question, Toast.LENGTH_SHORT).show()
                tvQuestion.text = question
                tvResponse.text = "Please wait..."
                getResponse(question) { response, error ->
                    runOnUiThread {
                        if (response.isNotEmpty()) {
                            tvResponse.text = response
                        }
                        if (error.isNotEmpty()) {
                          Toast.makeText(this, "API FAILED!", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            } else {
                Toast.makeText(this, "Введите текст!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getResponse(question: String, callback: (String, String) -> Unit) {
        val requestBody = """
            {
                "text":"$question"
            }
        """.trimIndent()

        val request = Request.Builder()
            .url(API_URL)
            .addHeader("Accept", "application/json")
            .addHeader("Content-Type", "application/json")
            .post(requestBody.toRequestBody())
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("error", "API failed", e)
            }

            override fun onResponse(call: Call, response: Response) {
                val responseBody = response.body?.string() // Изменено для безопасного вызова и однократного чтения

                if (responseBody != null) {
                    try {
                        val jsonObject = JSONObject(responseBody)
                        val textResult = jsonObject.getString("message")
                        Log.i("data", textResult)
                        callback(textResult,"")
                    } catch (e: Exception) {
                        Log.e("data", "Error parsing response", e)
                        callback("","Ошибка разбора ответа API!")
                    }
                } else {
                    Log.i("data", "empty")
                    callback("","Ошибка разбора ответа API!")
                }
            }
        })
    }

    companion object {
        private val client = OkHttpClient.Builder()
            .connectTimeout(API_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(API_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(API_TIMEOUT, TimeUnit.SECONDS)
            .build()
    }
}

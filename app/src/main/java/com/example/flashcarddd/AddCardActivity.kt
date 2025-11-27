package com.example.flashcarddd

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AddCardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_card)

        val questionEditText = findViewById<EditText>(R.id.edit_question)
        val answerEditText = findViewById<EditText>(R.id.edit_answer)
        val wrongAnswer1EditText = findViewById<EditText>(R.id.edit_wrong_answer_1)
        val wrongAnswer2EditText = findViewById<EditText>(R.id.edit_wrong_answer_2)
        val saveButton = findViewById<Button>(R.id.btn_save)

        saveButton.setOnClickListener {
            val question = questionEditText.text.toString()
            val answer = answerEditText.text.toString()
            val wrongAnswer1 = wrongAnswer1EditText.text.toString()
            val wrongAnswer2 = wrongAnswer2EditText.text.toString()

            if (question.isEmpty() || answer.isEmpty() || wrongAnswer1.isEmpty() || wrongAnswer2.isEmpty()) {
                Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show()
            } else {
                val data = Intent()
                data.putExtra("question", question)
                data.putExtra("answer", answer)
                data.putExtra("wrong_answer_1", wrongAnswer1)
                data.putExtra("wrong_answer_2", wrongAnswer2)
                setResult(Activity.RESULT_OK, data)
                finish()
            }
        }
    }
}
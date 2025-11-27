package com.example.flashcarddd

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.flashcarddd.AddCardActivity

class MainActivity : AppCompatActivity() {

    private lateinit var allFlashcards: MutableList<Flashcard>
    private var currentCardIndex = 0

    private lateinit var questionTextView: TextView
    private lateinit var radioGroup: RadioGroup
    private lateinit var radio1: RadioButton
    private lateinit var radio2: RadioButton
    private lateinit var radio3: RadioButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        questionTextView = findViewById(R.id.flashcard_question)
        radioGroup = findViewById(R.id.radioGroup)
        radio1 = findViewById(R.id.radio1)
        radio2 = findViewById(R.id.radio2)
        radio3 = findViewById(R.id.radio3)

        val btnVerifier = findViewById<Button>(R.id.btnVerifier)
        val tvResultat = findViewById<TextView>(R.id.tvResultat)
        val btnAddQuestion = findViewById<Button>(R.id.btnAddQuestion)
        val btnNext = findViewById<Button>(R.id.btnNext)
        val btnPrevious = findViewById<Button>(R.id.btnPrevious)


        allFlashcards = mutableListOf(
            Flashcard(
                question = "Who is the 44th president of the United States?",
                correctAnswer = "Barack Obama",
                wrongAnswers = listOf("Donald Trump", "Joe Biden")
            )
        )

        showCard(allFlashcards[currentCardIndex])

        btnVerifier.setOnClickListener {
            val selectedId = radioGroup.checkedRadioButtonId
            if (selectedId == -1) {
                Toast.makeText(this, "Veuillez choisir une réponse", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val currentCard = allFlashcards[currentCardIndex]
            val selectedRadioButton = findViewById<RadioButton>(selectedId)

            if (selectedRadioButton.text == currentCard.correctAnswer) {
                tvResultat.text = "✅ Bonne réponse !"
            } else {
                tvResultat.text = "❌ Mauvaise réponse !"
            }
        }

        btnAddQuestion.setOnClickListener {
            val intent = Intent(this, AddCardActivity::class.java)
            startActivityForResult(intent, 100)
        }

        btnNext.setOnClickListener {
            if (allFlashcards.isNotEmpty()) {
                currentCardIndex = (currentCardIndex + 1) % allFlashcards.size
                showCard(allFlashcards[currentCardIndex])
                tvResultat.text = "" // Reset result
                radioGroup.clearCheck() // Clear selection
            }
        }

        btnPrevious.setOnClickListener {
            if (allFlashcards.isNotEmpty()) {
                currentCardIndex = if (currentCardIndex == 0) allFlashcards.size - 1 else currentCardIndex - 1
                showCard(allFlashcards[currentCardIndex])
                tvResultat.text = "" // Reset result
                radioGroup.clearCheck() // Clear selection
            }
        }
    }

    private fun showCard(flashcard: Flashcard) {
        questionTextView.text = flashcard.question

        val answers = (flashcard.wrongAnswers + flashcard.correctAnswer).shuffled()
        radio1.text = answers[0]
        radio2.text = answers[1]
        radio3.text = answers[2]
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode == Activity.RESULT_OK && data != null) {
            val question = data.getStringExtra("question")
            val answer = data.getStringExtra("answer")
            val wrongAnswer1 = data.getStringExtra("wrong_answer_1")
            val wrongAnswer2 = data.getStringExtra("wrong_answer_2")

            if (!question.isNullOrEmpty() && !answer.isNullOrEmpty() && !wrongAnswer1.isNullOrEmpty() && !wrongAnswer2.isNullOrEmpty()) {
                val newCard = Flashcard(question, answer, listOf(wrongAnswer1, wrongAnswer2))
                allFlashcards.add(newCard)
                currentCardIndex = allFlashcards.size - 1
                showCard(allFlashcards[currentCardIndex])
            }
        }
    }
}
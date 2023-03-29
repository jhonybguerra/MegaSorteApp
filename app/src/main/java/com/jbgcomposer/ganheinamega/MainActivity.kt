package com.jbgcomposer.ganheinamega

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.jbgcomposer.ganheinamega.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var prefs: SharedPreferences

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        prefs = getSharedPreferences("db", Context.MODE_PRIVATE)
        val result = prefs.getString("result", null)

        result?.let{
            binding.tvResult.text = "$result"
        }



        binding.apply {
            btnGenerate.setOnClickListener {
                numberGenerated(edtNumber.text.toString(), tvResult)
            }
        }

    }

    private fun numberGenerated(text: String, tvResult: TextView) {
        if (text.isEmpty()) {
            binding.edtNumber.requestFocus()
            binding.edtNumber.error = "Informe um número entre 6 e 15"
            return
        }
        val qtd = text.toInt()

        if (qtd < 6 || qtd > 15) {
            Toast.makeText(this, "O valor digitado não é permitido", Toast.LENGTH_LONG).show()
            return
        }

        val numbers = mutableSetOf<Int>()
        val random = Random()

        while (true) {
            val number = random.nextInt(60)
            numbers.add(number + 1)

            if (numbers.size == qtd) {
                break
            }
        }
        tvResult.text = """Seus números são 
            |
            |${numbers.joinToString(" - ")}""".trimMargin()

        prefs.edit().apply {
            putString("result", tvResult.text.toString())
            apply()
        }

    }
}
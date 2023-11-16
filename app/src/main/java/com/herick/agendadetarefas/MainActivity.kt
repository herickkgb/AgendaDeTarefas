package com.herick.agendadetarefas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.herick.agendadetarefas.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.brCadastrar.setOnClickListener {
            val intent = Intent(this, CadastrarUsuario::class.java)
            startActivity(intent)
        }
    }
}
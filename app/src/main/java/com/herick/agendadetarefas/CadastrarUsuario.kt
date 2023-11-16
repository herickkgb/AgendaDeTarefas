package com.herick.agendadetarefas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.herick.agendadetarefas.databinding.ActivityCadastrarUsuarioBinding
import com.herick.agendadetarefas.databinding.ActivityMainBinding

class CadastrarUsuario : AppCompatActivity() {
    private lateinit var binding: ActivityCadastrarUsuarioBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCadastrarUsuarioBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
}
package com.herick.agendadetarefas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.herick.agendadetarefas.dao.UsuarioDAO
import com.herick.agendadetarefas.databinding.ActivityCadastrarUsuarioBinding
import com.herick.agendadetarefas.databinding.ActivityMainBinding
import com.herick.agendadetarefas.model.Usuario
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CadastrarUsuario : AppCompatActivity() {
    private lateinit var binding: ActivityCadastrarUsuarioBinding

    private lateinit var usuarioDAO: UsuarioDAO

    private val listaUsuario: MutableList<Usuario> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCadastrarUsuarioBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.brCadastrar.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                val nome = binding.editNome.text.toString().trim()
                val sobrenome = binding.editSobrenome.text.toString().trim()
                val idade = binding.editIdade.text.toString().trim()
                val telefone = binding.editTelefone.text.toString().trim()

                val mensagem: Boolean

                if (nome.isEmpty() || sobrenome.isEmpty() || idade.isEmpty() || telefone.isEmpty()) {
                    mensagem = false
                } else {
                    mensagem = true
                    cadastrarUsuario(nome, sobrenome, idade, telefone)
                }
                withContext(Dispatchers.Main) {
                    if (!mensagem) {
                        Toast.makeText(
                            applicationContext,
                            "Preencha todos os campos!",
                            Toast.LENGTH_SHORT
                        ).show()

                    } else {
                        Toast.makeText(
                            applicationContext,
                            "Sucesso ao cadastrar usuário!",
                            Toast.LENGTH_SHORT
                        ).show()
                        finish()

                    }
                }
            }

        }

    }

    private fun cadastrarUsuario(nome: String, sobrenome: String, idade: String, telefone: String) {
        val usuario = Usuario(nome, sobrenome, idade, telefone)
        listaUsuario.add(usuario)
        usuarioDAO = AppDataBase.getInstance(this).usuarioDao()
        usuarioDAO.inserir(listaUsuario)
    }
}
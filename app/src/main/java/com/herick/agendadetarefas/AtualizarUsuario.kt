package com.herick.agendadetarefas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.herick.agendadetarefas.dao.UsuarioDAO
import com.herick.agendadetarefas.databinding.ActivityAtualizarUsuarioBinding
import com.herick.agendadetarefas.databinding.ActivityCadastrarUsuarioBinding
import com.herick.agendadetarefas.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AtualizarUsuario : AppCompatActivity() {
    private lateinit var binding: ActivityAtualizarUsuarioBinding

    private lateinit var usuarioDAO: UsuarioDAO
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAtualizarUsuarioBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val nomeRecuperado = intent.extras?.getString("nome")
        val sobrenomeRecuperado = intent.extras?.getString("sobrenome")
        val numeroRecuperado = intent.extras?.getString("numero")
        val idadeRecuperado = intent.extras?.getString("idade")
        val uidRecuperado = intent.extras?.getString("uid")

        binding.editNome.setText(nomeRecuperado)
        binding.editSobrenome.setText(sobrenomeRecuperado)
        binding.editTelefone.setText(numeroRecuperado)
        binding.editIdade.setText(idadeRecuperado)

        binding.btAtualizar.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                val nome = binding.editNome.text.toString()
                val sobrenome = binding.editSobrenome.text.toString()
                val numero = binding.editTelefone.text.toString()
                val idade =
                    binding.editIdade.text.toString()  // Assuming you have an EditText for age with the id 'editIdade'
                val uid = intent.extras!!.getInt("uid")
                val mensagem: Boolean

                if (nome.isEmpty() || sobrenome.isEmpty() || numero.isEmpty() || idade.isEmpty()) {
                    mensagem = false
                } else {
                    mensagem = true

                    atualizarContato(uid, nome, sobrenome, numero, idade)

                }

                withContext(Dispatchers.Main) {
                    if (mensagem) {
                        Toast.makeText(
                            this@AtualizarUsuario,
                            "Atualizado com sucesso",
                            Toast.LENGTH_SHORT
                        ).show()
                        finish()
                    } else {
                        Toast.makeText(
                            this@AtualizarUsuario,
                            "Preencha todos os campos",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }


    }

    private fun atualizarContato(
        uid: Int,
        nome: String,
        sobrenome: String,
        numero: String,
        idade: String

    ) {
        usuarioDAO = AppDataBase.getInstance(this).usuarioDao()
        usuarioDAO.atualizar(uid, nome, sobrenome, numero, idade)
    }

}
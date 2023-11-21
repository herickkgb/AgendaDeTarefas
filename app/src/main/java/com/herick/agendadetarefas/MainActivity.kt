package com.herick.agendadetarefas

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.herick.agendadetarefas.adapter.ContatoAdapter
import com.herick.agendadetarefas.dao.UsuarioDAO
import com.herick.agendadetarefas.databinding.ActivityMainBinding
import com.herick.agendadetarefas.model.Usuario
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var usuarioDAO: UsuarioDAO

    private val _listaUsuarios = MutableLiveData<MutableList<Usuario>>()
    private lateinit var contatoAdapter: ContatoAdapter

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

    @SuppressLint("NotifyDataSetChanged")
    override fun onResume() {
        super.onResume()

        CoroutineScope(Dispatchers.IO).launch {

            getContatos()

            withContext(Dispatchers.Main) {
                _listaUsuarios.observe(this@MainActivity) { it ->

                    val recyclerViewContatos = binding.recyclerViewContatos
                    recyclerViewContatos.layoutManager = LinearLayoutManager(this@MainActivity)
                    recyclerViewContatos.setHasFixedSize(true)
                    contatoAdapter = ContatoAdapter(this@MainActivity, it)
                    recyclerViewContatos.adapter = contatoAdapter
                    contatoAdapter.notifyDataSetChanged()
                }
            }
        }
    }

    private fun getContatos() {
        usuarioDAO = AppDataBase.getInstance(this).usuarioDao()
        val listaUsuarios: MutableList<Usuario> = usuarioDAO.get()
        _listaUsuarios.postValue(listaUsuarios)
    }
}
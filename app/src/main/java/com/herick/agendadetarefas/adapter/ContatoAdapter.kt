package com.herick.agendadetarefas.adapter

import android.content.Context
import android.content.Intent
import android.os.Build
import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.herick.agendadetarefas.AppDataBase
import com.herick.agendadetarefas.AtualizarUsuario
import com.herick.agendadetarefas.dao.UsuarioDAO
import com.herick.agendadetarefas.databinding.ContatoItemBinding
import com.herick.agendadetarefas.model.Usuario
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ContatoAdapter(private val context: Context, private val listaUsuario: MutableList<Usuario>) :
    RecyclerView.Adapter<ContatoAdapter.ContatoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContatoViewHolder {
        val inflater = LayoutInflater.from(context)
        val itemBinding = ContatoItemBinding.inflate(inflater, parent, false)
        return ContatoViewHolder(itemBinding)
    }

    override fun getItemCount() = listaUsuario.size

    override fun onBindViewHolder(holder: ContatoViewHolder, position: Int) {
        val usuario = listaUsuario[position]

        with(holder) {
            txtNome.text = "Nome: <i>${usuario.nome}</i>"
            txtSobrenome.text = "<i>${usuario.sobrenome}</i>"
            txtNumero.text = "Celular: ${usuario.celular}"
            txtIdade.text = "Idade: <i>${usuario.idade}</i>"

            // Permitindo que o TextView exiba formatação HTML
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                txtNome.text = Html.fromHtml(txtNome.text.toString(), Html.FROM_HTML_MODE_LEGACY)
                txtSobrenome.text =
                    Html.fromHtml(txtSobrenome.text.toString(), Html.FROM_HTML_MODE_LEGACY)
                txtIdade.text = Html.fromHtml(txtIdade.text.toString(), Html.FROM_HTML_MODE_LEGACY)
            } else {
                @Suppress("DEPRECATION")
                txtNome.text = Html.fromHtml(txtNome.text.toString())
                txtSobrenome.text = Html.fromHtml(txtSobrenome.text.toString())
                txtIdade.text = Html.fromHtml(txtIdade.text.toString())
            }

            btAtualizar.setOnClickListener {
                val intent = Intent(context, AtualizarUsuario::class.java).apply {
                    putExtra("nome", usuario.nome)
                    putExtra("sobrenome", usuario.sobrenome)
                    putExtra("numero", usuario.celular)
                    putExtra("idade", usuario.idade)
                    putExtra("uid", usuario.uid)
                }
                context.startActivity(intent)
            }

            btDeletar.setOnClickListener {
                CoroutineScope(Dispatchers.IO).launch {
                    val usuarioDAO: UsuarioDAO = AppDataBase.getInstance(context).usuarioDao()
                    usuarioDAO.delete(usuario.uid)
                    withContext(Dispatchers.Main) {
                        listaUsuario.removeAt(position)
                        notifyItemRemoved(position)
                    }
                }
            }
        }
    }

    inner class ContatoViewHolder(private val binding: ContatoItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        val txtNome = binding.textNome
        val txtSobrenome = binding.txtSobrenome
        val txtNumero = binding.textNumero
        val txtIdade = binding.textIdade
        val btAtualizar = binding.imgEdit
        val btDeletar = binding.imgDelete
    }
}

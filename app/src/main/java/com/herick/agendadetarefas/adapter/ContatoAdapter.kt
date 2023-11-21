package com.herick.agendadetarefas.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.herick.agendadetarefas.AtualizarUsuario
import com.herick.agendadetarefas.databinding.ActivityCadastrarUsuarioBinding
import com.herick.agendadetarefas.databinding.ContatoItemBinding
import com.herick.agendadetarefas.model.Usuario

class ContatoAdapter(private val context: Context, private val listaUsuario: MutableList<Usuario>) :
    RecyclerView.Adapter<ContatoAdapter.ContatoViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContatoViewHolder {
        val itemLista = ContatoItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return ContatoViewHolder(itemLista)
    }

    override fun getItemCount() = listaUsuario.size

    override fun onBindViewHolder(holder: ContatoViewHolder, position: Int) {

        holder.txtNome.text = "Nome: <i>${listaUsuario[position].nome}</i>"
        holder.txtSobrenome.text = "<i>${listaUsuario[position].sobrenome}</i>"
        holder.txtNumero.text = "Celular: ${listaUsuario[position].celular}"

        holder.txtidade.text = "Idade: <i>${listaUsuario[position].idade}</i>"

        // Permitindo que o TextView exiba formatação HTML
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            holder.txtNome.text =
                Html.fromHtml(holder.txtNome.text.toString(), Html.FROM_HTML_MODE_LEGACY)
            holder.txtSobrenome.text =
                Html.fromHtml(holder.txtSobrenome.text.toString(), Html.FROM_HTML_MODE_LEGACY)
            holder.txtidade.text =
                Html.fromHtml(holder.txtidade.text.toString(), Html.FROM_HTML_MODE_LEGACY)
        } else {
            @Suppress("DEPRECATION")
            holder.txtNome.text = Html.fromHtml(holder.txtNome.text.toString())
            holder.txtSobrenome.text = Html.fromHtml(holder.txtSobrenome.text.toString())
            holder.txtidade.text = Html.fromHtml(holder.txtidade.text.toString())
        }

        holder.btAtualizar.setOnClickListener {
            val intent = Intent(context, AtualizarUsuario::class.java)

            intent.putExtra("nome", listaUsuario[position].nome)
            intent.putExtra("sobrenome", listaUsuario[position].sobrenome)
            intent.putExtra("numero", listaUsuario[position].celular)
            intent.putExtra("idade", listaUsuario[position].idade)
            intent.putExtra("uid", listaUsuario[position].uid)

            context.startActivity(intent)
        }

    }

    inner class ContatoViewHolder(binding: ContatoItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        val txtNome = binding.textNome
        val txtSobrenome = binding.txtSobrenome
        val txtNumero = binding.textNumero
        val txtidade = binding.textIdade
        val btAtualizar = binding.imgEdit
        val btDeletar = binding.imgDelete

    }


}
package com.herick.agendadetarefas.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.herick.agendadetarefas.model.Usuario

@Dao
interface UsuarioDAO {

    @Insert
    fun inserir(listaUsuarios: MutableList<Usuario>)

    @Query("SELECT * FROM tabela_usuarios ORDER BY nome ASC")
    fun get(): MutableList<Usuario>

    @Query("UPDATE tabela_usuarios SET nome = :novoNome, sobrenome = :novoSobrenome,celular = :novoCelular, idade = :novaIdade WHERE uid = :id")
    fun atualizar(
        id: Int,
        novoNome: String,
        novoSobrenome: String,
        novoCelular: String,
        novaIdade: String

    )
}

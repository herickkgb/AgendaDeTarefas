package com.herick.agendadetarefas.dao

import androidx.room.Dao
import androidx.room.Insert
import com.herick.agendadetarefas.model.Usuario

@Dao
interface UsuarioDAO {

    @Insert
    fun inserir(listaUsuarios:MutableList<Usuario>)
}
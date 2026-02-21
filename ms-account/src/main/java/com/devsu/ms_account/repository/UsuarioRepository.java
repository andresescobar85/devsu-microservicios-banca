package com.devsu.ms_account.repository;

import org.springframework.data.annotation.Persistent;
import org.springframework.data.jpa.repository.JpaRepository;

import com.devsu.ms_account.entity.Usuario;

@Persistent
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Usuario findByIdentificacion(String identificacion);

}

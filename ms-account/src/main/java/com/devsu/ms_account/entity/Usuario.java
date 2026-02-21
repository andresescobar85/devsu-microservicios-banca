package com.devsu.ms_account.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Usuario {

    @Id
    private Long id;
    private String nombre;
    @Column(unique = true)
    private String identificacion;

    private String direccion;
    private String telefono;
    private String contrasena;

    private Boolean estado;
    
}

package com.devsu.ms_client.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "cliente")
@Getter
@Setter
public class Cliente extends Persona {

    @Column(name = "cliente_id", nullable = false, unique = true)
    private String clienteId;

    private String contrasena;
    private Boolean estado;
    
}

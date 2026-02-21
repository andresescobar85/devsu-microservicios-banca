package com.devsu.ms_client.dto;

import lombok.Data;

@Data
public class ClienteDTO {
    private String nombre;
    private String genero;
    private Integer edad;
    private String identificacion;
    private String direccion;
    private String telefono;

    private String clienteId;
    private String contrasena;
    private Boolean estado;

    private Long id;
}

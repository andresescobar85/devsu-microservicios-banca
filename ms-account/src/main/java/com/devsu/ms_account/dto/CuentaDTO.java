package com.devsu.ms_account.dto;

import lombok.Data;

@Data
public class CuentaDTO {

    private Long id;
    private String numeroCuenta;
    private String tipoCuenta; 
    private Double saldoInicial;
    private Boolean estado;
    private String identificacionCliente;
    private String clienteId;
    
}

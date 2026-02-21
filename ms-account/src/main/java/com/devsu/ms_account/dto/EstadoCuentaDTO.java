package com.devsu.ms_account.dto;

import lombok.Data;

@Data
public class EstadoCuentaDTO {

    private String numeroCuenta;
    private String tipoCuenta; 
    private Double saldoInicial;
    private Double saldoDisponible;
    private Boolean estado;
    private String fecha;
    private Double movimiento;
    
}

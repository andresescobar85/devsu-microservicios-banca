package com.devsu.ms_account.dto;

import lombok.Data;


@Data
public class MovimientoDTO {

    private String numeroCuenta;
    private String tipoMovimiento; 
    private Double valorMovimiento;
    private String identificacionCliente;
    private String descripcionRetiro;
    
}

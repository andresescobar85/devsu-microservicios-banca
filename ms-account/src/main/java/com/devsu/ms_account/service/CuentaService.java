package com.devsu.ms_account.service;

import java.util.List;

import com.devsu.ms_account.dto.CuentaDTO;

public interface CuentaService {
    
    List<CuentaDTO> listarTodas(); 
    CuentaDTO obtenerCuentaPorNumero(String numeroCuenta);
    CuentaDTO obtenerCuentaPorId(Long id);
    CuentaDTO crearCuenta(CuentaDTO cuentadto);
    CuentaDTO actualizarCuenta(Long id, CuentaDTO cuentadto);
    void eliminarCuenta(Long id);
}

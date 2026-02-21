package com.devsu.ms_account.service;

import java.time.LocalDate;
import java.util.List;

import com.devsu.ms_account.dto.EstadoCuentaDTO;
import com.devsu.ms_account.dto.MovimientoDTO;
import com.devsu.ms_account.entity.Movimiento;

public interface MovimientoService {
    
    Movimiento registrarMovimiento(MovimientoDTO movimientoDTO);
    List<EstadoCuentaDTO> obtenerReporte(String nombre, LocalDate fechaInicio, LocalDate fechaFin);
}

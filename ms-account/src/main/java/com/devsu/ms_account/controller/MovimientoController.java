package com.devsu.ms_account.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.devsu.ms_account.dto.EstadoCuentaDTO;
import com.devsu.ms_account.dto.MovimientoDTO;
import com.devsu.ms_account.entity.Movimiento;
import com.devsu.ms_account.service.MovimientoService;

@RestController
@RequestMapping("/api/movimientos")
public class MovimientoController {

    @Autowired
    private MovimientoService movimientoService;

    @PostMapping
    public ResponseEntity<Movimiento> registrarMovimiento(@RequestBody MovimientoDTO movimientoDTO) {
        return new ResponseEntity<>(movimientoService.registrarMovimiento(movimientoDTO), HttpStatus.CREATED);

    }

    @GetMapping("/reporte")
    public List<EstadoCuentaDTO> obtenerReporte(@RequestParam String nombre, 
        @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fechaInicio,
        @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fechaFin) {
        
        return movimientoService.obtenerReporte(nombre, fechaInicio, fechaFin);
    }
    
    
}

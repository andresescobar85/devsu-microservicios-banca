package com.devsu.ms_account.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devsu.ms_account.dto.EstadoCuentaDTO;
import com.devsu.ms_account.dto.MovimientoDTO;
import com.devsu.ms_account.entity.Cuenta;
import com.devsu.ms_account.entity.Movimiento;
import com.devsu.ms_account.exception.SaldoInsuficienteException;
import com.devsu.ms_account.repository.CuentaRepository;
import com.devsu.ms_account.repository.MovimientoRepository;

import jakarta.transaction.Transactional;

@Service
public class MovimientoServiceImpl implements MovimientoService {
    
    @Autowired
    private MovimientoRepository movimientoRepository;

    @Autowired
    private CuentaRepository cuentaRepository;
    
    @Override
    @Transactional
    public Movimiento registrarMovimiento(MovimientoDTO movimientoDTO) {
       
        Cuenta cuenta = cuentaRepository.findByNumeroCuenta(movimientoDTO.getNumeroCuenta())
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada con número de cuenta: " + movimientoDTO.getNumeroCuenta()));

        Double saldoActual = cuenta.getSaldoInicial();
        Double valorMovimiento = movimientoDTO.getValorMovimiento();
        Double nuevoSaldo = saldoActual + valorMovimiento;

        if(nuevoSaldo < 0) {
            throw new SaldoInsuficienteException("Saldo insuficiente para realizar el movimiento");
        }
        
        cuenta.setSaldoInicial(nuevoSaldo);
        cuentaRepository.save(cuenta);

        Movimiento nuevoMovimiento = new Movimiento();
        nuevoMovimiento.setFecha(LocalDate.now());
        nuevoMovimiento.setValor(valorMovimiento);
        nuevoMovimiento.setTipoMovimiento(valorMovimiento > 0 ? "DEPOSITO" : "RETIRO");
        nuevoMovimiento.setSaldo(nuevoSaldo);
        nuevoMovimiento.setCuenta(cuenta);
        nuevoMovimiento.setIdentificacionCliente(cuenta.getIdentificacionCliente());
        nuevoMovimiento.setDescripcionRetiro(movimientoDTO.getDescripcionRetiro());
        return movimientoRepository.save(nuevoMovimiento);
    }

    @Override
    public List<EstadoCuentaDTO> obtenerReporte(String nombre, LocalDate fechaInicio, LocalDate fechaFin) {
        List<Movimiento> movimientos = movimientoRepository.findByNombreClienteAndFechaBetween(nombre, fechaInicio, fechaFin);

        return movimientos.stream().map(m-> {
            EstadoCuentaDTO dto = new EstadoCuentaDTO();
            dto.setFecha(m.getFecha().toString());
            dto.setNumeroCuenta(m.getCuenta().getNumeroCuenta());
            dto.setTipoCuenta(m.getCuenta().getTipoCuenta());
            dto.setSaldoInicial(m.getSaldo() - m.getValor());
            dto.setEstado(m.getCuenta().getEstado());
            dto.setMovimiento(m.getValor());
            dto.setSaldoDisponible(m.getSaldo());
            return dto;
        }).collect(Collectors.toList());
    }
}
package com.devsu.ms_account.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.devsu.ms_account.entity.Movimiento;

@Repository
public interface MovimientoRepository extends JpaRepository<Movimiento, Long> {

    @Query("SELECT COALESCE(SUM(m.valor), 0) FROM Movimiento m WHERE m.cuenta.id = :cuentaId AND m.tipoMovimiento = 'DEPOSITO'")
    List<Movimiento> findByCuentaIdAndTipoMovimientoDeposito(Long cuentaId);

    @Query("SELECT m FROM Movimiento m JOIN m.cuenta c " +
       "WHERE c.nombre = :nombre " +
       "AND m.fecha BETWEEN :fechaInicio AND :fechaFin")
    List<Movimiento> findByNombreClienteAndFechaBetween( @Param("nombre") String nombre, @Param("fechaInicio") LocalDate fechaInicio, @Param("fechaFin") LocalDate fechaFin);
    
  
}

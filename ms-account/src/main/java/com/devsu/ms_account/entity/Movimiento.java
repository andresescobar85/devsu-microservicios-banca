package com.devsu.ms_account.entity;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Movimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "movimiento_seq")
    @SequenceGenerator(name = "movimiento_seq", sequenceName = "movimiento_seq", allocationSize = 1)
    private Long id;

    private LocalDate fecha;
    private String tipoMovimiento;
    private Double valor;
    private Double saldo;

    @ManyToOne
    @JoinColumn(name = "cuenta_id")
    @JsonBackReference
    @JsonIgnoreProperties("movimientos")
    private Cuenta cuenta;

    private String identificacionCliente;

    private String descripcionRetiro;
    
}

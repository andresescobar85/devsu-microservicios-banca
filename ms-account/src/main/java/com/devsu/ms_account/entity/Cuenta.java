package com.devsu.ms_account.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
public class Cuenta {

 @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cuenta_seq")
    @SequenceGenerator(name = "cuenta_seq", sequenceName = "cuenta_seq", allocationSize = 1)
    private Long id;
    
    private String numeroCuenta;
    private String tipoCuenta;
    private Double saldoInicial;
    private Boolean estado;
    @Column(name = "cliente_id")
    private String clienteId;
    @Column(name = "identificacion_cliente")
    private String identificacionCliente;

    private String nombre;
    @OneToMany(mappedBy = "cuenta",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JsonManagedReference
    @ToString.Exclude
    private List<Movimiento> movimientos;
    
}

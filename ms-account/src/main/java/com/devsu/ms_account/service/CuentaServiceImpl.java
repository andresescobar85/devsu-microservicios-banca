package com.devsu.ms_account.service;

import java.util.List;

import com.devsu.ms_account.dto.CuentaDTO;
import com.devsu.ms_account.entity.Cuenta;
import com.devsu.ms_account.entity.Usuario;
import com.devsu.ms_account.repository.CuentaRepository;
import com.devsu.ms_account.repository.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CuentaServiceImpl implements CuentaService {

    @Autowired
    private CuentaRepository cuentaRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;


    @Override
    @Transactional(readOnly = true)
    public List<CuentaDTO> listarTodas() {
        return cuentaRepository.findAll().stream().map(this::convertirAcuentaDTO).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public CuentaDTO obtenerCuentaPorNumero(String numeroCuenta) {
        return convertirAcuentaDTO(cuentaRepository.findByNumeroCuenta(numeroCuenta)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada con número de cuenta: " + numeroCuenta)));
    }

    @Override
    @Transactional(readOnly = true)
    public CuentaDTO obtenerCuentaPorId(Long id) {
        return convertirAcuentaDTO(cuentaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada con id: " + id)));
    }

    @Override
    @Transactional
    public CuentaDTO crearCuenta(CuentaDTO cuentaDTO) {

        Usuario usuario = usuarioRepository.findByIdentificacion(cuentaDTO.getIdentificacionCliente());
        Cuenta cuenta = convertirAcuenta(cuentaDTO);
        cuenta.setNombre(usuario.getNombre());
        cuenta.setEstado(true);
        return convertirAcuentaDTO(cuentaRepository.save(cuenta));
    }

    @Override
    @Transactional
    public CuentaDTO actualizarCuenta(Long id, CuentaDTO cuentaDTO) {
        Cuenta cuentaExistente = cuentaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada con id: " + id));
        cuentaExistente.setNumeroCuenta(cuentaDTO.getNumeroCuenta());
        cuentaExistente.setTipoCuenta(cuentaDTO.getTipoCuenta());

        return convertirAcuentaDTO(cuentaRepository.save(cuentaExistente));
    }

    @Override
    @Transactional
    public void eliminarCuenta(Long id) {
        Cuenta cuentaExistente = cuentaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada con id: " + id));
        cuentaRepository.delete(cuentaExistente);
    }


    private Cuenta convertirAcuenta(CuentaDTO cuentaDTO) {
        Cuenta cuenta = new Cuenta();
        cuenta.setNumeroCuenta(cuentaDTO.getNumeroCuenta());
        cuenta.setTipoCuenta(cuentaDTO.getTipoCuenta());
        cuenta.setSaldoInicial(cuentaDTO.getSaldoInicial());
        cuenta.setEstado(cuentaDTO.getEstado());
        cuenta.setClienteId(cuentaDTO.getClienteId());
        return cuenta;
    }

    private CuentaDTO convertirAcuentaDTO(Cuenta cuenta) {
        CuentaDTO cuentaDTO = new CuentaDTO();
        cuentaDTO.setId(cuenta.getId());
        cuentaDTO.setNumeroCuenta(cuenta.getNumeroCuenta());
        cuentaDTO.setTipoCuenta(cuenta.getTipoCuenta());
        cuentaDTO.setSaldoInicial(cuenta.getSaldoInicial());
        cuentaDTO.setEstado(cuenta.getEstado());
        cuentaDTO.setClienteId(cuenta.getClienteId());
        cuentaDTO.setIdentificacionCliente(cuenta.getIdentificacionCliente());
        return cuentaDTO;
    }   
    
}

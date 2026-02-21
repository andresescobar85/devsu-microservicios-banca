
package com.devsu.ms_client.service;

import java.util.List;
import com.devsu.ms_client.dto.ClienteDTO;

public interface ClienteService {
    ClienteDTO crearCliente(ClienteDTO clienteDTO);
    ClienteDTO obtenerClientePorId(String clienteId);
    List<ClienteDTO> obtenerTodosLosClientes();
    ClienteDTO actualizarCliente(String clienteId, ClienteDTO clienteDTO);
    void eliminarCliente(String clienteId);
}
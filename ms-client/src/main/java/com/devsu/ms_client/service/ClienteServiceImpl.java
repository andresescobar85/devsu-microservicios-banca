package com.devsu.ms_client.service;

import com.devsu.ms_client.config.RabbitMQConfig;
import com.devsu.ms_client.dto.ClienteDTO;
import com.devsu.ms_client.entity.Cliente;
import com.devsu.ms_client.exception.BadRequestException;
import com.devsu.ms_client.exception.ResourceNotFoundException;
import com.devsu.ms_client.repository.ClienteRepository;

import java.util.List;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    @Transactional
    public ClienteDTO crearCliente(ClienteDTO clienteDTO){

        if(clienteRepository.existsByIdentificacion(clienteDTO.getIdentificacion())){
            throw new BadRequestException("Ya existe un cliente con esa identificacion");
        }

        Cliente cliente = convertirACliente(clienteDTO);
        cliente = clienteRepository.save(cliente);

        ClienteDTO clienteCreadoDTO = convertirAClienteDTO(cliente);    

        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.ROUTING_KEY, clienteCreadoDTO);
        
        return clienteCreadoDTO;
    }

    @Override
    @Transactional(readOnly = true)
    public ClienteDTO obtenerClientePorId(String clienteId) {
        Cliente cliente = clienteRepository.findByClienteId(clienteId)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con id: " + clienteId));
        return convertirAClienteDTO(cliente);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClienteDTO> obtenerTodosLosClientes() {
        List<Cliente> clientes = clienteRepository.findAll();
        return clientes.stream().map(this::convertirAClienteDTO).toList();
    }

    @Override
    @Transactional
    public ClienteDTO actualizarCliente(String clienteId, ClienteDTO clienteDTO) {
        Cliente clienteExistente = clienteRepository.findByClienteId(clienteId)
                .orElseThrow(() -> new ResourceNotFoundException("El cliente no se puede actualizar: " + clienteId));
        clienteExistente.setNombre(clienteDTO.getNombre());
        clienteExistente.setGenero(clienteDTO.getGenero());
        clienteExistente.setEdad(clienteDTO.getEdad());
        clienteExistente.setDireccion(clienteDTO.getDireccion());
        clienteExistente.setTelefono(clienteDTO.getTelefono());
        clienteExistente.setContrasena(clienteDTO.getContrasena());
        clienteExistente.setEstado(clienteDTO.getEstado());

        return convertirAClienteDTO(clienteRepository.save(clienteExistente));
    }

    @Override
    @Transactional
    public void eliminarCliente(String clienteId) {
        Cliente clienteExistente = clienteRepository.findByClienteId(clienteId)
                .orElseThrow(() -> new ResourceNotFoundException("El cliente no se puede eliminar: " + clienteId));

        clienteRepository.deleteById(clienteExistente.getId());
    }

    private ClienteDTO convertirAClienteDTO(Cliente cliente) {
        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setId(cliente.getId());
        clienteDTO.setNombre(cliente.getNombre());
        clienteDTO.setGenero(cliente.getGenero());
        clienteDTO.setEdad(cliente.getEdad());
        clienteDTO.setIdentificacion(cliente.getIdentificacion());
        clienteDTO.setDireccion(cliente.getDireccion());
        clienteDTO.setTelefono(cliente.getTelefono());
        clienteDTO.setClienteId(cliente.getClienteId());
        clienteDTO.setContrasena(cliente.getContrasena());
        clienteDTO.setEstado(cliente.getEstado());
        return clienteDTO;
    }

    private Cliente convertirACliente(ClienteDTO clienteDTO) {
        Cliente cliente = new Cliente();
        cliente.setNombre(clienteDTO.getNombre());
        cliente.setGenero(clienteDTO.getGenero());
        cliente.setEdad(clienteDTO.getEdad());
        cliente.setIdentificacion(clienteDTO.getIdentificacion());
        cliente.setDireccion(clienteDTO.getDireccion());
        cliente.setTelefono(clienteDTO.getTelefono());
        cliente.setClienteId(clienteDTO.getClienteId());
        cliente.setContrasena(clienteDTO.getContrasena());
        cliente.setEstado(clienteDTO.getEstado());
        return cliente;
    }
    
}

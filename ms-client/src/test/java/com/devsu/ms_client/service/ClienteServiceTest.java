package com.devsu.ms_client.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*; 

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.devsu.ms_client.repository.ClienteRepository;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import com.devsu.ms_client.dto.ClienteDTO;
import com.devsu.ms_client.entity.Cliente;
import com.devsu.ms_client.exception.BadRequestException;


@ExtendWith(MockitoExtension.class)
public class ClienteServiceTest {
    

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteServiceImpl clienteService;

    @Test
    @DisplayName("PRUEBA DE CREACION DE CLIENTE")
    void GuardarCliente() {
        // Arrange
        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setNombre("Juan Perez");
        clienteDTO.setIdentificacion("1234567890");

        Cliente clienteEntity = new Cliente();
        clienteEntity.setId(1L);
        clienteEntity.setNombre(clienteDTO.getNombre());
        clienteEntity.setIdentificacion(clienteDTO.getIdentificacion());

        when(clienteRepository.existsByIdentificacion(clienteDTO.getIdentificacion())).thenReturn(false);
        when(clienteRepository.save(any(Cliente.class))).thenReturn(clienteEntity);

        ClienteDTO resultado = clienteService.crearCliente(clienteDTO);

        assertNotNull(resultado);
        assertEquals("1234567890", resultado.getIdentificacion());    

        verify(clienteRepository).existsByIdentificacion(clienteDTO.getIdentificacion());
        verify(clienteRepository).save(any(Cliente.class));
    }

    @Test
    @DisplayName("DEBE LANZAR LA EXCEPCION CUANDO SE INTENTA CREAR UN CLIENTE CON UNA IDENTIFICACION EXISTENTE")
    void crearClienteConIdentificacionExistente() {
        // Arrange
        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setNombre("Juan Perez");
        clienteDTO.setIdentificacion("1234567890");

        when(clienteRepository.existsByIdentificacion(clienteDTO.getIdentificacion())).thenReturn(true);

        // Act & Assert
        assertThrows(BadRequestException.class, () -> {
            clienteService.crearCliente(clienteDTO);
        });

        verify(clienteRepository, never()).save(any(Cliente.class));
    }

}

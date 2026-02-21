package com.devsu.ms_account.messaging;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.devsu.ms_account.dto.ClienteDTO;
import com.devsu.ms_account.entity.Usuario;
import com.devsu.ms_account.repository.UsuarioRepository;

@Component
public class ClienteConsumer {

    @Autowired
    private UsuarioRepository clienteLocalRepository;

    @RabbitListener(queues = "client.queue")
    public void recibirCliente(ClienteDTO clienteDTO) {
        Usuario usuario = new Usuario();
        usuario.setId(clienteDTO.getId());
        usuario.setIdentificacion(clienteDTO.getIdentificacion());
        usuario.setNombre(clienteDTO.getNombre());
        usuario.setDireccion(clienteDTO.getDireccion());
        usuario.setTelefono(clienteDTO.getTelefono());
        usuario.setContrasena(clienteDTO.getContrasena());
        usuario.setEstado(clienteDTO.getEstado());
        clienteLocalRepository.save(usuario);
    }
    
}

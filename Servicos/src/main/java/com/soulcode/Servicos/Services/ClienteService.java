package com.soulcode.Servicos.Services;

import com.soulcode.Servicos.Models.Cliente;
import com.soulcode.Servicos.Repositories.ClienteRepository;
import com.soulcode.Servicos.Services.Exceptions.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    ClienteRepository clienteRepository;

    @Cacheable("clientesCache") // só chama o return se o cache expirar clientesCache::[]
    public List<Cliente> mostrarTodosClientes() {
        return clienteRepository.findAll();
    }

    @Cacheable(value = "clientesCache", key = "#idCliente")  // clientesCache::1
    public Cliente mostrarClientePeloId(Integer idCliente){
        Optional<Cliente> cliente = clienteRepository.findById(idCliente);
        return cliente.orElseThrow(
                () -> new EntityNotFoundException("Cliente não cadastrado no Id: " + idCliente)
        );
    }

    @Cacheable(value = "clientesCache", key = "#email")
    public Cliente mostrarUmClientePeloEmail(String email) {
        Optional<Cliente> emailCliente = clienteRepository.findByEmail(email);
        return emailCliente.orElseThrow();
    }

    @CachePut(value = "clientesCache", key = "#cliente.idCliente")
    public Cliente cadastrarCliente(Cliente cliente) {
        cliente.setIdCliente(null);
        return clienteRepository.save(cliente);
    }

    @CacheEvict(value = "clientesCache", key = "#idCliente", allEntries = true)
    public void excluirCliente(Integer idCliente) {
        clienteRepository.deleteById(idCliente);
    }

    @CachePut(value = "clientesCache", key = "#cliente.idCliente")
    public Cliente editarCliente(Cliente cliente) {
        mostrarClientePeloId(cliente.getIdCliente());
        return clienteRepository.save(cliente);
    }
}

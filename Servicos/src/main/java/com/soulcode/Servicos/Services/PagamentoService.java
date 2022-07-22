package com.soulcode.Servicos.Services;


import com.soulcode.Servicos.Models.Chamado;
import com.soulcode.Servicos.Models.Pagamento;
import com.soulcode.Servicos.Models.StatusPagamento;
import com.soulcode.Servicos.Repositories.ChamadoRepository;
import com.soulcode.Servicos.Repositories.PagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class PagamentoService {

    @Autowired
    PagamentoRepository pagamentoRepository;

    @Autowired
    ChamadoRepository chamadoRepository;

    @Cacheable("pagamentosCache")
    public List<Pagamento> mostrarTodosPagamentos() {
        return pagamentoRepository.findAll();
    }

    @Cacheable(value = "pagamentosCache", key = "#idPagamento")
    public Pagamento mostrarUmPagamentoPeloId(Integer idPagamento) {
        Optional<Pagamento> pagamento = pagamentoRepository.findById(idPagamento);
        return pagamento.orElseThrow();
    }

    @Cacheable("pagamentosCache")
    public List<Pagamento> buscarPagamentoPeloStatus(String status) {
        return pagamentoRepository.findByStatus(status);
    }

    @CachePut(value = "pagamentosCache", key = "#idChamado")
    public Pagamento cadastrarPagamento(Pagamento pagamento, Integer idChamado) throws Exception{
        Optional<Chamado> chamado = chamadoRepository.findById(idChamado);
        if(chamado.isPresent()) {
            pagamento.setIdPagamento(idChamado);
            pagamentoRepository.save(pagamento);
            pagamento.setStatusPagamento(StatusPagamento.LANCADO);
            chamado.get().setPagamento(pagamento);
            chamadoRepository.save(chamado.get());
            return pagamento;
        } else {
            throw new Exception();
        }
    }

    @CachePut(value = "pagamentosCache", key = "#pagamento.idPagamento")
    public Pagamento editarPagamento(Pagamento pagamento){
        return pagamentoRepository.save(pagamento);
    }

    @CachePut(value = "pagamentoCache", key = "#status")
    public Pagamento modificarStatusPagamento(Integer idPagamento, String status) {
        Pagamento pagamento = mostrarUmPagamentoPeloId(idPagamento);

        switch (status) {
            case "LANCADO": {
                pagamento.setStatusPagamento(StatusPagamento.LANCADO);
                break;
            }
            case "QUITADO": {
                pagamento.setStatusPagamento(StatusPagamento.QUITADO);
                break;
            }
        }
        return pagamentoRepository.save(pagamento);
    }

    @Cacheable(value = "pagamentosCache")
    public List<List> orcamentoComServicoCliente() {
        return Collections.singletonList(pagamentoRepository.orcamentoComServicoCliente());
    }
}

package com.soulcode.Servicos.Controllers;

import com.soulcode.Servicos.Models.Pagamento;
import com.soulcode.Servicos.Services.PagamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;


@CrossOrigin
@RestController
@RequestMapping("servicos")
public class PagamentoController {

    @Autowired
    PagamentoService pagamentoService;

    @GetMapping("/pagamentos")
    public List<Pagamento> mostrarTodosPagamentos() {
        List<Pagamento> pagamentos = pagamentoService.mostrarTodosPagamentos();
        return pagamentos;
    }

    @GetMapping("/pagamentos/{idPagamento}")
    public ResponseEntity<Pagamento> mostrarUmPagamentoPeloId(@PathVariable Integer idPagamento) {
        Pagamento pagamento = pagamentoService.mostrarUmPagamentoPeloId(idPagamento);
        return ResponseEntity.ok().body(pagamento);
    }

    @GetMapping("/pagamentosPeloStatus")
    public List<Pagamento> buscarPagamentoPeloStatus(@RequestParam("status") String status){
        List<Pagamento> pagamentos = pagamentoService.buscarPagamentoPeloStatus(status);
        return pagamentos;
    }

    @GetMapping("/pagamentosChamadosComCliente")
    public List<List> orcamentoComServicoCliente() {
        List<List> pagamentos = pagamentoService.orcamentoComServicoCliente();
        return pagamentos;
    }

    @PostMapping("/pagamentos/{idChamado}")
    public ResponseEntity<Pagamento> cadastrarPagamento(@PathVariable Integer idChamado,
                                                        @RequestBody Pagamento pagamento) {
        try {
            pagamento = pagamentoService.cadastrarPagamento(pagamento, idChamado);
            URI novaUri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                    .buildAndExpand(pagamento.getIdPagamento()).toUri();
            return ResponseEntity.created(novaUri).body(pagamento);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/pagamentos/{idPagamento}")
    public ResponseEntity<Pagamento> editarPagamento(@PathVariable Integer idPagamento,
                                                     @RequestBody Pagamento pagamento) {
        pagamento.setIdPagamento(idPagamento);
        pagamentoService.editarPagamento(pagamento);
        return ResponseEntity.ok().body(pagamento);
    }

    @PutMapping("/pagamentoModificarStatus/{idPagamento}")
    public ResponseEntity<Pagamento> modificarStatusPagamento(@PathVariable Integer idPagamento,
                                                              @RequestParam("status") String status){
        pagamentoService.modificarStatusPagamento(idPagamento, status);
        return ResponseEntity.noContent().build();
    }
}

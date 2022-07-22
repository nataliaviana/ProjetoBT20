package com.soulcode.Servicos.Controllers;

import com.soulcode.Servicos.Models.Funcionario;
import com.soulcode.Servicos.Services.FuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@CrossOrigin
@RestController //API do tipo Rest (cada endpoint precisa ser independente um do outro - usado HTTP)-
//Controller significa que vai ser responsavel pela camada de Controller da API
//(Camada que é responsavel pela criação dos EndPoints e são colocados na Aplicação)
@RequestMapping("servicos") //o que for acessar dentro da api, começa em serviços
public class FuncionarioController {

    @Autowired
    FuncionarioService funcionarioService;

    @GetMapping("/funcionarios") // '/funcionarios' seria onde vamos buscar
    public List<Funcionario> mostrarTodosFuncionarios() {
        List<Funcionario> funcionarios = funcionarioService.mostrarTodosFuncionarios(); //funcionaros = nome da lista
        return funcionarios;
    }

    @GetMapping("/funcionarios/{idFuncionario}")
    public ResponseEntity<Funcionario> mostrarUmFuncionarioPeloId(@PathVariable Integer idFuncionario) {
        // @PathVariable está dizendo que vai vir pelo caminho da rota e vai substitui o {idFuncionario}
        Funcionario funcionario = funcionarioService.mostrarUmFuncionarioPeloId(idFuncionario);
        return ResponseEntity.ok().body(funcionario);
    }

    @GetMapping("/funcionariosEmail/{email}")
    public ResponseEntity<Funcionario> mostrarUmFuncionarioPeloEmail(@PathVariable String email) {
        Funcionario funcionario = funcionarioService.mostrarUmFuncionarioPeloEmail(email);
        return ResponseEntity.ok().body(funcionario);
    }

    @GetMapping("/funcionariosPeloCargo/{idCargo}")
    public List<Funcionario> mostrarFuncionariosPeloCargo(@PathVariable Integer idCargo) {
        List<Funcionario> funcionarios = funcionarioService.mostrarFuncionariosPeloCargo(idCargo);
        return funcionarios;
    }

    @PostMapping("/funcionarios/")
    public ResponseEntity<Funcionario> cadastrarFuncionario(@RequestBody Funcionario funcionario) {
        //nessa linha 42, o funcionario já é salvo na tabela do database
        //agora precisamos criar uma uri para esse novo registro da tabela
        funcionario = funcionarioService.cadastrarFuncionario(funcionario);
//        if (funcionario == null) {
//            return ResponseEntity.badRequest().body(null);
//        }
        URI novaUri = ServletUriComponentsBuilder.fromCurrentRequest().path("id")
                .buildAndExpand(funcionario.getIdFuncionario()).toUri();
        return ResponseEntity.created(novaUri).body(funcionario);
    }

    @DeleteMapping("/funcionarios/{idFuncionario}")
    public ResponseEntity<Void> excluirFuncionario(@PathVariable Integer idFuncionario) {
        funcionarioService.excluirFuncionario(idFuncionario);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/funcionarios/{idFuncionario}")
    public ResponseEntity<Funcionario> editarFuncionario(@PathVariable Integer idFuncionario,
                                                         @RequestBody Funcionario funcionario){
        funcionario.setIdFuncionario(idFuncionario);
        funcionarioService.editarFuncionario(funcionario);
        return ResponseEntity.ok().body(funcionario);
    }
}
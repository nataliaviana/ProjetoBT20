package com.soulcode.Servicos.Controllers;

import com.soulcode.Servicos.Models.Cargo;
import com.soulcode.Servicos.Services.CargoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("servicos")
public class CargoController {

    @Autowired
    CargoService cargoService;

    // Buscar todos os cargos
    @GetMapping("/cargos")
    public List<Cargo> mostrarTodosCargos() {
        List<Cargo> cargos = cargoService.mostrarTodosCargos();
        return cargos;
    }

    // Buscar cargo pelo ID
    @GetMapping("/cargos/{idCargo}")
    public ResponseEntity<Cargo> mostrarCargoPorId(@PathVariable Integer idCargo) {
        Cargo cargo = cargoService.mostrarUmCargoPeloId(idCargo);
        return ResponseEntity.ok().body(cargo);
    }

    // Cadastro de cargos
    @PostMapping("/cargos")
    public ResponseEntity<Cargo> cadastrarCargo(@RequestBody Cargo cargo) {
        cargo = cargoService.cadastrarCargo(cargo);

        URI novaURI = ServletUriComponentsBuilder.fromCurrentRequest().path("id")
                .buildAndExpand(cargo.getIdCargo()).toUri();
        return ResponseEntity.created(novaURI).body(cargo);
    }

    // Deletar Cargos
    @DeleteMapping("/cargos/{idCargo}")
    public ResponseEntity<Void> excluirCargo(@PathVariable Integer idCargo) {
        cargoService.excluirCargo(idCargo);
        return ResponseEntity.noContent().build();
    }

    // Editar cargos
    @PutMapping("/cargos/{idCargo}")
    public ResponseEntity<Cargo> editarCargo(@PathVariable Integer idCargo,
                                             @RequestBody Cargo cargo) {
        cargo.setIdCargo(idCargo);
        cargoService.editarCargo(cargo);
        return ResponseEntity.ok().body(cargo);
    }
}

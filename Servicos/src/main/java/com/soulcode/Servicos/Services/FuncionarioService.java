package com.soulcode.Servicos.Services;

import com.soulcode.Servicos.Models.Cargo;
import com.soulcode.Servicos.Models.Funcionario;
import com.soulcode.Servicos.Repositories.CargoRepository;
import com.soulcode.Servicos.Repositories.FuncionarioRepository;
import com.soulcode.Servicos.Services.Exceptions.DataIntegrityViolationException;
import com.soulcode.Servicos.Services.Exceptions.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

//Quando se fala em serviços, estamos falando dos metodos do crud da tabela

@Service
public class FuncionarioService {

    //primeiro serviço na tabela de funcionarios vai ser a leitura de todos os funcionarios cadastrados
    //findAll -> método do spring Data JPA -> busca todos os registros de uma tabela
    @Autowired
    FuncionarioRepository funcionarioRepository;

    @Autowired
    CargoRepository cargoRepository;
    public List<Funcionario> mostrarTodosFuncionarios() {
        return funcionarioRepository.findAll();
    }

    //vamos mais um serviço relacionado ao funcionário
    //criar um serviço de buscar apenas um funcionário pelo seu id (chave primária)
    public Funcionario mostrarUmFuncionarioPeloId(Integer idFuncionario) {
        Optional<Funcionario> funcionario = funcionarioRepository.findById(idFuncionario);
        return funcionario.orElseThrow(
                ()-> new EntityNotFoundException("Funcionário não cadastrado no Id: " + idFuncionario)
        );
    }

    //vamos criar mais um serviço pra buscar um funcionario pelo seu email
    public Funcionario mostrarUmFuncionarioPeloEmail(String email) {
        Optional<Funcionario> emailFuncionario = funcionarioRepository.findByEmail(email);
        return emailFuncionario.orElseThrow();
    }

    public List<Funcionario> mostrarFuncionariosPeloCargo(Integer idCargo){
        Optional<Cargo> cargoFuncionarios = cargoRepository.findById(idCargo);
        return funcionarioRepository.findByCargo(cargoFuncionarios);
    }

    //vamos criar um serviço para cadastrar um novo funcionário
    public Funcionario cadastrarFuncionario(Funcionario funcionario) {
        //só por precaução nós vamos colocar o id do funcionário como nullo
        try {
            funcionario.setIdFuncionario(null);
//
//            Optional<Cargo> cargo = cargoRepository.findById(idCargo);
            funcionario.setCargo(null);
//        if (funcionario.getCargo() == null){
//            return null;
//        }
            return funcionarioRepository.save(funcionario);
        } catch (Exception e) {
            throw new DataIntegrityViolationException("Erro ao cadastrar funcionários" + funcionario);
        }
    }

    public void excluirFuncionario(Integer idFuncionario) {
        funcionarioRepository.deleteById(idFuncionario);
    }

    public Funcionario editarFuncionario(Funcionario funcionario) {
        Funcionario funcionarioProcurado = mostrarUmFuncionarioPeloId(funcionario.getIdFuncionario());
        return funcionarioRepository.save(funcionario);
    }

    public Funcionario salvarFoto(Integer idFuncionario, String caminhoFoto) {
        Funcionario funcionario = mostrarUmFuncionarioPeloId(idFuncionario);
        funcionario.setFoto(caminhoFoto);
        return funcionarioRepository.save(funcionario);
    }
}
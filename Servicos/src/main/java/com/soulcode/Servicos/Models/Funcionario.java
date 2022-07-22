package com.soulcode.Servicos.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Funcionario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idFuncionario;

    @Column(nullable = false, length = 100) //nullable false = não pode ser nulo
    private String nome;

    @Column(nullable = false, length = 100, unique = true)
    private String email;

    @Column(nullable = true)
    private String foto;

    @JsonIgnore
    @OneToMany(mappedBy = "funcionario")
    private List<Chamado> chamados = new ArrayList<Chamado>();

    // a primeira parte da anotação do relacionamento diz respeito a tabela que estamos inserindo o relacionalmento (funcionário)
    // a segunda parte da anotação do
    @ManyToOne
    @JoinColumn(name = "idCargo", nullable = true)
    private Cargo cargo;

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    public Integer getIdFuncionario() {
        return idFuncionario;
    }

    public void setIdFuncionario(Integer idFuncionario) {
        this.idFuncionario = idFuncionario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}
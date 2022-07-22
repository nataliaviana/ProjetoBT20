package com.soulcode.Servicos.Models;

public enum StatusChamado {

    RECEBIDO("Recebido"),
    ATRIBUIDO("Atribuído"),
    CONCLUIDO("Concluído"),
    ARQUIVADO("Arquivado");

    private String conteudo;

    StatusChamado(String conteudo){
        this.conteudo = conteudo;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }
}
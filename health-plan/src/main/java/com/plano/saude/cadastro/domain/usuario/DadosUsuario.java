package com.plano.saude.cadastro.domain.usuario;

public record DadosUsuario(Long id, String nome, String login, Boolean admin) {

    public DadosUsuario(Usuario usuario) {
        this(usuario.getId(), usuario.getNome(), usuario.getLogin(), usuario.isAdmin());
    }
}

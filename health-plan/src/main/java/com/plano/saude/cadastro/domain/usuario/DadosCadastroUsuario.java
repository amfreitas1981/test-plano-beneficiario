package com.plano.saude.cadastro.domain.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record DadosCadastroUsuario(
        @NotBlank(message = "Nome é obrigatório!")
        String nome,
        @NotBlank(message = "E-mail é obrigatório!")
        @Email(message = "E-mail em formato inválido!")
        String login,
        @NotBlank(message = "Senha é obrigatória!")
        String password,
        Boolean admin) {
}

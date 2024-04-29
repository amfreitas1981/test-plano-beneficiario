package com.plano.saude.cadastro.domain.medico;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record DadosCadastroMedico(

        @NotBlank
        String nome,

        @NotBlank
        @Email
        String email,

        @NotBlank
        @Pattern(regexp = "\\d{4,6}")
        String crm,

        String telefone,

        @NotNull
        Especialidade especialidade,

        com.plano.saude.cadastro.domain.endereco.DadosEndereco endereco) {
}

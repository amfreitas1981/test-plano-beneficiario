package com.plano.saude.cadastro.domain.documento;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record DadosCadastroDocumento(
        @NotNull
        TipoDocumento tipoDocumento,
        @NotNull
        String numero,
        @NotNull
        LocalDate dataExpedicao,
        String descricao,
        @NotNull
        LocalDate dataInclusao,
        LocalDate dataAtualizacao) {
}

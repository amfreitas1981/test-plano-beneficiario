package com.plano.saude.cadastro.domain.beneficiario;

import com.plano.saude.cadastro.domain.documento.TipoDocumento;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record DadosAtualizacaoDocumento(
        @NotNull
        Long id,
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

package com.plano.saude.cadastro.domain.documento;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record DadosDetalhamentoDocumento(
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
        public DadosDetalhamentoDocumento(Documento documento) {
                this(
                        documento.getId(),
                        documento.getTipoDocumento(),
                        documento.getNumero(),
                        documento.getDataExpedicao(),
                        documento.getDescricao(),
                        documento.getDataInclusao(),
                        documento.getDataAtualizacao()
                );
        }
}

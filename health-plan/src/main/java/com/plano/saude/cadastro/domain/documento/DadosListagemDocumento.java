package com.plano.saude.cadastro.domain.documento;

import java.time.LocalDate;

public record DadosListagemDocumento(
        Long id,
        TipoDocumento tipoDocumento,
        String numero,
        LocalDate dataExpedicao,
        String descricao,
        LocalDate dataInclusao,
        LocalDate dataAtualizacao) {
    public DadosListagemDocumento(Documento documento) {
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

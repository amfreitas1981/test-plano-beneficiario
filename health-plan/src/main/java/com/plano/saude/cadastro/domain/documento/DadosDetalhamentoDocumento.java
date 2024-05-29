package com.plano.saude.cadastro.domain.documento;

import com.plano.saude.cadastro.domain.beneficiario.DadosAtualizacaoDocumento;
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

        public DadosDetalhamentoDocumento(DadosCadastroDocumento dadosCadastroDocumento) {
                this(
                        null,
                        dadosCadastroDocumento.tipoDocumento(),
                        dadosCadastroDocumento.numero(),
                        dadosCadastroDocumento.dataExpedicao(),
                        dadosCadastroDocumento.descricao(),
                        dadosCadastroDocumento.dataInclusao(),
                        dadosCadastroDocumento.dataAtualizacao()
                );
        }

        public DadosDetalhamentoDocumento(DadosAtualizacaoDocumento dadosAtualizacaoDocumento) {
                this(
                        null,
                        dadosAtualizacaoDocumento.tipoDocumento(),
                        dadosAtualizacaoDocumento.numero(),
                        dadosAtualizacaoDocumento.dataExpedicao(),
                        dadosAtualizacaoDocumento.descricao(),
                        dadosAtualizacaoDocumento.dataInclusao(),
                        dadosAtualizacaoDocumento.dataAtualizacao()
                );
        }
}

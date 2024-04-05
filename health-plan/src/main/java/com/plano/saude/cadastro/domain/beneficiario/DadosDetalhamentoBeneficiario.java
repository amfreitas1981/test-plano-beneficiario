package com.plano.saude.cadastro.domain.beneficiario;

import com.plano.saude.cadastro.domain.documento.DadosDetalhamentoDocumento;
import com.plano.saude.cadastro.domain.endereco.Endereco;

import java.time.LocalDate;
import java.util.List;

public record DadosDetalhamentoBeneficiario(
        Long id,
        String nome,
        String telefone,
        LocalDate dataNascimento,
        LocalDate dataInclusao,
        LocalDate dataAtualizacao,
        List<DadosDetalhamentoDocumento> documentos,
        Endereco endereco) {

    public DadosDetalhamentoBeneficiario(Beneficiario beneficiario) {
        this(
                beneficiario.getId(),
                beneficiario.getNome(),
                beneficiario.getTelefone(),
                beneficiario.getDataNascimento(),
                beneficiario.getDataInclusao(),
                beneficiario.getDataAtualizacao(),
                beneficiario.getDocumentos().stream().map(DadosDetalhamentoDocumento::new).toList(),
                beneficiario.getEndereco()
        );
    }
}

package com.plano.saude.cadastro.domain.beneficiario;

import com.plano.saude.cadastro.domain.documento.DadosListagemDocumento;
import com.plano.saude.cadastro.domain.endereco.Endereco;

import java.time.LocalDate;
import java.util.List;

public record DadosListagemBeneficiario(
        Long id,
        String nome,
        String telefone,
        LocalDate dataNascimento,
        LocalDate dataInclusao,
        LocalDate dataAtualizacao,
        List<DadosListagemDocumento> documentos,
        Endereco endereco) {

    public DadosListagemBeneficiario(Beneficiario beneficiario){
        this(
                beneficiario.getId(),
                beneficiario.getNome(),
                beneficiario.getTelefone(),
                beneficiario.getDataNascimento(),
                beneficiario.getDataInclusao(),
                beneficiario.getDataAtualizacao(),
                beneficiario.getDocumentos().stream().map(DadosListagemDocumento::new).toList(),
                beneficiario.getEndereco()
        );
    }
}

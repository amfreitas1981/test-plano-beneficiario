package com.plano.saude.cadastro.domain.beneficiario;

import com.plano.saude.cadastro.domain.endereco.DadosEndereco;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record DadosAtualizacaoBeneficiario(
        @NotNull
        Long id,
        String nome,
        String telefone,
        DadosEndereco endereco,
        List<DadosAtualizacaoDocumento> documentos) {
}

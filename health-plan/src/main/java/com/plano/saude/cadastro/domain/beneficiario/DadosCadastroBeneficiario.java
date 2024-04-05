package com.plano.saude.cadastro.domain.beneficiario;

import com.plano.saude.cadastro.domain.documento.DadosCadastroDocumento;
import com.plano.saude.cadastro.domain.endereco.DadosEndereco;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public record DadosCadastroBeneficiario(
        @NotBlank
        String nome,
        @NotBlank
        String telefone,
        @NotBlank
        LocalDate dataNascimento,
        @NotBlank
        LocalDate dataInclusao,
        LocalDate dataAtualizacao,
        @NotNull
        @Valid
        List<DadosCadastroDocumento> documentos,
        @NotNull
        @Valid
        DadosEndereco endereco) {
}

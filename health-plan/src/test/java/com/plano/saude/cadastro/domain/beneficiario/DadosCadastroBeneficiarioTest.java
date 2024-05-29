package com.plano.saude.cadastro.domain.beneficiario;

import com.plano.saude.cadastro.domain.documento.DadosCadastroDocumento;
import com.plano.saude.cadastro.domain.documento.TipoDocumento;
import com.plano.saude.cadastro.domain.endereco.DadosEndereco;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class DadosCadastroBeneficiarioTest {

    @Test
    @DisplayName("Teste para mapear retorno sucesso")
    void testDadosCadastroBeneficiarioSucesso(){
        DadosCadastroDocumento dadosCadastroDocumento = new DadosCadastroDocumento(
                TipoDocumento.CARTEIRA_NACIONAL_HABILITACAO,
                "123456789",
                LocalDate.parse("2010-03-18"),
                "Documento Teste",
                LocalDate.parse("2023-12-18"),
                LocalDate.parse("2023-12-18")
        );

        DadosEndereco dadosEndereco = new DadosEndereco(
                "Rua xpto",
                "Bairro",
                "12345-789",
                "Cidade",
                "SP",
                "Casa",
                "789");

        DadosCadastroBeneficiario dadosCadastroBeneficiario = new DadosCadastroBeneficiario(
                "Nome Beneficiario",
                "1132878987",
                LocalDate.parse("2000-01-14"),
                LocalDate.parse("2020-08-17"),
                LocalDate.parse("2020-09-17"),
                Collections.singletonList(dadosCadastroDocumento),
                dadosEndereco
        );

        assertNotNull(dadosCadastroBeneficiario);
    }
}
package com.plano.saude.cadastro.domain.documento;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class DadosCadastroDocumentoTest {

    @Test
    @DisplayName("Teste para mapear retorno sucesso")
    void testDadosCadastroDocumentoSucesso(){
        DadosCadastroDocumento dadosCadastroDocumento = new DadosCadastroDocumento(
                TipoDocumento.CARTEIRA_NACIONAL_HABILITACAO,
                "123456789",
                LocalDate.parse("2010-03-18"),
                "Documento Teste",
                LocalDate.parse("2023-12-18"),
                LocalDate.parse("2023-12-18")
        );

        assertNotNull(dadosCadastroDocumento);
    }
}
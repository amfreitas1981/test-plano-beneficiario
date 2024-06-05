package com.plano.saude.cadastro.domain.documento;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class DadosDetalhamentoDocumentoTest {

    @Test
    @DisplayName("Teste para mapear retorno sucesso")
    void testDadosDetalhamentoDocumentoSucesso(){
        List<DadosDetalhamentoDocumento> documentosEsperados = Collections.singletonList(new DadosDetalhamentoDocumento(
                null,
                TipoDocumento.CARTEIRA_DE_TRABALHO,
                "12345678910",
                LocalDate.parse("2000-03-03"),
                "Test descrp",
                LocalDate.parse("2024-03-03"),
                LocalDate.parse("2024-03-03")
        ));

        assertNotNull(documentosEsperados);
    }
}
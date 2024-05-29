package com.plano.saude.cadastro.domain.beneficiario;

import com.plano.saude.cadastro.domain.documento.TipoDocumento;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class DadosAtualizacaoDocumentoTest {

    @Test
    @DisplayName("Teste para mapear retorno sucesso")
    void testDadosAtualizacaoDocumentoSucesso(){
        DadosAtualizacaoDocumento dadosAtualizacaoDocumento = new DadosAtualizacaoDocumento(
                null,
                TipoDocumento.CARTEIRA_IDENTIDADE,
                "43789563",
                LocalDate.parse("2019-09-18"),
                "Test description",
                LocalDate.parse("2024-04-16"),
                LocalDate.parse("2024-04-16"));

        assertNotNull(dadosAtualizacaoDocumento);
    }
}
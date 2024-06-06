package com.plano.saude.cadastro.domain.documento;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static com.plano.saude.cadastro.util.BeneficiarioCreator.createBeneficiarioToBeSaved;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class DocumentoTest {

    @Test
    @DisplayName("Deveria retornar sucesso")
    void testDocumentoSucesso(){
        Documento documento = new Documento(
                1L,
                TipoDocumento.CARTEIRA_NACIONAL_HABILITACAO,
                "123456",
                LocalDate.parse("2023-11-01"),
                createBeneficiarioToBeSaved(),
                "Descricao",
                LocalDate.parse("2023-11-11"),
                LocalDate.parse("2023-12-21"),
                true
        );

        assertNotNull(documento);
    }
}
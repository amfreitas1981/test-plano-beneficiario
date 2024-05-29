package com.plano.saude.cadastro.domain.documento;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TipoDocumentoTest {

    @Test
    @DisplayName("Conversão de String para Enum com sucesso")
    public void testFromStringSuccess() {
        assertNotNull(TipoDocumento.CARTEIRA_IDENTIDADE);
        assertNotNull(TipoDocumento.valueOf("CARTEIRA_IDENTIDADE"));
        assertEquals(TipoDocumento.CARTEIRA_IDENTIDADE, TipoDocumento.fromString("CARTEIRA_IDENTIDADE"));
        assertNotEquals("RG", TipoDocumento.CARTEIRA_IDENTIDADE.name());

        assertNotNull(TipoDocumento.CPF);
        assertNotNull(TipoDocumento.valueOf("CPF"));
        assertEquals(TipoDocumento.CPF, TipoDocumento.fromString("CPF"));
        assertNotEquals("CIC", TipoDocumento.CPF.name());

        assertNotNull(TipoDocumento.CREA);
        assertNotNull(TipoDocumento.valueOf("CREA"));
        assertEquals(TipoDocumento.CREA, TipoDocumento.fromString("CREA"));
        assertNotEquals("CREIA", TipoDocumento.CREA.name());

        assertNotNull(TipoDocumento.CRM);
        assertNotNull(TipoDocumento.valueOf("CRM"));
        assertEquals(TipoDocumento.CRM, TipoDocumento.fromString("CRM"));
        assertNotEquals("MEDICINA", TipoDocumento.CRM.name());

        assertNotNull(TipoDocumento.CRO);
        assertNotNull(TipoDocumento.valueOf("CRO"));
        assertEquals(TipoDocumento.CRO, TipoDocumento.fromString("CRO"));
        assertNotEquals("DENTISTA", TipoDocumento.CRO.name());

        assertNotNull(TipoDocumento.CRP);
        assertNotNull(TipoDocumento.valueOf("CRP"));
        assertEquals(TipoDocumento.CRP, TipoDocumento.fromString("CRP"));
        assertNotEquals("PSICOLOGIA", TipoDocumento.CRP.name());

        assertNotNull(TipoDocumento.CRF);
        assertNotNull(TipoDocumento.valueOf("CRF"));
        assertEquals(TipoDocumento.CRF, TipoDocumento.fromString("CRF"));
        assertNotEquals("FARMACIA", TipoDocumento.CRF.name());

        assertNotNull(TipoDocumento.CARTEIRA_NACIONAL_HABILITACAO);
        assertNotNull(TipoDocumento.valueOf("CARTEIRA_NACIONAL_HABILITACAO"));
        assertEquals(TipoDocumento.CARTEIRA_NACIONAL_HABILITACAO, TipoDocumento.fromString("CARTEIRA_NACIONAL_HABILITACAO"));
        assertNotEquals("CNH", TipoDocumento.CARTEIRA_NACIONAL_HABILITACAO.name());

        assertNotNull(TipoDocumento.CARTEIRA_IDENTIFICACAO_FUNCIONAL);
        assertNotNull(TipoDocumento.valueOf("CARTEIRA_IDENTIFICACAO_FUNCIONAL"));
        assertEquals(TipoDocumento.CARTEIRA_IDENTIFICACAO_FUNCIONAL, TipoDocumento.fromString("CARTEIRA_IDENTIFICACAO_FUNCIONAL"));
        assertNotEquals("CIF", TipoDocumento.CARTEIRA_IDENTIFICACAO_FUNCIONAL.name());

        assertNotNull(TipoDocumento.CARTEIRA_DE_TRABALHO);
        assertNotNull(TipoDocumento.valueOf("CARTEIRA_DE_TRABALHO"));
        assertEquals(TipoDocumento.CARTEIRA_DE_TRABALHO, TipoDocumento.fromString("CARTEIRA_DE_TRABALHO"));
        assertNotEquals("CTPS", TipoDocumento.CARTEIRA_DE_TRABALHO.name());

        assertNotNull(TipoDocumento.PASSAPORTE);
        assertNotNull(TipoDocumento.valueOf("PASSAPORTE"));
        assertEquals(TipoDocumento.PASSAPORTE, TipoDocumento.fromString("PASSAPORTE"));
        assertNotEquals("PASSPORT", TipoDocumento.PASSAPORTE.name());

        assertNotNull(TipoDocumento.REGISTRO_NACIONAL_ESTRANGEIRO);
        assertNotNull(TipoDocumento.valueOf("REGISTRO_NACIONAL_ESTRANGEIRO"));
        assertEquals(TipoDocumento.REGISTRO_NACIONAL_ESTRANGEIRO, TipoDocumento.fromString("REGISTRO_NACIONAL_ESTRANGEIRO"));
        assertNotEquals("RNE", TipoDocumento.REGISTRO_NACIONAL_ESTRANGEIRO.name());
    }

    @Test
    @DisplayName("Conversão de String para Enum com falha")
    public void testFromStringFailure() {
        assertThrows(IllegalArgumentException.class, () -> TipoDocumento.fromString("INEXISTENTE"));
    }
}
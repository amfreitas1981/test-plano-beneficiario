package com.plano.saude.cadastro.domain.consulta;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MotivoCancelamentoTest {

    @Test
    @DisplayName("Conversão de String para Enum com sucesso")
    public void testFromStringSuccess() {
        assertNotNull(MotivoCancelamento.BENEFICIARIO_DESISTIU);
        assertNotNull(MotivoCancelamento.valueOf("BENEFICIARIO_DESISTIU"));
        assertEquals(MotivoCancelamento.BENEFICIARIO_DESISTIU, MotivoCancelamento.fromString("BENEFICIARIO_DESISTIU"));
        assertNotEquals("DESISTIU", MotivoCancelamento.BENEFICIARIO_DESISTIU.name());

        assertNotNull(MotivoCancelamento.MEDICO_CANCELOU);
        assertNotNull(MotivoCancelamento.valueOf("MEDICO_CANCELOU"));
        assertEquals(MotivoCancelamento.MEDICO_CANCELOU, MotivoCancelamento.fromString("MEDICO_CANCELOU"));
        assertNotEquals("CANCELADO", MotivoCancelamento.MEDICO_CANCELOU.name());

        assertNotNull(MotivoCancelamento.OUTROS);
        assertNotNull(MotivoCancelamento.valueOf("OUTROS"));
        assertEquals(MotivoCancelamento.OUTROS, MotivoCancelamento.fromString("OUTROS"));
        assertNotEquals("OUTRA_COISA", MotivoCancelamento.OUTROS.name());
    }

    @Test
    @DisplayName("Conversão de String para Enum com falha")
    public void testFromStringFailure() {
        assertThrows(IllegalArgumentException.class, () -> MotivoCancelamento.fromString("INEXISTENTE"));
    }
}
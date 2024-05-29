package com.plano.saude.cadastro.domain.medico;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class EspecialidadeTest {

    @Test
    @DisplayName("Conversão de String para Enum com sucesso")
    public void testFromStringSuccess() {
        assertNotNull(Especialidade.ORTOPEDIA);
        assertNotNull(Especialidade.valueOf("ORTOPEDIA"));
        assertEquals(Especialidade.ORTOPEDIA, Especialidade.fromString("ORTOPEDIA"));
        assertNotEquals("ORTOPEIA", Especialidade.ORTOPEDIA.name());

        assertNotNull(Especialidade.CARDIOLOGIA);
        assertNotNull(Especialidade.valueOf("CARDIOLOGIA"));
        assertEquals(Especialidade.CARDIOLOGIA, Especialidade.fromString("CARDIOLOGIA"));
        assertNotEquals("CARDIOLOIA", Especialidade.CARDIOLOGIA.name());

        assertNotNull(Especialidade.GINECOLOGIA);
        assertNotNull(Especialidade.valueOf("GINECOLOGIA"));
        assertEquals(Especialidade.GINECOLOGIA, Especialidade.fromString("GINECOLOGIA"));
        assertNotEquals("GINECLOGIA", Especialidade.GINECOLOGIA.name());

        assertNotNull(Especialidade.DERMATOLOGIA);
        assertNotNull(Especialidade.valueOf("DERMATOLOGIA"));
        assertEquals(Especialidade.DERMATOLOGIA, Especialidade.fromString("DERMATOLOGIA"));
        assertNotEquals("DERMATOLOGI", Especialidade.DERMATOLOGIA.name());
    }

    @Test
    @DisplayName("Conversão de String para Enum com falha")
    public void testFromStringFailure() {
        assertThrows(IllegalArgumentException.class, () -> Especialidade.fromString("INEXISTENTE"));
    }
}

package com.plano.saude.cadastro.domain.medico;

import com.plano.saude.cadastro.domain.endereco.Endereco;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class MedicoTest {

    @Test
    @DisplayName("Teste para mapear retorno sucesso")
    void testMedicoSucesso(){
        Medico medico = new Medico(
            1L,
            "Nome Medico",
            "medico@doutor.com",
            "123456",
            "11994567843",
            Especialidade.CARDIOLOGIA,
            new Endereco(),
            true
        );

        assertNotNull(medico);
    }
}
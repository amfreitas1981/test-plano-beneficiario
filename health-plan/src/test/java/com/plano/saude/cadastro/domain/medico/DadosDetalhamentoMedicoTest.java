package com.plano.saude.cadastro.domain.medico;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.plano.saude.cadastro.util.DadosDetalhamentoMedicoCreator.createDadosDetalhamentoMedicoToBeSaved;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class DadosDetalhamentoMedicoTest {

    @Test
    @DisplayName("Teste para mapear retorno sucesso")
    void testDadosDetalhamentoMedicoSucesso(){
        DadosDetalhamentoMedico dadosDetalhamentoMedico = createDadosDetalhamentoMedicoToBeSaved();

        assertNotNull(dadosDetalhamentoMedico);
    }
}
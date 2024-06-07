package com.plano.saude.cadastro.domain.medico;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.plano.saude.cadastro.util.DadosAtualizacaoMedicoCreator.createDadosAtualizacaoMedicoToBeSaved;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class DadosAtualizacaoMedicoTest {

    @Test
    @DisplayName("Teste para mapear retorno sucesso")
    void testDadosAtualizacaoMedicoSucesso(){
        DadosAtualizacaoMedico dadosAtualizacaoMedico = createDadosAtualizacaoMedicoToBeSaved();

        assertNotNull(dadosAtualizacaoMedico);
    }
}
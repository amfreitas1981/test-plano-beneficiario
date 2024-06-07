package com.plano.saude.cadastro.domain.medico;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class DadosListagemMedicoTest {

    @Test
    @DisplayName("Teste para mapear retorno sucesso")
    void testDadosListagemMedicoSucesso(){
        DadosListagemMedico dadosListagemMedico = new DadosListagemMedico(
                1L,
                "Nome Listagem Medico",
                "email.listagem@medico.com",
                "231457",
                "1174873347",
                Especialidade.DERMATOLOGIA
        );

        assertNotNull(dadosListagemMedico);
    }
}
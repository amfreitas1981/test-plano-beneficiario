package com.plano.saude.cadastro.domain.consulta;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DadosCancelamentoConsultaTest {

    @Test
    @DisplayName("Teste para mapear retorno sucesso")
    void testDadosCancelamentoConsultaSucesso(){
        DadosCancelamentoConsulta dadosCancelamentoConsulta = new DadosCancelamentoConsulta(
                1L,
                MotivoCancelamento.OUTROS
        );

        assertNotNull(dadosCancelamentoConsulta);
    }
}
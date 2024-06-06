package com.plano.saude.cadastro.domain.consulta;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class DadosDetalhamentoConsultaTest {

    @Test
    @DisplayName("Teste para mapear retorno sucesso")
    void testDadosDetalhamentoConsultaSucesso(){

        DadosDetalhamentoConsulta dadosDetalhamentoConsulta = new DadosDetalhamentoConsulta(
                1L,
                2L,
                3L,
                LocalDateTime.now()
        );

        assertNotNull(dadosDetalhamentoConsulta);
    }
}
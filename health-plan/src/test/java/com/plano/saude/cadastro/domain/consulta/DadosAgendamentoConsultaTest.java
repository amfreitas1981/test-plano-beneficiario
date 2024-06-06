package com.plano.saude.cadastro.domain.consulta;

import com.plano.saude.cadastro.domain.medico.Especialidade;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class DadosAgendamentoConsultaTest {

    @Test
    @DisplayName("Teste para mapear retorno sucesso")
    void testDadosAgendamentoConsultaSucesso(){
        DadosAgendamentoConsulta dadosAgendamentoConsulta = new DadosAgendamentoConsulta(
                1L,
                1L,
                LocalDateTime.now(),
                Especialidade.CARDIOLOGIA
        );

        assertNotNull(dadosAgendamentoConsulta);
    }
}
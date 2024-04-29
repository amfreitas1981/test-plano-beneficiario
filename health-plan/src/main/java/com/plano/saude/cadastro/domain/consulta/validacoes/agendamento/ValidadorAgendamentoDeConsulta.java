package com.plano.saude.cadastro.domain.consulta.validacoes.agendamento;

import com.plano.saude.cadastro.domain.consulta.DadosAgendamentoConsulta;

public interface ValidadorAgendamentoDeConsulta {
    void validar(DadosAgendamentoConsulta dados);
}

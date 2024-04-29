package com.plano.saude.cadastro.domain.consulta.validacoes.agendamento;

import com.plano.saude.cadastro.domain.ValidacaoException;
import com.plano.saude.cadastro.domain.consulta.ConsultaRepository;
import com.plano.saude.cadastro.domain.consulta.DadosAgendamentoConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorBeneficiarioSemOutraConsultaNoDia implements ValidadorAgendamentoDeConsulta{
    @Autowired
    private ConsultaRepository consultaRepository;
    public void validar(DadosAgendamentoConsulta dados){
        var primeiroHorario = dados.data().withHour(7);
        var ultimoHorario = dados.data().withHour(18);
        var beneficiarioPossuiOutraConsultaNoDia = consultaRepository.existsByBeneficiarioIdAndDataBetween(dados.idBeneficiario(), primeiroHorario, ultimoHorario);
        if (beneficiarioPossuiOutraConsultaNoDia){
            throw new ValidacaoException("Beneficiário já possui uma consulta agendada nesse dia");
        }
    }
}

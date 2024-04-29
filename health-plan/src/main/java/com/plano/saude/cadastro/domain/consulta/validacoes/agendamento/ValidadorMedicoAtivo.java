package com.plano.saude.cadastro.domain.consulta.validacoes.agendamento;

import com.plano.saude.cadastro.domain.ValidacaoException;
import com.plano.saude.cadastro.domain.consulta.DadosAgendamentoConsulta;
import com.plano.saude.cadastro.domain.medico.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorMedicoAtivo implements ValidadorAgendamentoDeConsulta {

    @Autowired
    private MedicoRepository medicoRepository;

    public void validar(DadosAgendamentoConsulta dados){
        //escolha do médico opcional
        if (dados.idMedico() == null){
            return;
        }

        var medicoEstaAtivo = medicoRepository.findAtivoById(dados.idMedico());
        if (!medicoEstaAtivo){
            throw new ValidacaoException("Consulta não pode ser agendada com médico excluído");
        }
    }
}

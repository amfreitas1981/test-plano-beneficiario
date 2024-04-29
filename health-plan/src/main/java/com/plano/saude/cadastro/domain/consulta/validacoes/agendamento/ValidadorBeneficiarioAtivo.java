package com.plano.saude.cadastro.domain.consulta.validacoes.agendamento;

import com.plano.saude.cadastro.domain.ValidacaoException;
import com.plano.saude.cadastro.domain.beneficiario.BeneficiarioRepository;
import com.plano.saude.cadastro.domain.consulta.DadosAgendamentoConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorBeneficiarioAtivo implements ValidadorAgendamentoDeConsulta{
    @Autowired
    private BeneficiarioRepository beneficiarioRepository;

    public void validar(DadosAgendamentoConsulta dados){
        var beneficiarioEstaAtivo = beneficiarioRepository.findAtivoById(dados.idBeneficiario());
        if (!beneficiarioEstaAtivo){
            throw new ValidacaoException("Consulta não pode ser agendada com beneficiário excluído");
        }
    }
}

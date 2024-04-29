package com.plano.saude.cadastro.domain.consulta;

import com.plano.saude.cadastro.domain.ValidacaoException;
import com.plano.saude.cadastro.domain.beneficiario.BeneficiarioRepository;
import com.plano.saude.cadastro.domain.consulta.validacoes.agendamento.ValidadorAgendamentoDeConsulta;
import com.plano.saude.cadastro.domain.consulta.validacoes.cancelamento.ValidadorCancelamentoDeConsulta;
import com.plano.saude.cadastro.domain.medico.Medico;
import com.plano.saude.cadastro.domain.medico.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgendaDeConsultas {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private BeneficiarioRepository beneficiarioRepository;

    @Autowired
    private List<ValidadorAgendamentoDeConsulta> validadores;

    @Autowired
    private List<ValidadorCancelamentoDeConsulta> validadoresCancelamento;

    public DadosDetalhamentoConsulta agendar(DadosAgendamentoConsulta dados){
        if (!beneficiarioRepository.existsById(dados.idBeneficiario())){
            throw new ValidacaoException("Id do beneficiário informado não existe!");
        }
        if (dados.idMedico() != null && !medicoRepository.existsById(dados.idMedico())){
            throw new ValidacaoException("Id do médico informado não existe!");
        }

        validadores.forEach(v -> v.validar(dados));

        var beneficiario = beneficiarioRepository.getReferenceById(dados.idBeneficiario());
//        var medico = medicoRepository.findById(dados.idMedico()).get(); // Funcionaria, se eu tiver todos os ids. Como a escolha pode ser opcional, pode acontecer de estar nula, então esse algoritmo, precisa ser mudado.
        var medico = escolherMedico(dados);

        if (medico == null){
            throw new ValidacaoException("Não existe médico disponível nessa data");
        }

        var consulta = new Consulta(null, medico, beneficiario, dados.data(), null);

        consultaRepository.save(consulta);

        return new DadosDetalhamentoConsulta(consulta);
    }

    private Medico escolherMedico(DadosAgendamentoConsulta dados) {
        if (dados.idMedico() != null){
            return medicoRepository.getReferenceById(dados.idMedico());
        }

        if (dados.especialidade() == null){
            throw new ValidacaoException("Especialidade é obrigatória quando o médico não for escolhido!");
        }

        return medicoRepository.escolherMedicoAleatorioLivreNaData(dados.especialidade(), dados.data());
    }

    public void cancelar(DadosCancelamentoConsulta dados) {
        if (!consultaRepository.existsById(dados.idConsulta())) {
            throw new ValidacaoException("Id da consulta informado não existe!");
        }

        validadoresCancelamento.forEach(v -> v.validar(dados));

        var consulta = consultaRepository.getReferenceById(dados.idConsulta());
        consulta.cancelar(dados.motivo());
    }
}

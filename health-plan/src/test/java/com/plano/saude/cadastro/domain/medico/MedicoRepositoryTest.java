package com.plano.saude.cadastro.domain.medico;

import com.plano.saude.cadastro.domain.beneficiario.Beneficiario;
import com.plano.saude.cadastro.domain.beneficiario.DadosCadastroBeneficiario;
import com.plano.saude.cadastro.domain.consulta.Consulta;
import com.plano.saude.cadastro.domain.documento.DadosCadastroDocumento;
import com.plano.saude.cadastro.domain.documento.TipoDocumento;
import com.plano.saude.cadastro.domain.endereco.DadosEndereco;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class MedicoRepositoryTest {

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private TestEntityManager em;

    @Test
    @DisplayName("Deveria retornar null, quando único médico cadastrado não estiver disponível na data")
    void escolherMedicoAleatorioLivreNaDataCenario1() {
        // given ou arrange
        var proximaSegundaAs10 = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY)).atTime(10, 0);

        var medico = cadastrarMedico(1L, "Medico", "medico@plano.saude", "123456", Especialidade.CARDIOLOGIA);
        var beneficiario = cadastrarBeneficiario(
                null,
                "Beneficiário",
                "234-5678",
                LocalDate.parse("1983-06-18"),
                LocalDate.parse("2023-03-27"),
                LocalDate.parse("2023-03-27"),
                dadosCadastroDocumento(),
                dadosEndereco());
        cadastrarConsulta(medico, beneficiario, proximaSegundaAs10);

        // when ou act
        var medicoLivre = medicoRepository.escolherMedicoAleatorioLivreNaData(Especialidade.CARDIOLOGIA, proximaSegundaAs10);

        // then ou assert
        assertThat(medicoLivre).isNull();
    }

    @Test
    @DisplayName("Deveria retornar medico, quando médico cadastrado estiver disponível na data")
    void escolherMedicoAleatorioLivreNaDataCenario2() {
        // given ou arrange
        var proximaSegundaAs10 = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY)).atTime(10, 0);

        var medico = cadastrarMedico(1L, "Medico", "medico@plano.saude", "123456", Especialidade.CARDIOLOGIA);

        // when ou act
        var medicoLivre = medicoRepository.escolherMedicoAleatorioLivreNaData(Especialidade.CARDIOLOGIA, proximaSegundaAs10);

        // then ou assert
        assertThat(medicoLivre).isEqualTo(medico);
    }

    private void cadastrarConsulta(Medico medico, Beneficiario beneficiario, LocalDateTime data) {
        em.persist(new Consulta(null, medico, beneficiario, data, null));
    }

    private Medico cadastrarMedico(Long id, String nome, String email, String crm, Especialidade especialidade) {
        var medico = new Medico(dadosMedico(id, nome, email, crm, especialidade));
        em.persist(medico);

        return medico;
    }

    private Beneficiario cadastrarBeneficiario(Long id, String nome, String telefone, LocalDate dataNascimento, LocalDate dataInclusao, LocalDate dataAtualizacao, List<DadosCadastroDocumento> dadosCadastroDocumentos, DadosEndereco dadosEndereco){
        var beneficiario = new Beneficiario(dadosBeneficiario(id, nome, telefone, dataNascimento, dataAtualizacao, dataInclusao, dadosCadastroDocumentos, dadosEndereco));
        em.persist(beneficiario);

        return beneficiario;
    }

    private DadosCadastroBeneficiario dadosBeneficiario(Long id, String nome, String telefone, LocalDate dataNascimento, LocalDate dataAtualizacao, LocalDate dataInclusao, List<DadosCadastroDocumento> dadosCadastroDocumentos, DadosEndereco dadosEndereco) {
        return new DadosCadastroBeneficiario(
                nome, telefone, dataNascimento, dataInclusao, dataAtualizacao, dadosCadastroDocumentos, dadosEndereco
        );
    }

    private DadosCadastroMedico dadosMedico(Long id, String nome, String email, String crm, Especialidade especialidade) {
        return new DadosCadastroMedico(
                nome,
                email,
                crm,
                "1198724827",
                especialidade,
                dadosEndereco()
        );
    }

    private List<DadosCadastroDocumento> dadosCadastroDocumento() {
        return Collections.singletonList(new DadosCadastroDocumento(
                TipoDocumento.CARTEIRA_NACIONAL_HABILITACAO,
                "123456789",
                LocalDate.parse("2010-03-18"),
                "Documento Teste",
                LocalDate.parse("2023-12-18"),
                LocalDate.parse("2023-12-18")
        ));
    }

    private DadosEndereco dadosEndereco() {
        return new DadosEndereco(
                "rua xpto",
                "bairro",
                "00000000",
                "Brasilia",
                "DF",
                null,
                null
        );
    }
}

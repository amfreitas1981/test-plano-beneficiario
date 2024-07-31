package com.plano.saude.cadastro.domain.consulta;

//import com.plano.saude.cadastro.domain.beneficiario.BeneficiarioRepository;
//import com.plano.saude.cadastro.domain.consulta.validacoes.agendamento.ValidadorAgendamentoDeConsulta;
//import com.plano.saude.cadastro.domain.consulta.validacoes.cancelamento.ValidadorCancelamentoDeConsulta;
//import com.plano.saude.cadastro.domain.medico.MedicoRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.Mockito.any;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//public class AgendaDeConsultasTest {
//
//    @Mock
//    private ConsultaRepository consultaRepository;
//
//    @Mock
//    private MedicoRepository medicoRepository;
//
//    @Mock
//    private BeneficiarioRepository beneficiarioRepository;
//
//    @Mock
//    private List<ValidadorAgendamentoDeConsulta> validadores;
//
//    @Mock
//    private List<ValidadorCancelamentoDeConsulta> validadoresCancelamento;
//
//    @InjectMocks
//    private AgendaDeConsultas agendaDeConsultas;
//
//    @BeforeEach
//    void setUp() {
//        // Configurar mocks se necessário
//    }
//
//    @Test
//    void testAgendamentoDeConsultaComSucesso() {
//        // Configurar os mocks para o cenário de sucesso
//        when(consultaRepository.save(any())).thenReturn(new Consulta());
//        // Adicione mais configurações de mocks conforme necessário
//
//        // Chamar o método a ser testado
//        agendaDeConsultas.agendar(/* parâmetros necessários */);
//
//        // Verificar se os métodos dos mocks foram chamados corretamente
//        verify(consultaRepository).save(any());
//        // Adicione mais verificações conforme necessário
//    }
//
//    @Test
//    void testAgendamentoDeConsultaComErro() {
//        // Configurar os mocks para o cenário de erro
//        when(consultaRepository.save(any())).thenThrow(new RuntimeException("Erro ao salvar consulta"));
//        // Adicione mais configurações de mocks conforme necessário
//
//        // Chamar o método a ser testado e verificar se a exceção é lançada
//        assertThrows(RuntimeException.class, () -> {
//            agendaDeConsultas.agendar(/* parâmetros necessários */);
//        });
//
//        // Verificar se os métodos dos mocks foram chamados corretamente
//        verify(consultaRepository).save(any());
//        // Adicione mais verificações conforme necessário
//    }
//}

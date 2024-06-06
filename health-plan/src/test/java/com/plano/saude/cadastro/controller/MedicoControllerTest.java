package com.plano.saude.cadastro.controller;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.plano.saude.cadastro.domain.endereco.DadosEndereco;
import com.plano.saude.cadastro.domain.endereco.Endereco;
import com.plano.saude.cadastro.domain.medico.DadosAtualizacaoMedico;
import com.plano.saude.cadastro.domain.medico.DadosCadastroMedico;
import com.plano.saude.cadastro.domain.medico.DadosDetalhamentoMedico;
import com.plano.saude.cadastro.domain.medico.DadosListagemMedico;
import com.plano.saude.cadastro.domain.medico.Especialidade;
import com.plano.saude.cadastro.domain.medico.Medico;
import com.plano.saude.cadastro.domain.medico.MedicoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class MedicoControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<DadosCadastroMedico> dadosCadastroMedicoJson;

    @Autowired
    private JacksonTester<DadosAtualizacaoMedico> dadosAtualizacaoMedicoJson;

    @Autowired
    private JacksonTester<DadosDetalhamentoMedico> dadosDetalhamentoMedicoJson;

    @JsonIgnoreProperties(ignoreUnknown = true)
    private record DadosMedicos(@JsonProperty("content") List<DadosListagemMedico> medicos) {};

    @MockBean
    private MedicoRepository medicoRepository;

    @Test
    @DisplayName("Deveria devolver código http 400, quando informações estão inválidas")
    @WithMockUser
    void cadastrarCenario1() throws Exception {
        var response = mvc.perform(post("/medicos")).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Deveria devolver código http 201 quando as informações estiverem válidas")
    @WithMockUser
    void cadastraCenario2() throws Exception {
        DadosCadastroMedico dadosCadastro = new DadosCadastroMedico("Medico", "medico@plano.saude", "619999",
                "11997823456", Especialidade.CARDIOLOGIA, dadosEndereco());

        when(medicoRepository.save(any())).thenReturn(new Medico(dadosCadastro));

        MockHttpServletResponse response = mvc
                .perform(post("/medicos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dadosCadastroMedicoJson.write(dadosCadastro).getJson())).andReturn().getResponse();

        DadosDetalhamentoMedico dadosDetalhamento = new DadosDetalhamentoMedico(null, dadosCadastro.nome(),
                dadosCadastro.email(), dadosCadastro.crm(), dadosCadastro.telefone(), dadosCadastro.especialidade(),
                new Endereco(dadosCadastro.endereco()));

        String jsonEsperado = dadosDetalhamentoMedicoJson.write(dadosDetalhamento).getJson();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());

        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
    }

    private DadosEndereco dadosEndereco() {
        return new DadosEndereco("Rua xpto", "Bairro", "12345-789", "Cidade", "SP", "Casa", "123");
    }

    @Test
    @DisplayName("Deveria devolver código http 404, quando as informações estão inválidas")
    @WithMockUser
    void listarCenario1() throws Exception {
        // Simular que o pageable não existe
        given(medicoRepository.findAllByAtivoTrue(any())).willThrow(EntityNotFoundException.class);

        var response = mvc.perform(get("/medicos")
                        .param("page", "0")
                        .param("size", "10")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @Test
    @DisplayName("Deveria devolver código http 200, quando as informações estiverem válidas")
    @WithMockUser
    void listarCenario2() throws Exception {
        // Arrange

        /**
         * Usadas as anotações @Builder e @AllArgsConstructor do Lombok na classe Medico.
         * Assim ele gera um método estático que retorna um builder do objeto.
         */
        Medico medico = Medico.builder()
                .id(1L)
                .nome("Nome Medico")
                .email("medico@doutor.com")
                .crm("123456")
                .telefone("11994567843")
                .especialidade(Especialidade.CARDIOLOGIA)
                .endereco(new Endereco())
                .ativo(true)
                .build();

        Page<Medico> medicos = new PageImpl<>(List.of(medico));
        when(medicoRepository.findAllByAtivoTrue(any(Pageable.class))).thenReturn(medicos);

        // Act
        var result = mvc.perform(get("/medicos")
                        .param("page", "0")
                        .param("size", "10")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, true);
        objectMapper.registerModule(new JavaTimeModule());

        DadosMedicos responseBody = null;
        try {
            String json = result.getResponse().getContentAsString();
            responseBody = objectMapper.readValue(json, new TypeReference<DadosMedicos>() {});
        } catch (Exception e) {
            e.printStackTrace();
        }
        DadosListagemMedico objectResult = responseBody.medicos().get(0);

        // Asserts
        assertNotNull(responseBody);
        assertFalse(responseBody.medicos().isEmpty());
        assertEquals(1, responseBody.medicos().size());

        assertEquals(objectResult.id(), medico.getId());
        assertEquals(objectResult.nome(), medico.getNome());
        assertEquals(objectResult.email(), medico.getEmail());
        assertEquals(objectResult.crm(), medico.getCrm());
        assertEquals(objectResult.telefone(), medico.getTelefone());
        assertEquals(objectResult.especialidade(), medico.getEspecialidade());

        // Passado o endereço com atributos null. Não tem necessidade de validá-los
    }

    @Test
    @DisplayName("Deveria devolver código http 400, quando as informações estão inválidas")
    @WithMockUser
    void atualizarCenario1() throws Exception {
        var response = mvc.perform(put("/medicos")).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Deveria devolver código http 200, quando as informações estão inválidas")
    @WithMockUser
    void atualizarCenario2() throws Exception {
        DadosEndereco dadosEndereco = new DadosEndereco(
                "Rua Morada do Beneficiario",
                "Bairro",
                "12345-789",
                "Cidade",
                "SP",
                "Casa",
                "789");

        DadosAtualizacaoMedico dadosAtualizacaoMedico = new DadosAtualizacaoMedico(
                1L,
                "Nome Medico Atualizado",
                "1174588639",
                dadosEndereco
        );

        // Considerando que sua classe Endereco tenha um construtor que receba um objeto DadosEndereco:
        var enderecoEsperado = new Endereco(dadosAtualizacaoMedico.endereco());

        DadosDetalhamentoMedico dadosDetalhamentoMedico = new DadosDetalhamentoMedico(
                null,
                dadosAtualizacaoMedico.nome(),
                "email@email.com",
                "456123",
                dadosAtualizacaoMedico.telefone(),
                Especialidade.DERMATOLOGIA,
                enderecoEsperado
        );

        // Simular que o id existe
        when(medicoRepository.getReferenceById(any())).thenReturn(new Medico(dadosDetalhamentoMedico));

        var response = mvc
                .perform(
                        put("/medicos")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(dadosAtualizacaoMedicoJson.write(dadosAtualizacaoMedico).getJson()))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

        String jsonEsperado = dadosDetalhamentoMedicoJson.write(dadosDetalhamentoMedico).getJson();

        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
    }

    @Test
    @DisplayName("Deveria devolver código http 404, quando as informações estão inválidas")
    @WithMockUser
    void excluirCenario1() throws Exception {
        Long id = 2L;

        var medico = medicoRepository.getReferenceById(id);

        // simulando que o id não existe
        given(medico).willThrow(EntityNotFoundException.class);

        var response = mvc.perform(delete("/medicos/{id}", id)).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @Test
    @DisplayName("Deveria devolver código http 204, quando as informações estiverem válidas")
    @WithMockUser
    void excluirCenario2() throws Exception {
        Long id = 1L;

        var repository = medicoRepository.getReferenceById(id);

        // simular a ação de inativar o beneficiário no efeito de remover (cancelar).
        Medico medico = new Medico();
        medico.excluir();
        when(repository).thenReturn(medico);

        var response = mvc.perform(delete("/medicos/{id}", id)).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @Test
    @DisplayName("Deveria devolver código http 404, quando as informações estão inválidas")
    @WithMockUser
    void excluirDefinitivoCenario1() throws Exception {
        Long id = 2L;

        var repository = medicoRepository;
        repository.deleteById(id);

        // simulando que o id não existe
        given(repository).willThrow(EntityNotFoundException.class);

        var response = mvc.perform(delete("/medicos/definitivo/{id}", id)).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @Test
    @DisplayName("Deveria devolver código http 204, quando as informações estiverem válidas")
    @WithMockUser
    void excluirDefinitivoCenario2() throws Exception {
        Long id = 1L;

        var response = mvc.perform(delete("/medicos/definitivo/{id}", id)).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @Test
    @DisplayName("Deveria devolver código http 404, quando informações estão inválidas")
    @WithMockUser
    void detalharCenario1() throws Exception {
        Long id = 2L;

        var repository = medicoRepository.getReferenceById(id);

        // Simular que o id não existe
        given(repository).willThrow(EntityNotFoundException.class);

        var response = mvc.perform(get("/medicos/{id}", id)).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @Test
    @DisplayName("Deveria devolver código http 200, quando as informações estiverem válidas")
    @WithMockUser
    void detalharCenario2() throws Exception {
        Long id = 1L;

        var repository = medicoRepository.getReferenceById(id);

        DadosEndereco dadosEndereco = new DadosEndereco(
                "Rua",
                "Barrio",
                "05000-000",
                "Cidadela",
                "YY",
                "Barraco",
                "957"
        );

        DadosAtualizacaoMedico dadosAtualizacaoMedico = new DadosAtualizacaoMedico(
                null,
                "Nome Medico Atualizado",
                "11987456218",
                dadosEndereco
        );

        DadosDetalhamentoMedico dadosDetalhamentoMedico = new DadosDetalhamentoMedico(
                null,
                dadosAtualizacaoMedico.nome(),
                "email@email.com",
                "666666",
                dadosAtualizacaoMedico.telefone(),
                Especialidade.DERMATOLOGIA,
                new Endereco()
        );

        when(medicoRepository.getReferenceById(anyLong())).thenReturn(new Medico(dadosDetalhamentoMedico));

        var response = mvc.perform(get("/medicos/{id}", id)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(dadosDetalhamentoMedicoJson.write(dadosDetalhamentoMedico).getJson())
                )
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }
}

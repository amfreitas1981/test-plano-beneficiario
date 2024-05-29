package com.plano.saude.cadastro.controller;

import com.plano.saude.cadastro.domain.endereco.DadosEndereco;
import com.plano.saude.cadastro.domain.endereco.Endereco;
import com.plano.saude.cadastro.domain.medico.DadosCadastroMedico;
import com.plano.saude.cadastro.domain.medico.DadosDetalhamentoMedico;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class MedicoControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<DadosCadastroMedico> dadosCadastroMedicoJson;

    @Autowired
    private JacksonTester<DadosDetalhamentoMedico> dadosDetalhamentoMedicoJson;

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

//    @Test
//    @DisplayName("Deveria devolver código http 404, quando as informações estão inválidas")
//    @WithMockUser
//    void listarCenario1() throws Exception {
////        Pageable pageable = Pageable.unpaged(Sort.by(new String[]{"Nome"}));
//        Long id = 2L;
//
//        var repository = medicoRepository;
//        Pageable pageable = (Pageable) repository.findAllByAtivoTrue(id);
//
//        // simulando que o pageable não existe
//        given(repository).willThrow(EntityNotFoundException.class);
//
//        var response = mvc.perform(get("/medicos/{id}", id)).andReturn().getResponse();
//
//        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
//    }

    @Test
    @DisplayName("Deveria devolver código http 200, quando as informações estiverem válidas")
    @WithMockUser
    void listarCenario2() throws Exception {
        var response = mvc.perform(get("/medicos")).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
//        mvc.perform(get("/medicos")).andExpect(status().isOk());
    }

    @Test
    @DisplayName("Deveria devolver código http 400, quando as informações estão inválidas")
    @WithMockUser
    void atualizarCenario1() throws Exception {
        var response = mvc.perform(put("/medicos")).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
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
        var response = mvc.perform(get("/medicos/1")).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }
}
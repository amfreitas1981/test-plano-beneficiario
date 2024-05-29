package com.plano.saude.cadastro.controller;

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

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

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
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.plano.saude.cadastro.domain.beneficiario.Beneficiario;
import com.plano.saude.cadastro.domain.beneficiario.BeneficiarioRepository;
import com.plano.saude.cadastro.domain.beneficiario.DadosAtualizacaoBeneficiario;
import com.plano.saude.cadastro.domain.beneficiario.DadosAtualizacaoDocumento;
import com.plano.saude.cadastro.domain.beneficiario.DadosCadastroBeneficiario;
import com.plano.saude.cadastro.domain.beneficiario.DadosDetalhamentoBeneficiario;
import com.plano.saude.cadastro.domain.beneficiario.DadosListagemBeneficiario;
import com.plano.saude.cadastro.domain.documento.DadosCadastroDocumento;
import com.plano.saude.cadastro.domain.documento.DadosDetalhamentoDocumento;
import com.plano.saude.cadastro.domain.documento.DadosListagemDocumento;
import com.plano.saude.cadastro.domain.documento.Documento;
import com.plano.saude.cadastro.domain.documento.TipoDocumento;
import com.plano.saude.cadastro.domain.endereco.DadosEndereco;
import com.plano.saude.cadastro.domain.endereco.Endereco;

import jakarta.persistence.EntityNotFoundException;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class BeneficiarioControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<DadosCadastroBeneficiario> dadosCadastroBeneficiarioJson;

    @Autowired
    private JacksonTester<DadosAtualizacaoBeneficiario> dadosAtualizacaoBeneficiarioJson;

    @Autowired
    private JacksonTester<DadosDetalhamentoBeneficiario> dadosDetalhamentoBeneficiarioJson;
    
    @JsonIgnoreProperties(ignoreUnknown = true)
    private record DadosBeneficiarios(@JsonProperty("content") List<DadosListagemBeneficiario> beneficiarios) {};
    
    @MockBean
    private BeneficiarioRepository beneficiarioRepository;
    

    @Test
    @DisplayName("Deveria devolver código http 400, quando informações estão inválidas")
    @WithMockUser
    void createBeneficiaryCenario1() throws Exception {
        var response = mvc.perform(post("/beneficiaries"))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Deveria devolver código http 201, quando as informações estiverem válidas")
    @WithMockUser
    void createBeneficiaryCenario2() throws Exception {
        DadosCadastroDocumento dadosCadastroDocumento = new DadosCadastroDocumento(
                TipoDocumento.CARTEIRA_NACIONAL_HABILITACAO,
                "123456789",
                LocalDate.parse("2010-03-18"),
                "Documento Teste",
                LocalDate.parse("2023-12-18"),
                LocalDate.parse("2023-12-18")
        );

        DadosEndereco dadosEndereco = new DadosEndereco(
                "Rua xpto",
                "Bairro",
                "12345-789",
                "Cidade",
                "SP",
                "Casa",
                "789");

        DadosCadastroBeneficiario dadosCadastroBeneficiario = new DadosCadastroBeneficiario(
                "Nome",
                "1165459787",
                LocalDate.parse("2002-03-12"),
                LocalDate.parse("2023-10-25"),
                LocalDate.parse("2023-10-25"),
                Collections.singletonList(dadosCadastroDocumento),
                dadosEndereco);

        when(beneficiarioRepository.save(any())).thenReturn(new Beneficiario(dadosCadastroBeneficiario));

        var response = mvc
                .perform(
                        post("/beneficiaries")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(dadosCadastroBeneficiarioJson.write(dadosCadastroBeneficiario).getJson()
                                )
                )
                .andReturn().getResponse();

        DadosDetalhamentoDocumento dadosDetalhamentoDocumento = new DadosDetalhamentoDocumento(
                null,
                TipoDocumento.CARTEIRA_NACIONAL_HABILITACAO,
                "1234567890",
                LocalDate.parse("2000-02-11"),
                "Teste descrição",
                LocalDate.parse("2023-12-12"),
                LocalDate.parse("2023-12-12")
        );

        // considerando que sua classe Endereco tenha um construtor que receba um objeto DadosEndereco:
        var enderecoEsperado = new Endereco(dadosCadastroBeneficiario.endereco());

        // considerando que sua classe DadosDetalhamentoDocumento tenha um construtor que receba uma lista DadosCadastroDocumento:
        List<DadosDetalhamentoDocumento> documentosEsperados = dadosCadastroBeneficiario.documentos().stream().map(DadosDetalhamentoDocumento::new).toList();

        DadosDetalhamentoBeneficiario dadosDetalhamentoBeneficiario = new DadosDetalhamentoBeneficiario(
                null,
                dadosCadastroBeneficiario.nome(),
                dadosCadastroBeneficiario.telefone(),
                dadosCadastroBeneficiario.dataNascimento(),
                dadosCadastroBeneficiario.dataInclusao(),
                dadosCadastroBeneficiario.dataAtualizacao(),
                documentosEsperados,
                enderecoEsperado);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());

        String jsonEsperado = dadosDetalhamentoBeneficiarioJson.write(dadosDetalhamentoBeneficiario).getJson();

        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
    }

    @Test
    @DisplayName("Deveria devolver código http 404, quando as informações estão inválidas")
    @WithMockUser
    void listBeneficiaryCenario1() throws Exception {
        Pageable pageable = Pageable.unpaged(Sort.by(new String[]{"Nome"}));

        var repository = beneficiarioRepository;
        repository.findAllByAtivoTrue(pageable);

        // simulando que o pageable não existe
        given(repository).willThrow(EntityNotFoundException.class);

        var response = mvc.perform(get("/beneficiaries")
                        .param("page", "0")
                        .param("size", "10")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @Test
    @DisplayName("Deveria devolver código http 200, quando as informações estiverem válidas")
    @WithMockUser
    void listBeneficiaryCenario2() throws Exception {
        // arrange
        List<Documento> documentos = List.of(Documento.builder()
                .id(1L)
                .tipoDocumento(TipoDocumento.CPF)
                .numero("40420854070")
                .dataExpedicao(LocalDate.now().minusMonths(3L))
                .descricao("Uma descricao")
                .dataInclusao(LocalDate.now().minusDays(10L))
                .dataAtualizacao(LocalDate.now().minusDays(5L))
                .ativo(true)
                .build());
        
        Beneficiario beneficiario = Beneficiario.builder()
                .id(1L)
                .nome("Beneficiario-san")
                .telefone("47940028922")
                .dataNascimento(LocalDate.now().minusYears(26L))
                .dataInclusao(LocalDate.now().minusDays(10L))
                .dataAtualizacao(LocalDate.now().minusDays(5L))
                .documentos(documentos)
                .ativo(true)
                .endereco(new Endereco())
                .build();
        documentos.get(0).setBeneficiario(beneficiario);
        
        Page<Beneficiario> beneficiarios = new PageImpl<>(List.of(beneficiario));
        when(beneficiarioRepository.findAllByAtivoTrue(any(Pageable.class))).thenReturn(beneficiarios);

        // act
        var result = mvc.perform(
                get("/beneficiaries")
                        .param("page", "0")
                        .param("size", "10")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, true);
        objectMapper.registerModule(new JavaTimeModule());
        
        DadosBeneficiarios responseBody = null;
        try {
            String json = result.getResponse().getContentAsString();
            responseBody = objectMapper.readValue(json, new TypeReference<DadosBeneficiarios>() {});

        } catch (Exception e) {
            e.printStackTrace();
        }
        DadosListagemBeneficiario objectResult = responseBody.beneficiarios().get(0);

        // assert
        assertNotNull(responseBody);
        assertFalse(responseBody.beneficiarios().isEmpty());
        assertEquals(1, responseBody.beneficiarios().size());

        assertEquals(objectResult.id(), beneficiario.getId());
        assertEquals(objectResult.nome(), beneficiario.getNome());
        assertEquals(objectResult.telefone(), beneficiario.getTelefone());
        assertEquals(objectResult.dataInclusao(), beneficiario.getDataInclusao());
        assertEquals(objectResult.dataNascimento(), beneficiario.getDataNascimento());
        assertEquals(objectResult.dataAtualizacao(), beneficiario.getDataAtualizacao());
        
//        assertEquals(objectResult.documentos(), beneficiario.getDocumentos()); // precisa comparar os atributos, pois os objetos são diferentes
        
        DadosListagemDocumento documentoResult = objectResult.documentos().get(0);
        Documento documentoBeneficiario = beneficiario.getDocumentos().get(0);
        assertEquals(documentoResult.id(), documentoBeneficiario.getId());
        assertEquals(documentoResult.tipoDocumento(), documentoBeneficiario.getTipoDocumento());
        assertEquals(documentoResult.numero(), documentoBeneficiario.getNumero());
        assertEquals(documentoResult.dataExpedicao(), documentoBeneficiario.getDataExpedicao());
        assertEquals(documentoResult.descricao(), documentoBeneficiario.getDescricao());
        assertEquals(documentoResult.dataInclusao(), documentoBeneficiario.getDataInclusao());
        assertEquals(documentoResult.dataAtualizacao(), documentoBeneficiario.getDataAtualizacao());
        
//        assertEquals(objectResult.endereco(), beneficiario.getEndereco()); // passei o endereço com atributos null, não tem necessidade de valida-los
    }

    @Test
    @DisplayName("Deveria devolver código http 404, quando as informações estão inválidas")
    @WithMockUser
    void consultDetailByIdBeneficiaryCenario1() throws Exception {
        Long id = 2L;

        var repository = beneficiarioRepository;
        repository.getReferenceById(id);

        // simulando que o id não existe
        given(repository).willThrow(EntityNotFoundException.class);

        var response = mvc.perform(get("/beneficiaries/{id}", id))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @Test
    @DisplayName("Deveria devolver código http 200, quando as informações estiverem válidas")
    @WithMockUser
    void consultDetailByIdBeneficiaryCenario2() throws Exception {
        Long id = 1L;

        var repository = beneficiarioRepository;
        repository.getReferenceById(id);

        DadosAtualizacaoDocumento dadosAtualizacaoDocumento = new DadosAtualizacaoDocumento(
                null,
                TipoDocumento.CARTEIRA_IDENTIDADE,
                "43789563",
                LocalDate.parse("2019-09-18"),
                "Test description",
                LocalDate.parse("2024-04-16"),
                LocalDate.parse("2024-04-16"));

        DadosEndereco dadosEndereco = new DadosEndereco("Rua Casas Unidas", "Bairro", "12345-789", "Cidade", "SP", "Casa", "789");

        DadosAtualizacaoBeneficiario dadosAtualizacaoBeneficiario = new DadosAtualizacaoBeneficiario(
                null,
                "Nome",
                "112345678",
                dadosEndereco,
                Collections.singletonList(dadosAtualizacaoDocumento)
        );

        // considerando que sua classe Endereco tenha um construtor que receba um objeto DadosEndereco:
        var enderecoEsperado = new Endereco(dadosAtualizacaoBeneficiario.endereco());

        // considerando que sua classe DadosDetalhamentoDocumento tenha um construtor que receba uma lista DadosCadastroDocumento:
        List<DadosDetalhamentoDocumento> documentosEsperados = dadosAtualizacaoBeneficiario.documentos().stream().map(DadosDetalhamentoDocumento::new).toList();

        DadosDetalhamentoBeneficiario dadosDetalhamentoBeneficiario = new DadosDetalhamentoBeneficiario(
                null,
                dadosAtualizacaoBeneficiario.nome(),
                dadosAtualizacaoBeneficiario.telefone(),
                null,
                null,
                null,
                documentosEsperados,
                enderecoEsperado);

        when(beneficiarioRepository.getReferenceById(anyLong())).thenReturn(new Beneficiario(dadosDetalhamentoBeneficiario));

        var response = mvc
                .perform(
                        get("/beneficiaries/{id}", id)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(dadosDetalhamentoBeneficiarioJson.write(dadosDetalhamentoBeneficiario).getJson())
                )
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("Deveria devolver código http 404, quando as informações estão inválidas")
    @WithMockUser
    void updateBeneficiaryCenario1() throws Exception {
        Long id = 2L;

        var repository = beneficiarioRepository;
        repository.getReferenceById(id);

        DadosAtualizacaoDocumento dadosAtualizacaoDocumento = new DadosAtualizacaoDocumento(
                5L,
                TipoDocumento.CARTEIRA_IDENTIDADE,
                "43789563",
                LocalDate.parse("2019-09-18"),
                "Test description",
                LocalDate.parse("2024-04-16"),
                LocalDate.parse("2024-04-16"));

        DadosEndereco dadosEndereco = new DadosEndereco("Rua xpto", "Bairro", "12345-789", "Cidade", "SP", "Casa", "789");

        DadosAtualizacaoBeneficiario dadosAtualizacaoBeneficiario = new DadosAtualizacaoBeneficiario(
                2L,
                "Nome",
                "112345678",
                dadosEndereco,
                Collections.singletonList(dadosAtualizacaoDocumento)
        );

        // simulando que o id não existe
        given(repository).willThrow(EntityNotFoundException.class);

        var response = mvc
                .perform(
                        put("/beneficiaries", id)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(dadosAtualizacaoBeneficiarioJson.write(dadosAtualizacaoBeneficiario).getJson())
                )
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @Test
    @DisplayName("Deveria devolver código http 200, quando as informações estiverem válidas")
    @WithMockUser
    void updateBeneficiaryCenario2() throws Exception {
        DadosAtualizacaoDocumento dadosAtualizacaoDocumento = new DadosAtualizacaoDocumento(
                null,
                TipoDocumento.CARTEIRA_IDENTIDADE,
                "43789563",
                LocalDate.parse("2019-09-18"),
                "Test description",
                LocalDate.parse("2024-04-16"),
                LocalDate.parse("2024-04-17"));

        DadosEndereco dadosEndereco = new DadosEndereco("Rua Morada do Beneficiario", "Bairro", "12345-789", "Cidade", "SP", "Casa", "789");

        DadosAtualizacaoBeneficiario dadosAtualizacaoBeneficiario = new DadosAtualizacaoBeneficiario(
                null,
                "Nome do Beneficiario",
                "43789563",
                dadosEndereco,
                Collections.singletonList(dadosAtualizacaoDocumento)
        );

        // considerando que sua classe Endereco tenha um construtor que receba um objeto DadosEndereco:
        var enderecoEsperado = new Endereco(dadosAtualizacaoBeneficiario.endereco());

        // considerando que sua classe DadosDetalhamentoDocumento tenha um construtor que receba uma lista DadosCadastroDocumento:
        List<DadosDetalhamentoDocumento> documentosEsperados = dadosAtualizacaoBeneficiario.documentos().stream().map(DadosDetalhamentoDocumento::new).toList();

        DadosDetalhamentoBeneficiario dadosDetalhamentoBeneficiario = new DadosDetalhamentoBeneficiario(
                null,
                dadosAtualizacaoBeneficiario.nome(),
                dadosAtualizacaoBeneficiario.telefone(),
                null,
                null,
                null,
                documentosEsperados,
                enderecoEsperado);

        // simulando que o id existe
        when(beneficiarioRepository.getReferenceById(any())).thenReturn(new Beneficiario(dadosDetalhamentoBeneficiario));

        var response = mvc
                .perform(
                        put("/beneficiaries")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(dadosAtualizacaoBeneficiarioJson.write(dadosAtualizacaoBeneficiario).getJson())
                )
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

        String jsonEsperado = dadosDetalhamentoBeneficiarioJson.write(dadosDetalhamentoBeneficiario).getJson();

        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
    }

    @Test
    @DisplayName("Deveria devolver código http 404, quando as informações estão inválidas")
    @WithMockUser
    void deleteBeneficiaryCenario1() throws Exception {
        Long id = 2L;
        var repository = beneficiarioRepository;
        repository.deleteById(id);

        // simulando que o id não existe
        given(repository).willThrow(EntityNotFoundException.class);

        var response = mvc.perform(delete("/beneficiaries/{id}", id))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @Test
    @DisplayName("Deveria devolver código http 204, quando as informações estiverem válidas")
    @WithMockUser
    void deleteBeneficiaryCenario2() throws Exception {
        Long id = 1L;

        var response = mvc.perform(delete("/beneficiaries/{id}", id)).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @Test
    @DisplayName("Deveria devolver código http 404, quando as informações estão inválidas")
    @WithMockUser
    void deletelogicBeneficiaryCenario1() throws Exception {
        Long id = 2L;
        var beneficiario = beneficiarioRepository.getReferenceById(id);

        // simulando que o id não existe
        given(beneficiario).willThrow(EntityNotFoundException.class);

        var response = mvc.perform(delete("/beneficiaries/deletelogicId/{id}", id))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @Test
    @DisplayName("Deveria devolver código http 204, quando as informações estiverem válidas")
    @WithMockUser
    void deletelogicBeneficiaryCenario2() throws Exception {
        Long id = 1L;

        var repository = beneficiarioRepository.getReferenceById(id);

        // simular a ação de inativar o beneficiário no efeito de remover (cancelar).
        Beneficiario beneficiario = new Beneficiario();
        beneficiario.deleteOrInvalidateInformations();
        when(repository).thenReturn(beneficiario);

        var response = mvc.perform(delete("/beneficiaries/deletelogicId/{id}", id))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }
}
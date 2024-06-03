package com.plano.saude.cadastro.controller;

import com.plano.saude.cadastro.domain.perfil.Perfil;
import com.plano.saude.cadastro.domain.usuario.*;
import com.plano.saude.cadastro.infra.security.DadosToken;
import com.plano.saude.cadastro.infra.security.TokenService;
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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class LoginControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<DadosAutenticacao> dadosAutenticacaoJson;

    @Autowired
    private JacksonTester<DadosCadastroUsuario> dadosCadastroUsuarioJson;

    @Autowired
    private JacksonTester<DadosUsuario> dadosDadosUsuarioJson;

    @Autowired
    private JacksonTester<DadosToken> dadosTokenJson;

    @MockBean
    private UsuarioService usuarioService;

    @MockBean
    private TokenService tokenService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Test
    @DisplayName("Deveria devolver código http 401, quando informações estão inválidas")
    @WithMockUser
    void efetuateLoginCenario1() throws Exception {
        var dadosAutenticacao = new DadosAutenticacao("user.test@plano.saude", "12345");

        var response = mvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(dadosAutenticacaoJson.write(dadosAutenticacao).getJson()))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    @DisplayName("Deveria devolver código http 200, quando as informações estiverem válidas")
    @WithMockUser
    void efetuateLoginCenario2() throws Exception {
        var dadosAutenticacao = new DadosAutenticacao("user.test@plano.saude", "12345");

        DadosCadastroUsuario dadosCadastroUsuario = new DadosCadastroUsuario(
                "Nome Cadastro Usuario",
                "user.test@plano.com",
                "12345",
                true
        );

        List<Perfil> perfis = new ArrayList<>();

        var usuario = new Usuario(dadosCadastroUsuario, "12345", perfis);

        // Mock do TokenService para retornar um token fictício
        when(tokenService.generateToken(usuario)).thenReturn("token_ficticio");

        // Mock do AuthenticationManager para simular uma autenticação bem-sucedida
        Authentication authentication = mock(Authentication.class);

        when(authentication.isAuthenticated()).thenReturn(true);
        when(authenticationManager.authenticate(any(Authentication.class))).thenReturn(authentication);

        var response = mvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(dadosAutenticacaoJson.write(dadosAutenticacao).getJson()))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

        var jsonRetorno = dadosTokenJson.write(new DadosToken("token_ficticio")).getJson();

        assertThat(response.getContentAsString()).isEqualTo(jsonRetorno);
    }

    @Test
    @DisplayName("Deveria devolver código http 500, quando as informações estiverem inválidas")
    @WithMockUser
    void registerCenario1() throws Exception {
        var response = mvc.perform(post("/login/registers"))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

    @Test
    @DisplayName("Deveria devolver código http 200, quando as informações estiverem válidas")
    @WithMockUser
    void registerCenario2() throws Exception {
        var dadosUsuario = new DadosUsuario(1l, "Nome", "teste@test.com.br", true);

        when(usuarioService.cadastrar(any())).thenReturn(dadosUsuario);

        var response = mvc
                .perform(
                        post("/login/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(dadosCadastroUsuarioJson.write(
                                        new DadosCadastroUsuario("Nome", "teste@test.com.br", "12345", true)
                                ).getJson())
                )
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

        var jsonRetorno = dadosDadosUsuarioJson.write(dadosUsuario).getJson();

        assertThat(response.getContentAsString()).isEqualTo(jsonRetorno);
    }
}

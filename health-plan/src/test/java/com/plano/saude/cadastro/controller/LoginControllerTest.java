package com.plano.saude.cadastro.controller;

import com.plano.saude.cadastro.domain.usuario.DadosAutenticacao;
import com.plano.saude.cadastro.domain.usuario.DadosCadastroUsuario;
import com.plano.saude.cadastro.domain.usuario.DadosUsuario;
import com.plano.saude.cadastro.domain.usuario.Usuario;
import com.plano.saude.cadastro.domain.usuario.UsuarioService;
import com.plano.saude.cadastro.infra.security.DadosToken;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Base64;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class LoginControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private JacksonTester<DadosAutenticacao> dadosAutenticacaoJson;

    @Autowired
    private JacksonTester<DadosCadastroUsuario> dadosCadastroUsuarioJson;

    @Autowired
    private JacksonTester<DadosUsuario> dadosUsuarioJson;

    @Autowired
    private JacksonTester<DadosToken> dadosTokenJson;

    @MockBean
    private UsuarioService usuarioService;

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
        /* Este aqui substituirá o usuário que deveria ser recuperado do banco de dados.
         *
         * Se não criptografar a senha, não irá funcionar, pois o DaoAuthenticationProvider
         * (ele é chamado pelo AuthenticationManager) está esperando uma senha criptografada
         * para o objeto que é recuperado do banco de dados.
         */
        Usuario usuario = Usuario.builder()
                .id(1L)
                .nome("Senhor Usuario")
                .login("user.test@plano.saude")
                .password(encoder.encode("12345"))
                .build();

        // Será o requestBody
        var dadosAutenticacao = new DadosAutenticacao("user.test@plano.saude", "12345");

        /* Nesse projeto a classe UsuarioService implementa a interface UserDetailsService.
         *
         * O método `authenticate` do AuthenticationManager, por baixo dos panos faz
         * uma chamada ao `loadUserByUsername` a partir da interface UserDetailsService
         * para recuperar o usuário do banco de dados e comparar com os dados enviados
         * pelo cliente.
         *
         * Como esse é um teste de unidade, esse passo será mockado para ceder os
         * dados necessários para o resto da lógica de autenticação fluir.
         *
         */
        when(usuarioService.loadUserByUsername(any())).thenReturn(usuario);

        var response = mvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(dadosAutenticacaoJson.write(dadosAutenticacao).getJson()))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

        // Dessa forma, podemos utilizar mais uma assertion, para saber se o formato do token é válido:
        var tokens = dadosTokenJson
                .parseObject(response.getContentAsString())
                .token();
        List<String> tokenParts = List.of(tokens.split("\\."));
        assertThat(this.isFormatTokenValid(tokenParts)).isTrue();
    }

    /* Para validar o token, é utilizada a lógica abaixo: */
    /* O JWT é dividido em 3 partes, header, payload e signature, separados
     * pelo caractere de ponto. Por isso é necessário validar se foi possível
     * dividir ele em 3 partes.
     *
     * O JWT é codificado em Base64Url, por isso usamos o Base64 URL decoder
     * para decodificar as partes, onde, caso a decodificação falhe, é lançada
     * uma exceção que é capturada para retornar false, negando a validade do token.
     * */
    boolean isFormatTokenValid(List<String> tokenParts) {
        if (tokenParts.size() != 3)
            return false;

        try {
            tokenParts.forEach(part -> Base64.getUrlDecoder().decode(part));
            return true;

        } catch(IllegalArgumentException ex) {
            return false;
        }
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

        var jsonRetorno = dadosUsuarioJson.write(dadosUsuario).getJson();

        assertThat(response.getContentAsString()).isEqualTo(jsonRetorno);
    }
}

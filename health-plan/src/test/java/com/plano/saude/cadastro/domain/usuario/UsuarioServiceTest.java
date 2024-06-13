package com.plano.saude.cadastro.domain.usuario;

//import com.plano.saude.cadastro.domain.perfil.PerfilRepository;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//import java.util.Optional;
//
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.Mockito.when;
//
//@DisplayName("Testar classe UsuarioService")
//@SpringBootTest
//@AutoConfigureMockMvc
//class UsuarioServiceTest {
//
//    @MockBean
//    private UsuarioRepository usuarioRepository;
//
//    @MockBean
//    private PerfilRepository perfilRepository;
//
//    @MockBean
//    private PasswordEncoder passwordEncoder;
//
//    @Autowired
//    private UsuarioService usuarioService;
//
//    @Test
//    @DisplayName("Cadastrar um usuario")
//    public void cadastrarSucesso(){
//        DadosCadastroUsuario dadosCadastroUsuario = new DadosCadastroUsuario(
//                "Nome Usuario",
//                "e-mail.usuario@email.com",
//                "12345",
//                true
//        );
//
//        Optional<DadosUsuario> usuario = Optional.of(criarCadastro());
//        when(usuarioRepository.existsByLogin(anyString())).thenReturn(usuario);
//
//        usuarioService.cadastrar(dadosCadastroUsuario);
//    }
//
//    private DadosUsuario criarCadastro() {
//        DadosUsuario usuario = Mockito.mock(DadosUsuario.class);
//        return usuario;
//    }
//}
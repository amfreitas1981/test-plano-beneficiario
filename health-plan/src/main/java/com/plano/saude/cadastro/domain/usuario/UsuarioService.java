package com.plano.saude.cadastro.domain.usuario;

import com.plano.saude.cadastro.domain.ValidacaoException;
import com.plano.saude.cadastro.domain.perfil.Perfil;
import com.plano.saude.cadastro.domain.perfil.PerfilRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UsuarioService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PerfilRepository perfilRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        return this.usuarioRepository.findByLogin(login);
    }

    public DadosUsuario cadastrar(DadosCadastroUsuario dados) {
        var emailJaCadastrado = this.usuarioRepository.existsByLogin(dados.login());
        if (emailJaCadastrado) {
            throw new ValidacaoException("E-mail já cadastrado para outro usuário!");
        }

        var senhaBCrypt = passwordEncoder.encode(dados.password());
        var perfis = carregarPerfis(dados.admin());

        var usuario = new Usuario(dados, senhaBCrypt, perfis);

        this.usuarioRepository.save(usuario);
        return new DadosUsuario(usuario);
    }

    private List<Perfil> carregarPerfis(Boolean admin) {
        var perfis = new ArrayList<Perfil>();
        var perfilUser = perfilRepository.findByNome("ROLE_USER");
        perfis.add(perfilUser);

        if (admin != null && admin) {
            var perfilAdmin = perfilRepository.findByNome("ROLE_ADMIN");
            perfis.add(perfilAdmin);
        }

        return perfis;
    }
}

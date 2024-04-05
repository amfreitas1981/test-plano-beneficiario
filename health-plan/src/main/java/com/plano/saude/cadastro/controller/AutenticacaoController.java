package com.plano.saude.cadastro.controller;

import com.plano.saude.cadastro.domain.usuario.DadosAutenticacao;
import com.plano.saude.cadastro.domain.usuario.Usuario;
import com.plano.saude.cadastro.infra.security.DadosTokenJWT;
import com.plano.saude.cadastro.infra.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AutenticacaoController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity efetuateLogin(@RequestBody @Valid DadosAutenticacao dados){
        try {
            var authenticationToken = new UsernamePasswordAuthenticationToken(dados.login(), dados.password());
            var authentication = authenticationManager.authenticate(authenticationToken);

            var tokenJWT = tokenService.generateToken((Usuario) authentication.getPrincipal());

            return ResponseEntity.ok(new DadosTokenJWT(tokenJWT));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

package com.plano.saude.cadastro.controller;

import com.plano.saude.cadastro.domain.consulta.AgendaDeConsultas;
import com.plano.saude.cadastro.domain.consulta.DadosAgendamentoConsulta;
import com.plano.saude.cadastro.domain.consulta.DadosCancelamentoConsulta;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("consultas")
@SecurityRequirement(name = "bearer-key")
public class ConsultaController {

    @Autowired
    private AgendaDeConsultas agendaDeConsultas;

    @PostMapping
    @Transactional
    public ResponseEntity agendar(@RequestBody @Valid DadosAgendamentoConsulta dados){
        var dto = agendaDeConsultas.agendar(dados);

        return ResponseEntity.ok(dto);
    }

    @DeleteMapping
    @Transactional
    public ResponseEntity cancelar(@RequestBody @Valid DadosCancelamentoConsulta dados) {
        agendaDeConsultas.cancelar(dados);
        return ResponseEntity.noContent().build();
    }
}

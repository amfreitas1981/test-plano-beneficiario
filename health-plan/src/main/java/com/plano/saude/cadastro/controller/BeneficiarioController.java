package com.plano.saude.cadastro.controller;

import com.plano.saude.cadastro.domain.beneficiario.Beneficiario;
import com.plano.saude.cadastro.domain.beneficiario.BeneficiarioRepository;
import com.plano.saude.cadastro.domain.beneficiario.DadosAtualizacaoBeneficiario;
import com.plano.saude.cadastro.domain.beneficiario.DadosCadastroBeneficiario;
import com.plano.saude.cadastro.domain.beneficiario.DadosDetalhamentoBeneficiario;
import com.plano.saude.cadastro.domain.beneficiario.DadosListagemBeneficiario;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;


@RestController
@RequestMapping("beneficiaries")
@SecurityRequirement(name = "bearer-key")
public class BeneficiarioController {

    @Autowired
    private BeneficiarioRepository beneficiarioRepository;

    @PostMapping
    @Transactional
    public ResponseEntity createBeneficiary(@RequestBody @Valid DadosCadastroBeneficiario dados, UriComponentsBuilder uriBuilder){
        var beneficiario = new Beneficiario(dados);
        beneficiarioRepository.save(beneficiario);
        var uri = uriBuilder.path("/beneficiaries/{id}").buildAndExpand(beneficiario.getId()).toUri();

        return ResponseEntity.created(uri).body(new DadosDetalhamentoBeneficiario(beneficiario));
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemBeneficiario>> listBeneficiary(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao){
        var page = beneficiarioRepository.findAllByAtivoTrue(paginacao).map(DadosListagemBeneficiario::new);

        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity consultDetailByIdBeneficiary(@PathVariable Long id){
        var beneficiario = beneficiarioRepository.getReferenceById(id);

        return ResponseEntity.ok(new DadosDetalhamentoBeneficiario(beneficiario));
    }

    @PutMapping
    @Transactional
    public ResponseEntity updateBeneficiary(@RequestBody @Valid DadosAtualizacaoBeneficiario dados) {
        var beneficiario = beneficiarioRepository.getReferenceById(dados.id());
        beneficiario.atualizarInformacoes(dados);

        return ResponseEntity.ok(new DadosDetalhamentoBeneficiario(beneficiario));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity deleteBeneficiary(@PathVariable Long id){
        var beneficiario = beneficiarioRepository;
        beneficiario.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/deletelogicId/{id}")
    @Transactional
    public ResponseEntity deletelogicBeneficiary(@PathVariable Long id){
        var beneficiario = beneficiarioRepository.getReferenceById(id);
        beneficiario.deleteOrInvalidateInformations();

        return ResponseEntity.noContent().build();
    }
}

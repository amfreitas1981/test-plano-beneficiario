package com.plano.saude.cadastro.domain.beneficiario;

import com.plano.saude.cadastro.domain.documento.Documento;
import com.plano.saude.cadastro.domain.endereco.Endereco;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Table(name = "beneficiarios")
@Entity(name = "Beneficiario")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Beneficiario {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String telefone;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dataNascimento;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dataInclusao;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dataAtualizacao;

    @OneToMany(mappedBy = "beneficiario", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Documento> documentos;

    @Embedded
    private Endereco endereco;

    private Boolean ativo;

    public Beneficiario(DadosCadastroBeneficiario dados){
        this.nome = dados.nome();
        this.telefone = dados.telefone();
        this.dataNascimento = dados.dataNascimento();
        this.dataInclusao = dados.dataInclusao();
        this.dataAtualizacao = dados.dataAtualizacao();
        this.documentos = dados.documentos().stream().map(Documento::new).toList();
        this.documentos.forEach(d -> d.setBeneficiario(this));
        this.endereco = new Endereco(dados.endereco());
        this.ativo = true;
    }

    public Beneficiario(DadosDetalhamentoBeneficiario dados){
        this.nome = dados.nome();
        this.telefone = dados.telefone();
        this.dataNascimento = dados.dataNascimento();
        this.dataInclusao = dados.dataInclusao();
        this.dataAtualizacao = dados.dataAtualizacao();
        this.documentos = dados.documentos().stream().map(Documento::new).toList();
        this.documentos.forEach(d -> d.setBeneficiario(this));
        this.endereco = new Endereco(dados.endereco());
        this.ativo = true;
    }

    public void atualizarInformacoes(DadosAtualizacaoBeneficiario dados) {
        if (dados.nome() != null){
            this.nome = dados.nome();
        }
        if (dados.telefone() != null){
            this.telefone = dados.telefone();
        }
        if (dados.endereco() != null){
            this.endereco.atualizarInformacoes(dados.endereco());
        }
        if (dados.documentos() != null){
            this.documentos = dados.documentos().stream().map(Documento::new).toList();
            this.documentos.forEach(d -> d.setBeneficiario(this));
        }
    }

    public void deleteOrInvalidateInformations(){
        this.ativo = false;
    }
}

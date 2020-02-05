package com.desafio.pessoa.controller.v1.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description="Pessoa criada")
public class PessoaCriadaDTO {
	@ApiModelProperty("Identificador")
	private Long id;
	
	@ApiModelProperty("Nome")
	private String nome;
	
	@ApiModelProperty("Sexo")
	private String sexo;
	
	@ApiModelProperty("E-mail")
	private String email;
	
	@ApiModelProperty("Data de nascimento")
	private LocalDate dtNascimento;
	
	@ApiModelProperty("Naturalidade")
	private String naturalidade;
	
	@ApiModelProperty("Nacionalidade")
	private String nacionalidade;
	
	@ApiModelProperty("Data da criação")
	private LocalDateTime dataCriacao;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public LocalDate getDtNascimento() {
		return dtNascimento;
	}

	public void setDtNascimento(LocalDate dtNascimento) {
		this.dtNascimento = dtNascimento;
	}

	public String getNaturalidade() {
		return naturalidade;
	}

	public void setNaturalidade(String naturalidade) {
		this.naturalidade = naturalidade;
	}

	public String getNacionalidade() {
		return nacionalidade;
	}

	public void setNacionalidade(String nacionalidade) {
		this.nacionalidade = nacionalidade;
	}

	public LocalDateTime getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(LocalDateTime dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

}

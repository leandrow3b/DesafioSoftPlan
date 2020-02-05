package com.desafio.pessoa.controller.v1.dto;

import java.time.LocalDate;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.br.CPF;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description="Nova pessoa")
public class NovaPessoaDTO {
	@ApiModelProperty("Nome completo da pessoa")
	@NotBlank(message= "{name.not.blank}")
	private String nome;
	
	@ApiModelProperty("Genero da pessoa")
	private String sexo;
	
	@ApiModelProperty("E-mail")
	@Email
	private String email;

	@ApiModelProperty("Data de nascimento")
	private LocalDate dtNascimento;
	
	@ApiModelProperty("Naturalidade")
	private String naturalidade;
	
	@ApiModelProperty("Nacionalidade")
	private String nacionalidade;

	@ApiModelProperty("CPF")
	@NotBlank(message= "{cpf.not.blank}")
	@CPF
	private String cpf;

	public NovaPessoaDTO() {
	}
	
	public NovaPessoaDTO(@NotBlank(message = "{name.not.blank}") String nome, String sexo, @Email String email,
			LocalDate dtNascimento, String naturalidade, String nacionalidade,
			@NotBlank(message = "{cpf.not.blank}") @CPF String cpf) {
		super();
		this.nome = nome;
		this.sexo = sexo;
		this.email = email;
		this.dtNascimento = dtNascimento;
		this.naturalidade = naturalidade;
		this.nacionalidade = nacionalidade;
		this.cpf = cpf;
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

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
}

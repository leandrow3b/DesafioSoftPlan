package com.desafio.pessoa.controller.v1;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import com.desafio.pessoa.controller.v1.dto.NovaPessoaDTO;
import com.desafio.pessoa.entity.Pessoa;

@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
@TestMethodOrder(OrderAnnotation.class)
@WithMockUser(value = "user", password = "123")
class PessoaControllerTest {

	@Autowired
	private MockMvc mvc;

	@Test
	@Order(1)  
	void deveCriarUmaNovaPessoa() throws Exception {
		NovaPessoaDTO novaPessoaDTO = new NovaPessoaDTO("João Oliveira", "Masculino", "joao.oliveira@email.com",
				LocalDate.of(1988, 3, 8), "Amapaense", "Brasileira", "421.150.540-83");
		mvc.perform(post("/api/pessoa").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(novaPessoaDTO)))
				.andExpect(status().isCreated()).andExpect(jsonPath("$.id", is(1)));
	}

	@Test
	@Order(2)  
	void naoDeveCriarUmaNovaPessoaCpfRepetido() throws Exception {
		NovaPessoaDTO novaPessoaDTO = new NovaPessoaDTO("João Oliveira", "Masculino", "joao.oliveira@email.com",
				LocalDate.of(1988, 3, 8), "Amapaense", "Brasileira", "421.150.540-83");
		mvc.perform(post("/api/pessoa").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(novaPessoaDTO)))
				.andExpect(status().isNotAcceptable());
	}
	
	@Test
	@Order(3)  
	void naoDeveRepecurarTodas() throws Exception {
		mvc.perform(get("/api/pessoa").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(1)));
	}
	
	@Test
	@Order(4)  
	void naoDeveAtualizarPessoa() throws Exception {
		Pessoa pessoa = new Pessoa(1L, "João Silva de Oliveira", "Masculino", "joao.oliveira@email.com",
				LocalDate.of(1988, 3, 8), "Amapaense", "Brasileira", "421.150.540-83");
		mvc.perform(put("/api/pessoa/1").contentType(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(pessoa)))
				.andExpect(status().isOk()).andExpect(jsonPath("$.nome", not(equalTo("João Oliveira"))));
	}
	
	@Test
	@Order(5)  
	void naoDeveRecuperarPessoa() throws Exception {
		mvc.perform(get("/api/pessoa/1").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.nome", equalTo("João Silva de Oliveira")))
				.andExpect(jsonPath("$.sexo", equalTo("Masculino")))
				.andExpect(jsonPath("$.email", equalTo("joao.oliveira@email.com")))
				.andExpect(jsonPath("$.dtNascimento", equalTo("1988-03-08")))
				.andExpect(jsonPath("$.naturalidade", equalTo("Amapaense")))
				.andExpect(jsonPath("$.nacionalidade", equalTo("Brasileira")))
				.andExpect(jsonPath("$.cpf", equalTo("421.150.540-83")));
		
	}
	
	@Test
	@Order(6)  
	void naoDeveRemoverPessoa() throws Exception {
		mvc.perform(delete("/api/pessoa/1").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}
}

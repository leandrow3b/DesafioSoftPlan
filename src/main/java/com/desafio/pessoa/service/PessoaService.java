package com.desafio.pessoa.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.desafio.pessoa.entity.Pessoa;
import com.desafio.pessoa.repository.PessoaRepository;

@Service
public class PessoaService {

	@Autowired
	private PessoaRepository pessoaRepository;
	
	public Pessoa save(Pessoa pessoa) {
		return pessoaRepository.save(pessoa);
	}

	public Optional<Pessoa> findById(long id) {
		return pessoaRepository.findById(id);
	}

	public void delete(Pessoa pessoa) {
		pessoaRepository.delete(pessoa);
	}

	public List<Pessoa> findAll() {
		return pessoaRepository.findAll();
	}
	
	public Optional<Pessoa> findByCpf(String cpf) {
		return pessoaRepository.findByCpf(cpf);
	}

}

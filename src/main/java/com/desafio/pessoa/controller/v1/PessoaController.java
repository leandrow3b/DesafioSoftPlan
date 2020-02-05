package com.desafio.pessoa.controller.v1;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.desafio.pessoa.controller.v1.dto.NovaPessoaDTO;
import com.desafio.pessoa.controller.v1.dto.PessoaCriadaDTO;
import com.desafio.pessoa.entity.Pessoa;
import com.desafio.pessoa.service.PessoaService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import ma.glasnost.orika.MapperFacade;

@Api(value = "/pessoa")
@RestController
@RequestMapping("/api")
public class PessoaController {

	@Autowired
    private PessoaService pessoaService;
	
	@Autowired
	private MapperFacade mapperFacade;
	
	@ApiOperation(value = "Operação responsavel por criar uma pessoa")
	@RequestMapping(value = "/pessoa", method =  RequestMethod.POST)
    public ResponseEntity<Object> add(@Valid @RequestBody NovaPessoaDTO novaPessoaDTO) {
		Pessoa pessoa = mapperFacade.map(novaPessoaDTO, Pessoa.class);
		Optional<Pessoa> optionalPessoa = pessoaService.findByCpf(pessoa.getCpf());
		if(optionalPessoa.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
		}
        return new ResponseEntity<Object>(mapperFacade.map(
        		pessoaService.save(pessoa), PessoaCriadaDTO.class), HttpStatus.CREATED);
    }
	@ApiOperation(value = "Operação responsavel por remover uma pessoa")
	@RequestMapping(value = "/pessoa/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> remove(@PathVariable(value = "id") long id) {
      
		Optional<Pessoa> pessoa = pessoaService.findById(id);
      
        if(pessoa.isPresent()){
        	
        	pessoaService.delete(pessoa.get());
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
	
	@ApiOperation(value = "Operação responsavel por listar uma pessoa")
	@RequestMapping(value = "/pessoa", method = RequestMethod.GET)
	public List<Pessoa> list() {
		return pessoaService.findAll();
	}
	
	@RequestMapping(value = "/pessoa/{id}", method = RequestMethod.GET)
    public ResponseEntity<Object> get(@PathVariable(value = "id") long id) {
      
		Optional<Pessoa> pessoa = pessoaService.findById(id);
      
        if(pessoa.isPresent()){
            return new ResponseEntity<>(pessoa.get(), HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
	
	@ApiOperation(value = "Operação responsavel por atualizar uma pessoa")
	@RequestMapping(value = "/pessoa/{id}", method =  RequestMethod.PUT)
    public ResponseEntity<Pessoa> update(@PathVariable(value = "id") long id,
    		@Valid @RequestBody Pessoa newPessoa){
        Optional<Pessoa> oldPessoa = pessoaService.findById(id);

        if(oldPessoa.isPresent()){
        	
            Pessoa pessoa = oldPessoa.get();
            pessoa.setNome(newPessoa.getNome());
            pessoa.setSexo(newPessoa.getSexo());
            pessoa.setEmail(newPessoa.getEmail());
            pessoa.setDtNascimento(newPessoa.getDtNascimento());
            pessoa.setNaturalidade(newPessoa.getNaturalidade());
            pessoa.setNacionalidade(newPessoa.getNacionalidade());
            
            if(!pessoa.getCpf().equals(newPessoa.getCpf())) {
            	Optional<Pessoa> optionalPessoa = pessoaService.findByCpf(newPessoa.getCpf());
            	if(optionalPessoa.isPresent()) {
            		return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
            	}
            }
            pessoa.setCpf(newPessoa.getCpf());
            
            pessoaService.save(pessoa);
            return new ResponseEntity<Pessoa>(pessoa, HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}

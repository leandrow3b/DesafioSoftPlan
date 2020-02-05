package com.desafio.pessoa.controller.v1;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
public class UsuarioController {

	
	@GetMapping("/semRegistro")
    public String semRegistro() {
        return "Você precisa está logado para acessar o sistema.";
    }

    @GetMapping("/login")
    public RedirectView acessoRestrito() {
    	return new RedirectView("/");
    }
    
}

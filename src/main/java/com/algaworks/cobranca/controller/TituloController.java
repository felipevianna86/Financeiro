package com.algaworks.cobranca.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.algaworks.cobranca.model.StatusTitulo;
import com.algaworks.cobranca.model.Titulo;
import com.algaworks.cobranca.repository.Titulos;

@Controller
@RequestMapping("/titulos")
public class TituloController {
	
	@Autowired
	private Titulos titulos;
	
	@RequestMapping("/novo")
	public ModelAndView novo() {
		ModelAndView model = new ModelAndView("CadastroTitulo");
		return model;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView Salvar(Titulo titulo) {
		//TODO Salvar no banco de dados.
		titulos.save(titulo);
		ModelAndView model = new ModelAndView("CadastroTitulo");
		model.addObject("mensagem", "Operação realizada com sucesso!");
		return model;
	}
	
	/***
	 * Método que busca todos os status disponíveis, evitando replicação de código.
	 * Lista de Status
	 * @return {@link StatusTitulo}
	 */
	@ModelAttribute("statusTitulo")
	public List<StatusTitulo> statusTitulo(){
		return Arrays.asList(StatusTitulo.values());
	}
}

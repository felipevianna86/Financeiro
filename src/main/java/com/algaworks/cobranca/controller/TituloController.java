package com.algaworks.cobranca.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.algaworks.cobranca.model.StatusTitulo;
import com.algaworks.cobranca.model.Titulo;
import com.algaworks.cobranca.repository.Titulos;

@Controller
@RequestMapping("/titulos")
public class TituloController {
	//Injeção de dependência
	@Autowired
	private Titulos titulos;
	
	private static final String CADASTRO_VIEW = "CadastroTitulo";
	
	@RequestMapping("/novo")
	public ModelAndView novo() {
		ModelAndView model = new ModelAndView("CadastroTitulo");
		model.addObject(new Titulo());
		return model;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String Salvar(@Validated Titulo titulo, Errors errors, RedirectAttributes attributes) {		
		if(errors.hasErrors()) {
			return CADASTRO_VIEW;
		}
		
		titulos.save(titulo);
		
		attributes.addFlashAttribute("mensagem", "Operação realizada com sucesso!");		
		return "redirect:/titulos/novo";
	}
	
	/**
	 * Método criado pra editar um título. Utilizando benefícios do JPARepository
	 * @param titulo
	 * @return
	 */
	@RequestMapping("{codigo}")
	public ModelAndView editar(@PathVariable("codigo") Titulo titulo) {		
		ModelAndView model = new ModelAndView(CADASTRO_VIEW);
		model.addObject(titulo);
		
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
	
	/***
	 * Página default do sistema.
	 * Pesquisa de títulos
	 */
	@RequestMapping
	public ModelAndView pesquisar() {
		List<Titulo> todostitulos = titulos.findAll();
		ModelAndView model = new ModelAndView("PesquisaTitulos");
		model.addObject("titulos", todostitulos);
		return model;
	}
}

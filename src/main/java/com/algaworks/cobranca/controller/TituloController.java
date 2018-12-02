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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.algaworks.cobranca.model.StatusTitulo;
import com.algaworks.cobranca.model.Titulo;
import com.algaworks.cobranca.repository.filter.TituloFilter;
import com.algaworks.cobranca.service.CadastroTituloService;

/**
 * Controlador responsável por ações referentes à classe Título.
 * @author felipe
 *
 */

@Controller
@RequestMapping("/titulos")
public class TituloController {
		
	@Autowired
	private CadastroTituloService titulosService;
	
	private static final String CADASTRO_VIEW = "CadastroTitulo";
	
	/**
	 * Método que inicializa o cadastro de um título
	 * @return
	 */
	@RequestMapping("/novo")
	public ModelAndView novo() {
		ModelAndView model = new ModelAndView("CadastroTitulo");
		model.addObject(new Titulo());
		return model;
	}
	
	/**
	 * Método responsável por salvar um título.
	 * @param titulo
	 * @param errors
	 * @param attributes
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	public String Salvar(@Validated Titulo titulo, Errors errors, RedirectAttributes attributes) {		
		if(errors.hasErrors()) {
			return CADASTRO_VIEW;
		}
		
		try {			
		
			titulosService.salvar(titulo);
			
			attributes.addFlashAttribute("mensagem", "Operação realizada com sucesso!");		
			return "redirect:/titulos/novo";
		} catch (IllegalArgumentException e) {
			errors.reject("dataVencimento", null, e.getMessage());
			return CADASTRO_VIEW;
		}
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
	public ModelAndView pesquisar(@ModelAttribute("filtro") TituloFilter filtro) {
		
		List<Titulo> todostitulos = titulosService.filtrar(filtro);
		ModelAndView model = new ModelAndView("PesquisaTitulos");
		model.addObject("titulos", todostitulos);
		return model;
	}
	
	/**
	 * Método responsável por excluir um título.
	 * @param codigo
	 * @param attributes
	 * @return
	 */
	@RequestMapping(value="{codigo}", method= RequestMethod.DELETE) 
	public String excluir (@PathVariable Long codigo, RedirectAttributes attributes) {
		titulosService.excluir(codigo);
		
		attributes.addFlashAttribute("mensagem", "Título excluído com sucesso!");
		return "redirect:/titulos";
	}
	
	/**
	 * Método responsável por alterar o status de um título, de Pendente para Recebido.
	 * @param codigo
	 * @return
	 */
	@RequestMapping(value = "/{codigo}/receber", method = RequestMethod.PUT)
	public @ResponseBody String receber(@PathVariable Long codigo) {
		titulosService.receber(codigo);
		return titulosService.receber(codigo);
	}
}

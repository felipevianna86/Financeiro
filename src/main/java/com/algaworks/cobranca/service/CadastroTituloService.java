package com.algaworks.cobranca.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.algaworks.cobranca.model.StatusTitulo;
import com.algaworks.cobranca.model.Titulo;
import com.algaworks.cobranca.repository.Titulos;

/**
 * 
 * @author felipe
 * Criado em 21/11/2018
 * 
 * Classe de Serviço responsável por realizar transações a respeito de um Título.
 */

@Service
public class CadastroTituloService {
	
	@Autowired
	private Titulos titulos;
	
	/**
	 * Método responsável por salvar um título.
	 * @param titulo
	 */
	public void salvar(Titulo titulo) {
		
		try {
			titulos.save(titulo);
		} catch (DataIntegrityViolationException e) {
			throw new IllegalArgumentException("Formato de data inválido!");
		}
		
	}
	
	/**
	 * Método responsável por excluir um título.
	 * @param codigo
	 */
	public void excluir(Long codigo) {
		titulos.delete(codigo);
	}
	
	/**
	 * Método responsável por atualizar um título para o status Recebido.
	 * @param codigo
	 * @return
	 */
	public String receber(Long codigo) {
		Titulo titulo = titulos.findOne(codigo);
		titulo.setStatus(StatusTitulo.RECEBIDO);
		titulos.save(titulo);
		
		return StatusTitulo.RECEBIDO.getDescricao();
	}
	
}

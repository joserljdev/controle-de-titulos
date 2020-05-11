package br.com.joserljdev.controledetitulos.service;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.joserljdev.controledetitulos.controller.page.PageWrapper;
import br.com.joserljdev.controledetitulos.model.StatusTitulo;
import br.com.joserljdev.controledetitulos.model.Titulo;
import br.com.joserljdev.controledetitulos.repository.TituloRepository;

@Service
public class TituloService {
	
	@Autowired
	private TituloRepository tituloRepository;
	
	public void excluir(Long codigo) {
		tituloRepository.deleteById(codigo);
	}
	
	public String receber(Long codigo) {
		Optional<Titulo> titulo = tituloRepository.findById(codigo);
		titulo.get().setStatus(StatusTitulo.RECEBIDO);
		tituloRepository.save(titulo.get());
		
		return StatusTitulo.RECEBIDO.getDescricao();
	}
	
	public PageWrapper<Titulo> filtrar(Titulo titulo, Pageable pageable, HttpServletRequest httpServletRequest) {
		String descricao = titulo.getDescricao() == null ? "" : titulo.getDescricao();	
		int page = pageable.getPageNumber();
		int size  = pageable.getPageSize();

		return new PageWrapper<Titulo>(tituloRepository.findByDescricaoContaining(descricao, PageRequest.of(page, size)), httpServletRequest);
	}
}
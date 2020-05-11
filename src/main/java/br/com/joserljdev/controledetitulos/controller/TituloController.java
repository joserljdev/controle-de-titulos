package br.com.joserljdev.controledetitulos.controller;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.joserljdev.controledetitulos.controller.page.PageWrapper;
import br.com.joserljdev.controledetitulos.model.StatusTitulo;
import br.com.joserljdev.controledetitulos.model.Titulo;
import br.com.joserljdev.controledetitulos.repository.TituloRepository;
import br.com.joserljdev.controledetitulos.service.TituloService;

@Controller
@RequestMapping(path = "/titulos")
public class TituloController {

	private static final String CADASTRO_VIEW = "CadastroTitulo";

	@Autowired
	private TituloRepository tituloRepository;
	
	@Autowired
	private TituloService tituloService;

	@GetMapping(path = "/novo")
	public ModelAndView novo() {
		ModelAndView mv = new ModelAndView(CADASTRO_VIEW);
		mv.addObject(new Titulo());
		return mv;
	}

	@PostMapping
	public String salvar(@Validated Titulo titulo, Errors erros, RedirectAttributes attributes) {
		if (erros.hasErrors()) {
			return CADASTRO_VIEW;
		}

		tituloRepository.save(titulo);
		attributes.addFlashAttribute("mensagem", "Título salvo com sucesso!");
		return "redirect:/titulos/novo";
	}
	
	@GetMapping
	public ModelAndView pesquisar(@ModelAttribute("titulo") Titulo titulo, @PageableDefault(size = 5) Pageable pageable, 
			HttpServletRequest httpServletRequest) {

		ModelAndView mv = new ModelAndView("PesquisaTitulos");
		
		PageWrapper<Titulo> paginaWrapper = tituloService.filtrar(titulo, pageable,httpServletRequest);
		
		mv.addObject("pagina", paginaWrapper);                                                                                                           
		
		return mv;
	}

	@GetMapping("/{codigo}")
	public ModelAndView editar(@PathVariable("codigo") Titulo titulo) {
		ModelAndView mv = new ModelAndView(CADASTRO_VIEW);
		mv.addObject(titulo);
		return mv;
	}

	@DeleteMapping(value = "{codigo}")
	public String excluir(@PathVariable Long codigo, RedirectAttributes attributes) {
		tituloService.excluir(codigo);
		
		attributes.addFlashAttribute("mensagem", "Título excluído com sucesso!");
		return "redirect:/titulos";
	}

	@PutMapping("/{codigo}/receber")
	public @ResponseBody String receber(@PathVariable Long codigo) {
		return tituloService.receber(codigo);
	}
	
	@ModelAttribute("todosStatusTitulo")
	public List<StatusTitulo> todosStatusTitulo() {
		return Arrays.asList(StatusTitulo.values());
	}
}
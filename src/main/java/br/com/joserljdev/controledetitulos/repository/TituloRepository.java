package br.com.joserljdev.controledetitulos.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.joserljdev.controledetitulos.model.Titulo;

public interface TituloRepository extends JpaRepository<Titulo, Long> {
	Page<Titulo> findByDescricaoContaining(String descricao, Pageable pageable);
}
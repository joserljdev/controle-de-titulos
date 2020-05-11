package br.com.joserljdev.controledetitulos.config;

import java.util.Locale;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.FixedLocaleResolver;

import nz.net.ultraq.thymeleaf.LayoutDialect;

@Configuration
public class WebConfiguration {

	@Bean
	public LocaleResolver localeResolver() {
		return new FixedLocaleResolver(new Locale("pt", "BR"));
	}
	
	@Bean
	LayoutDialect thymeleafLayoutDialect() {
		return new LayoutDialect();
	}
}
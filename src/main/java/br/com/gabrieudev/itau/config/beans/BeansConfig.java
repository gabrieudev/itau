package br.com.gabrieudev.itau.config.beans;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.gabrieudev.itau.adapters.output.persistence.repositories.TransacaoRepositoryAdapter;
import br.com.gabrieudev.itau.adapters.output.persistence.repositories.jpa.JpaTransacaoRepository;
import br.com.gabrieudev.itau.application.ports.output.TransacaoOutputPort;
import br.com.gabrieudev.itau.application.services.TransacaoService;

@Configuration
public class BeansConfig {
    @Bean
    TransacaoService transacaoService(TransacaoOutputPort transacaoOutputPort) {
        return new TransacaoService(transacaoOutputPort);
    }

    @Bean
    TransacaoOutputPort transacaoOutputPort(JpaTransacaoRepository jpaTransacaoRepository) {
        return new TransacaoRepositoryAdapter(jpaTransacaoRepository);
    }
}

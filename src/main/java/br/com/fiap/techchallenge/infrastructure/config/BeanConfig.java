package br.com.fiap.techchallenge.infrastructure.config;

import br.com.fiap.techchallenge.infrastructure.persistence.jpa.adapter.ConsultaRepositoryJpaAdapter;
import br.com.fiap.techchallenge.infrastructure.persistence.jpa.mapper.ConsultaEntityMapper;
import br.com.fiap.techchallenge.infrastructure.persistence.jpa.repository.ConsultaJpaRepository;
import jakarta.persistence.EntityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    public ConsultaEntityMapper consultaEntityMapper(EntityManager manager) {
        return new ConsultaEntityMapper(manager);
    }

    @Bean
    public ConsultaRepositoryJpaAdapter consultaRepositoryJpaAdapter(
            ConsultaJpaRepository jpa,
            ConsultaEntityMapper mapper
    ) {
        return new ConsultaRepositoryJpaAdapter(jpa, mapper);
    }
}

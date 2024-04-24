package ru.musicapp.coreservice.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import ru.musicapp.coreservice.model.UserExtendedDetails;
import ru.musicapp.coreservice.security.SecurityContextFacade;

import java.util.Optional;
import java.util.UUID;

@Configuration
@EnableJpaAuditing
public class PersistenceConfiguration {

    @Bean
    public AuditorAware<UUID> auditorAware() {
        return () -> {
            UserExtendedDetails details = SecurityContextFacade.get();
            return Optional.ofNullable(details.getId());
        };
    }

}

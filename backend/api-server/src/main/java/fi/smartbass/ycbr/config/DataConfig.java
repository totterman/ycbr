package fi.smartbass.ycbr.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jdbc.repository.config.EnableJdbcAuditing;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@Configuration
@EnableJdbcRepositories(basePackages = {"fi.smartbass.ycbr.register", "fi.smartbass.ycbr.i9event"})
@EnableJdbcAuditing(auditorAwareRef = "auditorAware")
public class DataConfig {

    @Bean
    public AuditorAware<String> auditorAware() {
        return () -> Optional.of(getCurrentUsername());
    }

    private String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            return authentication.getName();
        }
        return "system";
    }
}

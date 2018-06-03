package ai.advance.cloud.config.server.environment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.config.server.environment.EnvironmentRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;

@ConditionalOnProperty(prefix = "spring.cloud.config.server.git", name = "parent-support-enabled", havingValue = "true")
@ConditionalOnMissingBean(EnvironmentRepository.class)
@Configuration
public class GitParentSupportConfiguration {


  @Autowired
  private ConfigurableEnvironment environment;

  @Value("${spring.cloud.config.server.git.timeout:10}")
  private Integer timeout;

  @Bean
  @ConditionalOnMissingBean(EnvironmentRepository.class)
  public EnvironmentRepository environmentRepository() {
    final GitParentSupportMultipleJGitEnvironmentRepository repository =
        new GitParentSupportMultipleJGitEnvironmentRepository(this.environment);

    if (this.timeout != null) {
      repository.setTimeout(this.timeout);
    }

    return repository;
  }
}

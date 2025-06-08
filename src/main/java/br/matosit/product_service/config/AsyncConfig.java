package br.matosit.product_service.config;

import java.util.concurrent.Executor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
public class AsyncConfig implements AsyncConfigurer {

  private final ApplicationProperties applicationProperties;

  public AsyncConfig(ApplicationProperties applicationProperties) {
    this.applicationProperties = applicationProperties;
  }

  @Override
  @Bean(name = "asyncExecutor")
  public Executor getAsyncExecutor() {
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    executor.setCorePoolSize(applicationProperties.getCorePoolSize());
    executor.setMaxPoolSize(applicationProperties.getMaxPoolSize());
    executor.setQueueCapacity(applicationProperties.getQueueCapacity());
    executor.setThreadNamePrefix(applicationProperties.getThreadNamePrefix());
    executor.initialize();
    return executor;
  }
}

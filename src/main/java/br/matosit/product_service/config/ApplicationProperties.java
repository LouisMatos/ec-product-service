package br.matosit.product_service.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationProperties {

  @Value("${app.async.core-pool-size}")
  private int corePoolSize;

  @Value("${app.async.max-pool-size}")
  private int maxPoolSize;

  @Value("${app.async.queue-capacity}")
  private int queueCapacity;

  @Value("${app.async.thread-name-prefix}")
  private String threadNamePrefix;

  @Value("${app.kafka.topic.product.name}")
  private String topic;

  // Getters and Setters
  public String getTopic() {
    return topic;
  }

  public void setTopic(String topic) {
    this.topic = topic;
  }

  public int getCorePoolSize() {
    return corePoolSize;
  }

  public void setCorePoolSize(int corePoolSize) {
    this.corePoolSize = corePoolSize;
  }

  public int getMaxPoolSize() {
    return maxPoolSize;
  }

  public void setMaxPoolSize(int maxPoolSize) {
    this.maxPoolSize = maxPoolSize;
  }

  public int getQueueCapacity() {
    return queueCapacity;
  }

  public void setQueueCapacity(int queueCapacity) {
    this.queueCapacity = queueCapacity;
  }

  public String getThreadNamePrefix() {
    return threadNamePrefix;
  }

  public void setThreadNamePrefix(String threadNamePrefix) {
    this.threadNamePrefix = threadNamePrefix;
  }

}

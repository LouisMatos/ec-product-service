package br.matosit.product_service.config;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import br.matosit.product_service.shared.filter.MdcInterceptor;

@Component
public class WebConfig implements WebMvcConfigurer {

  @Override
  public void addInterceptors(@NonNull InterceptorRegistry registry) {
    registry.addInterceptor(new MdcInterceptor());
  }
}

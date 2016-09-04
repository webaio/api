package io.weba.api.ui.rest.security.oauth2.resource;

import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
@ComponentScan({"io.weba.api.ui.rest.controller"})
public class ResourceServerWebConfig extends WebMvcConfigurerAdapter {
}

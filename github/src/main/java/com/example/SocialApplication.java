//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.example;

import com.example.ClientResources;
import com.example.MyOAuth2RestTemplate;
import com.example.MyUserInfoTokenServices;
import com.example.WorkaroundRestTemplateCustomizer;
import java.security.Principal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.servlet.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer.AuthorizedUrl;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.filter.CompositeFilter;

@SpringBootApplication
@RestController
@EnableOAuth2Client
@EnableAuthorizationServer
@Order(6)
public class SocialApplication extends WebSecurityConfigurerAdapter {
	@Autowired
	OAuth2ClientContext oauth2ClientContext;

	public SocialApplication() {
	}

	@RequestMapping({"/user", "/me"})
	public Map<String, String> user(Principal principal) {
		LinkedHashMap map = new LinkedHashMap();
		map.put("name", principal.getName());
		return map;
	}

	protected void configure(HttpSecurity http) throws Exception {
		((HttpSecurity)((HttpSecurity)((HttpSecurity)((HttpSecurity)((AuthorizedUrl)((AuthorizedUrl)http.antMatcher("/**").authorizeRequests().antMatchers(new String[]{"/", "/login**", "/webjars/**"})).permitAll().anyRequest()).authenticated().and()).exceptionHandling().authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/")).and()).logout().logoutSuccessUrl("/").permitAll().and()).csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()).and()).addFilterBefore(this.ssoFilter(), BasicAuthenticationFilter.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(SocialApplication.class, args);
	}

	@Bean
	public FilterRegistrationBean oauth2ClientFilterRegistration(OAuth2ClientContextFilter filter) {
		FilterRegistrationBean registration = new FilterRegistrationBean();
		registration.setFilter(filter);
		registration.setOrder(-100);
		return registration;
	}

	@Bean
	@ConfigurationProperties("github")
	public ClientResources github() {
		return new ClientResources();
	}

	@Bean
	@ConfigurationProperties("o2wifi")
	public ClientResources o2wifi() {
		return new ClientResources();
	}

	@Bean
	@ConfigurationProperties("facebook")
	public ClientResources facebook() {
		return new ClientResources();
	}

	@Bean
	@ConfigurationProperties("google")
	public ClientResources google() {
		return new ClientResources();
	}

	@Bean
	@ConfigurationProperties("sparklr")
	public ClientResources sparklr() {
		return new ClientResources();
	}

	@Bean
	public WorkaroundRestTemplateCustomizer workaroundRestTemplateCustomizer() {
		return new WorkaroundRestTemplateCustomizer();
	}

	private Filter ssoFilter() {
		CompositeFilter filter = new CompositeFilter();
		ArrayList filters = new ArrayList();
		filters.add(this.ssoFilter(this.facebook(), "/login/facebook"));
		filters.add(this.ssoFilter(this.github(), "/login/github"));
		filters.add(this.o2wifiFilter(this.o2wifi(), "/login/o2wifi"));
		filters.add(this.ssoFilter(this.google(), "/login/google"));
		filters.add(this.ssoFilter(this.sparklr(), "/login/sparklr"));
		filter.setFilters(filters);
		return filter;
	}

	private Filter ssoFilter(ClientResources client, String path) {
		OAuth2ClientAuthenticationProcessingFilter oAuth2ClientAuthenticationFilter = new OAuth2ClientAuthenticationProcessingFilter(path);
		OAuth2RestTemplate oAuth2RestTemplate = new OAuth2RestTemplate(client.getClient(), this.oauth2ClientContext);
		oAuth2ClientAuthenticationFilter.setRestTemplate(oAuth2RestTemplate);
		UserInfoTokenServices tokenServices = new UserInfoTokenServices(client.getResource().getUserInfoUri(), client.getClient().getClientId());
		tokenServices.setRestTemplate(oAuth2RestTemplate);
		oAuth2ClientAuthenticationFilter.setTokenServices(tokenServices);
		return oAuth2ClientAuthenticationFilter;
	}

	private Filter o2wifiFilter(ClientResources client, String path) {
		OAuth2ClientAuthenticationProcessingFilter oAuth2ClientAuthenticationFilter = new OAuth2ClientAuthenticationProcessingFilter(path);

		//////////////////////////////////////
		MyOAuth2RestTemplate oAuth2RestTemplate = new MyOAuth2RestTemplate(client.getClient(), this.oauth2ClientContext);
		////////////////////////
		oAuth2ClientAuthenticationFilter.setRestTemplate(oAuth2RestTemplate);
		///////////////////////
		MyUserInfoTokenServices tokenServices = new MyUserInfoTokenServices(client.getResource().getUserInfoUri(), client.getClient().getClientId());
		///////////////////////

		tokenServices.setRestTemplate(oAuth2RestTemplate);
		oAuth2ClientAuthenticationFilter.setTokenServices(tokenServices);
		return oAuth2ClientAuthenticationFilter;
	}

	@Configuration
	@EnableResourceServer
	protected static class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {
		protected ResourceServerConfiguration() {
		}

		public void configure(HttpSecurity http) throws Exception {
			((AuthorizedUrl)http.antMatcher("/me").authorizeRequests().anyRequest()).authenticated();
		}
	}
}

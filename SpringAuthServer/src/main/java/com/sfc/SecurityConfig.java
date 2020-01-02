package com.sfc;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableWebSecurity

//security.oauth2.resource.filter-order = 3
//@Order(3)


@EnableGlobalMethodSecurity(prePostEnabled = true)

//CONFIGURACION SPRING SECURITY
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Value("${security.signing-key}")
	private String signingKey;

	@Value("${security.encoding-strength}")
	private Integer encodingStrength;

	@Value("${security.security-realm}")
	private String securityRealm;

	//DB( del properties)
	@Autowired
	private DataSource datasource;

	//Tablas usuario/rol. Propia de Spring
	@Autowired	
	private UserDetailsService userDetailsService;


	// -------------------Hash de passWords 
	//Para poder usar Esta función! (AUTOWIRED necesita instancia)
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		return bCryptPasswordEncoder;
	}
	@Autowired
	private BCryptPasswordEncoder bcrypt;
	//-------------------Hash de passWords 



	//------------------ Mecanismo de AUTH
	@Bean
	@Override
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}
	@Autowired
	public void configure(AuthenticationManagerBuilder auth) throws Exception{
		auth.userDetailsService(userDetailsService).passwordEncoder(bcrypt);
	}
	//------------------ Mecanismo de AUTH



	//------------------ Conf HTTP
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http	
		//Req token. sesion NO en servidor
		.sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS)

		//Apodo Petición
		.and()
		.httpBasic()
		.realmName(securityRealm)

		//Desabilit tonken SPRING predef
		.and()
		.csrf()
		.disable();        
	}
	//------------------ Conf HTTP



	///-------------------------GESTION CREACION DE TOKENS!!!!!

	//Instancia del TOKEN
	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
		converter.setSigningKey(signingKey);		
		return converter;
	}

	//Instancia Dónde está token	
	@Bean
	public TokenStore tokenStore() {
		//instancia del de arriba
//		return new JwtTokenStore(accessTokenConverter());
		return new JdbcTokenStore(this.datasource);
	}


	//Intancia de gest TOKEN primero 
	@Bean
	@Primary
	public DefaultTokenServices tokenServices() {
		DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
		defaultTokenServices.setTokenStore(tokenStore());
		defaultTokenServices.setSupportRefreshToken(true);	
		defaultTokenServices.setReuseRefreshToken(false);	
		return defaultTokenServices;
	}
	
	///-------------------------GESTION CREACION DE TOKENS!!!!!


}

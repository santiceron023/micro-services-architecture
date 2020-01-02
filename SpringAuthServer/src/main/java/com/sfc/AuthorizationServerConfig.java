package com.sfc;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

@Configuration
//oauth
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter  {

	@Value("${security.jwt.client-id}")
	private String clientId;

	@Value("${security.jwt.client-secret}")
	private String clientSecret;

	@Value("${security.jwt.grant-type}")
	private String grantType;

	@Value("${security.jwt.scope-read}")
	private String scopeRead;

	@Value("${security.jwt.scope-write}")
	private String scopeWrite = "write";

	@Value("${security.jwt.resource-ids}")
	private String resourceIds;

	//INI-CAMBIO PARA SPRING BOOT 2
	//https://stackoverflow.com/questions/49197111/migration-to-spring-boot-2-security-encoded-password-does-not-look-like-bcrypt
	@Autowired
	@Lazy
	private BCryptPasswordEncoder bcrypt;
	//FIN-CAMBIO PARA SPRING BOOT 2


	//-----------beans del conf
	@Autowired
	private TokenStore tokenStore;
	@Autowired
	private JwtAccessTokenConverter accessTokenConverter;
	@Autowired
	private AuthenticationManager authenticationManager;	
	//-----------beans del conf


	//-----------ciclo de vida token
	@Override
	public void configure(ClientDetailsServiceConfigurer configurer) throws Exception {
		configurer.inMemory()
		//INI-CAMBIO PARA SPRING BOOT 2 secret(clientSecret) => secret(bcrypt.encode(clientSecret)		
		.withClient(clientId).secret(bcrypt.encode(clientSecret)).authorizedGrantTypes(grantType)
		//FIN-CAMBIO PARA SPRING BOOT 2
		
		.scopes(scopeRead, scopeWrite).resourceIds(resourceIds)
		//puede ser de DB
		.accessTokenValiditySeconds(600)
		//expira se debe solcitar otro
		.refreshTokenValiditySeconds(0);
	}	


	//----------- Estructura token, alcance
	//nombre usuario, expiración....id 
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		TokenEnhancerChain enhancerChain = new TokenEnhancerChain();

		enhancerChain.setTokenEnhancers(Arrays.asList(accessTokenConverter));
		endpoints.tokenStore(tokenStore).accessTokenConverter(accessTokenConverter)
		.tokenEnhancer(enhancerChain)
		.authenticationManager(authenticationManager);
	}

}

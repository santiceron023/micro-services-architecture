package com.sfc.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sfc.dao.IUsuarioDAO;
import com.sfc.model.Usuario;



//implementa interfaz de spring 
//apodo al servicio par que el autowired
@Service("userDetailsService")
public class UserServiceImpl{
//	implements UserDetailsService{
	@Autowired
	private IUsuarioDAO userDAO;

//	@Override
//	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		Usuario usuario = userDAO.findOneByUsername(username); //from usuario where username := username
//
//		if (usuario == null) {
//			throw new UsernameNotFoundException(String.format("Usuario no existe", username));
//		}
//		
//		//---------------------Lista de roles de clase Sprin por medio de NOMBRE
//		List<GrantedAuthority> authorities = new ArrayList<>();		
//		//SIEMORE POBLADA EAGEER
//		usuario.getRoles().forEach( rol -> {
//			authorities.add(new SimpleGrantedAuthority(rol.getNombre()));
//		});
//		//tipo user spring
//		UserDetails userDetails = new User(usuario.getUsername(),usuario.getPassword(),authorities);		
//		return userDetails;
//	}

}

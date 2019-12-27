package com.sfc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sfc.dao.IResetTokenDAO;
import com.sfc.model.ResetToken;
import com.sfc.service.IResetTokenService;

@Service
public class ResetTokenService implements IResetTokenService {

	@Autowired
	private IResetTokenDAO dao;
	
	@Override
	public void guardar(ResetToken token) {
		dao.save(token);	
	}

	@Override
	public void eliminar(ResetToken token) {
		dao.delete(token);		
	}

	@Override
	public ResetToken findByToken(String userName) {
		return dao.findByToken(userName);
	}

	
	

}

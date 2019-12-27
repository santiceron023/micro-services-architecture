package com.sfc.service;

import com.sfc.model.ResetToken;

public interface IResetTokenService {
	
	void guardar(ResetToken token);
	void eliminar(ResetToken token);
	ResetToken findByToken(String userName);

}

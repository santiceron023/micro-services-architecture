package com.sfc.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sfc.model.ResetToken;

public interface IResetTokenDAO extends JpaRepository<ResetToken, Long> {

	//like select from where : 
	ResetToken findByToken(String token);

}

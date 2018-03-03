package com.aniruddha.stock.respository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aniruddha.stock.model.Quote;

public interface QuotesRepository extends JpaRepository<Quote, Integer>{
	
	List<Quote> findByUserName(String username);
	

}

package com.TaskMange.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.TaskMange.Entity.TokenBlockList;

public interface TokenBlockListRepository extends JpaRepository<TokenBlockList,Long> {
	boolean existsByToken(String token);

}



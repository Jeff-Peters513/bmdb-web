package com.bmdb.db;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bmdb.business.Credit;

public interface CreditRepository extends JpaRepository<Credit, Integer> {

}

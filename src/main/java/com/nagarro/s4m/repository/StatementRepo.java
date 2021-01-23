/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nagarro.s4m.repository;

import com.nagarro.s4m.entity.Statement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author david
 */
@Repository
public interface StatementRepo extends JpaRepository<Statement, Integer>{
    
}

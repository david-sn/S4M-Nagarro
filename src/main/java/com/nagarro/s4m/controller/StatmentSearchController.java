/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nagarro.s4m.controller;

import com.nagarro.s4m.body.ReqBody;
import com.nagarro.s4m.entity.Statement;
import com.nagarro.s4m.service.impl.DoSearchService;
import java.security.Principal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author david
 */
@RestController
public class StatmentSearchController {

    @Autowired
    private DoSearchService doSearchService;

    @PostMapping("/")
    public List<Statement> test(@RequestBody ReqBody body, Principal p) {
        return doSearchService.doSearch(body.getAccountId(), body.getFromDate(), body.getToDate(), body.getFromAmount(), body.getToAmount(), p);
    }
}

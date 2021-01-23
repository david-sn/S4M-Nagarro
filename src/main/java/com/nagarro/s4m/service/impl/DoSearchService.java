/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nagarro.s4m.service.impl;

import com.nagarro.s4m.entity.Statement;
import com.nagarro.s4m.repository.StatementRepo;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

/**
 *
 * @author david
 */
@Service
public class DoSearchService {
//                  

    @Autowired
    private StatementRepo statementRepo;
    @Autowired
    private EntityManager em;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<Statement> doSearch(Integer accountNumber, String dateFrom, String dateTo, String amountFrom, String amountTo, Principal principal) {
        performUserValidate(principal, dateFrom, amountFrom);

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Statement> cq = cb.createQuery(Statement.class);

        Root<Statement> book = cq.from(Statement.class);

        Predicate accountNo = cq.getRestriction();
        if (accountNumber != null) {
            accountNo = cb.and(cb.equal(book.get("account_id"), accountNumber));
        }

        Predicate dateRangePredicate = cq.getRestriction();
        if (dateFrom != null && dateTo != null) {
            dateRangePredicate = cb.and(cb.between(book.get("datefield"), dateFrom, dateTo));
        } else {
            //adding 3 month range
            Calendar currentDate = Calendar.getInstance();
            currentDate.add(Calendar.MONTH, -3);
            String formattedFromDate = new SimpleDateFormat("dd-MM-yyyy").format(currentDate.getTime());
            String formattedToDate = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
            dateRangePredicate = cb.and(cb.between(book.get("datefield"), formattedFromDate, formattedToDate));
        }

        Predicate amountReang = cq.getRestriction();
        if (amountFrom != null && amountTo != null) {
            amountReang = cb.and(cb.between(book.get("amount"), amountFrom, amountTo));
        }
        cq.where(cb.and(dateRangePredicate, accountNo, amountReang));

        TypedQuery<Statement> query = em.createQuery(cq);
        List<Statement> resultList = query.getResultList();
        Stream<Statement> map = resultList.stream().map(statment -> {
            statment.setAccountHashed(hashingAccountNumber(statment.getAccount_id()));
            statment.setAccount_id(null);
            return statment;
        });

        return map.collect(Collectors.toList());

    }

    void performUserValidate(Principal principal, String dateFrom, String amountFrom) {
        OAuth2Authentication auth2Authentication = (OAuth2Authentication) principal;
        Optional<? extends GrantedAuthority> findAny = auth2Authentication.getUserAuthentication().getAuthorities()
                .stream()
                .filter(role -> role.getAuthority().equals("ROLE_USER"))
                .findAny();

        if (findAny.isPresent() && dateFrom != null && amountFrom != null) {
            throw new HttpClientErrorException(HttpStatus.FORBIDDEN, "not allowed to do this resouce");
        }
    }

    private String hashingAccountNumber(Integer plainText) {
        return passwordEncoder.encode(plainText.toString());
    }
}

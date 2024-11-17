package com.springmvc.ControlPresupuestario.service;

import com.springmvc.ControlPresupuestario.model.Transaction;
import com.springmvc.ControlPresupuestario.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;


@Service
public class TransactionService implements ITransactionService{

    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    IMyUserDetailsService userDetailsService;

    public List<Transaction> getTransactions() {

        return this.transactionRepository.findAll();
    }

    public Transaction getTransaction(long id){

        return this.transactionRepository.findById(id).get();

    }

    public Transaction saveTransaction(Transaction newTransaction) {

        long millis=System.currentTimeMillis();

        newTransaction.setDateCreation(new Date(millis));

//        newTransaction.setEmployee(userDetailsService.getUserDetailsService());
        //newTransaction.setEmployee(userDetailsService.getUserDetailsService());

        return this.transactionRepository.save(newTransaction);
    }

    public void updateTransaction(long id, Transaction newTransaction) {

        Transaction transactionUpdate = this.transactionRepository.findById(id).get();
        if (this.transactionRepository.findById(id).isPresent()) {

            long millis=System.currentTimeMillis();

            transactionUpdate.setDateUpdate(new Date(millis));

            if (newTransaction.getId()!= null) transactionUpdate.setId(newTransaction.getId());
            if (newTransaction.getConcept() != null) transactionUpdate.setConcept(newTransaction.getConcept());
            if (newTransaction.getCuantity() != 0) transactionUpdate.setCuantity(newTransaction.getCuantity());
            if (newTransaction.getEmployee() != null) transactionUpdate.setEmployee(newTransaction.getEmployee());

            this.transactionRepository.save(transactionUpdate);
        }
    }

    public void deleteTransaction(long id) {

        this.transactionRepository.deleteById(id);

    }
}

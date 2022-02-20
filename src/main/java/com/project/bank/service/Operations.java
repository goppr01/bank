package com.project.bank.service;

import com.project.bank.exception.CustomerAlreadyExists;
import com.project.bank.exception.NoCustomerException;
import com.project.bank.exception.InSufficientBalanceException;
import com.project.bank.model.BankCustomers;
import com.project.bank.model.CustomerAccount;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class Operations {

    private BankCustomers bankCustomers;

    public boolean withdraw(Long customerId, BigDecimal amountToWithdraw) throws InSufficientBalanceException, NoCustomerException {
        CustomerAccount customerAccount = getCustomerDetails(customerId);
        //Withdraw only if balance is greater or equal to the amount to withdraw
        if (customerAccount.getBalance().compareTo(amountToWithdraw) >= 0 ) {
            customerAccount.setBalance(customerAccount.getBalance().subtract(amountToWithdraw));
            return true;
        } else {
            throw new InSufficientBalanceException("No sufficient Balance for customer to withdraw");
        }
    }

    public CustomerAccount getCustomerDetails(Long customerId) {
        CustomerAccount customerAccount = bankCustomers.getCustomerAccounts().stream().filter(bankUser -> bankUser.getCustomerId().equals(customerId)).findFirst().orElse(null);
        if(customerAccount == null ) {
            throw new NoCustomerException("Customer does not exist");
        }
        return customerAccount;
    }

    public BigDecimal getCustomerBalance(Long customerId) {
        CustomerAccount customerAccount = bankCustomers.getCustomerAccounts().stream().filter(bankUser -> bankUser.getCustomerId().equals(customerId)).findFirst().orElse(null);
        if(customerAccount == null ) {
            throw new NoCustomerException("Customer does not exist");
        }
        return customerAccount.getBalance();
    }

    public boolean isCustomerExists(Long customerId) {
        return bankCustomers.getCustomerAccounts().stream().anyMatch(bankUser -> bankUser.getCustomerId().equals(customerId));
    }

    public BigDecimal getTotalBankBalance() {
        return bankCustomers.getCustomerAccounts().stream().map(CustomerAccount::getBalance).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void deposit(Long customerId, BigDecimal amount) throws NoCustomerException {
        CustomerAccount customerAccount = getCustomerDetails(customerId);
        if(customerAccount.getBalance() == null){
            customerAccount.setBalance(amount);
        } else {
            customerAccount.setBalance(customerAccount.getBalance().add(amount));
        }
    }

    public void addCustomer(Long customerId, String name) {
        if (isCustomerExists(customerId)) {
            throw new CustomerAlreadyExists("Customer already exists");
        }
        bankCustomers.getCustomerAccounts().add(CustomerAccount.builder().customerId(customerId).customerName(name).build());
    }
}

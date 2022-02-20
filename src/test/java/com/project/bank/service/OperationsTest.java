package com.project.bank.service;

import com.project.bank.exception.CustomerAlreadyExists;
import com.project.bank.exception.InSufficientBalanceException;
import com.project.bank.exception.NoCustomerException;
import com.project.bank.model.BankCustomers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class OperationsTest {

    private Operations operations;

    @BeforeEach
    public void setUp() {
        operations = new Operations(new BankCustomers());
    }

    @Test
    public void testWithdrawAndInsufficientBalance() {

        //Given: Bank has user Alice.
        operations.addCustomer(1L, "Alice");

        //when: Alice deposits $30
        operations.deposit(1L, new BigDecimal(30.00));

        //then Alice withdraws $20
        operations.withdraw(1L, new BigDecimal(20.00));

        //then Alice balance should be $10
        assertEquals(operations.getCustomerBalance(1L), new BigDecimal(10.00));

        //then Bank balance should be $10
        assertEquals(operations.getTotalBankBalance(), new BigDecimal(10.00));

        //And Alice will be prevented to withdraw $11
        assertThrows(InSufficientBalanceException.class, () -> operations.withdraw(1L, new BigDecimal(11.00)));
    }

    @Test
    public void testCustomerNotExists() {

        //Given: Bank has no users
        //when: Alice deposits $30
        //Then No Customer Exception is thrown
        assertThrows(NoCustomerException.class, () -> operations.deposit(1L, new BigDecimal(30.00)));
    }

    @Test
    public void testMultipleUsersDepositAndWithdraw() {

        //Given: Bank has users Alice, John
        operations.addCustomer(1L, "Alice");
        operations.addCustomer(2L, "John");

        //when: Alice deposits $30 and John deposits 100
        operations.deposit(1L, new BigDecimal(30.00));
        operations.deposit(2L, new BigDecimal(100.00));

        //then Alice withdraws $20
        operations.withdraw(1L, new BigDecimal(20.00));

        //then Alice balance should be $10 and John Balance should be 100
        assertEquals(operations.getCustomerBalance(1L), new BigDecimal(10.00));
        assertEquals(operations.getCustomerBalance(2L), new BigDecimal(100.00));

        //then Bank balance should be $110
        assertEquals(operations.getTotalBankBalance(), new BigDecimal(110.00));

        //And Alice will be prevented to withdraw $11
        assertThrows(InSufficientBalanceException.class, () -> operations.withdraw(1L, new BigDecimal(11.00)));

        //And John can withdraw 100
        operations.withdraw(2L, new BigDecimal(100.00));

        //And John balance should be 0
        assertEquals(operations.getCustomerBalance(2L), new BigDecimal(0.00));

        //then Bank balance should be $10
        assertEquals(operations.getTotalBankBalance(), new BigDecimal(10.00));

    }

    @Test
    public void testTotalBalanceWhenNoCustomers() {
        //Given No customers present in the bank
        //then the total balance should be 0
        assertEquals(operations.getTotalBankBalance(), new BigDecimal(0.00));
    }

    @Test
    public void testCreateAnotherCustomerWithSameId() {
        //Given: Bank has users Alice
        operations.addCustomer(1L, "Alice");

        //And won't be able to create another user with the sameId
        assertThrows(CustomerAlreadyExists.class, () -> operations.addCustomer(1L, "John"));
    }

}

package com.project.bank.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class BankCustomers {

    List<CustomerAccount> customerAccounts = new ArrayList<>();
}

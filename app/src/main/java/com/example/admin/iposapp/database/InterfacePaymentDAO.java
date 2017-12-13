package com.example.admin.iposapp.database;

import com.example.admin.iposapp.model.Payment;

import java.util.List;

/**
 * Created by sopor on 12/12/2017.
 */

public interface InterfacePaymentDAO {
    Payment fetchPaymentById(int id);
    List<Payment> fetchAllPayments();
    boolean addPayment(Payment payment);
    boolean addPayments(List<Payment> payments);
    boolean deletePayments();
    boolean isEmpty();
    boolean updatePayment();
}

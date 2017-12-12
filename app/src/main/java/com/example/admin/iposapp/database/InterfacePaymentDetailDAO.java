package com.example.admin.iposapp.database;

import com.example.admin.iposapp.model.Payment;
import com.example.admin.iposapp.model.PaymentDetail;

import java.util.List;

/**
 * Created by sopor on 12/12/2017.
 */

public interface InterfacePaymentDetailDAO {
    PaymentDetail fetchPaymentDetailById(int id);
    List<PaymentDetail> fetchAllPaymentDetails();
    boolean addPaymentDetail(PaymentDetail paymentDetail);
    boolean addPaymentDetails(List<PaymentDetail> paymentDetails);
    boolean deletePaymentDetails();
    boolean updatePaymentDetail();
}

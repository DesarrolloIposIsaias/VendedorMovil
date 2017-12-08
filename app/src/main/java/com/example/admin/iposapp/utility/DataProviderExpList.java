package com.example.admin.iposapp.utility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by admin on 07/09/2016.
 */
public class DataProviderExpList
{
    public static HashMap<String, List<String>> getInfo()
    {
        HashMap<String, List<String >> data = new HashMap<>();
        List<String> weekDays = Arrays.asList("Lunes", "Martes", "Miercoles", "Jueves", "Viernes");
        List<String> paymentMethods = Arrays.asList("Tarjeta", "Credito", "Cheque", "Transferencia");
        List<String> reviewDay = new ArrayList<>(weekDays);
        List<String> payDay = new ArrayList<>(weekDays);
        List<String> paymentMethod = new ArrayList<>(paymentMethods);

        data.put("Revision", reviewDay);
        data.put("Pago", payDay);
        data.put("Tipo de pago", paymentMethod);

        return data;
    }
}

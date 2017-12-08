package com.example.admin.iposapp.utility;


import android.support.annotation.NonNull;
import android.util.Log;

import com.example.admin.iposapp.model.Client;
import com.example.admin.iposapp.model.Product;
import com.example.admin.iposapp.utility.core.DbfMetadata;
import com.example.admin.iposapp.utility.core.DbfRecord;
import com.example.admin.iposapp.utility.reader.DbfReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

/**
 * Created by admin on 20/09/2016.
 */
public class ReadDbf
{
    public ReadDbf()
    {

    }


    public List<Product> getProductData(String fileName)
    {
        Map<String, Object> mapData;
        List<Product> data = new ArrayList<>();
        Charset stringCharset = Charset.forName("cp1252");

        FileInputStream dbf = null;
        try
        {
            dbf = new FileInputStream(new File(CurrentData.getInternalStoragePath() + "/" + fileName));
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

        DbfRecord rec;
        try
        {
            String field;
            BigDecimal decimalField;
            DbfReader reader = new DbfReader(dbf);
            DbfMetadata meta = reader.getMetadata();
            System.out.println("Read DBF Metadata: " + meta);
            while ((rec = reader.read()) != null)
            {
                Product product = new Product();
                rec.setStringCharset(stringCharset);
                System.out.println("Record #" + rec.getRecordNumber() + ": " + rec.toMap());

                mapData = rec.toMap();
                field = (String) mapData.get("PRODUCTO");
                product.setClave(field);
                field = (String) mapData.get("DESC1");
                product.setDescripcion1(field);
                field = (String) mapData.get("DESC2");
                product.setDescripcion2(field);
                field = (String) mapData.get("DESC3");
                product.setDescripcion3(field);
                decimalField = (BigDecimal) mapData.get("PRECIO1");
                product.setPrecio1(decimalField.floatValue());
                field = (String) mapData.get("CBARRAS");
                product.setcBarras(field);
                field = (String) mapData.get("CEMPAQUE");
                product.setcEmpaque(field);
                field = (String) mapData.get("CODIGO");
                product.setEan(field);
                decimalField = (BigDecimal) mapData.get("PZACAJA");
                product.setPzaCaja(decimalField.floatValue());
                decimalField = (BigDecimal) mapData.get("U_EMPAQUE");
                product.setuEmpaque(decimalField.floatValue());
                decimalField = (BigDecimal) mapData.get("EXIS1");
                product.setExistencia(decimalField.floatValue());

                //Log.w("PRODUCT ADDED", product.getDescripcion1());

                data.add(product);

            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        return data;
    }


    public List<Client> getClientData(String fileName)
    {
        Map<String, Object> mapData;
        List<Client> data = new ArrayList<>();
        Charset stringCharset = Charset.forName("cp1252");

        FileInputStream dbf = null;
        try
        {
            dbf = new FileInputStream(new File(CurrentData.getInternalStoragePath() + "/" + fileName));
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

        DbfRecord rec;
        try
        {
            String field;
            BigDecimal decimalField;
            DbfReader reader = new DbfReader(dbf);
            DbfMetadata meta = reader.getMetadata();
            System.out.println("Read DBF Metadata: " + meta);
            while ((rec = reader.read()) != null)
            {
                Client client = new Client();
                rec.setStringCharset(stringCharset);

                System.out.println("Record #" + rec.getRecordNumber() + ": " + rec.toMap());
                mapData = rec.toMap();
                field = (String) mapData.get("CLIENTE");
                client.setClave(field);
                field = (String) mapData.get("NOMBRE");
                client.setNombre(field);
                field = (String) mapData.get("NOMBRES");
                client.setNombres(field);
                client.setApellidos("NA");
                field = (String) mapData.get("CALLE");
                client.setDomicilio(field);
                field = (String) mapData.get("TELEFONO");
                client.setTelefono1(field);
                field = (String) mapData.get("TELEFONO2");
                client.setTelefono2(field);
                field = (String) mapData.get("RFC");
                client.setRfc(field);
                decimalField = (BigDecimal) mapData.get("SALDO");
                client.setSaldo(decimalField.floatValue());
                field = (String) mapData.get("CIUDAD");
                client.setCiudad(field);
                field = (String) mapData.get("VENDEDOR");
                client.setVendedorId(field);
                field = (String) mapData.get("CP");
                client.setCodigoPostal(field);
                client.setListaPrecioId("NA");
                decimalField = (BigDecimal) mapData.get("LIMITE");
                client.setLimiteCredito(decimalField.floatValue());
                decimalField = (BigDecimal) mapData.get("DIAS");
                client.setDias(decimalField.intValue());
                client.setCalles("NA");
                field = (String) mapData.get("CONTACTO");
                client.setContacto1(field);
                field = (String) mapData.get("CONTACTO2");
                client.setContacto2(field);
                field = (String) mapData.get("EMAIL");
                client.setEmail1(field);
                field = (String) mapData.get("EMAIL2");
                client.setEmail2(field);
                client.setSerie("");
                client.setPais("Mexico");
                field = (String) mapData.get("ESTADO");
                client.setEstado(field);
                field = (String) mapData.get("COLONIA");
                client.setColonia(field);
                client.setNumeroInterior("NA");
                client.setNumeroExterior("NA");
                field = (String) mapData.get("CTA_IEPS");
                client.setCuentaIeps(field);
                field = (String) mapData.get("SERVDOMI");
                client.setServicioDomicilio(field);
                field = (String) mapData.get("CRUZACON");
                client.setPlazo(field);
                client.setPrecio("NA");
                field = (String) mapData.get("PAGOS");
                client.setDiaPago(field);
                field = (String) mapData.get("REVISION");
                client.setRevision(field);
                client.setTarjeta("NA");
                client.setCredito("NA");
                client.setCheque("NA");
                client.setTransferencia("NA");
                field = (String) mapData.get("BLOQUEADO");
                client.setBloqueado(field);

                Log.w("CLIENT ADDED", client.getNombre());

                data.add(client);

            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            Log.w("Error", "M_CLIP problem");
        }

        return data;
    }


}

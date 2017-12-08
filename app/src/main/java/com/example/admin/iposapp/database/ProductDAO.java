package com.example.admin.iposapp.database;


        import android.content.ContentValues;
        import android.content.Context;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteConstraintException;
        import android.database.sqlite.SQLiteDatabase;
        import android.support.v4.util.LogWriter;
        import android.util.Log;

        import com.example.admin.iposapp.utility.CurrentData;
        import com.example.admin.iposapp.model.Product;

        import java.io.BufferedReader;
        import java.io.File;
        import java.io.FileInputStream;
        import java.io.FileNotFoundException;
        import java.io.InputStream;
        import java.io.InputStreamReader;
        import java.util.ArrayList;
        import java.util.List;
/**
 * Created by admin on 21/06/2016.
 */

public class ProductDAO extends DbContentProvider
        implements InterfaceProductSchema, InterfaceProductDAO {

    private Cursor cursor;
    private ContentValues initialValues;

    public ProductDAO(SQLiteDatabase db)
    {
        super(db);
    }

    private void setContentValue(Product product)
    {
        initialValues = new ContentValues();
        initialValues.put(columnId, product.getClave());
        initialValues.put(columnDescripcion1, product.getDescripcion1());
        initialValues.put(columnDescripcion2, product.getDescripcion2());
        initialValues.put(columnDescripcion3, product.getDescripcion3());
        initialValues.put(columnPrecio1, product.getPrecio1());
        initialValues.put(columnCBarras, product.getcBarras());
        initialValues.put(columnCEmpaque, product.getcEmpaque());
        initialValues.put(columnEan, product.getEan());
        initialValues.put(columnUnidad, product.getUnidad());
        initialValues.put(columnPzaCaja, product.getPzaCaja());
        initialValues.put(columnU_empaque, product.getuEmpaque());
        initialValues.put(columnExistencia, product.getExistencia());
    }

    private ContentValues getContentValue()
    {
        return initialValues;
    }

    protected Product cursorToEntity(Cursor cursor) {
        Product product = new Product();

        int idIndex;
        int descripcion1Index;
        int descripcion2Index;
        int descripcion3Index;
        int precio1Index;
        int cbarrasIndex;
        int cEmpaqueIndex;
        int eanIndex;
        int unidadIndex;
        int pzaCajaIndex;
        int u_EmpaqueIndex;
        int existenciaIndex;

        if(cursor != null)
        {
            if(cursor.getColumnIndex(columnId) != -1)
            {
                idIndex = cursor.getColumnIndexOrThrow(columnId);
                product.setClave(cursor.getString(idIndex));
            }
            if(cursor.getColumnIndex(columnDescripcion1) != -1)
            {
                descripcion1Index = cursor.getColumnIndexOrThrow(columnDescripcion1);
                product.setDescripcion1(cursor.getString(descripcion1Index));
            }
            if(cursor.getColumnIndex(columnDescripcion2) != -1)
            {
                descripcion2Index = cursor.getColumnIndexOrThrow(columnDescripcion2);
                product.setDescripcion2(cursor.getString(descripcion2Index));
            }

            if(cursor.getColumnIndex(columnDescripcion3) != -1)
            {
                descripcion3Index = cursor.getColumnIndexOrThrow(columnDescripcion3);
                product.setDescripcion3(cursor.getString(descripcion3Index));
            }
            if(cursor.getColumnIndex(columnPrecio1) != -1)
            {
                precio1Index = cursor.getColumnIndexOrThrow(columnPrecio1);
                product.setPrecio1(cursor.getFloat(precio1Index));
            }
            if(cursor.getColumnIndex(columnCBarras) != -1)
            {
                cbarrasIndex = cursor.getColumnIndexOrThrow(columnCBarras);
                product.setcBarras(cursor.getString(cbarrasIndex));
            }
            if(cursor.getColumnIndex(columnCEmpaque) != -1)
            {
                cEmpaqueIndex = cursor.getColumnIndexOrThrow(columnCEmpaque);
                product.setcEmpaque(cursor.getString(cEmpaqueIndex));
            }
            if(cursor.getColumnIndex(columnEan) != -1)
            {
                eanIndex = cursor.getColumnIndexOrThrow(columnEan);
                product.setEan(cursor.getString(eanIndex));
            }
            if(cursor.getColumnIndex(columnUnidad) != -1)
            {
                unidadIndex = cursor.getColumnIndexOrThrow(columnUnidad);
                product.setUnidad(cursor.getString(unidadIndex));
            }
            if(cursor.getColumnIndex(columnPzaCaja) != -1)
            {
                pzaCajaIndex = cursor.getColumnIndexOrThrow(columnPzaCaja);
                product.setPzaCaja(cursor.getFloat(pzaCajaIndex));
            }
            if(cursor.getColumnIndex(columnU_empaque) != -1)
            {
                u_EmpaqueIndex = cursor.getColumnIndexOrThrow(columnU_empaque);
                product.setuEmpaque(cursor.getFloat(u_EmpaqueIndex));
            }
            if(cursor.getColumnIndex(columnExistencia) != -1)
            {
                existenciaIndex = cursor.getColumnIndexOrThrow(columnExistencia);
                product.setExistencia(cursor.getFloat(existenciaIndex));
            }
        }
        return product;
    }

    public Product fetchProductById(String productId) {
        final String selectionArgs[] = {String.valueOf(productId)};
        final String selection = productId + "=?";
        Product product = new Product();
        //cursor = super.query(tableName, productColumns, selection, selectionArgs, columnId);
        cursor = super.rawQuery("SELECT * FROM PRODUCTO WHERE clave = '" + productId+ "'", null);

        Log.w("QUERY: ", "SELECT * FROM PRODUCTO WHERE clave = '" + productId + "'");

        if(cursor != null)
        {
            cursor.moveToFirst();
            while(!cursor.isAfterLast())
            {
                product = cursorToEntity(cursor);
                cursor.moveToNext();
            }
            cursor.close();
        }

        return product;
    }

    public List<Product> fetchAllProducts() {
        return null;
    }

    public boolean addProduct(Product product) {
        return false;
    }

    public boolean addProducts(List<Product> products)
    {
        for (int i = 0; i < products.size(); i++)
        {
            setContentValue(products.get(i));
            try
            {
                if(super.insert(tableName, getContentValue()) > 0)
                {
                    Log.w("Product: ", "Added " + products.get(i).getDescripcion1());
                }
                else Log.w("Product: ", "Problem adding client");
            }
            catch (SQLiteConstraintException ex)
            {
                Log.w("Database", ex.getMessage());
                return false;
            }
        }
        return true;
    }

    public boolean deleteAllProducts() {
        return false;
    }

    public List<Product> searchByFirstLetter(char search) {
        List<Product> productsList = new ArrayList<Product>();
        cursor = database.rawQuery("SELECT descripcion1, clave, descripcion2, existencia FROM PRODUCTO " +
                "WHERE clave LIKE '" + search +"%' or descripcion1 like '"+ search +
                "%' or descripcion2 like '"+ search+"%'", null);

        if(cursor != null)
        {
            cursor.moveToFirst();
            while(!cursor.isAfterLast())
            {
                Product product = cursorToEntity(cursor);
                productsList.add(product);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return productsList;
    }

    public List<Product> fetchProductsWith(String string)
    {
        List<Product> productList = new ArrayList<>();

        cursor = database.rawQuery("SELECT " +
                                                columnDescripcion1 + ", " +
                                                columnId + ", " +
                                                columnDescripcion2 +
                                                " FROM " + tableName +
                                                " WHERE " + columnDescripcion1 +
                                                " LIKE '%" + string +
                                                "%' OR " + columnDescripcion2 +
                                                " LIKE '%" + string + "%'", null);

        Log.w("SEARCH QUERY", "SELECT " +
                columnDescripcion1 + ", " +
                columnId + ", " +
                columnDescripcion2 +
                " FROM " + tableName +
                " WHERE " + columnDescripcion1 +
                " LIKE '%" + string +
                "%' OR " + columnDescripcion2 +
                " LIKE '%" + string + "%'");

        if(cursor != null)
        {
            cursor.moveToFirst();
            while (!cursor.isAfterLast())
            {
                Product product = cursorToEntity(cursor);
                productList.add(product);
                cursor.moveToNext();
            }
            cursor.close();
        }

        return productList;
    }

    public void updateProducts(Context ctx)
    {
        database.execSQL("delete from PRODUCTO where clave != -1");
        //InputStream is = ctx.getResources().openRawResource(R.raw.productos);
        File file = new File(CurrentData.getInternalStoragePath() + "/productos.sql");

        Log.d("UPDATE", "path: " + file.getAbsolutePath());

        InputStreamReader isReader = null;
        BufferedReader r = null;

        InputStream is = null;
        try
        {
            is = new FileInputStream(file);
            isReader = new InputStreamReader(is);
            r = new BufferedReader(isReader);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

        String line;
        try{
            if(r != null)
            {
                while((line = r.readLine()) != null)
                {
                    String header = line;
                    line = r.readLine();
                    String values = line;
                    String query = header + values;
                    database.execSQL(query);

                }
            }
        }
        catch(Exception ex)
        {
            int i = 0;
        }
    }
}

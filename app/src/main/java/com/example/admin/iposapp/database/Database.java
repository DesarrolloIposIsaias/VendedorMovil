package com.example.admin.iposapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.admin.iposapp.model.Payment;
import com.example.admin.iposapp.model.PaymentDetail;
import com.example.admin.iposapp.model.Product;
import com.example.admin.iposapp.model.SaleDetail;

/**
 * Created by admin on 21/06/2016.
 */
public class   Database
{

    private static final String tag = "iPosDB";
    private static final String dataBaseName = "iposDb";
    private DatabaseHelper dataBase;
    private static int version;
    private final Context ctx;
    public static ClientDAO clientDao;
    public static ProductDAO productDao;
    public static SaleDAO saleDAO;
    public static SaleDetailDAO saleDetailDAO;
    public static SettingsDAO settingsDAO;
    public static PaymentDAO paymentDAO;
    public static PaymentDetailDAO paymentDetailDAO;
    public static DataBaseVersionDAO dataBaseVersionDAO;
    public static BankDAO bankDAO;
    public static CrepDAO crepDAO;
    public static KitDAO kitDAO;
    public static StateDAO stateDAO;

    public Context getCtx() {
        return ctx;
    }

    public Database open() throws SQLException
    {

        try
        {
            dataBase = new DatabaseHelper(ctx);
            SQLiteDatabase mDb = dataBase.getWritableDatabase();
            version = mDb.getVersion();
            //dataBase.onUpgrade(mDb, 2, 2);
            dataBase.onCreate(mDb);

            clientDao = new ClientDAO(mDb);
            productDao = new ProductDAO(mDb);
            saleDAO = new SaleDAO(mDb);
            saleDetailDAO = new SaleDetailDAO(mDb);
            settingsDAO = new SettingsDAO(mDb);
            dataBaseVersionDAO = new DataBaseVersionDAO(mDb);
            paymentDAO = new PaymentDAO(mDb);
            paymentDetailDAO = new PaymentDetailDAO(mDb);
            bankDAO = new BankDAO(mDb);
            crepDAO = new CrepDAO(mDb);
            kitDAO = new KitDAO(mDb);
            stateDAO = new StateDAO(mDb);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return this;
    }

    public void close()
    {
        SQLiteDatabase db = dataBase.getWritableDatabase();
        dataBase.close();
    }

    public Database(Context context) {
        ctx = context;
    }

    public void upgrade()
    {
        SQLiteDatabase db = dataBase.getWritableDatabase();
        dataBase.onUpgrade(db, 2, 2);
    }


    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context)
        {
            super(context, dataBaseName, null, 2);
        }

        @Override
        public void onCreate(SQLiteDatabase db)
        {
            try
            {
                db.execSQL(InterfaceClientSchema.createQuery);
                db.execSQL(InterfaceProductSchema.createQuery);
                db.execSQL(InterfaceSaleDetailSchema.createQuery);
                db.execSQL(InterfaceSaleSchema.createQuery);
                db.execSQL(InterfaceSettingsSchema.createQuery);
                db.execSQL(InterfaceDataBaseVersionSchema.createQuery);
                db.execSQL(InterfacePaymentSchema.createQuery);
                db.execSQL(InterfacePaymentDetailSchema.createQuery);
                db.execSQL(InterfaceBankSchema.createQuery);
                db.execSQL(InterfaceCrepSchema.createQuery);
                db.execSQL(InterfaceKitSchema.createQuery);
                db.execSQL(InterfaceStateSchema.createQuery);

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion,
                              int newVersion)
        {
            Log.w(tag, "Upgrading database from version "
                    + oldVersion + " to "
                    + newVersion + " which destroys all old data");

            db.execSQL("DROP TABLE IF EXISTS "
                    + InterfaceClientSchema.tableName);
            db.execSQL("DROP TABLE IF EXISTS "
                    +  InterfaceProductSchema.tableName);
            db.execSQL("DROP TABLE IF EXISTS "
                    +  InterfaceSaleSchema.tableName);
            db.execSQL("DROP TABLE IF EXISTS "
                    +  InterfaceSaleDetailSchema.tableName);
            /*db.execSQL("DROP TABLE IF EXISTS "
                    +  InterfaceSettingsSchema.tableName);*/
            db.execSQL("DROP TABLE IF EXISTS "
                    +  InterfaceDataBaseVersionSchema.tableName);
            db.execSQL("DROP TABLE IF EXISTS "
                    + InterfacePaymentSchema.tableName);

            db.execSQL("DROP TABLE IF EXISTS "
                    + InterfacePaymentDetailSchema.tableName);

            db.execSQL("DROP TABLE IF EXISTS "
                    + InterfaceBankSchema.tableName);

            db.execSQL("DROP TABLE IF EXISTS "
                    + InterfaceCrepSchema.tableName);

            db.execSQL("DROP TABLE IF EXISTS "
                    + InterfaceKitSchema.tableName);

            db.execSQL("DROP TABLE IF EXISTS "
                    + InterfaceStateSchema.tableName);

            onCreate(db);
        }
    }

}


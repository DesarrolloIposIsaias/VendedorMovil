package com.example.admin.iposapp.controler;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.admin.iposapp.R;
import com.example.admin.iposapp.backgroundTask.AddSaleSoapTask;
import com.example.admin.iposapp.database.Database;
import com.example.admin.iposapp.model.Sale;
import com.example.admin.iposapp.utility.CurrentData;
import com.example.admin.iposapp.utility.FTPConnection;
import com.example.admin.iposapp.utility.WSMobileSalesHelper;

import java.io.File;
import java.util.List;

public class SyncFragment extends Fragment
{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private FTPConnection ftpClient;
    private Button download, upload;
    private ProgressBar progress;
    private AlertDialog alertDialog;
    Database database;

    public SyncFragment()
    {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SyncFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SyncFragment newInstance(String param1, String param2)
    {
        SyncFragment fragment = new SyncFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        final View view = inflater.inflate(R.layout.fragment_sync, container, false);
        download = (Button)view.findViewById(R.id.downloadBtn);
        upload = (Button)view.findViewById(R.id.uploadBtn);
        database = new Database(getContext());

        download.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                if(CurrentData.isSync())
                {
                    database.open();
                    List<Sale> sales = Database.saleDAO.fetchPendingSales();
                    database.close();
                    boolean pending = sales.size() > 0;


                    if (!pending)
                    {
                        new AlertDialog.Builder(getContext())
                                .setTitle("Se eliminaran las cotizaciones")
                                .setMessage("Desea continuar?")
                                .setCancelable(false)
                                .setPositiveButton("Si", new DialogInterface.OnClickListener()
                                {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which)
                                    {
                                        try
                                        {
                                            database.open();
                                            database.upgrade();
                                            database.close();
                                            //progress.setVisibility(View.VISIBLE);
                                            WSMobileSalesHelper wsHelper = new WSMobileSalesHelper(getContext());



                                            wsHelper.getBanks();
                                            wsHelper.getCrep();
                                            wsHelper.getStates();
                                            wsHelper.getProducts();
                                            wsHelper.getClients();
                                            wsHelper.getKits();


                                        }
                                        catch (Exception e)
                                        {
                                            e.printStackTrace();
                                            progress.setVisibility(View.GONE);
                                        }
                                    }

                                })
                                .setNegativeButton("No", null).show();
                    }
                    else
                    {
                        Toast.makeText(
                                getContext(),
                                "Hay ventas pendientes",
                                Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(
                            getContext(),
                            "Debe configurar los datos del servidor",
                            Toast.LENGTH_LONG).show();
                }

            }
        });

        upload.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                AddSaleSoapTask task = new AddSaleSoapTask(getContext());
                task.execute();
            }
        });

        return view;
    }

    void cleanFilesFolder(String path)
    {
        File dir = new File(path);
        if (dir.isDirectory())
        {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++)
            {
                File file = new File(dir, children[i]);
                boolean deleted = file.delete();
                if(deleted)
                    Log.w("File: ", "Deleted");
            }
        }
    }

}




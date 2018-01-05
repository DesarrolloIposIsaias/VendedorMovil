package com.example.admin.iposapp.controler;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.dd.processbutton.iml.ActionProcessButton;
import com.example.admin.iposapp.R;
import com.example.admin.iposapp.database.Database;
import com.example.admin.iposapp.model.Settings;
import com.example.admin.iposapp.utility.CurrentData;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FtpSettingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FtpSettingFragment extends Fragment
{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private ActionProcessButton btnSync;
    private EditText serverET,
            companyET,
            branchET,
            appUserET,
            appUserPassET,
            serieET;
    private TextView lastSaleTv, lastClientTv;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public static Database database;


    public FtpSettingFragment()
    {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FtpSettingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FtpSettingFragment newInstance(String param1, String param2)
    {
        FtpSettingFragment fragment = new FtpSettingFragment();
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
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_ftp_setting, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        database = new Database(getContext());

        btnSync = (ActionProcessButton) view.findViewById(R.id.button_save_config);
        serverET = (EditText)view.findViewById(R.id.edit_text_webservice);
        companyET = (EditText)view.findViewById(R.id.edit_text_company);
        branchET = (EditText)view.findViewById(R.id.edit_text_branch);

        appUserET = (EditText)view.findViewById(R.id.edit_text_app_user);
        appUserPassET = (EditText)view.findViewById(R.id.edit_text_app_user_pass);
        serieET = (EditText)view.findViewById(R.id.edit_text_seller_serie);
        lastSaleTv = (TextView)view.findViewById(R.id.text_view_last_sale);
        lastClientTv = (TextView)view.findViewById(R.id.text_view_last_client_added);

        btnSync.setMode(ActionProcessButton.Mode.PROGRESS);
        btnSync.setLoadingText("SINCRONIZANDO");
        btnSync.setCompleteText("SINCRONIZADO");

        if(CurrentData.isSync())
        {
            serverET.setText(CurrentData.getSettings().getServer());
            companyET.setText(CurrentData.getSettings().getCompany());
            branchET.setText(CurrentData.getSettings().getBranch());
            appUserET.setText(CurrentData.getSettings().getAppUser());
            appUserPassET.setText(CurrentData.getSettings().getAppUserPass());
            serieET.setText(CurrentData.getSettings().getSellerSerie());
            String lastClientStr = "Ultimo Cliente: " + CurrentData.getSettings().getClientSerie();
            lastClientTv.setText(lastClientStr);
            String lastSaleStr = "Ultima Venta: " + CurrentData.getSettings().getLastSale();
            lastSaleTv.setText(lastSaleStr);
        }

        btnSync.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                btnSync.setProgress(10);
                Settings settings = new Settings();
                btnSync.setProgress(20);
                settings.setServer(serverET.getText().toString());
                btnSync.setProgress(30);
                settings.setCompany(companyET.getText().toString());
                btnSync.setProgress(40);
                settings.setBranch(branchET.getText().toString());
                btnSync.setProgress(90);
                settings.setAppUser(appUserET.getText().toString());
                settings.setAppUserPass(appUserPassET.getText().toString());
                settings.setSellerSerie(serieET.getText().toString());

                CurrentData.setSettings(settings);

                try
                {
                    database.open();
                    int id = Database.settingsDAO.exist(
                            settings.getAppUser(),
                            settings.getAppUserPass());
                    if(id >= 0)
                    {
                        settings.setId(id);
                        Database.settingsDAO.update(settings);
                        Log.w("Config: ", "Updated");
                    }
                    else
                    {
                        Database.settingsDAO.addSettings(settings);
                        Log.w("Config: ", "New");
                    }
                    database.close();
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                }
                CurrentData.setIsSync(true);
                btnSync.setProgress(100);
            }
        });

        return view;
    }

}

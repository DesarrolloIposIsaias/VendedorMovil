package com.example.admin.iposapp.controler;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.admin.iposapp.R;
import com.example.admin.iposapp.backgroundTask.AddIndividualSaleSoapTask;
import com.example.admin.iposapp.database.Database;
import com.example.admin.iposapp.model.Sale;
import com.example.admin.iposapp.utility.CurrentData;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PaymentMethodFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PaymentMethodFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class  PaymentMethodFragment extends BaseFragment
{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Button fareButton, sendSaleBtn, finishSaleBtn;
    private EditText note1;
    private EditText note2;
    private RadioButton creditoRadioBtn;
    private RadioButton contadoRadioBtn;
    private RadioButton remisionRadioBtn;
    private RadioButton facturaRadioBtn;
    private Database database;

    private OnFragmentInteractionListener mListener;

    public PaymentMethodFragment()
    {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PaymentMethodFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PaymentMethodFragment newInstance(String param1, String param2)
    {
        PaymentMethodFragment fragment = new PaymentMethodFragment();
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
        View view = inflater.inflate(R.layout.fragment_payment_method, container, false);
        database = new Database(getContext());
        fareButton = (Button) view.findViewById(R.id.btnFinishSale);
        sendSaleBtn = (Button) view.findViewById(R.id.btnSendSale);
        finishSaleBtn = (Button) view.findViewById(R.id.btnEnd);
        note1 = (EditText) view.findViewById(R.id.note1ET);
        note2 = (EditText) view.findViewById(R.id.note2ET);
        contadoRadioBtn = (RadioButton) view.findViewById(R.id.contadoRadioBtn);
        creditoRadioBtn = (RadioButton) view.findViewById(R.id.creditoRadioBtn);
        facturaRadioBtn = (RadioButton) view.findViewById(R.id.facturaRadioBtn);
        remisionRadioBtn = (RadioButton) view.findViewById(R.id.remisionRadioBtn);
        Sale fetchedSale;

        contadoRadioBtn.setChecked(true);
        remisionRadioBtn.setChecked(true);

        database.open();
        fetchedSale = Database.saleDAO.fetchSaleById(CurrentData.getSaleId());
        database.close();

        if (CurrentData.isCallFromAllSales())
        {
            if(CurrentData.isOnlyForView())
            {
                contadoRadioBtn.setEnabled(false);
                creditoRadioBtn.setEnabled(false);
                facturaRadioBtn.setEnabled(false);
                remisionRadioBtn.setEnabled(false);
                note1.setFocusableInTouchMode(false);
                note2.setFocusableInTouchMode(false);
            }

            note1.setText(fetchedSale.getDescription1());
            note2.setText(fetchedSale.getDescription2());
            try
            {
                if (fetchedSale.getPaymentMethod().equals("Contado"))
                {
                    contadoRadioBtn.setChecked(true);
                } else
                {
                    creditoRadioBtn.setChecked(true);
                }
                if (fetchedSale.getStatus1().equals("R"))
                {
                    remisionRadioBtn.setChecked(true);
                } else
                {
                    facturaRadioBtn.setChecked(true);
                }

                CurrentData.setCallFromAllSales(false);
            }
            catch(Exception ex)
            {
                Log.d("ERROR", "onCreateView: " +  ex.toString());
            }
        }


        if(fetchedSale.getSend().equals("Y"))
        {
            fareButton.setVisibility(View.GONE);
            sendSaleBtn.setVisibility(View.GONE);
            finishSaleBtn.setVisibility(View.VISIBLE);

            finishSaleBtn.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    CurrentData.setTotal(0);
                    CurrentData.setSubtotal(0);
                    MenuSales newFragment = new MenuSales();
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragmentContainer, newFragment);
                    transaction.addToBackStack(null);

                    // Commit the transaction
                    transaction.commit();
                }
            });
        }
        else
        {
            fareButton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {

                    database.open();

                    Sale sale = Database.saleDAO.fetchSaleById(CurrentData.getSaleId());

                    sale.setDescription1(note1.getText().toString());
                    sale.setDescription2(note2.getText().toString());
                    sale.setTotal(CurrentData.getTotal());
                    if(contadoRadioBtn.isChecked())
                    {
                        sale.setPaymentMethod(contadoRadioBtn.getText().toString());
                    }
                    else
                    {
                        sale.setPaymentMethod(creditoRadioBtn.getText().toString());
                    }

                    if(remisionRadioBtn.isChecked())
                    {
                        sale.setStatus1("R");
                    }
                    else
                    {
                        sale.setStatus1("F");
                    }

                    sale.setSend("N");

                    if(Database.saleDAO.updateSale(CurrentData.getSaleId(), sale))
                    {
                        Toast.makeText(getContext(),"Venta finalizada",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(getContext(),
                                "Problema al registrar la venta",Toast.LENGTH_SHORT).show();
                    }
                    database.close();

                    CurrentData.setTotal(0);
                    CurrentData.setSubtotal(0);

                    MenuSales newFragment = new MenuSales();

                    FragmentTransaction transaction = getFragmentManager().beginTransaction();

                    // Replace whatever is in the fragment_container view with this fragment,
                    // and add the transaction to the back stack
                    transaction.replace(R.id.fragmentContainer, newFragment);
                    transaction.addToBackStack(null);

                    // Commit the transaction
                    transaction.commit();
                }
            });

            sendSaleBtn.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    database.open();

                    Sale sale = Database.saleDAO.fetchSaleById(CurrentData.getSaleId());

                    sale.setDescription1(note1.getText().toString());
                    sale.setDescription2(note2.getText().toString());
                    sale.setTotal(CurrentData.getTotal());
                    if(contadoRadioBtn.isChecked())
                    {
                        sale.setPaymentMethod(contadoRadioBtn.getText().toString());
                    }
                    else
                    {
                        sale.setPaymentMethod(creditoRadioBtn.getText().toString());
                    }

                    if(remisionRadioBtn.isChecked())
                    {
                        sale.setStatus1("R");
                    }
                    else
                    {
                        sale.setStatus1("F");
                    }

                    sale.setSend("N");

                    if(Database.saleDAO.updateSale(CurrentData.getSaleId(), sale))
                    {
                        Toast.makeText(getContext(),"Venta finalizada",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(getContext(),
                                "Problema al registrar la venta",Toast.LENGTH_SHORT).show();
                    }
                    database.close();

                    CurrentData.setTotal(0);
                    CurrentData.setSubtotal(0);

                    AddIndividualSaleSoapTask task = new AddIndividualSaleSoapTask(getContext());
                    task.execute();

                    MenuSales newFragment = new MenuSales();

                    FragmentTransaction transaction = getFragmentManager().beginTransaction();

                    transaction.replace(R.id.fragmentContainer, newFragment);
                    transaction.addToBackStack(null);

                    transaction.commit();
                }
            });
        }

        getActivity().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        return view;
    }

    public void onButtonPressed(Uri uri)
    {
        if (mListener != null)
        {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener)
        {
            mListener = (OnFragmentInteractionListener) context;
        } else
        {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener
    {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onBackPressed(){
        //getActivity().getSupportFragmentManager().popBackStackImmediate();
        super.onBackPressed();

    }
}

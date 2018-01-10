package com.example.admin.iposapp.controler;

import android.content.Context;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ScrollView;

import com.example.admin.iposapp.R;
import com.example.admin.iposapp.database.Database;
import com.example.admin.iposapp.model.Client;
import com.example.admin.iposapp.utility.CurrentData;
import com.hanks.htextview.HTextView;

import de.halfbit.fabuless.FabView;


public class TabContactInformationFragment extends Fragment
{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ScrollView sV;
    private FabView fabSaveChanges;
    //modify's string values
    //animation for floating action button
    //boleans for modify event
    private boolean enableFab;
    private boolean secondContactIsDrawed, firstContactIsDrawed,secondPhoneIsDrawed,
                    firstPhoneIsDrawed, secondMailIsDrawed, firstMailIsDrawed, RFCIsDrawed,
                    codePostalIsDrawed, countryIsDrawed, stateIsDrawed, cityIsDrawed,colonyIsDrawed,
                    adressIsDrawed, nameIsDrawed;
    private EditText etSecondContact, etFirstContact,etSecondPhone,etFirstPhone,
            etSecondMail, etFirstMail, etRFC, etCodePostal,etCountry, etState, etCity,
            etColony, etAdress,etName;
    private HTextView clientNameTView,clientAdressTView,
            clientColonyTView, clientCityTView, clientStateTView,
            clientCountryTView, clientCodePostalTView, clientRFCTView,
            clientFirstMailTView, clientSecondMailTView, clientFirstPhoneTView,
            clientSecondPhoneTView, clientFirstContact, clientSecondContact;

    public TabContactInformationFragment()
    {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TabContactInformationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TabContactInformationFragment newInstance(String param1, String param2)
    {
        TabContactInformationFragment fragment = new TabContactInformationFragment();
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
        final View view = inflater.inflate(R.layout.fragment_tab_contact_information, container, false);
        //animations for fab initialization
        final Animation show = AnimationUtils.loadAnimation(getContext(), R.anim.show);
        final Animation hide = AnimationUtils.loadAnimation(getContext(), R.anim.hide);
        //modify's boolean initialization
        enableFab = false;
        //fab binding
        fabSaveChanges = (FabView) view.findViewById(R.id.fab);
        //boolean initilization
        nameIsDrawed = false;
        adressIsDrawed = false;
        colonyIsDrawed = false;
        cityIsDrawed = false;
        stateIsDrawed = false;
        countryIsDrawed = false;
        codePostalIsDrawed = false;
        RFCIsDrawed = false;
        firstMailIsDrawed = false;
        secondMailIsDrawed = false;
        firstPhoneIsDrawed = false;
        secondPhoneIsDrawed = false;
        secondContactIsDrawed = false;
        firstContactIsDrawed = false;
        //edit text binding
        etName = (EditText) view.findViewById(R.id.clientNameEt);
        etAdress = (EditText) view.findViewById(R.id.clientAdressEt);
        etColony = (EditText) view.findViewById(R.id.clientColonyEt);
        etCity = (EditText) view.findViewById(R.id.clientCityEt);
        etState = (EditText) view.findViewById(R.id.clientStateEt);
        etCountry = (EditText) view.findViewById(R.id.clientCountryEt);
        etCodePostal = (EditText) view.findViewById(R.id.clientPostalCodeEt);
        etRFC = (EditText) view.findViewById(R.id.clientRfcEt);
        etFirstMail = (EditText) view.findViewById(R.id.clientMail1Et);
        etSecondMail = (EditText) view.findViewById(R.id.clientMail2Et);
        etFirstPhone =(EditText) view.findViewById(R.id.clientPhone1Et);
        etSecondPhone = (EditText) view.findViewById(R.id.clientPhone2Et);
        etFirstContact = (EditText) view.findViewById(R.id.clientContact1Et);
        etSecondContact = (EditText) view.findViewById(R.id.clientContact2Et);
        //scrollview binding
        sV = (ScrollView) view.findViewById(R.id.scrollView);
        //Linkin code with xml controls
        clientNameTView = (HTextView) view.findViewById(R.id.tvClientName);
        clientAdressTView = (HTextView) view.findViewById(R.id.tvClientAdress);
        clientColonyTView = (HTextView) view.findViewById(R.id.tvClientColony);
        clientCityTView = (HTextView) view.findViewById(R.id.tvCity);
        clientStateTView = (HTextView) view.findViewById(R.id.tvState);
        clientCountryTView = (HTextView) view.findViewById(R.id.tvCountry);
        clientCodePostalTView = (HTextView) view.findViewById(R.id.tvCodePostal);
        clientRFCTView = (HTextView) view.findViewById(R.id.tvRFC);
        clientFirstMailTView = (HTextView) view.findViewById(R.id.tvFirstMail);
        clientSecondMailTView = (HTextView) view.findViewById(R.id.tvSecondMail);
        clientFirstPhoneTView = (HTextView) view.findViewById(R.id.tvFirstPhone);
        clientSecondPhoneTView = (HTextView) view.findViewById(R.id.tvSecondPhone);
        clientFirstContact = (HTextView) view.findViewById(R.id.tvFirstContact);
        clientSecondContact = (HTextView) view.findViewById(R.id.tvSecondContact);

        fabSaveChanges.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v)
            {
                Client client = new  Client();
                client.setTelefono1(etFirstPhone.getText().toString());
                client.setTelefono2(etSecondPhone.getText().toString());
                client.setContacto1(etFirstContact.getText().toString());
                client.setContacto2(etSecondContact.getText().toString());
                client.setEmail1(etFirstMail.getText().toString());
                client.setEmail2(etSecondMail.getText().toString());
                client.setClave(CurrentData.getClientId());
                Database database = new Database(getContext());
                database.open();
                Database.clientDao.updateClient(client);
                database.close();
                enableFab = false;
                fabSaveChanges.startAnimation(hide);

            }
        });

        sV.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {

            @Override
            public void onScrollChanged() {
                Rect scrollBounds = new Rect();
                //client name animation
                if (clientNameTView.getLocalVisibleRect(scrollBounds)) {
                    if(!nameIsDrawed){
                        clientNameTView.animateText("NOMBRE DEL CLIENTE");
                        nameIsDrawed = true;
                        // Any portion of the imageView, even a single pixel, is within the visible window
                    }

                }
                else
                {

                    nameIsDrawed = false;
                }
                //client adress animation
                if (clientAdressTView.getLocalVisibleRect(scrollBounds)) {
                    if(!adressIsDrawed){
                        clientAdressTView.animateText("DIRECCION");
                        adressIsDrawed = true;
                        // Any portion of the imageView, even a single pixel, is within the visible window
                    }

                }
                else
                {
                    adressIsDrawed = false;
                }
                //client neighbor animation
                if (clientColonyTView.getLocalVisibleRect(scrollBounds)) {
                    if(colonyIsDrawed == false){
                        clientColonyTView.animateText("COLONIA");
                        colonyIsDrawed = true;
                        // Any portion of the imageView, even a single pixel, is within the visible window
                    }

                }
                else
                {
                    colonyIsDrawed = false;
                }
                //client city animation
                if ( clientCityTView.getLocalVisibleRect(scrollBounds)) {
                    if(cityIsDrawed == false){
                        clientCityTView.animateText("CIUDAD");
                        cityIsDrawed = true;
                        // Any portion of the imageView, even a single pixel, is within the visible window
                    }

                }
                else
                {

                    cityIsDrawed = false;
                }
                //client state animation
                if (clientStateTView.getLocalVisibleRect(scrollBounds)) {
                    if(stateIsDrawed == false){
                        clientStateTView.animateText("ESTADO");
                        stateIsDrawed = true;
                        // Any portion of the imageView, even a single pixel, is within the visible window
                    }

                }
                else
                {

                    stateIsDrawed = false;
                }
                //client country animation
                if (clientCountryTView.getLocalVisibleRect(scrollBounds)) {
                    if(countryIsDrawed == false){
                        clientCountryTView.animateText("PAIS");
                        countryIsDrawed = true;
                        // Any portion of the imageView, even a single pixel, is within the visible window
                    }

                }
                else
                {
                    countryIsDrawed = false;
                }
                //client's postal code animation
                if ( clientCodePostalTView.getLocalVisibleRect(scrollBounds)) {
                    if(codePostalIsDrawed == false){
                        clientCodePostalTView.animateText("CODIGO POSTAL");
                        codePostalIsDrawed = true;
                        // Any portion of the imageView, even a single pixel, is within the visible window
                    }

                }
                else
                {
                    codePostalIsDrawed = false;
                }
                //client's rfc animation
                if ( clientRFCTView.getLocalVisibleRect(scrollBounds)) {
                    if( RFCIsDrawed == false){
                        clientRFCTView.animateText("RFC");
                        RFCIsDrawed = true;
                        // Any portion of the imageView, even a single pixel, is within the visible window
                    }

                }
                else
                {
                    RFCIsDrawed = false;
                }
                //first client mail
                if (clientFirstMailTView.getLocalVisibleRect(scrollBounds)) {
                    if( firstMailIsDrawed == false){
                        clientFirstMailTView.animateText("CORREO PRINCIPAL");
                        firstMailIsDrawed = true;
                        // Any portion of the imageView, even a single pixel, is within the visible window
                    }

                }
                else
                {

                    firstMailIsDrawed = false;
                }
                //second client mail
                if (clientSecondMailTView.getLocalVisibleRect(scrollBounds)) {
                    if( secondMailIsDrawed == false){
                        clientSecondMailTView.animateText("CORREO ALTERNO");
                        secondMailIsDrawed = true;
                        // Any portion of the imageView, even a single pixel, is within the visible window
                    }

                }
                else
                {
                    secondMailIsDrawed = false;
                }
                // first phone animation
                if (clientFirstPhoneTView.getLocalVisibleRect(scrollBounds)) {
                    if(firstPhoneIsDrawed == false){
                        clientFirstPhoneTView.animateText("TELEFONO PRINCIPAL");
                        firstPhoneIsDrawed = true;
                        // Any portion of the imageView, even a single pixel, is within the visible window
                    }

                }
                else
                {
                    firstPhoneIsDrawed = false;
                }
                //second phone animation
                if ( clientSecondPhoneTView.getLocalVisibleRect(scrollBounds)) {
                    if(secondPhoneIsDrawed == false){
                        clientSecondPhoneTView.animateText("TELEFONO ALTERNO");
                        secondPhoneIsDrawed = true;
                        // Any portion of the imageView, even a single pixel, is within the visible window
                    }

                }
                else
                {
                    secondPhoneIsDrawed = false;
                }
                //first contact animation
                if ( clientFirstContact.getLocalVisibleRect(scrollBounds)) {
                    if(firstContactIsDrawed == false){
                        clientFirstContact.animateText("CONTACTO PRINCIPAL");
                        firstContactIsDrawed = true;
                        // Any portion of the imageView, even a single pixel, is within the visible window
                    }

                }
                else
                {
                    firstContactIsDrawed = false;
                }
                //second contact animation
                if ( clientSecondContact.getLocalVisibleRect(scrollBounds)) {
                    if(secondContactIsDrawed == false){
                        clientSecondContact.animateText("CONTACTO ALTERNO");
                        secondContactIsDrawed = true;
                        // Any portion of the imageView, even a single pixel, is within the visible window
                    }

                }
                else
                {
                    secondContactIsDrawed = false;
                }

            }
        });
        etName.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                v.getParent().requestDisallowInterceptTouchEvent(true);
                switch (event.getAction() & MotionEvent.ACTION_MASK){
                    case MotionEvent.ACTION_SCROLL:
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }
                return false;
            }
        });
        etAdress.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                v.getParent().requestDisallowInterceptTouchEvent(true);
                switch (event.getAction() & MotionEvent.ACTION_MASK){
                    case MotionEvent.ACTION_SCROLL:
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }
                return false;
            }
        });
        etColony.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                v.getParent().requestDisallowInterceptTouchEvent(true);
                switch (event.getAction() & MotionEvent.ACTION_MASK){
                    case MotionEvent.ACTION_SCROLL:
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }
                return false;
            }
        });
        etCity.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                v.getParent().requestDisallowInterceptTouchEvent(true);
                switch (event.getAction() & MotionEvent.ACTION_MASK){
                    case MotionEvent.ACTION_SCROLL:
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }
                return false;
            }
        });
        etState.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                v.getParent().requestDisallowInterceptTouchEvent(true);
                switch (event.getAction() & MotionEvent.ACTION_MASK){
                    case MotionEvent.ACTION_SCROLL:
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }
                return false;
            }
        });
        etCountry.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                v.getParent().requestDisallowInterceptTouchEvent(true);
                switch (event.getAction() & MotionEvent.ACTION_MASK){
                    case MotionEvent.ACTION_SCROLL:
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }
                return false;
            }
        });
        etCodePostal.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                v.getParent().requestDisallowInterceptTouchEvent(true);
                switch (event.getAction() & MotionEvent.ACTION_MASK){
                    case MotionEvent.ACTION_SCROLL:
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }
                return false;
            }
        });
        etRFC.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                v.getParent().requestDisallowInterceptTouchEvent(true);
                switch (event.getAction() & MotionEvent.ACTION_MASK){
                    case MotionEvent.ACTION_SCROLL:
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }
                return false;
            }
        });
        etFirstMail.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                v.getParent().requestDisallowInterceptTouchEvent(true);
                switch (event.getAction() & MotionEvent.ACTION_MASK){
                    case MotionEvent.ACTION_SCROLL:
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }
                return false;
            }
        });
        etSecondMail.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                v.getParent().requestDisallowInterceptTouchEvent(true);
                switch (event.getAction() & MotionEvent.ACTION_MASK){
                    case MotionEvent.ACTION_SCROLL:
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }
                return false;
            }
        });
        etFirstPhone.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                v.getParent().requestDisallowInterceptTouchEvent(true);
                switch (event.getAction() & MotionEvent.ACTION_MASK){
                    case MotionEvent.ACTION_SCROLL:
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }
                return false;
            }
        });
        etSecondPhone.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                v.getParent().requestDisallowInterceptTouchEvent(true);
                switch (event.getAction() & MotionEvent.ACTION_MASK){
                    case MotionEvent.ACTION_SCROLL:
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }
                return false;
            }
        });
        etFirstContact.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                v.getParent().requestDisallowInterceptTouchEvent(true);
                switch (event.getAction() & MotionEvent.ACTION_MASK){
                    case MotionEvent.ACTION_SCROLL:
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }
                return false;
            }
        });
        etSecondContact.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                v.getParent().requestDisallowInterceptTouchEvent(true);
                switch (event.getAction() & MotionEvent.ACTION_MASK){
                    case MotionEvent.ACTION_SCROLL:
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }
                return false;
            }
        });

        //change text events to modify
        etFirstPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s)
            {

                if(!enableFab && CurrentData.isClientModificationEnabled())
                {
                    enableFab = true;
                    fabSaveChanges.setVisibility(view.VISIBLE);
                    fabSaveChanges.startAnimation(show);
                }
            }
        });

        etSecondPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s)
            {
                if(!enableFab && CurrentData.isClientModificationEnabled())
                {
                    enableFab = true;
                    fabSaveChanges.setVisibility(view.VISIBLE);
                    fabSaveChanges.startAnimation(show);
                }
            }
        });

        etFirstMail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s)
            {

                if(!enableFab && CurrentData.isClientModificationEnabled())
                {
                    enableFab = true;
                    fabSaveChanges.setVisibility(view.VISIBLE);
                    fabSaveChanges.startAnimation(show);
                }

            }
        });

        etSecondMail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s)
            {
                if(!enableFab && CurrentData.isClientModificationEnabled())
                {
                    enableFab = true;
                    fabSaveChanges.setVisibility(view.VISIBLE);
                    fabSaveChanges.startAnimation(show);
                }

            }
        });

        etFirstContact.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s)
            {
                if(!enableFab && CurrentData.isClientModificationEnabled())
                {
                    enableFab = true;
                    fabSaveChanges.setVisibility(view.VISIBLE);
                    fabSaveChanges.startAnimation(show);
                }
            }
        });

        etSecondContact.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s)
            {

                if(!enableFab && CurrentData.isClientModificationEnabled())
                {
                    enableFab = true;
                    fabSaveChanges.setVisibility(view.VISIBLE);
                    fabSaveChanges.startAnimation(show);
                }
            }
        });
        return view;
    }

    public void refreshData(Client client){
        if (client.getNombre() != null)
        {
            etName.setText(client.getNombre());
        }
        else
        {
            etName.setText("");
        }

        if (client.getDomicilio() != null)
        {
            etAdress.setText(client.getDomicilio());
        }
        else
        {
            etAdress.setText("");
        }

        if (client.getColonia() != null)
        {
            etColony.setText(client.getColonia());
        }
        else
        {
            etColony.setText("");
        }

        if (client.getCiudad() != null)
        {
            etCity.setText(client.getCiudad());
        }
        else
        {
            etCity.setText("");
        }

        if(client.getEstado() != null)
        {
            etState.setText(client.getEstado());
        }
        else
        {
            etState.setText("");
        }

        if(client.getPais() != null)
        {
            etCountry.setText(client.getPais());
        }
        else
        {
            etCountry.setText("");
        }

        if(client.getCodigoPostal() != null)
        {
            etCodePostal.setText(client.getCodigoPostal());
        }
        else
        {
            etCodePostal.setText("");
        }

        if(client.getRfc() != null)
        {
            etRFC.setText(client.getRfc());
        }
        else{
            etRFC.setText("");
        }

        if(client.getEmail1() != null)
        {
            etFirstMail.setText(client.getEmail1());
        }
        else
        {
            etFirstMail.setText("");
        }

        if(client.getEmail2() != null)
        {
            etSecondMail.setText(client.getEmail2());
        }
        else{
            etSecondMail.setText("");
        }

        if(client.getTelefono1() != null)
        {
            etFirstPhone.setText(client.getTelefono1());
        }
        else{
            etFirstPhone.setText("");
        }

        if(client.getTelefono2() != null)
        {
            etSecondPhone.setText(client.getTelefono2());
        }
        else{
            etSecondPhone.setText("");
        }

        if(client.getContacto1() != null)
        {
            etFirstContact.setText(client.getContacto1());
        }
        else{
            etFirstContact.setText("");
        }

        if(client.getContacto2() != null)
        {
            etSecondContact.setText(client.getContacto2());
        }
        else{
            etSecondContact.setText("");
        }

        CurrentData.setClientId(client.getClave());
    }
}

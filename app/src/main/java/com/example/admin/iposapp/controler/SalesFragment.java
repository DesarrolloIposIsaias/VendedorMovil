package com.example.admin.iposapp.controler;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.daasuu.bl.ArrowDirection;
import com.daasuu.bl.BubbleLayout;
import com.daasuu.bl.BubblePopupHelper;
import com.example.admin.iposapp.R;
import com.example.admin.iposapp.backgroundTask.GetStockSoapTask;
import com.example.admin.iposapp.database.Database;
import com.example.admin.iposapp.model.Product;
import com.example.admin.iposapp.model.SaleDetail;
import com.example.admin.iposapp.utility.CurrentData;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.hanks.htextview.HTextView;
import com.hanks.htextview.HTextViewType;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 */
public class SalesFragment extends BaseFragment
{
    private AutoCompleteTextView products;
    private HTextView hTextViewPrice, hTextViewTotal;
    private Product product;
    private ChangeClientDialogFragment dialogFragment;
    private ListViewAdapter lvAdapter;
    private Button cancelProductBtn, goCrepButton;
    private ListView neverEmptyListView;
    private List<Product> allProducts;
    static List<SaleDetail> allSaleDetails;
    private EditText amount, discount, price;
    boolean amountNDiscAvailable = false;
    boolean entryAvailable = true;
    public static Database dataBase;
    boolean callDiscount = false;
    boolean callPrice = false;
    static ArrayList<String> items;
    private FloatingActionButton fabAdd;
    private FloatingActionButton fabCheckout;
    private FloatingActionButton fabStock;
    private FloatingActionMenu fabm;
    private static GetStockSoapTask stockTask;
    private Button addProductBtn;
    PopupWindow popupWindow;
    BubbleLayout bubbleLayout;
    private  HTextView hTextViewClient;
    private TextView existenceMessage;

    public SalesFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState)
    {
        final View view = inflater.inflate(R.layout.fragment_sales, container, false);
        final View clientChange = inflater.inflate(
                R.layout.client_changed_dialog,
                container,
                false
        );
        //Context context = view.getContext();

        //adjust margin on products autocomplete
        View viewCustom = inflater.inflate(R.layout.row_products, container, false);

        TextView customItem = (TextView) viewCustom.findViewById(R.id.autoCompleteItem);

        customItem.setMaxLines(1);
        customItem.setHorizontalScrollBarEnabled(true);
        customItem.setMovementMethod(new ScrollingMovementMethod());
        View.OnTouchListener listener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                boolean isLarger;

                isLarger = ((TextView) v).getLineCount()
                        * ((TextView) v).getLineHeight() > v.getHeight();
                if (event.getAction() == MotionEvent.ACTION_MOVE
                        && isLarger) {
                    v.getParent().requestDisallowInterceptTouchEvent(true);

                } else {
                    v.getParent().requestDisallowInterceptTouchEvent(false);

                }
                return false;
            }
        };

        customItem.setOnTouchListener(listener);


        //pop up layout
        bubbleLayout = (BubbleLayout) inflater.inflate(R.layout.popup_layout_existence,container,false);
        popupWindow = BubblePopupHelper.create(getActivity(), bubbleLayout);
        existenceMessage = (TextView) bubbleLayout.findViewById(R.id.existence_message);
        //end of pop up layout

        dialogFragment = new ChangeClientDialogFragment();
        dialogFragment.setCancelable(true);

        dataBase = new Database(getContext());

        cancelProductBtn = (Button) view.findViewById(R.id.cancelBtn);

        hTextViewClient = (HTextView) view.findViewById(R.id.tvClient);
        hTextViewClient.setAnimateType(HTextViewType.FALL);
        hTextViewTotal = (HTextView) view.findViewById(R.id.tvTotal);
        hTextViewTotal.setAnimateType(HTextViewType.FALL);
        hTextViewClient.animateText(CurrentData.getClientName());
        hTextViewPrice = (HTextView) view.findViewById(R.id.tvPrice);
        hTextViewPrice.setAnimateType(HTextViewType.ANVIL);

        //This help us to recreate the view when the screen rotate
        int currentOrientation = getResources().getConfiguration().orientation;
        if (currentOrientation == Configuration.ORIENTATION_LANDSCAPE) {
            if(CurrentData.getSubtotal() != 0){
                hTextViewPrice.animateText(
                        "Subtotal:" + String.valueOf(CurrentData.getSubtotal()));
            }
            if(CurrentData.getTotal() != 0){
                hTextViewTotal.animateText(
                        "Total:" + String.valueOf(CurrentData.getTotal()));
            }
            if(CurrentData.getProductSelected()){
                amountNDiscAvailable = CurrentData.getProductSelected();
            }
        }
        else {
            if(CurrentData.getSubtotal() != 0){
                hTextViewPrice.animateText(
                        "Subtotal:" + String.valueOf(CurrentData.getSubtotal()));
            }
            if(CurrentData.getTotal() != 0){
                hTextViewTotal.animateText(
                        "Total:" + String.valueOf(CurrentData.getTotal()));
            }
            if(CurrentData.getProductSelected()){
                amountNDiscAvailable = CurrentData.getProductSelected();
            }
        }

        //Set NeverEmptyListView's adapter
        neverEmptyListView = (ListView) view.findViewById(R.id.lvCart);
        final ViewGroup headerView = (ViewGroup) getActivity().getLayoutInflater().inflate(
                R.layout.header_row_layout, neverEmptyListView, false);
        neverEmptyListView.addHeaderView(headerView, null, false);

        View emptyView = getActivity().getLayoutInflater().inflate(R.layout.empty_items, null);
        TextView emptyMsg = (TextView) emptyView.findViewById(R.id.infoMsgTextView);
        emptyMsg.setText(R.string.agregue_productos);

        //items
        ((ViewGroup)neverEmptyListView.getParent()).addView(emptyView);
        neverEmptyListView.setEmptyView(emptyView);
        items = new ArrayList<>();
        lvAdapter = new ListViewAdapter
                (getContext(), R.layout.row_layout, R.id.description, items);
        neverEmptyListView.setAdapter(lvAdapter);

        //fabs
        fabm = (FloatingActionMenu) view.findViewById(R.id.floating_menu);

        FloatingActionButton changeClient =
                (FloatingActionButton) view.findViewById(R.id.floating_client);

        fabAdd = (FloatingActionButton) view.findViewById(R.id.floating_add);
        fabAdd.setEnabled(false);
        fabCheckout = (FloatingActionButton) view.findViewById(R.id.floating_checkout);
        fabCheckout.setEnabled(false);
        fabStock = (FloatingActionButton) view.findViewById(R.id.floating_stock);
        fabStock.setEnabled(false);

        addProductBtn = (Button) view.findViewById(R.id.addProductBtn);
        goCrepButton = (Button) view.findViewById(R.id.goCrepBtn);

        products = (AutoCompleteTextView)view.findViewById(R.id.productAutoCompleteTextView);
        products.setThreshold(1);

        amount = (EditText)view.findViewById(R.id.etAmount);
        discount = (EditText)view.findViewById(R.id.discountEditText);
        price = (EditText)view.findViewById(R.id.etPrice);

        amount.setImeOptions(EditorInfo.IME_ACTION_DONE | EditorInfo.IME_FLAG_NO_EXTRACT_UI);
        price.setImeOptions(EditorInfo.IME_ACTION_DONE | EditorInfo.IME_FLAG_NO_EXTRACT_UI);
        discount.setImeOptions(EditorInfo.IME_ACTION_DONE | EditorInfo.IME_FLAG_NO_EXTRACT_UI);
        products.setImeOptions(EditorInfo.IME_ACTION_DONE | EditorInfo.IME_FLAG_NO_EXTRACT_UI);
        products.setFocusableInTouchMode(true);

        if(savedInstanceState != null)
        {
             products.setText(savedInstanceState.getString("product"));
        }

        //activate-deactivate cancel button
        if(products.getText().toString().trim().length() == 0)
        {
            cancelProductBtn.setEnabled(false);
            cancelProductBtn.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.cancelgray, 0, 0, 0);
        }
        else
        {
            cancelProductBtn.setEnabled(true);
            cancelProductBtn.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.cancelred, 0, 0, 0);
        }

        //Check if is called from AllSalesFragment
        if (CurrentData.isCallFromAllSales())
        {
            try
            {
                if(CurrentData.getSended().equals("Y") || !CurrentData.isMutable())
                {
                    addProductBtn.setEnabled(false);
                    fabCheckout.setEnabled(false);
                    fabAdd.setEnabled(false);
                    price.setEnabled(false);
                    discount.setEnabled(false);
                    products.setFocusableInTouchMode(false);
                    amount.setEnabled(false);
                    fabCheckout.setEnabled(true);

                }
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }

        }

        //update products list
        updateProductListView();

        if(!items.isEmpty())
        {
            //fabAdd.setEnabled(true);
            fabStock.setEnabled(true);
            fabCheckout.setEnabled(true);
        }

        goCrepButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClientsCrepFragment fragment = new ClientsCrepFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.fragmentContainer,fragment,fragment.getTag()).commit();
            }
        });

        addProductBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) { /////Checar esto ya que no permite registrar el producto si se volteo

                if(amountNDiscAvailable
                        && (discount.getText().toString().trim().length() != 0)
                        && (price.getText().toString().trim().length() != 0)
                        && (amount.getText().toString().trim().length() != 0))
                {
                    try
                    {
                        //dataBase.open();
                        SaleDetail saleDetail= new SaleDetail();
                        String productId = products.getText().toString();

                        saleDetail.setClient(CurrentData.getClientId());
                        saleDetail.setAmount(Float.parseFloat(amount.getText().toString()));
                        saleDetail.setDiscount(Float.parseFloat(discount.getText().toString()));

                        dataBase.open();
                        product = Database.productDao.fetchProductById(productId.substring(
                                productId.indexOf("<") + 1, productId.indexOf(">")));
                        dataBase.close();

                        saleDetail.setPrice(Float.parseFloat(price.getText().toString()));
                        saleDetail.setDescription(product.getDescripcion1());

                        saleDetail.setProductId(productId.substring(productId.indexOf("<") + 1,
                                productId.indexOf(">")));

                        saleDetail.setSaleId(CurrentData.getSaleId());

                        dataBase.open();
                        SaleDetail fetchedSaleDetail = Database.saleDetailDAO.exists(
                                saleDetail.getProductId(), CurrentData.getSaleId(),
                                String.valueOf(saleDetail.getPrice()));
                        dataBase.close();

                        dataBase.open();
                        if(!fetchedSaleDetail.getId().equals("-1"))
                        {
                            float tempAmount = fetchedSaleDetail.getAmount() +
                                    saleDetail.getAmount();

                            saleDetail.setAmount(tempAmount);

                            if(Database.saleDetailDAO.update(saleDetail, CurrentData.getSaleId()))
                            {
                                Toast.makeText(getContext(),"Registro actualizado",
                                        Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Toast.makeText(getContext(),"Problema al actualizar registro",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                        else
                        {
                            Database.saleDetailDAO.addSaleDetail(saleDetail);
                        }
                        dataBase.close();

                        float tempSubTotal = saleDetail.getAmount() * saleDetail.getPrice();

                        CurrentData.setSubtotal(tempSubTotal);

                        float tempTotal = CurrentData.getTotal() + CurrentData.getSubtotal();

                        CurrentData.setTotal(tempTotal);

                        dataBase.open();
                        allSaleDetails = Database.saleDetailDAO.fetchSaleDetailsBySale(
                                CurrentData.getSaleId());
                        dataBase.close();

                        items.clear();

                        for(int i = 0; i < allSaleDetails.size(); i++)
                        {
                            items.add(0,allSaleDetails.get(i).getDescription() + "_" +
                                    String.valueOf(allSaleDetails.get(i).getAmount()) + "_"+
                                    String.valueOf(allSaleDetails.get(i).getPrice()) + "_" +
                                    allSaleDetails.get(i).getDiscount() + "_" +
                                    String.valueOf(allSaleDetails.get(i).getAmount() *
                                            allSaleDetails.get(i).getPrice())+ "_" +
                                    String.valueOf(allSaleDetails.get(i).getProductId()));
                        }

                        lvAdapter.notifyDataSetInvalidated();
                        neverEmptyListView.invalidateViews();
                        products.requestFocus();
                        fabCheckout.setEnabled(true);
                        fabStock.setEnabled(true);

                        price.setText("");
                        discount.setText("");
                        amount.setText("");
                        products.setText("");
                        amountNDiscAvailable = false;
                        CurrentData.setProductSelected(amountNDiscAvailable);
                        cancelProductBtn.setEnabled(false);
                        hTextViewTotal.animateText(
                                "Total:" + String.valueOf(CurrentData.getTotal())); //<--Checar esto
                        cancelProductBtn.setCompoundDrawablesWithIntrinsicBounds(
                                R.mipmap.cancelgray, 0, 0, 0);
                        products.setFocusableInTouchMode(true);
                        neverEmptyListView.setSelection(1);



                    }
                    catch (Exception ex)
                    {
                        Log.d("IPOS DB: ", ex.toString());
                    }

                }
                else
                {
                    if(!CurrentData.getProductSelected())
                    {
                        Toast.makeText(
                                getActivity(),
                                "SELECCIONE UN PRODUCTO",
                                Toast.LENGTH_LONG).show();
                        products.requestFocus();
                    }
                    else if (amount.getText().toString().trim().length() == 0)
                    {
                        Toast.makeText(
                                getActivity(),
                                "INTRODUZCA CANTIDAD",
                                Toast.LENGTH_LONG).show();
                        amount.requestFocus();
                    }
                    else if (discount.getText().toString().trim().length() == 0)
                    {
                        Toast.makeText(
                                getActivity(),
                                "INTRODUZCA DESCUENTO",
                                Toast.LENGTH_LONG).show();
                        discount.requestFocus();
                    }
                    else if(price.getText().toString().trim().length() == 0)
                    {
                        Toast.makeText(
                                getActivity(),
                                "INTRODUZCA PRECIO",
                                Toast.LENGTH_LONG).show();
                        price.requestFocus();
                    }
                }
            }
        });


        cancelProductBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                cancelProductBtn.setEnabled(false);
                products.setFocusableInTouchMode(true);
                amountNDiscAvailable = false;
                CurrentData.setProductSelected(amountNDiscAvailable);
                products.setText("");
                products.requestFocus();
                amount.setText("");
                price.setText("");
                discount.setText("");
                cancelProductBtn.setCompoundDrawablesWithIntrinsicBounds(
                        R.mipmap.cancelgray, 0, 0, 0);
            }
        });




        changeClient.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                Fragment fragment = getActivity().
                                    getSupportFragmentManager().
                                    findFragmentByTag("fragment_sales");

                dialogFragment.setTargetFragment(fragment,1);
                dialogFragment.show(
                        getActivity().getSupportFragmentManager(),
                        "client_changed_dialog");


             }
        });

        fabAdd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                ExistencesDialogFragment dialogFragment = new ExistencesDialogFragment();
                dialogFragment.show(getActivity().getFragmentManager(), "existences_dialog");

                //stockTask.execute();
                //alertDialog.show();
    }
        });

        fabCheckout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                PaymentMethodFragment newFragment = new PaymentMethodFragment();

                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(
                        Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                // Replace whatever is in the fragment_container view with this fragment,
                // and add the transaction to the back stack
                transaction.replace(R.id.fragmentContainer, newFragment);
                transaction.addToBackStack(null);

                // Commit the transaction
                transaction.commit();

            }
        });

        fabStock.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                try
                {
                    fabAdd.setEnabled(true);
                    dataBase.open();
                    List<SaleDetail> products = Database.saleDetailDAO.fetchSaleDetailsBySale(
                            CurrentData.getSaleId());
                    dataBase.close();

                    CurrentData.setDetailProductList(products);
                    stockTask = new GetStockSoapTask(getContext(), products);
                    stockTask.execute();
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                }
            }
        });

        products.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s)
            {
                if(products.getText().toString().length() == 0)
                {
                    entryAvailable = true;
                }
                if(entryAvailable && products.getText().toString().length() == 1)
                {

                    try
                    {
                        dataBase.open();
                        allProducts = Database.productDao.searchByFirstLetter(
                                products.getText().toString().charAt(0));
                        dataBase.close();

                        String[] productsAdapter = new String[allProducts.size()];
                        for(int i = 0; i < allProducts.size(); i++)
                        {
                            productsAdapter[i] = allProducts.get(i).getDescripcion1() + ", "
                                    +allProducts.get(i).getDescripcion2()+"<"
                                    + allProducts.get(i).getClave()+">"+"_"
                                    + allProducts.get(i).getExistencia();
                        }
                        ArrayAdapter<String> toSet = new ArrayAdapter<>(
                                getActivity(),
                                R.layout.row_products,
                                R.id.autoCompleteItem,
                                productsAdapter);

                        products.setAdapter(toSet);
                        discount.setText("0.0");
                    }
                    catch (Exception ex)
                    {
                        ex.printStackTrace();
                    }

                    entryAvailable = false;
                }
            }
        });



        products.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long rowId) {
                //TODO Do something with the selected text
                //dataBase = new Database(getContext());
                String productId = products.getText().toString();
                dataBase.open();
                Product product = Database.productDao.fetchProductById(productId.substring(
                        productId.indexOf("<") + 1, productId.indexOf(">")));
                dataBase.close();
                existenceMessage.setText(String.valueOf(product.getExistencia()));
                price.setText(String.valueOf(product.getPrecio1()));
                CurrentData.setPrice(product.getPrecio1());
                amountNDiscAvailable = true;
                CurrentData.setProductSelected(amountNDiscAvailable);
                amount.requestFocus();
                cancelProductBtn.setEnabled(true);
                cancelProductBtn.setCompoundDrawablesWithIntrinsicBounds(
                        R.mipmap.cancelred, 0, 0, 0);
                products.setFocusableInTouchMode(false);
                products.setSelection(0);

            }
        });

        products.setOnEditorActionListener(new TextView.OnEditorActionListener()
        {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
            {
                entryAvailable = true;
                if(actionId == EditorInfo.IME_ACTION_DONE)
                {
                    try
                    {
                        dataBase.open();
                        allProducts = Database.productDao.fetchProductsWith(
                                    products.getText().toString());
                        dataBase.close();

                        ArrayList<String> productsAdapter = new ArrayList<String>();
                        for(int i = 0; i < allProducts.size(); i++)
                        {
                            productsAdapter.add(i,allProducts.get(i).getDescripcion1() + ", "
                                    +allProducts.get(i).getDescripcion2()+"<"
                                    + allProducts.get(i).getClave()+">"+"_"+ allProducts.get(i).getExistencia());
                        }
                        ProductsArrayAdapter toSet = new ProductsArrayAdapter
                                (getContext(), R.layout.row_products, R.id.autoCompleteItem, productsAdapter);
                        products.setAdapter(toSet);
                        toSet.notifyDataSetChanged();

                        //discount.setText(String.valueOf("0.0"));
                    }
                    catch (Exception ex)
                    {
                        ex.printStackTrace();
                    }
                }

                return false;
            }
        });


        amount.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {

                if(!CurrentData.getProductSelected())
                {
                    Toast.makeText(
                            getActivity(),"SELECCIONE UN PRODUCTO", Toast.LENGTH_LONG).show();
                    products.requestFocus();
                    return true;
                }
                return false;
            }
        });



        amount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(price.getText().toString().trim().length() > 0 &&
                        amount.getText().toString().trim().length() > 0){
                    Float amountProduct = Float.parseFloat(amount.getText().toString());
                    Float priceProduct = Float.parseFloat(price.getText().toString());
                    String subtotalProduct = String.valueOf(amountProduct*priceProduct);
                    CurrentData.setSubtotal(Float.parseFloat(subtotalProduct));
                    hTextViewPrice.animateText("Subtotal producto: " + subtotalProduct);
                }
                else
                {
                    hTextViewPrice.animateText(" ");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        amount.setOnEditorActionListener(new TextView.OnEditorActionListener()
        {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
            {
                if(actionId == EditorInfo.IME_ACTION_DONE)
                {
                    //amount.clearFocus();
                    discount.requestFocus();
                    discount.setText("");
                    discount.setCursorVisible(true);
                }

                return false;
            }
        });

        discount.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {

                if(!CurrentData.getProductSelected())
                {
                    Toast.makeText(getActivity(),"SELECCIONE UN PRODUCTO", Toast.LENGTH_LONG).show();
                    products.requestFocus();
                    return true;
                }
                else
                {
                    discount.setText("");
                }

                return false;
            }
        });

        discount.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {

            }

            @Override
            public void afterTextChanged(Editable s)
            {

                if(!callPrice)
                {

                    if(price.getText().toString().trim().length() > 0 &&
                            discount.getText().toString().trim().length() > 0){
                        try
                        {
                            Float priceProduct = CurrentData.getPrice();
                            Float discountProduct = Float.parseFloat(discount.getText().toString());
                            String finalPrice = String.valueOf(
                                    priceProduct-(priceProduct*(
                                            discountProduct/Float.parseFloat("100"))));
                            callDiscount = true;
                            price.setText(finalPrice);
                            priceProduct = Float.parseFloat(price.getText().toString());
                            Float qty = Float.parseFloat(s.toString());
                            hTextViewPrice.animateText("Subtotal producto: " + String.valueOf(
                                    priceProduct*qty));
                            CurrentData.setSubtotal(priceProduct*qty);
                        }
                        catch (Exception ex)
                        {
                            ex.printStackTrace();
                        }
                    }
                    else
                    {
                        price.setText(String.valueOf(CurrentData.getPrice()));
                    }
                }
                else
                {
                    callPrice = false;
                }
            }
        });

        discount.setOnFocusChangeListener(new View.OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View v, boolean hasFocus)
            {
                if(!hasFocus)
                {
                    if(discount.getText().toString().equals(""))
                    {
                        discount.setText("0.0");
                    }
                }
            }
        });

        discount.setOnEditorActionListener(new TextView.OnEditorActionListener()
        {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
            {
                if(actionId == EditorInfo.IME_ACTION_DONE)
                {
                    //discount.clearFocus();
                    price.requestFocus();
                    price.setCursorVisible(true);
                }

                return false;
            }
        });

        price.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {

                if(!CurrentData.getProductSelected())
                {
                    Toast.makeText(
                            getActivity(),"SELECCIONE UN PRODUCTO", Toast.LENGTH_LONG).show();
                    products.requestFocus();
                    return true;
                }
                return false;
            }
        });

        price.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {

            }

            @Override
            public void afterTextChanged(Editable s)
            {
                try{
                    if(!callDiscount)
                    {
                        if(price.getText().toString().trim().length() > 0 &&
                                discount.getText().toString().trim().length() > 0){

                            Float priceProduct = Float.parseFloat(price.getText().toString());
                            Float amountProduct = Float.parseFloat(amount.getText().toString());
                            String subtotalProduct = String.valueOf(amountProduct*priceProduct);
                            discount.setText(String.valueOf(
                                    100-((priceProduct*100)/CurrentData.getPrice())));
                            callPrice = true;
                            hTextViewPrice.animateText("Subtotal producto: " + subtotalProduct);
                        }
                        else
                        {
                            hTextViewPrice.animateText(" ");
                        }
                    }
                    else
                    {
                        callDiscount = false;
                    }
                }
                catch(Exception ex)
                {
                    ex.printStackTrace();
                }
            }
        });

        price.setOnEditorActionListener(new TextView.OnEditorActionListener()
        {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
            {
                if(actionId == EditorInfo.IME_ACTION_DONE)
                {
                    addProductBtn.requestFocus();
                    addProductBtn.performClick();
                    products.setFocusableInTouchMode(true);
                    products.requestFocus();
                    addProductBtn.setCursorVisible(true);

                }

                return false;
            }
        });


        neverEmptyListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           final int pos, long id) {
                // TODO Auto-generated method stub
                if(pos != 0)
                {
                    new AlertDialog.Builder(getContext())
                            .setMessage("¿Que accion desea llevar a cabo?")
                            .setCancelable(true)
                            .setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    try
                                    {
                                        if (CurrentData.getSended().equals("Y"))
                                        {
                                            Toast.makeText(
                                                    getContext(),
                                                    "No se puede modificar una venta enviada",
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                        else
                                        {
                                            String item = lvAdapter.getItem(pos-1);
                                            lvAdapter.remove(item);
                                            lvAdapter.notifyDataSetChanged();
                                            String [] itemsList = new String[1];
                                            itemsList[0] = item;
                                            itemsList = itemsList[0].split("_");
                                            //dataBase = new Database(getContext());
                                            dataBase.open();
                                            Database.saleDetailDAO.deleteSaleDetail(
                                                    CurrentData.getSaleId(),
                                                    itemsList[2],
                                                    itemsList[0]);
                                            dataBase.close();
                                            if(lvAdapter.isEmpty())
                                            {
                                                fabAdd.setEnabled(false);
                                                fabCheckout.setEnabled(false);
                                                fabStock.setEnabled(false);
                                            }
                                            CurrentData.setTotal(
                                                    CurrentData.getTotal() -
                                                            Float.parseFloat(itemsList[2]) *
                                                                    Float.parseFloat(itemsList[1]));
                                            hTextViewTotal.animateText("Total:" + String.valueOf(
                                                    CurrentData.getTotal()));
                                        }
                                    }
                                    catch(Exception ex)
                                    {
                                        ex.printStackTrace();
                                    }
                                }
                            })
                            .setNegativeButton("Editar", new DialogInterface.OnClickListener()
                            {
                                @Override
                                public void onClick(DialogInterface dialog, int which)
                                {
                                    try
                                    {
                                        if(CurrentData.getSended() == null)
                                        {
                                            CurrentData.setSended("N");
                                        }
                                        if (CurrentData.getSended().equals("Y"))
                                        {
                                            Toast.makeText(
                                                    getContext(),
                                                    "No se puede modificar una venta enviada",
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                        else
                                        {
                                            price.getText().clear();
                                            String item = lvAdapter.getItem(pos-1);
                                            lvAdapter.remove(item);
                                            lvAdapter.notifyDataSetChanged();
                                            String [] itemsList = new String[1];
                                            itemsList[0] = item;
                                            itemsList = itemsList[0].split("_");
                                            Log.w("ITEM LIST",
                                                    itemsList[0]
                                                            + "-"
                                                            + itemsList[1]
                                                            + "-" + itemsList[2]);


                                            dataBase.open();
                                            Product product = Database.productDao.fetchProductById(
                                                    itemsList[5]);
                                            Database.saleDetailDAO.deleteSaleDetail(
                                                    CurrentData.getSaleId(),
                                                    itemsList[2],
                                                    itemsList[0]);
                                            dataBase.close();

                                            String des = product.getDescripcion1() +
                                                    ", " + product.getDescripcion2()+"<"
                                                    + product.getClave()
                                                    + ">" + "_"
                                                    + product.getExistencia();

                                            products.setText(des);
                                            discount.setText(itemsList[3]);
                                            price.setText(itemsList[2]);
                                            amount.setText(itemsList[1]);
                                            products.dismissDropDown();
                                            products.setFocusableInTouchMode(false);
                                            cancelProductBtn.setEnabled(true);
                                            cancelProductBtn.setCompoundDrawablesWithIntrinsicBounds(
                                                    R.mipmap.cancelred, 0, 0, 0);
                                            amountNDiscAvailable = true;
                                            CurrentData.setProductSelected(amountNDiscAvailable);
                                            if(lvAdapter.isEmpty())
                                            {
                                                fabAdd.setEnabled(false);
                                                fabCheckout.setEnabled(false);
                                                fabStock.setEnabled(false);
                                            }
                                            CurrentData.setTotal(
                                                    CurrentData.getTotal() -
                                                            Float.parseFloat(itemsList[2]) *
                                                                    Float.parseFloat(itemsList[1]));
                                            hTextViewTotal.animateText("Total:" + String.valueOf(
                                                    CurrentData.getTotal()));
                                        }
                                    }
                                    catch(Exception ex)
                                    {
                                        ex.printStackTrace();
                                    }
                                }
                            })
                            .show();

                    return true;
                }
                return true;
            }
        });


        return  view;
    }

    public void updateProductListView()
    {
        dataBase.open();
        allSaleDetails = Database.saleDetailDAO.fetchSaleDetailsBySale(
                CurrentData.getSaleId());
        dataBase.close();
        items.clear();

        for(int i = 0; i < allSaleDetails.size(); i++)
        {
            items.add(0,allSaleDetails.get(i).getDescription() + "_" +
                    String.valueOf(allSaleDetails.get(i).getAmount()) + "_"+
                    String.valueOf(allSaleDetails.get(i).getPrice()) + "_" +
                    allSaleDetails.get(i).getDiscount() + "_" +
                    String.valueOf(allSaleDetails.get(i).getAmount() *
                            allSaleDetails.get(i).getPrice()) +
                    "_" + allSaleDetails.get(i).getProductId());
        }

        lvAdapter.notifyDataSetInvalidated();
        neverEmptyListView.invalidateViews();
    }

    @Override
    public void onBackPressed()
    {
        getActivity().getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onSaveInstanceState(Bundle bundle)
    {
        super.onSaveInstanceState(bundle);
        if(products != null)
        {
            bundle.putString("product", products.getText().toString());
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        // Make sure fragment codes match up
        try{
        if (requestCode == 1)
            {
                String editTextString = data.getStringExtra("newClient");
                hTextViewClient.animateText(editTextString);
            }

        }
        catch(Exception ex)
        {
            Log.d("error", "onActivityResult: " + ex.toString());
        }

    }
}



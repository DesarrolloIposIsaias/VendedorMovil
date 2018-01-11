package com.example.admin.iposapp.controler;


import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import com.example.admin.iposapp.controler.PaymentMethodFragment;
import com.example.admin.iposapp.R;
import com.example.admin.iposapp.database.Database;
import com.example.admin.iposapp.model.Client;
import com.example.admin.iposapp.utility.CurrentData;
import com.example.admin.iposapp.utility.ShowClientsPagerAdapter;

import java.util.List;

public class MenuActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        PaymentMethodFragment.OnFragmentInteractionListener
{
    private ActionBarDrawerToggle toggle;
    private EditText etSecondContact, etFirstContact,etSecondPhone,etFirstPhone,
            etSecondMail, etFirstMail, etRFC, etCodePostal,etCountry, etState, etCity,
            etColony, etAdress,etName;
    private Toolbar toolbar;
    private DrawerLayout drawer;
    //SearchView searchView;
    private CursorAdapter mAdapter;
    private Fragment fInfo,fTab;
    private View viewInfo,viewTab;
    Database dataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        dataBase = new Database(MenuActivity.this);
        super.onCreate(savedInstanceState);
        try
        {
            setContentView(R.layout.activity_menu);
            toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
        }
        catch (Exception ex)
        {
            Log.w("MainActivity: ", ex.getMessage());
        }
        //SEARCHVIEW REQUIRIMENTS OBJECTS FOR TAB CONTACT INFORMATION

        //DRAWER THINGS
        drawer = (DrawerLayout) findViewById(R.id.activity_drawer);
        toggle = new ActionBarDrawerToggle(
                this,
                drawer,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);

        if (drawer != null)
        {
            drawer.addDrawerListener(toggle);
        }

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if(navigationView != null)
        {

            navigationView.setNavigationItemSelectedListener(this);
        }
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState)
    {
        super.onPostCreate(savedInstanceState);
        toggle.syncState();
    }

    @Override
    protected void onPause()
    {
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {



        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu, menu);
        //activateSearchView(menu);
        return true;
    }

    /*private void activateSearchView(Menu menu)
    {

        MenuItem searchItem = menu.findItem(R.id.menu_search);
        searchView = (SearchView)MenuItemCompat.getActionView(searchItem);
        final SearchManager searchManager = (SearchManager) getSystemService
                (Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(
                new ComponentName(this, MenuActivity.class)
        ));

        if (searchView != null) {
            // Getting selected (clicked) item suggestion
            searchView.setOnSuggestionListener(new SearchView.OnSuggestionListener() {
                @Override
                public boolean onSuggestionClick(int position)
                {
                    List<Fragment> fInfo = getSupportFragmentManager().getFragments();


                    searchView.getSuggestionsAdapter().getCursor().moveToPosition(position);
                    dataBase.open();
                    String clientToSearch = searchView.getSuggestionsAdapter().getCursor().getString(1);
                    Client client = Database.clientDao.fetchClientById(clientToSearch.substring(
                            clientToSearch.indexOf("<") + 1, clientToSearch.indexOf(">")));
                    CurrentData.setClientModificationEnabled(true);
                    dataBase.close();
                    for(int i = 0; i < fInfo.size(); i++)
                    {
                        if(fInfo.get(i) instanceof TabContactInformationFragment)
                        {
                            ((TabContactInformationFragment) fInfo.get(i)).refreshData(client);
                        }
                        else if (fInfo.get(i) instanceof TabTaxInformationFragment)
                        {
                            ((TabTaxInformationFragment) fInfo.get(i)).refreshData(client);
                        }
                    }


                    return true;

                }

                @Override
                public boolean onSuggestionSelect(int position) {
                    // Your code here
                    return true;
                }
            });
        }




    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.ventas)
        {
            MenuSales scFragment = new MenuSales();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragmentContainer,
                    scFragment,
                    scFragment.getTag()).commit();

        }
        else if (id == R.id.clientes)
        {
            ClientMenuFragment mcFragment = new ClientMenuFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragmentContainer,
                    mcFragment,
                    mcFragment.getTag()).commit();

        }
        else if (id == R.id.pedidos)
        {
            ClientsCrepFragment fragment = new ClientsCrepFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragmentContainer,
                    fragment,
                    fragment.getTag()).commit();
            Toast.makeText(this, "Modulo no disponible", Toast.LENGTH_SHORT).show();
        }
        else if (id == R.id.inventario)
        {
            Toast.makeText(this, "Modulo no disponible", Toast.LENGTH_SHORT).show();
        }
        else if (id == R.id.pagos)
        {
            //ClientsMultipleCrepFragment cmcFragment = new ClientsMultipleCrepFragment();
            PaymentOptionsFragment cmcFragment = new PaymentOptionsFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragmentContainer,
                    cmcFragment,
                    cmcFragment.getTag()).addToBackStack(null).commit();

        }
        else if(id == R.id.settings)
        {
            FtpMenu scFragment = new FtpMenu();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragmentContainer,
                    scFragment,
                    scFragment.getTag()).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.activity_drawer);
        if (drawer != null)
        {
            drawer.closeDrawer(GravityCompat.START);
        }
        return true;
    }

    @Override
    public void onStart()
    {
        super.onStart();
    }

    @Override
    public void onStop()
    {
        super.onStop();
    }

    @Override
    public void onFragmentInteraction(Uri uri)
    {

    }

    @Override
    public void onBackPressed() {
        Fragment f = getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);
        if(f != null && f instanceof MenuSales)
        {
            handleDrawer();
        }
        else if(f != null && f instanceof SalesFragment)
        {
            handleDrawer();
        }
        else if(f != null && f instanceof AllSalesFragment)
        {
            super.onBackPressed();
        }
        else if(f != null && f instanceof SelectClientFragment)
        {
            super.onBackPressed();
        }
        else if(f != null && f instanceof PaymentMethodFragment)
        {
            super.onBackPressed();
            // ((BaseFragment)f).onBackPressed();
        }

        //tellFragments();
    }

    private void tellFragments(){
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        if(fragments != null ){
            for(Fragment f : fragments){
                if(f != null && f instanceof BaseFragment){
                    if(f instanceof SalesFragment)
                    {
                        handleDrawer();
                        return;
                    }
                    else
                    {
                        ((BaseFragment)f).onBackPressed();
                        return;
                    }
                }
            }
        }
        else
        {
            handleDrawer();
        }

    }



    public void handleDrawer(){

    }
}

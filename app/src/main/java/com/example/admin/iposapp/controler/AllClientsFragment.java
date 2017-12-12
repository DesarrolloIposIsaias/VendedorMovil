package com.example.admin.iposapp.controler;



import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;

import com.example.admin.iposapp.database.Database;
import com.example.admin.iposapp.model.Client;
import com.example.admin.iposapp.utility.CurrentData;
import com.example.admin.iposapp.utility.ShowClientsPagerAdapter;
import android.support.v4.app.Fragment;
import android.support.design.widget.TabLayout;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.SearchView;

import com.example.admin.iposapp.R;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AllClientsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AllClientsFragment extends Fragment

{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private SearchView searchView;
    private Database dataBase;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
;

    public AllClientsFragment()
    {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AllClientsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AllClientsFragment newInstance(String param1, String param2)
    {
        AllClientsFragment fragment = new AllClientsFragment();
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
        View view = inflater.inflate(R.layout.fragment_all_clients, container, false);

        LayoutInflater mInflater= LayoutInflater.from(getContext());
        setHasOptionsMenu(true);

        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tab_layout_add_kit);
        tabLayout.addTab(tabLayout.newTab().setText("CONTACTO"));
        tabLayout.addTab(tabLayout.newTab().setText("FISCAL"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) view.findViewById(R.id.pager);
        final ShowClientsPagerAdapter adapter =
                new ShowClientsPagerAdapter(
                        getActivity().getSupportFragmentManager(),
                        tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });



        return view;
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu)
    {
        getActivity().getMenuInflater().inflate(R.menu.menu, menu);
        activateSearchView(menu);
    }

    private void activateSearchView(Menu menu)
    {
        MenuItem searchItem = menu.findItem(R.id.menu_search);
        searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        final SearchManager searchManager =
                (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getActivity().getComponentName()));

        if (searchView != null)
        {
            // Getting selected (clicked) item suggestion
            searchView.setOnSuggestionListener(new SearchView.OnSuggestionListener() {
                @Override
                public boolean onSuggestionClick(int position)
                {
                    List<Fragment> fInfo = getActivity().getSupportFragmentManager().getFragments();


                    searchView.getSuggestionsAdapter().getCursor().moveToPosition(position);
                    dataBase = new Database(getContext());
                    dataBase.open();
                    String clientToSearch =
                            searchView.getSuggestionsAdapter().getCursor().getString(1);
                    Client client =
                            Database.clientDao.fetchClientById(clientToSearch.substring(
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

    }

}

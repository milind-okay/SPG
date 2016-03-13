package com.wordpress.milindkrohit.spg;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by milind on 18/1/16.
 */
public class ListScore extends ListFragment {
    private List<ListViewItem> mItems;        // ListView items list
    String players_name,players_score,mdisplay;
    DBHelper mydb;
    int pairNumber;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mydb = new DBHelper(getActivity());
        // initialize the items list

        pairNumber = mydb.numberOfRows();
        byRating();

    }
    public void sortby(int a){
        if(a == 1){
            byRating();
        }else{
            bysetup_fee();
        }
    }
    public void byRating(){
        if(pairNumber>0) {
            mItems = new ArrayList<ListViewItem>();
            Cursor rs = mydb.getDataRating();
            rs.moveToFirst();
            while(!rs.isLast()) {
                players_name = rs.getString(rs.getColumnIndex(DBHelper.PAY_NAME));
                mItems.add(new ListViewItem(players_name,players_score));
                rs.moveToNext();
            }

            if (!rs.isClosed())
                rs.close();
        }



        // initialize and set the list adapter
        setListAdapter(new ListViewAdapter(getActivity(), mItems));
    }
    public void bysetup_fee(){
        mItems = new ArrayList<ListViewItem>();
        if(pairNumber>0) {
            Cursor rs = mydb.getDataSetup();
            rs.moveToFirst();
            while(!rs.isLast()) {
                players_name = rs.getString(rs.getColumnIndex(DBHelper.PAY_NAME));
                mItems.add(new ListViewItem(players_name,players_score));
                rs.moveToNext();
            }

            if (!rs.isClosed())
                rs.close();
        }



        // initialize and set the list adapter
        setListAdapter(new ListViewAdapter(getActivity(), mItems));
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // remove the dividers from the ListView of the ListFragment
        getListView().setDivider(null);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // retrieve theListView item
        ListViewItem item = mItems.get(position);

        // do something
        Toast.makeText(getActivity(), item.description, Toast.LENGTH_SHORT).show();
    }
}


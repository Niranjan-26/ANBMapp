package com.example.anbmapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.widget.ToolbarWidgetWrapper;
import androidx.fragment.app.Fragment;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static android.content.Context.MODE_PRIVATE;

public class SearchFragment extends Fragment {

    String urladdress="http://njdrums.000webhostapp.com/fetchBandsPicUploads.php";
    String[] Band_Name;
    String[] Email;
    String[] caption;
    String[] url_upload;
    ListView listView;
    BufferedInputStream is;
    String line=null;
    String result=null;
    SharedPreferences sharedPreferences3;
    SharedPreferences.Editor editor3;
    public SearchFragment(){
        //required empty constructor
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_search, container, false);

        listView=(ListView)v.findViewById(R.id.lview);
        Toolbar toolbar = v.findViewById(R.id.toolbarguestuser);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        setHasOptionsMenu(true);


        StrictMode.setThreadPolicy((new StrictMode.ThreadPolicy.Builder().permitNetwork().build()));
        collectData();
        CustomListView customListView=new CustomListView(getContext(),Band_Name,Email,caption,url_upload);


        listView.setAdapter(customListView);




        return v;



    }
    private void collectData()
    {
//Connection
        try{

            URL url=new URL(urladdress);
            HttpURLConnection con=(HttpURLConnection)url.openConnection();
            con.setRequestMethod("POST");
            is=new BufferedInputStream(con.getInputStream());

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        //content
        try{
            BufferedReader br=new BufferedReader(new InputStreamReader(is));
            StringBuilder sb=new StringBuilder();
            while ((line=br.readLine())!=null){
                sb.append(line+"\n");
            }
            is.close();
            result=sb.toString();

        }
        catch (Exception ex)
        {
            ex.printStackTrace();

        }

//JSON
        try{
            JSONArray ja=new JSONArray(result);
            JSONObject jo=null;
            Band_Name=new String[ja.length()];
            Email=new String[ja.length()];
            caption=new String[ja.length()];
            url_upload=new String[ja.length()];

            for(int i=0;i<=ja.length();i++){
                jo=ja.getJSONObject(i);
                Band_Name[i]=jo.getString("Band_Name");
                Email[i]=jo.getString("Email");
                caption[i]=jo.getString("caption");
                url_upload[i]=jo.getString("url_upload");
            }
        }
        catch (Exception ex)
        {

            ex.printStackTrace();
        }


    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.drawermenu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_logout:
                sharedPreferences3 = this.getActivity().getSharedPreferences("loginref3", Context.MODE_PRIVATE);
                editor3 = sharedPreferences3.edit();
                editor3.remove("Username");
                editor3.remove("Password");
                editor3.putBoolean("state", false);
                editor3.apply();
                Intent intent = new Intent(getContext(), login_user.class);
                getContext().startActivity(intent);
                getActivity().finish();

                Toast.makeText(getContext(), "Loged Out", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

}

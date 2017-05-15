package com.example.bbdaiya.phvalue;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {
    ArrayList<Data> values = new ArrayList<>();
    Button get_last_value;
    Button get_all;
    TextView value_text_view;
    final String LOG = MainActivityFragment.class.getSimpleName();
    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        FetchData fetchData = new FetchData();
        fetchData.execute();
        View rootview = inflater.inflate(R.layout.fragment_main, container, false);
        get_last_value = (Button) rootview.findViewById(R.id.get_last_value_button);
        value_text_view = (TextView) rootview.findViewById(R.id.value_text_view);
        get_all = (Button) rootview.findViewById(R.id.get_all_button);
        get_last_value.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                value_text_view.setText("pH value: "+values.get(values.size()-1).getValue()+
                "\nDate Added: "+values.get(values.size()-1).getDate_added());
            }
        });
        get_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ListViewActitvity.class);
                for(int i = 0; i < values.size(); i++){
                    Log.v(LOG, i+" "+values.get(i).getValue());
                }
                Log.v(MainActivityFragment.class.getSimpleName(), "main activity "+values.get(values.size()-1).getValue());
                intent.putParcelableArrayListExtra("list", values);
                startActivity(intent);
            }
        });
        return rootview;


    }

    public class FetchData extends AsyncTask<Void, Void, ArrayList<Data>>{
        final String LOG = FetchData.class.getSimpleName();
        @Override
        protected ArrayList<Data> doInBackground(Void... params) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String datahtml = null;

            try{
                final String SOURCE_BASE_URL = "http://phvalues.herokuapp.com/";
                URL url = null;
                try{
                    url = new URL(SOURCE_BASE_URL);
                    Log.v(LOG, url.toString());
                }
                catch(MalformedURLException e){
                    e.printStackTrace();
                }
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();
//                if(urlConnection.getResponseCode()==401){
//                    if(Utils.checkConnection(getContext())){
//                        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
//                                getContext());
//
//                        // set title
//                        alertDialogBuilder.setTitle(getContext().getString(R.string.api_key));
//
//                        // set dialog message
//                        alertDialogBuilder
//                                .setMessage(getContext().getString(R.string.invalid_api_key))
//                                .setCancelable(false)
//                                .setPositiveButton(getContext().getString(R.string.exit),new DialogInterface.OnClickListener() {
//                                    public void onClick(DialogInterface dialog,int id) {
//                                        // if this button is clicked, close
//                                        // current activity
//                                        Intent intent = new Intent(getContext(), MainActivity.class);
//                                        intent.addFlags(FLAG_ACTIVITY_CLEAR_TOP);
//                                        getContext().startActivity(intent);
//                                    }
//                                });
//
//                        Activity activity = getActivity();
//                        activity.runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                // create alert dialog
//                                AlertDialog alertDialog = alertDialogBuilder.create();
//
//                                // show it
//                                alertDialog.show();
//                            }
//                        });
//
//                    }
//                }
                InputStream is = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if(is==null){
                    return null;
                }

                reader = new BufferedReader(new InputStreamReader(is));
                String line;
                while((line=reader.readLine())!=null){
                    buffer.append(line+"\n");           //for making debugging easy we add newline
                }
                if(buffer.length()==0){
                    //Stream is empty
                    return null;
                }

                datahtml = buffer.toString();
                Log.v(LOG, datahtml);
            }
            catch (IOException e){
                e.printStackTrace();
            }finally {
                if(urlConnection!=null){
                    urlConnection.disconnect();
                }
                if(reader!=null){
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            return getDataFromHtml(datahtml);


        }
        public ArrayList<Data> getDataFromHtml(String html){
            ArrayList<Data> result = new ArrayList<>();
            Document doc = Jsoup.parseBodyFragment(html);
            Element body = doc.body();
            Elements table = body.select("table");
            Elements tr = table.select("tr");
            for(Element row: tr){
                Elements td = row.select("td");
                int count = 0;
                String val = "";
                String date = "";
                for(Element d: td){
                    if(count==0){
                        val = d.text();
                    }
                    if(count==1){
                        date = d.text();
                    }
                    count++;
                    Log.v(LOG, d.text());
                }

//                Element val = td.get(0);
//                Log.v(LOG, val.text());
                if(!val.equals("")) {
                    Data data = new Data(
                            val,
                            date
                    );
                    result.add(data);
                }
            }
            return result;
        }

        @Override
        protected void onPostExecute(ArrayList<Data> datas) {
            values = datas;
            super.onPostExecute(datas);
        }
    }
}

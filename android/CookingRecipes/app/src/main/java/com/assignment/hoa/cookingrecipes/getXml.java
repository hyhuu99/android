package com.assignment.hoa.cookingrecipes;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.Xml;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

/**
 * Created by hoa on 3/13/17.
 */

public class getXml extends AsyncTask<String, Void, String> {
    Context mcontext;
    RecyclerView rc_lowcarb;
    public  ArrayList<Items> items = new ArrayList<>();
    public getXml(Context mcontext,RecyclerView rc_lowcarb) {
        this.mcontext = mcontext;
        this.rc_lowcarb = rc_lowcarb;
    }
    private static final String ns = null;
    @Override
    protected String doInBackground(String... params) {
        try {
            String site = params[0];
            URL url = new URL(site);
            URLConnection conn = url.openConnection();
            conn.setConnectTimeout(15000);
            conn.setDoInput(true);
//                conn.setDoOutput(true);
            InputStream inputStream = conn.getInputStream();

//
//                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));
//                StringBuilder builder = new StringBuilder();
//                String line;
//
//                while ( (line =reader.readLine()) != null){
//
//                    builder.append(line).append("\n");
//
//                }
//                inputStream.close();
//                return builder.toString();

            return parseXML(new InputStreamReader(inputStream));


        } catch (XmlPullParserException |IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Toast.makeText(mcontext, "success", Toast.LENGTH_SHORT).show();
//            t.setText(s);
//        adapter.notifyDataSetChanged();

        RcAdapter adapter = new RcAdapter(items,mcontext);
        rc_lowcarb.setAdapter(adapter);
        rc_lowcarb.setLayoutManager(new LinearLayoutManager(mcontext));
    }
    private String parseXML(InputStreamReader in)throws XmlPullParserException, IOException{

        try {
            try {
                XmlPullParser parser = Xml.newPullParser();
                parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                parser.setInput(in);
                parser.nextTag();

                while (parser.next() != XmlPullParser.END_TAG) {
                    if (parser.getEventType() != XmlPullParser.START_TAG) {
                        continue;
                    }
                    String name = parser.getName();
                    Log.i("TAG", "");
                    // Starts by looking for the entry tag
                    if (name.equals("item")) {
                        items.add(readItem(parser));
                    } else {
                        skip(parser);
                    }
                }
            } finally {
                in.close();
            }

        }catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }

            return "";
    }

    private Items readItem(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, "item");
        String title = "",link = "",img = "",caption = "",text="";
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("title")) {
                title = parser.getText();
                Log.i("TAG", "" + title);
            } else if (name.equals("link")) {
                link = parser.getText();
                Log.i("TAG", "" + link);
            } else if (name.equals("description")) {
                text = parser.getText();
                img=text.substring(text.indexOf("src=\"") +5,text.indexOf("jpg\"")+3);
                Log.i("TAG", "" + img);
                caption=text.substring(0,text.indexOf(".&lt"));
                Log.i("TAG", "" + caption);
            }
        }
        return new Items(img,title,link,caption);
    }

    private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }


}
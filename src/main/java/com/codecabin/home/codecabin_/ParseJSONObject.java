package com.codecabin.home.codecabin_;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class ParseJSONObject extends Activity {

    private static final String TAG ="ParseJSONObject";
    private final String EXTRA_JSON_OBJECT_INDEX = "com.codecabin.home.codecabin_";

    private Post mMobile;
    private Comments mComments;
    private TextView nameTextView;
    private TextView companyNameTextView;
    private TextView operatingSystemTextView;
    private TextView processorTextView;
    private TextView backCameraTextView;

    String url = "http://codecabin.co.za/headquarters/wp-json/wp/v2/posts";
    String url_new = "http://codecabin.co.za/headquarters/wp-json/wp/v2/comments";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parsejsonobject);

        nameTextView =(TextView)findViewById(R.id.edit_text_name);

        final   ProgressDialog pDialog = new ProgressDialog(ParseJSONObject.this);
        pDialog.setMessage("Loading Posts...");
        pDialog.show();
        // Displaying Posts
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Method.GET,url, null,new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                mMobile = JSONParser.parseFeed(response);
                nameTextView.setText("Title :" + mMobile.getTitle());
                //companyNameTextView.setText("Date :" + mMobile.getDate());
                operatingSystemTextView.setText(" Modified :" + mMobile.getModified());
                processorTextView.setText("Content :" + mMobile.getContent());
                backCameraTextView.setText("Link :"+mMobile.getLink());


                Log.d(TAG, response.toString());
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                // hide the progress dialog
                pDialog.hide();
            }
        });

        JsonObjectRequest jsonObjReq1 = new JsonObjectRequest(Method.GET,url_new, null,new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                mComments = JSONParser.parseFeedComment(response);
                nameTextView.setText(mComments.getTitle());
                Log.d(TAG, response.toString());
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                // hide the progress dialog when finished
                pDialog.hide();
            }
        });

        // Adding request to request queue
        Volley.newRequestQueue(getApplicationContext()).add(jsonObjReq);
        Volley.newRequestQueue(getApplicationContext()).add(jsonObjReq1);
    }
}
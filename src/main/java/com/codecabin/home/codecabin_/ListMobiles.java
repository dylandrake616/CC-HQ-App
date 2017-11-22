package com.codecabin.home.codecabin_;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import java.util.ArrayList;
import android.app.ListFragment;
import android.text.Html;
import org.sufficientlysecure.htmltextview.HtmlTextView;
import android.widget.TextView;
import org.json.JSONObject;
import org.json.JSONException;

public class ListMobiles extends ListFragment {
    private final String TAG = "ListMobiles";
    private ArrayList<Post>  mMobileList;
    private ArrayList<Comments>  mCommentList;
    String url = "http://codecabin.co.za/headquarters/wp-json/wp/v2/posts";
    String url_new = "http://codecabin.co.za/headquarters/wp-json/wp/v2/comments";
    private final String EXTRA_JSON_OBJECT = "mobileObject";
    private final String EXTRA_JSON_OBJECT_COMMENT = "commentObject";


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        final   ProgressDialog pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Loading Posts...");
        pDialog.show();

        JsonArrayRequest jsonArrayReq = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG,response.toString());
                        Log.d(TAG,"Len "+response.length());
                        mMobileList = JSONParser.parseArrayFeed(response);

                        pDialog.hide();
                        MobileAdapter adapter = new MobileAdapter(mMobileList);
                        setListAdapter(adapter);
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                // hide the progress dialog
                pDialog.hide();
            }
        });

        JsonArrayRequest jsonArrayReq1 = new JsonArrayRequest(url_new,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG,response.toString());
                        Log.d(TAG,"Len "+response.length());
                        mCommentList = JSONParser.parseArrayFeedComment(response);
                        pDialog.hide();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                // hide the progress dialog
                pDialog.hide();
            }
        });

        // Adding request to request queue
        Volley.newRequestQueue(getActivity()).add(jsonArrayReq1);
        Volley.newRequestQueue(getActivity()).add(jsonArrayReq);

    }

    private class MobileAdapter extends ArrayAdapter<Post> {
        public MobileAdapter(ArrayList<Post> mobiles) {
            super(getActivity(), 0, mobiles);
        }
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            // If we weren't given a view, inflate one

            Log.d(TAG,"pos "+position);
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater().inflate(R.layout.category_list_item_1, null);
            }

            // This is where the CodeCabin_ HQ post list is displayed
            Post c = mMobileList.get(position);
            String id = c.getID();
            TextView nameTextView = (TextView) convertView.findViewById(R.id.textview_name);
            TextView dateTextView = (TextView) convertView.findViewById(R.id.textview_date);
            //TextView excerptTextView = (TextView) convertView.findViewById(R.id.excerpt_view);
            HtmlTextView excerptTextView = (HtmlTextView) convertView.findViewById(R.id.excerpt_view);
            LinearLayout post_item = (LinearLayout) convertView.findViewById(R.id.post_item);

            String title = c.getTitle();
            title = Html.fromHtml(title).toString();
            nameTextView.setText(title);
            String date = c.getDate();
            date = date.replaceAll("T", " - ");
            dateTextView.setText("Published: "+date);
            String html_excerpt = c.getExcerpt();
            excerptTextView.setHtml(html_excerpt);
            post_item.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    JSONArray commentsArray = new JSONArray(mCommentList);
                    Post current_mobile = mMobileList.get(position);

                    JSONArray commentArray = new JSONArray();
                    try {
                        String id = current_mobile.getID();
                        for(int i = 0; i < mCommentList.size(); i++) {
                            Comments comment = mCommentList.get(i);

                            // Comment Object
                            String comment_id = comment.getID();
                            JSONObject list1 = new JSONObject();
                            if (comment_id.equals(id)) {
                                list1.put("author",comment.getTitle());
                                list1.put("post_id",comment.getID());
                                list1.put("content",comment.getContent());
                                list1.put("date",comment.getDate());
                                commentArray.put(list1);
                            }
                        }
                    } catch (JSONException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }

                    Intent i = new Intent(getActivity(),ParseJSONArrayObject.class);
                    Bundle args = new Bundle();
                    //args.putSerializable(EXTRA_JSON_MOBILE_OBJECT, mMobileList.get(position));
                    i.putExtra(EXTRA_JSON_OBJECT, mMobileList.get(position));
                    i.putExtra(EXTRA_JSON_OBJECT_COMMENT, commentArray.toString());
                    startActivity(i);
                }
            });
            return convertView;
        }
    }
}
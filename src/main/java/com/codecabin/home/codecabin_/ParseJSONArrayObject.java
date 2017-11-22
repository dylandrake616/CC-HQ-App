package com.codecabin.home.codecabin_;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.app.Activity;
import android.widget.ImageView;
import android.widget.TextView;
import android.text.Html;
import org.json.JSONArray;
import org.json.JSONObject;
import org.sufficientlysecure.htmltextview.HtmlTextView;
import org.json.JSONException;

public class ParseJSONArrayObject extends Activity {
    private static final String TAG ="ParseJSONObject";
    private Post mPosts;
    private String jsonArray;
    private TextView nameTextView;
    private TextView postDateTextView;
    private ImageView photoImageView;
    private String photoUrl;
    private final String EXTRA_JSON_OBJECT = "mobileObject";
    private final String EXTRA_JSON_OBJECT_COMMENT = "commentObject";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parsejsonobject);

        mPosts = (Post)getIntent().getSerializableExtra(EXTRA_JSON_OBJECT);
        jsonArray = getIntent().getStringExtra(EXTRA_JSON_OBJECT_COMMENT);
        nameTextView =(TextView)findViewById(R.id.edit_text_name);
        postDateTextView =(TextView)findViewById(R.id.edit_text_date);
        photoImageView = (ImageView)findViewById(R.id.image_view_mobile_picture);
        HtmlTextView htmlTextView = (HtmlTextView) findViewById(R.id.content_html);
        HtmlTextView htmlCommentView = (HtmlTextView) findViewById(R.id.comments_html);

        final ProgressDialog pDialog = new ProgressDialog(ParseJSONArrayObject.this);
        pDialog.setMessage("Loading...");

        // Displaying Posts
        String title =  mPosts.getTitle();
        title = Html.fromHtml(title).toString();
        nameTextView.setText(title);

        try {
            JSONArray commentArray = new JSONArray(jsonArray);
            String comment_content = new String();
            for (int i = 0; i < commentArray.length(); i++) {
                JSONObject comment = commentArray.getJSONObject(i);
                String author = new String();
                String date = new String();
                String content = new String();
                if (comment.has("author")) { author = comment.getString("author"); }
                if (comment.has("date")) { date = comment.getString("date"); }
                if (comment.has("content")) { content = comment.getString("content"); }
                date = date.replaceAll("T", " - ");
                comment_content = comment_content+"<br><b>"+author+"</b> - "+date+content+"<br><hr>";
            }
            //commentTextView.setText(comment_content);
            if(comment_content != null && !comment_content.isEmpty()) {
                htmlCommentView.setHtml(comment_content); }
            else {
                htmlCommentView.setHtml("There aren't any comments for this post...");
            }
        } catch (JSONException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        String date = mPosts.getDate();
        date = date.replaceAll("T", " - ");
        postDateTextView.setText("Published: "+date);
        String content = mPosts.getContent();
        htmlTextView.setHtml(content);
    }
}
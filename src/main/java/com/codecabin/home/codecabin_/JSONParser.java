package com.codecabin.home.codecabin_;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class JSONParser {
    public static ArrayList<Post> mMobiles = new ArrayList<>();
    public static ArrayList<Comments> mComments = new ArrayList<>();
    public static Post parseFeed(JSONObject obj) {
        try {
            Post mobile = new Post();
            JSONObject title = obj.getJSONObject("title");
            mobile.setTitle(title.getString("rendered"));
            mobile.setDate(obj.getString("date"));
            mobile.setModified(obj.getString("modified"));
            mobile.setExcerpt(obj.getString("excerpt"));
            mobile.setLink(obj.getString("link"));
            // Post Content
            JSONObject content = obj.getJSONObject("content");
            mobile.setContent(content.getString("rendered"));
            return mobile;
        } catch (JSONException e1) {
            e1.printStackTrace();
            return null;
        }
    }
    public static Comments parseFeedComment(JSONObject obj) {
        try {
            Comments comment = new Comments();

            comment.setTitle(obj.getString("author_name"));
            comment.setDate(obj.getString("date"));
            comment.setModified(obj.getString("modified"));
            comment.setExcerpt(obj.getString("excerpt"));
            comment.setLink(obj.getString("link"));
            // Post Content
            JSONObject content = obj.getJSONObject("content");
            comment.setContent(content.getString("rendered"));
            return comment;
        } catch (JSONException e1) {
            e1.printStackTrace();
            return null;
        }
    }
    // Posts Array JSON
    public static ArrayList<Post> parseArrayFeed(JSONArray arr) {
        JSONObject obj=null;
        Post mobile = null;
        mMobiles.clear();
        try {
            for(int i = 0;i<arr.length();i++) {
                obj = arr.getJSONObject(i);
                mobile= new Post();
                JSONObject title = obj.getJSONObject("title");
                mobile.setTitle(title.getString("rendered"));
                mobile.setDate(obj.getString("date"));
                mobile.setID(obj.getString("id"));
                mobile.setModified(obj.getString("modified"));
                JSONObject excerpt = obj.getJSONObject("excerpt");
                mobile.setExcerpt(excerpt.getString("rendered"));
                mobile.setLink(obj.getString("link"));
                JSONObject content = obj.getJSONObject("content");
                mobile.setContent(content.getString("rendered"));
                mMobiles.add(mobile);
            }
            return mMobiles;
        } catch (JSONException e1) {
            e1.printStackTrace();
            return null;
        }
    }

    public static ArrayList<Comments> parseArrayFeedComment(JSONArray arr) {
        JSONObject obj=null;
        Comments comment = null;
        mComments.clear();
        try {

            for(int i = 0;i<arr.length();i++) {
                obj = arr.getJSONObject(i);
                comment = new Comments();
                comment.setTitle(obj.getString("author_name"));
                comment.setDate(obj.getString("date"));
                comment.setID(obj.getString("post"));
                JSONObject content = obj.getJSONObject("content");
                comment.setContent(content.getString("rendered"));
                mComments.add(comment);
            }
            return mComments;
        } catch (JSONException e1) {
            e1.printStackTrace();
            return null;
        }
    }
}
package com.codecabin.home.codecabin_;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;

public class JSONParseActivity extends Activity {
    private final String EXTRA_JSON_OBJECT_INDEX = "com.codecabin.home.codecabin_";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = new Intent(getApplication(), ParseJSONArray.class);
        startActivity(i);
    }
}
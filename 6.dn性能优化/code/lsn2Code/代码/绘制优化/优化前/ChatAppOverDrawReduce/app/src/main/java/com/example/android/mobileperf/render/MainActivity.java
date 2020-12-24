/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.mobileperf.render;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * The main activity for the part of the course dealing with rendering performance. Works
 * simply as a table of contents: surfaces buttons that launch the ChatumLatinum and DroidCards
 * activities.
 */
public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }


    public void listDraw(View view) {
        Intent intent=new Intent(MainActivity.this,ChatumLatinumActivity.class);
        startActivity(intent);
    }

    public void viewDraw(View view) {
        Intent intent=new Intent(MainActivity.this,DroidCardsActivity.class);
        startActivity(intent);
    }
}
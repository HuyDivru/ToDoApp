package com.example.todo.base;

import androidx.annotation.CallSuper;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

import com.example.todo.R;
import com.example.todo.dagger.Injectable;

import butterknife.BindView;
import butterknife.ButterKnife;

public abstract class PrimaryActivity extends AppCompatActivity  implements PrimaryView, Injectable {

    @BindView(R.id.toolbar)
    protected Toolbar mToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());
        ButterKnife.bind(this);
    }

    @CallSuper
    protected void initializeViews(){
        setSupportActionBar(mToolbar);
    }
}
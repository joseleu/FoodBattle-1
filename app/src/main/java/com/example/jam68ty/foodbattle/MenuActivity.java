package com.example.jam68ty.foodbattle;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class MenuActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private BottomNavigationView mMainNav;
    private FrameLayout mMainFrame;
    private MenuFragment menuFragment;
    private AccountFragment accountFragment;
    private MarketFragment marketFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        firebaseAuth = FirebaseAuth.getInstance();
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null) {
                    Intent i = new Intent(MenuActivity.this, LoginActivity.class);
                    startActivity(i);
                }
            }

        };
        mMainNav = findViewById(R.id.main_nav);
        mMainFrame = findViewById(R.id.main_fragment);

        menuFragment = new MenuFragment();
        accountFragment = new AccountFragment();
        marketFragment = new MarketFragment();
        setFragment(menuFragment);

        mMainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.nav_menu:
                        setFragment(menuFragment);
                        return true;

                    case R.id.nav_account:
                        setFragment(accountFragment);
                        return true;

                    case R.id.nav_market:
                        setFragment(marketFragment);
                        return true;

                    default:
                        return false;
                }
            }


        });

    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_fragment, fragment);
        fragmentTransaction.commit();
    }

    public void Sign_out_Btn(View i) {
        Toast.makeText(this, "登出中...", Toast.LENGTH_LONG).show();
        signOut();
        firebaseAuth.addAuthStateListener(mAuthStateListener);
        if (firebaseAuth.getCurrentUser() == null) {
            this.finish();
        }

    }

    private void signOut() {
        firebaseAuth.signOut();
    }

}

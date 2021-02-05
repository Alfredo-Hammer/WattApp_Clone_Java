package com.hammer67.watsappclone.activities.utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.view.ViewAnimationUtils;

import androidx.appcompat.widget.SearchView;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AnimUtils {

    public static void showFabAddEstado(int positionTab,
                                        FloatingActionButton floatingActionButton,
                                        Context context) {

        floatingActionButton.animate().translationY(positionTab == 2 || positionTab == 3 ?
                PixelsDPUtils.convertPixelsToDp(-58, context) :
                PixelsDPUtils.convertPixelsToDp(0, context)).setDuration(150);

    }

    public static void showSearchView(SearchView searchView) {
        searchView.setVisibility(View.VISIBLE);

        Animator animator = ViewAnimationUtils.createCircularReveal(searchView,
                (searchView.getRight() + searchView.getLeft() / 2),
                (searchView.getTop() + searchView.getBottom() / 2),
                0f,
                searchView.getWidth());

        animator.setDuration(350);
        animator.start();
    }

    public static void hideSearchView(SearchView searchView, AppBarLayout appBarLayout) {
        Animator animator = ViewAnimationUtils.createCircularReveal(searchView,
                (searchView.getRight() + searchView.getLeft() / 2),
                (searchView.getTop() + searchView.getBottom() / 2),
                searchView.getWidth(),
                0f);

        animator.setDuration(300);
        animator.start();

        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                searchView.setVisibility(View.INVISIBLE);
                appBarLayout.setExpanded(true,true);
                animation.removeAllListeners();

            }
        });
    }


    public static void scaleFab(FloatingActionButton floatingActionButton){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                floatingActionButton.animate().scaleX(1.0f).scaleY(1.0f).setDuration(100);
            }
        },450);
    }

}

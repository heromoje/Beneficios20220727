package com.bcp.bo.discounts.layouts.menu;

import android.animation.ValueAnimator;
import android.content.res.Resources;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.bcp.bo.discounts.R;
import com.bcp.bo.discounts.adapters.CitiesAdapter;
import com.bcp.bo.discounts.base.BaseLayout;
import com.bcp.bo.discounts.base.BaseLayoutActivity;
import com.bcp.bo.discounts.layouts.discount.list.DscListLayout;
import com.bcp.bo.discounts.layouts.home.HomeLayout;

import java.util.ArrayList;
import java.util.List;

import bcp.bo.service.model.response.Category;
import bcp.bo.service.model.response.City;
import bcp.bo.service.model.response.PrivateToken;
import bcp.bo.service.model.response.Promotion;

public class MenuLeftLayout extends BaseLayout implements CitiesAdapter.ICitiesAdapter {

    private static final String KEY_CATEGORY_ID_SELECTED = "CATEGORY_ID_SELECTED";

    // views
    private TextView textView_HeadTitle;
    private TextView textView_HeadSubTitle;
    private TextView textView_AmountSaved;
    private ListView listView_Menu;
    private ImageButton imageButton_Close;
    private View view_City;
    private View view_Expandable;
    private DscListLayout listLayout = null;
    private Category _category;
    private List<Promotion> _promotions = new ArrayList<Promotion>();
    private boolean _isTopPromotions = true;
    private static final String LIST_CITIES = "LIST_CITIES";
    private static final String LIST_PROMOTIONS = "LIST_PRODUCTS";
    private static final String CATEGORY = "CATEGORY";
    private static final String ISTOPPROMOTIONS = "TOPPROMOTIONS";
//    private RecyclerView recyclerView_Cities;

    //observers to refresh list
    private List<DataSetObserver> observers = new ArrayList<>();

    //CitiesView
    private int originalHeight = 0;
    private boolean isExpanded;

    //category selected in the list
    private int categoryIdSelected = 0;

    private List<City> _cities;
    private List<Category> _categories;
    private PrivateToken _privateToken;
    private String _document;
    private String _amountSaved;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recycleView_List;

    public MenuLeftLayout(BaseLayoutActivity baseLayoutActivity, List<City> cities, PrivateToken privateToken, String document, String amountSaved) {
        super(baseLayoutActivity);
        _cities = cities;
        // _categories = categories;
        _privateToken = privateToken;
        _document = document;
        _amountSaved = amountSaved;
    }

    @Override
    protected int getXMLToInflate() {
        return R.layout.lay_menu_left;
    }

    @Override
    protected void initViews() {
        textView_HeadTitle = (TextView) view.findViewById(R.id.textView_HeadTitle);
        textView_HeadSubTitle = (TextView) view.findViewById(R.id.textView_HeadSubTitle);
        textView_AmountSaved = (TextView) view.findViewById(R.id.textView_AmountSaved);
        imageButton_Close = (ImageButton) view.findViewById(R.id.imageButton_Close);
        listView_Menu = (ListView) view.findViewById(R.id.listView_Menu);
        view_City = view.findViewById(R.id.view_city);
        //view_Expandable = view.findViewById(R.id.expandable);
//        recyclerView_Cities = (RecyclerView) view.findViewById(R.id.recyclerView_Cities);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getBaseLayoutActivity(), LinearLayoutManager.VERTICAL, false);
        recycleView_List = (RecyclerView) view.findViewById(R.id.recycleView_List);
        recycleView_List.setLayoutManager(mLayoutManager);
    }

    @Override
    protected void initBaseLayouts() {

    }

    @Override
    protected void initValues() {
        textView_HeadTitle.setText(_privateToken.FullName);
        textView_HeadSubTitle.setText(_document);
        textView_AmountSaved.setText(_amountSaved + " Bs.");
        //listView_Menu.setAdapter(menLeftAdapter);
//        recyclerView_Cities.setAdapter(new CitiesAdapter(this, _cities));
    }

    @Override
    protected void initListeners() {
        imageButton_Close.setOnClickListener(clickListener);
       // listView_Menu.setOnItemClickListener(itemClickListener);
        view_City.setOnClickListener(clickListener);
    }

    @Override
    protected void init() {
    }

    public int getCategoryIdSelected() {
        return categoryIdSelected;
    }

    @Override
    public void click(View view) {
        final int id = view.getId();
        HomeLayout homeLayout = (HomeLayout) findBaseLayoutParentByName(HomeLayout.class);
        switch (id) {
            case R.id.imageButton_Close:
                homeLayout.click(view);
                break;
            case R.id.view_city:
//                BaseActivity.publicPrivateTokenCIC.City = city.Code;
                homeLayout.showCities();
//                expandCities();
                break;
        }
    }
    public void loadCities(List<City> cities) {
        Resources r = BaseActivity.getResources();
        int pxTop = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 32, r.getDisplayMetrics());
        _category = null;
        _isTopPromotions = true;
        _cities = cities;
        recycleView_List.setPadding(0, pxTop, 0, 0);
        recycleView_List.setAdapter(new CitiesAdapter(this, _cities));
    }

    private void expandCities() {
        if (originalHeight == 0) {
//            originalHeight = recyclerView_Cities.getMeasuredHeight();
        }
//        originalHeight = recyclerView_Cities.getMeasuredHeight();
        originalHeight = 300;
        Log.d("SIZE", "" + originalHeight);
        ValueAnimator valueAnimator;
        if (!isExpanded) {
            isExpanded = true;
            valueAnimator = ValueAnimator.ofInt(0, originalHeight);
            Animation animation = new AlphaAnimation(0.00f, 1.00f);
            animation.setDuration(200);
            view_Expandable.startAnimation(animation);
        } else {
            isExpanded = false;
            valueAnimator = ValueAnimator.ofInt(originalHeight, 0);
            Animation animation = new AlphaAnimation(1.00f, 0.00f);
            animation.setDuration(200);
            view_Expandable.startAnimation(animation);
        }
        valueAnimator.setDuration(200);
        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                view_Expandable.getLayoutParams().height = (Integer) animation.getAnimatedValue();
                view_Expandable.requestLayout();
            }
        });
        valueAnimator.start();
    }

    @Override
    protected void handleMessageReceived(int what, int arg1, int arg2,
                                         Object obj) {

    }

    @Override
    protected void dataReceived(Bundle extras) {
        if (extras.containsKey(KEY_CATEGORY_ID_SELECTED)) {
            categoryIdSelected = extras.getInt(KEY_CATEGORY_ID_SELECTED);
        }
    }

    @Override
    protected void dataToSave(Bundle extras) {
        extras.putInt(KEY_CATEGORY_ID_SELECTED, categoryIdSelected);
        // save the category
        extras.putSerializable(LIST_CITIES, new ArrayList<>(_cities));
        extras.putSerializable(CATEGORY, _category);
        extras.putSerializable(LIST_PROMOTIONS, new ArrayList<>(_promotions));
        extras.putBoolean(ISTOPPROMOTIONS, _isTopPromotions);
    }

    @Override
    protected void dataToReturn(Bundle extras) {
    }

    //--------------------------- LISTENERS ----------------------

    @Override
    protected boolean keyPressed(int keyCode, KeyEvent event) {

        return false;
    }

    @Override
    public boolean unloadLayoutResult(Bundle data) {

        return false;
    }

    // -------------------------- ADAPTER -------------------------

    private void notifyObservers() {
        for (DataSetObserver observer : observers) {
            observer.onChanged();
        }
    }

    @Override
    public void onSelectCity(City city) {
        BaseActivity.publicPrivateTokenCIC.City = city.Code;
//        getBaseLayoutActivity().setLoadingMessage(R.string.LOADING_CATEGORY);
//        SLauncher.loadCategories(BaseActivity.publicPrivateTokenCIC, this);

        HomeLayout homeLayout = (HomeLayout) findBaseLayoutParentByName(HomeLayout.class);
        homeLayout.loadCategories(false);
    }
}
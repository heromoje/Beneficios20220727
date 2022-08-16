package com.bcp.bo.discounts.layouts.discount;

import java.util.ArrayList;
import java.util.List;

import android.database.DataSetObserver;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bcp.bo.discounts.R;
import com.bcp.bo.discounts.base.BaseLayout;
import com.bcp.bo.discounts.base.BaseLayoutActivity;
import com.bcp.bo.discounts.layouts.discount.list.DscListLayout;
import com.bcp.bo.discounts.layouts.discount.map.DscMapLayout;
import com.bcp.bo.discounts.layouts.home.HomeLayout;
import com.bcp.bo.discounts.util.StringUtil;
import com.bcp.bo.discounts.views.CustomSliderLLayout;
import com.bcp.bo.discounts.views.CustomSliderLLayout.CustomSliderListener;

import bcp.bo.service.model.response.Category;
import bcp.bo.service.model.response.Contact;
import bcp.bo.service.model.response.Promotion;

public class DscHomeListMapLayout extends BaseLayout{
	private static final String DSC_HOME_SLIDER_OPENED = "DSC_HOME_SLIDER_OPENED";
	
	//reference to base layouts
	private DscListLayout listLayout = null;
	private DscMapLayout mapLayout = null;
	private List<Category> _categories;
	private List<DataSetObserver> observers = new ArrayList<>();
	private int categoryIdSelected = 0;
	private ListView listView_Menu;
	private GridView GridView_Menu;
	//custom slider for map
	private CustomSliderLLayout customMapSlider;
	/**
	 * Flag to know if slider is open or close when return from other layout.
	 * Close by default: init.
	 */
	private boolean customMapSliderOpened = false;
	/** Extras received, used to set map later **/
	private Bundle extrasReceived = null;

	private BaseAdapter DscHomeListMapAdapter = new BaseAdapter() {

		@Override
		@SuppressWarnings("deprecation")
		public View getView(int position, View convertView, ViewGroup parent) {
			final Category category = getItem(position);

			//get view
			if (convertView == null) {
				convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.lay_hom_menu_cat_item, parent, false);
			}

			LinearLayout llCategory = (LinearLayout) convertView.findViewById(R.id.llCategory);
			TextView tvName = (TextView) convertView.findViewById(R.id.textView_Name);
			//TextView tvCounter = (TextView) convertView.findViewById(R.id.textView_Counter);
			ImageView ivCategoryIcon = (ImageView) convertView.findViewById(R.id.imageView_CategoryIcon);

			tvName.setText(category.Name);
			//tvCounter.setText(String.valueOf(category.CountPromotion));

			String categoryName = StringUtil.removeDiacriticalMarks(category.Name).toUpperCase();
			if (categoryName.contains(Category.CATEGORY_RESTAURANTES)) {
				ivCategoryIcon.setBackgroundResource(R.drawable.cat_1);
			} else if (categoryName.contains(Category.CATEGORY_ROPA)) {
				ivCategoryIcon.setBackgroundResource(R.drawable.cat_2);
			} else if (categoryName.contains(Category.CATEGORY_ACCESORIOS)) {
				ivCategoryIcon.setBackgroundResource(R.drawable.cat_2);
			} else if (categoryName.contains(Category.CATEGORY_DIVERSION)) {
				ivCategoryIcon.setBackgroundResource(R.drawable.cat_3);
			} else {
				ivCategoryIcon.setBackgroundResource(R.drawable.cat_4);
			}

			// set selected or not
			if (categoryIdSelected == category.Id) {
				llCategory.setSelected(true);
			} else {
				llCategory.setSelected(false);
			}

			return convertView;
		}

		@Override
		public int getCount() {
			return _categories.size();
		}

		@Override
		public Category getItem(int position) {
			return _categories.get(position);
		}

		@Override
		public long getItemId(int position) {
			return _categories.get(position).Id;
		}

		@Override
		public int getItemViewType(int position) {
			return 1;
		}

		@Override
		public int getViewTypeCount() {
			return 1;
		}

		@Override
		public boolean hasStableIds() {
			return true;
		}

		@Override
		public boolean isEmpty() {
			return _categories.size() == 0;
		}

		@Override
		public void registerDataSetObserver(DataSetObserver observer) {
			observers.add(observer);
		}

		@Override
		public void unregisterDataSetObserver(DataSetObserver observer) {
			observers.remove(observer);
		}

		@Override
		public boolean areAllItemsEnabled() {
			return true;
		}

		@Override
		public boolean isEnabled(int arg0) {
			return true;
		}
	};

	private BaseAdapter DscHomeGridMapAdapter = new BaseAdapter() {

		@Override
		@SuppressWarnings("deprecation")
		public View getView(int position, View convertView, ViewGroup parent) {
			final Category category = getItem(position);

			//get view
			if (convertView == null) {
				convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.lay_hom_menu_cat_item, parent, false);
			}

			LinearLayout llCategory = (LinearLayout) convertView.findViewById(R.id.llCategory);
			TextView tvName = (TextView) convertView.findViewById(R.id.textView_Name);
			//TextView tvCounter = (TextView) convertView.findViewById(R.id.textView_Counter);
			ImageView ivCategoryIcon = (ImageView) convertView.findViewById(R.id.imageView_CategoryIcon);

			tvName.setText(category.Name);
			//tvCounter.setText(String.valueOf(category.CountPromotion));

			String categoryName = StringUtil.removeDiacriticalMarks(category.Name).toUpperCase();
			if (categoryName.contains(Category.CATEGORY_RESTAURANTES)) {
				ivCategoryIcon.setBackgroundResource(R.drawable.food_delivery);
			} else if (categoryName.contains(Category.CATEGORY_ROPA)) {
				ivCategoryIcon.setBackgroundResource(R.drawable.clothes);
			} else if (categoryName.contains(Category.CATEGORY_SUPERMERCADOS)) {
				ivCategoryIcon.setBackgroundResource(R.drawable.supermercados);
			} else if (categoryName.contains(Category.CATEGORY_ACCESORIOS)) {
				ivCategoryIcon.setBackgroundResource(R.drawable.ropa);
			} else if (categoryName.contains(Category.CATEGORY_DIVERSION)) {
				ivCategoryIcon.setBackgroundResource(R.drawable.cat_3);
			} else if (categoryName.contains(Category.CATEGORY_SALUD)) {
				ivCategoryIcon.setBackgroundResource(R.drawable.salud);
			} else if (categoryName.contains(Category.CATEGORY_HOGAR)) {
				ivCategoryIcon.setBackgroundResource(R.drawable.hogar);
			} /*else if (categoryName.contains(Category.CATEGORY_DELIVERY)) {
                ivCategoryIcon.setBackgroundResource(R.drawable.delivery);
            }*/ else if (categoryName.contains(Category.CATEGORY_CAFETERIA)) {
				ivCategoryIcon.setBackgroundResource(R.drawable.cafe);
			} else if (categoryName.contains(Category.CATEGORY_TECNOLOGIA)) {
				ivCategoryIcon.setBackgroundResource(R.drawable.electronicos);
			} else {
				ivCategoryIcon.setBackgroundResource(R.drawable.cat_4);
			}

			// set selected or not
			if (categoryIdSelected == category.Id) {
				llCategory.setSelected(true);
			} else {
				llCategory.setSelected(false);
			}

			return convertView;
		}

		@Override
		public int getCount() {
			return _categories.size();
		}

		@Override
		public Category getItem(int position) {
			return _categories.get(position);
		}

		@Override
		public long getItemId(int position) {
			return _categories.get(position).Id;
		}

		@Override
		public int getItemViewType(int position) {
			return 1;
		}

		@Override
		public int getViewTypeCount() {
			return 1;
		}

		@Override
		public boolean hasStableIds() {
			return true;
		}

		@Override
		public boolean isEmpty() {
			return _categories.size() == 0;
		}

		@Override
		public void registerDataSetObserver(DataSetObserver observer) {
			observers.add(observer);
		}

		@Override
		public void unregisterDataSetObserver(DataSetObserver observer) {
			observers.remove(observer);
		}

		@Override
		public boolean areAllItemsEnabled() {
			return true;
		}

		@Override
		public boolean isEnabled(int arg0) {
			return true;
		}
	};
	/**
	 * Menu item click listener.
	 */
	private AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
			//get the category selected
			Object object = DscHomeListMapAdapter.getItem(position);
			if (object instanceof Category) {
				Category category = (Category) object;
				categoryIdSelected = category.Id;

				//refresh category
				HomeLayout homeLayout = (HomeLayout) findBaseLayoutParentByName(HomeLayout.class);
				homeLayout.loadCategory(category);
				GridView_Menu.setVisibility(View.INVISIBLE);

				//refresh menu
				notifyObservers();
				Log.d("SELECTED", category.Name);
			}
			Log.d("SELECTED", "ITEM");
		}
	};
	private void notifyObservers() {
		for (DataSetObserver observer : observers) {
			observer.onChanged();
		}
	}




	public DscHomeListMapLayout(BaseLayoutActivity baseLayoutActivity, List<Category> categories) {
		super(baseLayoutActivity);
		_categories = categories;
	}

	@Override
	protected int getXMLToInflate() {
		return R.layout.lay_dsc_home_listmap;
	}

	@Override
	protected void initViews() {
		customMapSlider = (CustomSliderLLayout) view.findViewById(R.id.csllMap);
		//customMapSlider.setPullView(R.drawable.dsc_list_handle, FrameLayout.LayoutParams.MATCH_PARENT);
		
		//create view map
		mapLayout = new DscMapLayout(getBaseLayoutActivity(), this);
		mapLayout.resume(extrasReceived);
		
		//remove content view if there was one
		customMapSlider.removeContentView();
		
		if(customMapSliderOpened && customMapSlider.getContentView() == null){
			customMapSlider.setContentView(mapLayout.view, FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
		}
		GridView_Menu = (GridView) view.findViewById(R.id.GridView_Menu);

	}

	@Override
	protected void initBaseLayouts() {
		//List
		listLayout = new DscListLayout(getBaseLayoutActivity());
		loadLayout(R.id.vDscList, listLayout, true);
	}

	@Override
	protected void initValues() {
		//configure customMapSlider for version of Android
		if(Build.VERSION.SDK_INT<14){
			customMapSlider.setAnimatedTouch(false);
			customMapSlider.setAnimated(false);
		}
		
		//get reference to list layout
		listLayout = (DscListLayout) findBaseLayoutSonByName(DscListLayout.class);
		
		//check if slider has to be opened
		if(customMapSliderOpened){
			customMapSlider.forceOpen(false);
		}
		GridView_Menu.setAdapter(DscHomeGridMapAdapter);
	}

	@Override
	protected void initListeners() {
		// Captures sliding events
		customMapSlider.setCustomSliderListener(customSliderListener);
		GridView_Menu.setOnItemClickListener(itemClickListener);
	}

	@Override
	protected void init() {
		
		//restore view for this one
		HomeLayout homeLayout = (HomeLayout) findBaseLayoutParentByName(HomeLayout.class);
		homeLayout.restoreView(this);
		
	}
	
	@Override
	protected void onDestroy(){

		//call to map because it is not added to stack
		mapLayout.destroy();
		mapLayout = null;
		
		super.onDestroy();
	}

	@Override
	public void click(View view) {

	}

	@Override
	protected void handleMessageReceived(int what, int arg1, int arg2, Object obj) {
		
	}

	@Override
	protected void dataReceived(Bundle extras) {
		customMapSliderOpened = extras.getBoolean(DSC_HOME_SLIDER_OPENED);
		
		//save extras to set map later
		extrasReceived = extras;
	}

	@Override
	protected void dataToSave(Bundle extras) {
		extras.putBoolean(DSC_HOME_SLIDER_OPENED, customMapSliderOpened);
		
		mapLayout.loadDataToSave(extras);
	}

	@Override
	protected void dataToReturn(Bundle extras) {
		
	}
	
	@Override
	protected boolean keyPressed(int keyCode, KeyEvent event) {

		return false;
	}

	@Override
	public boolean unloadLayoutResult(Bundle data) {

		return false;
	}
	
	//----------- LOAD DATA --------------

	public void loadPromotionsTop(List<Promotion> promotions,List<Contact> contacts){
		//load the list of discounts
		listLayout.loadPromotionsTop(promotions);
		mapLayout.loadProducts(promotions);


		HomeLayout homeLayout = (HomeLayout) findBaseLayoutParentByName(HomeLayout.class);
		homeLayout.setHomeBar(getBaseLayoutActivity().getString(R.string.DSC_VIEW_OFFERS_FROM_),null);
	}
	public void loadPromotions(Category category, List<Promotion> promotions,List<Contact> contacts){
		//load the list of discounts
		listLayout.loadPromotions(category, promotions);
    	mapLayout.loadProducts(promotions);


		HomeLayout homeLayout = (HomeLayout) findBaseLayoutParentByName(HomeLayout.class);
		homeLayout.setHomeBar(category.Name,null);
	}
	public void reloadCategories(List<Category> categories) {
		GridView_Menu.setVisibility(View.INVISIBLE);
		_categories = categories;
		DscHomeListMapAdapter.notifyDataSetChanged();
		notifyObservers();
	}
	/**
	 * Load detail in home layout
	 * @param promotion
	 * @param promotions
	 */
	public void loadProductDetail(Promotion promotion, List<Promotion> promotions){
		//call to home layout with this discount
		HomeLayout homeLayout = (HomeLayout) findBaseLayoutParentByName(HomeLayout.class);
		homeLayout.showPromotion(promotion,promotions);
	}

	//------------- SLIDER ---------------
	
	/**
	 * Listen for slider changes.
	 */
	private final CustomSliderListener customSliderListener = new CustomSliderListener(){
		@Override
		public void onSliderAction(int action, int moment) {
			
			if(action == CustomSliderLLayout.ACTION_OPEN) {
				
				if(moment==CustomSliderLLayout.MOMENT_START) {
					if(customMapSlider.getContentView() == null) {
						customMapSlider.setContentView(mapLayout.view, FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
					}
				}	
								
				customMapSliderOpened = true;
				
			} else {		
				customMapSliderOpened = false;
				
			}
			
		}	
	};
}

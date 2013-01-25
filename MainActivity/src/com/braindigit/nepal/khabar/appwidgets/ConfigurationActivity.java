package com.braindigit.nepal.khabar.appwidgets;

import static android.appwidget.AppWidgetManager.EXTRA_APPWIDGET_ID;
import static android.appwidget.AppWidgetManager.INVALID_APPWIDGET_ID;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import android.app.Activity;
import android.app.ProgressDialog;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProviderInfo;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.braindigit.nepal.khabar.R;

public class ConfigurationActivity extends Activity {
	private RadioGroup categoryListingRadioGroup;
	private ScrollView categoryScrollView;

	private RadioGroup.LayoutParams radioGroupsParamsBasic;
	private LinkedHashMap<String, String> newsCategoryAndLinkMap;

	int margin_2, margin_4, margin_10, count = 0;
	private ProgressDialog progressDialog;
	private String selectedCategory, selectedSource, selectedLanguage,
			sourceAndLanguage;

	public static final String NEWS_ARRAYLIST = "category";

	public static final String PREFS_NAME_CATEGORY = "category";
	public static final String PREFS_NAME_SOURCE = "source";
	public static final String PREFS_NAME_LANGUAGE = "language";
	public static final String PREFS_VALUE_CATEGORY = "";
	public static final String PREFS_VALUE_SOURCE = "";
	public static final String PREFS_VALUE_LANGUAGE = "";
	public static final String HAMRAKURA = "Hamrakura";
	public static final String KAROBAR = "Karobar";
	public static final String HOME = "Home";
	public static final String SPORTS = "Sports";
	public static final String ENTERTAINMENT = "Entertainment";

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_config_activity);
		setResult(RESULT_CANCELED);
		initListViews();
	}

	public void initListViews() {
		progressDialog = new ProgressDialog(this);
		// /
		categoryScrollView = (ScrollView) findViewById(R.id.configActivityCategoryLayout);
		categoryListingRadioGroup = (RadioGroup) findViewById(R.id.configActivityRadioGroup);
		margin_4 = convertPixelsToDp(4);
		margin_2 = convertPixelsToDp(2);
		margin_10 = convertPixelsToDp(10);
		Button okButton = (Button) findViewById(R.id.okButton);
		okButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				handleOkButton();
			}
		});
		getDataToFillInRadioGroup();
	}

	private void handleOkButton() {
		// initAppWidget();
		if (selectedCategory != null) {
			saveTheUserValueInPref(selectedCategory, sourceAndLanguage);
			showAppWidget();
		}

	}

	private void getDataToFillInRadioGroup() {

		LinkedHashMap<String, LinkedHashMap<String, String>> englishNewsList = new LinkedHashMap<String, LinkedHashMap<String, String>>();
		englishNewsList.put(HAMRAKURA, new LinkedHashMap<String, String>() {
			{
				// put("गृहपृष्ठ", "http://www.hamrakura.com/home-page/feed");
				put("समसामयिक कुरा",
						"http://www.hamrakura.com/category/current-affairs/feed");
				put("बिशेष कुरा",
						"http://www.hamrakura.com/category/hamrakura-exclusive/feed");
				put("मनोरन्जनका कुरा",
						"http://www.hamrakura.com/category/%e0%a4%ae%e0%a4%a8%e0%a5%8b%e0%a4%b0%e0%a4%a8%e0%a5%8d%e0%a4%9c%e0%a4%a8/feed");
				put("अर्थका कुरा",
						"http://www.hamrakura.com/category/finance/feed");
				put("प्रबिधिका कुरा",
						"http://www.hamrakura.com/category/it/feed");
				put("पर्यटनका कुरा",
						"http://www.hamrakura.com/category/tourism/feed");
				put("खेलकुदका कुरा",
						"http://www.hamrakura.com/category/sports/feed");
				put("प्रवासका कुरा",
						"http://www.hamrakura.com/category/prawas/feed");
				put("विविध कुरा", "http://www.hamrakura.com/category/misc/feed");
			}
		});

		englishNewsList.put(KAROBAR, new LinkedHashMap<String, String>() {
			{
				put("गृहपृष्ठ", "http://karobardaily.com/blog/rss/all.rss");
				put("प्रमुख समाचार",
						"http://karobardaily.com/blog/rss/%E0%A4%AA%E0%A5%8D%E0%A4%B0%E0%A4%AE%E0%A5%81%E0%A4%96-%E0%A4%B8%E0%A4%AE%E0%A4%BE%E0%A4%9A%E0%A4%BE%E0%A4%B0.rss");
				put("देश",
						"http://karobardaily.com/blog/rss/%E0%A4%A6%E0%A5%87%E0%A4%B6.rss");
				put("अर्थ",
						"http://karobardaily.com/blog/rss/%E0%A4%85%E0%A4%B0%E0%A5%8D%E0%A4%A5.rss");
				put("बैंक तथा बित्त",
						"http://karobardaily.com/blog/rss/%E0%A4%AC%E0%A5%88%E0%A4%82%E0%A4%95-%E0%A4%A4%E0%A4%A5%E0%A4%BE-%E0%A4%AC%E0%A4%BF%E0%A4%A4%E0%A5%8D%E0%A4%A4.rss");
				put("कर्पोरेट",
						"http://karobardaily.com/blog/rss/%E0%A4%95%E0%A4%B0%E0%A5%8D%E0%A4%AA%E0%A5%8B%E0%A4%B0%E0%A5%87%E0%A4%9F.rss");
				put("राजनीति/समाज",
						"http://karobardaily.com/blog/rss/%E0%A4%B0%E0%A4%BE%E0%A4%9C%E0%A4%A8%E0%A5%80%E0%A4%A4%E0%A4%BF-%E0%A4%B8%E0%A4%AE%E0%A4%BE%E0%A4%9C.rss");
				put("सूचना–प्रविधि",
						"http://karobardaily.com/blog/rss/%E0%A4%B8%E0%A5%82%E0%A4%9A%E0%A4%A8%E0%A4%BE%E2%80%93%E0%A4%AA%E0%A5%8D%E0%A4%B0%E0%A4%B5%E0%A4%BF%E0%A4%A7%E0%A4%BF.rss");
				put("रोजगार",
						"http://karobardaily.com/blog/rss/%E0%A4%B0%E0%A5%8B%E0%A4%9C%E0%A4%97%E0%A4%BE%E0%A4%B0.rss");
				put("अर्थरंग",
						"http://karobardaily.com/blog/rss/%E0%A4%85%E0%A4%B0%E0%A5%8D%E0%A4%A5%E0%A4%B0%E0%A4%82%E0%A4%97.rss");
			}
		});

		createList(englishNewsList, "Nepali");
	}

	private void createList(
			final LinkedHashMap<String, LinkedHashMap<String, String>> newsList,
			String language) {

		addFieldHeaderTextView(language);
		boolean isLangaugeInNepali = true;
		if (newsList.size() > 0) {
			count++;
			Iterator newsPaperAndCategoryPairsIterator = newsList.entrySet()
					.iterator();
			newsCategoryAndLinkMap = new LinkedHashMap<String, String>();

			while (newsPaperAndCategoryPairsIterator.hasNext()) {
				count++;
				Map.Entry nepaliNewsPaperAndCategoryPairs = (Map.Entry) newsPaperAndCategoryPairsIterator
						.next();
				String newsPaperName = (String) nepaliNewsPaperAndCategoryPairs
						.getKey();
				addFieldHeaderTextView(newsPaperName);

				newsCategoryAndLinkMap = (LinkedHashMap<String, String>) nepaliNewsPaperAndCategoryPairs
						.getValue();

				Iterator newsCategoryAndLinkPairsIterator = newsCategoryAndLinkMap
						.entrySet().iterator();
				while (newsCategoryAndLinkPairsIterator.hasNext()) {
					count++;
					final Map.Entry newsCategoryPairs = (Map.Entry) newsCategoryAndLinkPairsIterator
							.next();
					String newsCategoryName = (String) newsCategoryPairs
							.getKey();

					getRadioButton(newsCategoryName, newsPaperName + ","
							+ language, count, isLangaugeInNepali);

					if (categoryListingRadioGroup.getCheckedRadioButtonId() == -1) {
						categoryListingRadioGroup
								.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
									@Override
									public void onCheckedChanged(
											RadioGroup group, int checkedId) {

										RadioButton checkedRadioButton = (RadioButton) categoryScrollView
												.findViewById(checkedId);

										selectedCategory = checkedRadioButton
												.getText().toString();
										sourceAndLanguage = checkedRadioButton
												.getTag().toString();

									}

								});
					}
				}
			}
		}
	}

	private void getRadioButton(String categoryNameHeader, String language,
			int id, boolean isNepali) {
		RadioButton radiobutton = new RadioButton(ConfigurationActivity.this);
		radioGroupsParamsBasic = getRadioGroupLayoutParams();

		radiobutton.setLayoutParams(radioGroupsParamsBasic);

		radiobutton.setId(id);

		id++;
		radiobutton.setPadding(radiobutton.getBackground().getIntrinsicWidth(),
				margin_10, margin_10, margin_10);
		radiobutton.setText(categoryNameHeader);
		radiobutton.setTag(language);

		radiobutton.setTextColor(Color.parseColor("#6B6B6B"));
		radiobutton.setTextSize(16);
		if (isNepali) {
			Typeface Nepali = Typeface.createFromAsset(getAssets(),
					"fonts/Devanagari.ttf");
			radiobutton.setTypeface(Nepali);
		}

		categoryListingRadioGroup.addView(radiobutton);
	}

	int mAppWidgetId;

	private void showAppWidget() {

		mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
		Intent intent = getIntent();
		Bundle extras = intent.getExtras();
		if (extras != null) {
			mAppWidgetId = extras.getInt(EXTRA_APPWIDGET_ID,
					INVALID_APPWIDGET_ID);
			showProgressDialog();

			News news1 = new News();
			news1.description = "सत्तारूढ एमाओवादीका नेताकार्यकर्ताबाट ज्यान लिनेसम्मका धम्की आएपछि दैलेखका पत्रकारहरू बिहीबार साँझ सामूहिक रूपमा ";
			news1.title = "गृहपृष्ठ";

			News news2 = new News();
			news2.description = "सत्तारूढ एमाओवादीका नेताकार्यकर्ताबाट ज्यान लिनेसम्मका धम्की आएपछि दैलेखका पत्रकारहरू बिहीबार साँझ सामूहिक रूपमा ";
			news2.title = "गृहपृष्ठ";

			News news3 = new News();
			news3.description = "सत्तारूढ एमाओवादीका नेताकार्यकर्ताबाट ज्यान लिनेसम्मका धम्की आएपछि दैलेखका पत्रकारहरू बिहीबार साँझ सामूहिक रूपमा ";
			news3.title = "गृहपृष्ठ";

			News news4 = new News();
			news4.description = "सत्तारूढ एमाओवादीका नेताकार्यकर्ताबाट ज्यान लिनेसम्मका धम्की आएपछि दैलेखका पत्रकारहरू बिहीबार साँझ सामूहिक रूपमा ";
			news4.title = "गृहपृष्ठ";

			ArrayList<News> newsList = new ArrayList<News>();
			newsList.add(news1);
			newsList.add(news2);
			newsList.add(news3);
			newsList.add(news4);
			getNewsList(newsList);

		}
		if (mAppWidgetId == INVALID_APPWIDGET_ID) {
			Log.i("I am invalid", "I am invalid");
			finish();
		}

	}

	private void showProgressDialog() {
		progressDialog.setMessage("Loading News Category...");
		progressDialog.setIndeterminate(true);
		progressDialog.setCancelable(true);
		progressDialog.show();
	}

	private void addFieldHeaderTextView(String fieldHeader) {
		TextView tv = new TextView(this);
		radioGroupsParamsBasic = getRadioGroupLayoutParams();
		radioGroupsParamsBasic.setMargins(margin_4, margin_4, margin_4,
				margin_4);
		tv.setLayoutParams(radioGroupsParamsBasic);
		tv.setBackgroundResource(R.drawable.actionbar_bg_top);
		tv.setText(fieldHeader);
		tv.setTextColor(Color.parseColor("#FFFFFF"));
		tv.setShadowLayer(1f, 0, 0, Color.parseColor("#cee8f2"));
		tv.setTextSize(14);
		tv.setGravity(Gravity.CENTER);

		categoryListingRadioGroup.addView(tv);
	}

	private RadioGroup.LayoutParams getRadioGroupLayoutParams() {
		return new RadioGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
	}

	private int convertPixelsToDp(int px) {
		Resources resources = getResources();
		DisplayMetrics metrics = resources.getDisplayMetrics();
		int dp = (int) (px / (metrics.densityDpi / 160f));
		return dp;

	}

	private void saveTheUserValueInPref(String selectedCategory,
			String selectedSourceAndLanguage) {
		SharedPreferences.Editor prefsCategory = getSharedPreferences(
				PREFS_NAME_CATEGORY, MODE_WORLD_READABLE).edit();
		prefsCategory.putString(PREFS_VALUE_CATEGORY, selectedCategory);
		prefsCategory.commit();

		String[] splittArray = null;
		if (sourceAndLanguage != null) {
			splittArray = sourceAndLanguage.split(",");

		}
		if (splittArray != null && (splittArray.length == 2)) {
			Log.i("Split Array ko string herum na", splittArray[0] + "::"
					+ splittArray[1]);
			selectedSource = splittArray[0];
			selectedLanguage = splittArray[1];

			SharedPreferences.Editor prefsSource = getSharedPreferences(
					PREFS_NAME_SOURCE, MODE_WORLD_READABLE).edit();
			prefsSource.putString(PREFS_VALUE_SOURCE, selectedSource);
			prefsSource.commit();

			SharedPreferences.Editor prefsLanguage = getSharedPreferences(
					PREFS_NAME_LANGUAGE, MODE_WORLD_READABLE).edit();
			prefsLanguage.putString(PREFS_VALUE_LANGUAGE, selectedLanguage);
			prefsLanguage.commit();

		}

	}

	public static ArrayList<News> DATA_FOR_WIDGET = new ArrayList<News>();

	public void getNewsList(ArrayList<News> newsList) {
		Log.v("news size", newsList.size() + "");
		if (progressDialog != null)
			progressDialog.dismiss();
		DATA_FOR_WIDGET = newsList;
		AppWidgetProviderInfo providerInfo = AppWidgetManager.getInstance(
				getBaseContext()).getAppWidgetInfo(mAppWidgetId);
		String appWidgetLabel = providerInfo.label;

		Intent startService = new Intent(ConfigurationActivity.this,
				WidgetProviderLarge.UpdateWidgetService.class);
		startService.putExtra(EXTRA_APPWIDGET_ID, mAppWidgetId);
		startService.setAction("FROM CONFIGURATION ACTIVITY");
		setResult(RESULT_OK, startService);
		startService(startService);

		finish();
	}

}

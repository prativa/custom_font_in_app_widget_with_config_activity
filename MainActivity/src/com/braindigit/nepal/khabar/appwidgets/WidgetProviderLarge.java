package com.braindigit.nepal.khabar.appwidgets;

import static android.appwidget.AppWidgetManager.EXTRA_APPWIDGET_ID;
import static android.appwidget.AppWidgetManager.INVALID_APPWIDGET_ID;

import java.util.ArrayList;

import android.app.IntentService;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextUtils.TruncateAt;
import android.util.Log;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.RemoteViews;
import android.widget.TextView;

import com.braindigit.nepal.khabar.R;

public class WidgetProviderLarge extends AppWidgetProvider {

	public static String ACTION_WIDGET_VIEW_CLICK = "Action_config";
	public static String ACTION_WIDGET_TITLE_VIEW_CLICK = "Action_refresh";

	private static String NEWS_CATEGORY = "Category";
	private static String NEWS_TITLE = "NewsTitle";
	private static String NEWS_SUMMARY = "NewsSummary";
	private static String ACTION_WIDGET_CLICK = "Action_widgetclick";

	private static int count = 0;

	static int minCount = 0, maxCount;
	private static String newsCategory = "";
	private static String newsLanguage = "";
	private static String newsSource = "";

	// = new ArrayList<News>();
	Context context;

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		super.onUpdate(context, appWidgetManager, appWidgetIds);

		int appWidgetId = INVALID_APPWIDGET_ID;

		if (appWidgetIds != null) {
			int N = appWidgetIds.length;
			if (N == 1) {
				appWidgetId = appWidgetIds[0];
				Log.i("Lets test appwidget id", appWidgetId + "appWidgetId");
				Intent intent = new Intent(context, UpdateWidgetService.class);

				intent.putExtra(EXTRA_APPWIDGET_ID, appWidgetId);
				intent.setAction("FROM WIDGET PROVIDER");
				context.startService(intent);
			} else {
				for (int mAppWidgetId : appWidgetIds) {
					Log.i("Lets test appwidget id", mAppWidgetId
							+ "mAppWidgetId");
					Intent intent = new Intent(context,
							UpdateWidgetService.class);

					intent.putExtra(EXTRA_APPWIDGET_ID, mAppWidgetId);
					intent.setAction("FROM WIDGET PROVIDER");
					context.startService(intent);
				}
			}
		}

	}

	public static class UpdateWidgetService extends IntentService {
		private String title1, title2, title3, newsDetail1, newsDetail2,
				newsDetail3;

		public UpdateWidgetService() {
			super("UpdateWidgetService");

		}

		@Override
		protected void onHandleIntent(Intent intent) {

			AppWidgetManager appWidgetManager = AppWidgetManager
					.getInstance(UpdateWidgetService.this);

			int incomingAppWidgetId = intent.getIntExtra(EXTRA_APPWIDGET_ID,
					INVALID_APPWIDGET_ID);

			if (incomingAppWidgetId != INVALID_APPWIDGET_ID) {
				try {
					Log.i("Lets test appwidget id", incomingAppWidgetId
							+ " valid mAppWidgetId");
					updateNewsAppWidget(appWidgetManager, incomingAppWidgetId,
							intent);
				} catch (NullPointerException e) {
				}

			} else {
				Log.i("Lets test appwidget id", incomingAppWidgetId
						+ " invalid mAppWidgetId");
			}

		}

		RemoteViews views;
		private int appWidgetId;
		AppWidgetManager appWidgetManager;
		ArrayList<News> newsList;

		public void updateNewsAppWidget(AppWidgetManager appWidgetManager,
				int appWidgetId, Intent intent) {
			Log.v("String package name", this.getPackageName());
			this.views = new RemoteViews(this.getPackageName(),
					R.layout.layout_appwidget_large);
			this.appWidgetId = appWidgetId;
			this.appWidgetManager = appWidgetManager;

			initializeButtonClicks(intent);

			// appWidgetManager.updateAppWidget(appWidgetId, views);
			Log.i("Test if the function is null",
					views == null ? "App Widget Manager is null"
							: "app widget manager no problem");
			appWidgetManager.updateAppWidget(appWidgetId, views);
		}

		private void initializeButtonClicks(Intent intent) {
			String whichButton = intent.getAction();

			this.newsList = ConfigurationActivity.DATA_FOR_WIDGET;// intent.getParcelableExtra(ConfigurationActivity.NEWS_ARRAYLIST);
			Log.i("Check News List within config", newsList.size() + "");
			getSharedPreferenceValue();
			fillDataWithList("fromTop");

			maxCount = newsList.size();

		}

		private void bindViewsToWidget() {

			TextView tvCategory = createContactTextView(newsCategory,
					NEWS_CATEGORY);
			BitmapDrawable bdCategory = (BitmapDrawable) convertViewToDrawable(tvCategory);
			bdCategory.setBounds(0, 0, bdCategory.getIntrinsicWidth(),
					bdCategory.getIntrinsicHeight());

			views.setImageViewBitmap(R.id.widgetNewsCategoryTitle,
					bdCategory.getBitmap());

			bindDataToFirstViews(views, true);
			Log.i("Test after first bind view", "check");
			bindDataToSecondViews(views, false);
			Log.i("Test after second bind view", "check");
			bindDataToThirdViews(views, false);
			Log.i("Test after third bind view", "check");

		}

		private void getSharedPreferenceValue() {
			SharedPreferences prefsCategory = getSharedPreferences(
					ConfigurationActivity.PREFS_NAME_CATEGORY, 0);
			newsCategory = prefsCategory.getString(
					ConfigurationActivity.PREFS_VALUE_CATEGORY, "");

			SharedPreferences prefsLanguage = getSharedPreferences(
					ConfigurationActivity.PREFS_NAME_LANGUAGE, 0);
			newsLanguage = prefsLanguage.getString(
					ConfigurationActivity.PREFS_VALUE_LANGUAGE, "");

			SharedPreferences prefsSource = getSharedPreferences(
					ConfigurationActivity.PREFS_NAME_SOURCE, 0);

			newsSource = prefsSource.getString(
					ConfigurationActivity.PREFS_VALUE_SOURCE, "");

		}

		private void bindDataToFirstViews(RemoteViews views, Boolean setSummary) {

			TextView tvNewsBox = createContactTextView(title1, NEWS_TITLE);

			BitmapDrawable bdNewsBox = (BitmapDrawable) convertViewToDrawable(tvNewsBox);
			bdNewsBox.setBounds(0, 0, bdNewsBox.getIntrinsicWidth(),
					bdNewsBox.getIntrinsicHeight());

			TextView tvSummary = createContactTextView(newsDetail1,
					NEWS_SUMMARY);
			BitmapDrawable bdSummary = (BitmapDrawable) convertViewToDrawable(tvSummary);
			bdSummary.setBounds(0, 0, bdSummary.getIntrinsicWidth(),
					bdSummary.getIntrinsicHeight());

			views.setImageViewBitmap(R.id.widgetNewsText1,
					bdNewsBox.getBitmap());

			if (setSummary) {
				views.setImageViewBitmap(R.id.widgetNewsSummaryText,
						bdSummary.getBitmap());
			}

		}

		private void bindDataToSecondViews(RemoteViews views, Boolean setSummary) {
			TextView tv = createContactTextView(title2, NEWS_TITLE);
			BitmapDrawable bd = (BitmapDrawable) convertViewToDrawable(tv);
			bd.setBounds(0, 0, bd.getIntrinsicWidth(), bd.getIntrinsicHeight());
			views.setImageViewBitmap(R.id.widgetNewsText2, bd.getBitmap());
			if (setSummary) {
				TextView tv2 = createContactTextView(newsDetail2, NEWS_SUMMARY);
				BitmapDrawable bd2 = (BitmapDrawable) convertViewToDrawable(tv2);
				bd2.setBounds(0, 0, bd2.getIntrinsicWidth(),
						bd2.getIntrinsicHeight());

				views.setImageViewBitmap(R.id.widgetNewsSummaryText,
						bd2.getBitmap());
			}
		}

		private void bindDataToThirdViews(RemoteViews views, Boolean setSummary) {

			TextView tv = createContactTextView(title3, NEWS_TITLE);
			BitmapDrawable bd = (BitmapDrawable) convertViewToDrawable(tv);
			bd.setBounds(0, 0, bd.getIntrinsicWidth(), bd.getIntrinsicHeight());
			views.setImageViewBitmap(R.id.widgetNewsText3, bd.getBitmap());
			if (setSummary) {
				TextView tv2 = createContactTextView(newsDetail3, NEWS_SUMMARY);
				BitmapDrawable bd2 = (BitmapDrawable) convertViewToDrawable(tv2);
				bd2.setBounds(0, 0, bd2.getIntrinsicWidth(),
						bd2.getIntrinsicHeight());
				views.setImageViewBitmap(R.id.widgetNewsSummaryText,
						bd2.getBitmap());
			}
		}

		private TextView createContactTextView(String text, String fieldName) {
			TextView tv = new TextView(this);
			if (fieldName.equals(NEWS_CATEGORY)) {
				tv.setWidth(125);
				tv.setHeight(15);
				tv.setSingleLine(true);
				tv.setTextColor(Color.parseColor("#FFFFFF"));
				tv.setTextSize(14);
			} else if (fieldName.equals(NEWS_TITLE)) {
				tv.setWidth(125);
				tv.setHeight(115);
				tv.setMaxLines(4);
				tv.setEllipsize(TruncateAt.END);
				tv.setLines(4);
				tv.setTextColor(Color.parseColor("#6B6B6B"));
				tv.setTextSize(18);
				tv.setPadding(2, 2, 2, 5);

			} else if (fieldName.equals(NEWS_SUMMARY)) {
				tv.setWidth(375);
				tv.setHeight(35);
				tv.setMaxLines(2);
				tv.setTextColor(Color.parseColor("#6B6B6B"));
				tv.setTextSize(18);
			}

			// if (newsLanguage.equals("Nepali"){
			Typeface Nepali = Typeface.createFromAsset(getAssets(),
					"fonts/Devanagari.ttf");
			tv.setTypeface(Nepali);

			tv.setText(text);

			return tv;
		}

		private Object convertViewToDrawable(View view) {
			int spec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
			view.measure(spec, spec);

			view.layout(2, 2, view.getMeasuredWidth(), view.getMeasuredHeight());
			Bitmap b = Bitmap.createBitmap(view.getMeasuredWidth(),
					view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);

			Canvas c = new Canvas(b);
			c.translate(-view.getScrollX(), -view.getScrollY());
			view.draw(c);
			view.setDrawingCacheEnabled(true);
			Bitmap cacheBmp = view.getDrawingCache();
			Bitmap viewBmp = cacheBmp.copy(Bitmap.Config.ARGB_8888, true);
			view.destroyDrawingCache();
			// view.setBackgroundResource(Color.TRANSPARENT);
			return new BitmapDrawable(viewBmp);

		}

		private void fillDataWithList(String fromWhere) {

			if (fromWhere.equals("fromTop")) {
				getDataForFirstDisplay();

			} else if (fromWhere.equals("fromNext")) {

				getDataForNext();

			} else if (fromWhere.equals("fromPrevious")) {

				getDataForPrevious();

			}

			bindViewsToWidget();

		}

		private void getDataForFirstDisplay() {
			switch (newsList.size()) {
			case 0:
				title1 = "NO CONTENT";
				newsDetail1 = "NO CONTENT";
				title2 = "NO CONTENT";
				newsDetail2 = "NO CONTENT";
				title3 = "NO CONTENT";
				newsDetail3 = "NO CONTENT";
				break;
			case 1:
				title1 = newsList.get(count).title != null ? newsList
						.get(count).title : "NO CONTENT";
				newsDetail1 = newsList.get(count).description != null ? newsList
						.get(count).description : "NO CONTENT";
				title2 = "NO CONTENT";
				newsDetail2 = "NO CONTENT";
				title3 = "NO CONTENT";
				newsDetail3 = "NO CONTENT";

				break;
			case 2:
				title1 = newsList.get(count).title != null ? newsList
						.get(count).title : "NO CONTENT";
				newsDetail1 = newsList.get(count).description != null ? newsList
						.get(count).description : "NO CONTENT";
				title2 = newsList.get(count + 1).title != null ? newsList
						.get(count + 1).title : "NO CONTENT";
				newsDetail2 = newsList.get(count + 1).description != null ? newsList
						.get(count + 1).description : "NO CONTENT";
				title3 = "NO CONTENT";
				newsDetail3 = "NO CONTENT";
				break;

			default:
				title1 = newsList.get(count).title != null ? newsList
						.get(count).title : "NO CONTENT";
				newsDetail1 = newsList.get(count).description != null ? newsList
						.get(count).description : "NO CONTENT";
				title2 = newsList.get(count + 1).title != null ? newsList
						.get(count + 1).title : "NO CONTENT";
				newsDetail2 = newsList.get(count + 1).description != null ? newsList
						.get(count + 1).description : "NO CONTENT";
				title3 = newsList.get(count + 2).title != null ? newsList
						.get(count + 2).title : "NO CONTENT";
				newsDetail3 = newsList.get(count + 2).description != null ? newsList
						.get(count + 2).description : "NO CONTENT";
				break;
			}

		}

		private void getDataForNext() {
			count = count + 3;
			if (count <= newsList.size() - 3) {

				Log.i("Test count from next", count + "::count next ");
				Log.i("Test count from next", (count + 1) + "::count next");
				Log.i("Test count from next", (count + 2) + "::count next");

				title1 = newsList.get(count).title != null ? newsList
						.get(count).title : "NO CONTENT";

				title2 = newsList.get(count + 1).title != null ? newsList
						.get(count + 1).title : "NO CONTENT";
				title3 = newsList.get(count + 2).title != null ? newsList
						.get(count + 2).title : "NO CONTENT";

				newsDetail1 = newsList.get(count).description != null ? newsList
						.get(count).description : "NO CONTENT";
				newsDetail2 = newsList.get(count + 1).description != null ? newsList
						.get(count + 1).description : "NO CONTENT";
				newsDetail3 = newsList.get(count + 2).description != null ? newsList
						.get(count + 2).description : "NO CONTENT";

			} else {
				title1 = "NO CONTENT";
				newsDetail1 = "NO CONTENT";
				title2 = "NO CONTENT";
				newsDetail2 = "NO CONTENT";
				title3 = "NO CONTENT";
				newsDetail3 = "NO CONTENT";

			}

		}

		private void getDataForPrevious() {
			count = count - 3;
			Log.i("Test count from previous", count + "::count prev start");
			Log.i("Test minCount", minCount + " ::minCount");
			Log.i("Test maxCount", newsList.size() + " ::maxCount");
			if (count >= minCount && count < newsList.size()) {

				Log.i("Test count from previous", count
						+ "::count prev withing the loop");
				Log.i("Test count from previous", (count + 1)
						+ "::count prev withing the loop");
				Log.i("Test count from previous", (count + 2)
						+ "::count prev withing the loop");

				title1 = newsList.get(count).title != null ? newsList
						.get(count).title : "NO CONTENT";
				title2 = newsList.get(count + 1).title != null ? newsList
						.get(count + 1).title : "NO CONTENT";
				title3 = newsList.get(count + 2).title != null ? newsList
						.get(count + 2).title : "NO CONTENT";

				newsDetail1 = newsList.get(count).description != null ? newsList
						.get(count).description : "NO CONTENT";
				newsDetail2 = newsList.get(count + 1).description != null ? newsList
						.get(count + 1).description : "NO CONTENT";
				newsDetail3 = newsList.get(count + 2).description != null ? newsList
						.get(count + 2).description : "NO CONTENT";
			}

		}

	}
}

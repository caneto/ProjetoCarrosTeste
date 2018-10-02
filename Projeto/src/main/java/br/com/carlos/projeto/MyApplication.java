package br.com.carlos.projeto;

import android.app.Application;
import android.content.Context;

import com.flurry.android.FlurryAgent;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.splunk.mint.Mint;

import org.acra.ACRA;
import org.acra.ReportField;
import org.acra.ReportingInteractionMode;
import org.acra.annotation.ReportsCrashes;

import java.util.HashMap;

import io.realm.Realm;
import io.realm.RealmConfiguration;

@ReportsCrashes(formKey = "", // will not be used
mailTo = "mobile@carlos.com.br",
customReportContent = { ReportField.APP_VERSION_CODE, ReportField.APP_VERSION_NAME, ReportField.ANDROID_VERSION, ReportField.PHONE_MODEL, ReportField.CUSTOM_DATA, ReportField.STACK_TRACE, ReportField.LOGCAT },
mode = ReportingInteractionMode.TOAST,
resToastText = R.string.crash_toast_text
)

 public class MyApplication extends Application {

	public static Context CONTEXT;

	public static double Lat, Long;
	public static String provider;

	public static Context getContext() {
		return CONTEXT;
	}


	private static final String PROPERTY_ID = "UA-43946207-2";
		
	public enum TrackerName {
		APP_TRACKER,
		GLOBAL_TRACKER,
		ECOMMERCE_TRACKER,
	}

	HashMap<TrackerName, Tracker> mTrackers = new HashMap<TrackerName, Tracker>();

	public MyApplication() {
		super();
	}

	public synchronized Tracker getTracker(TrackerName trackerId) {
		if (!mTrackers.containsKey(trackerId)) {

			GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
			Tracker t = (trackerId == TrackerName.APP_TRACKER) ? analytics
					.newTracker(PROPERTY_ID)
					: (trackerId == TrackerName.GLOBAL_TRACKER) ? analytics
							.newTracker(R.xml.global_tracker) : analytics
							.newTracker(R.xml.app_tracker);
			mTrackers.put(trackerId, t);

		}
		return mTrackers.get(trackerId);

	}

	@Override
	public void onCreate() {
	  super.onCreate();

		if(CONTEXT == null){
			CONTEXT = getApplicationContext();
		}

		new FlurryAgent.Builder()
				.withLogEnabled(true)
				.withCaptureUncaughtExceptions(true)
				.withContinueSessionMillis(10)
				.build(this, "89J6H89QRKY9823N3XC9");

		Mint.initAndStartSession(this, "64865785");

		// The following line triggers the initialization of ACRA
		ACRA.init(this);

		// Setup Realm database
		Realm.init( this);

		RealmConfiguration config = new RealmConfiguration.Builder()
				.name("carlos-data.realm")
				.schemaVersion(1)
				// .migration(new MyMigration())
				.build();

		// Use the config
		Realm realm = Realm.getInstance(config);

	}

}

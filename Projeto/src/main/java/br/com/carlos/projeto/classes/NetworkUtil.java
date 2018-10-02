package br.com.carlos.projeto.classes;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class NetworkUtil {

	public static int TYPE_WIFI = 1;
	public static int TYPE_MOBILE = 2;
	public static int TYPE_NOT_CONNECTED = 0;

	public static int getConnectivityStatus(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
		if (null != activeNetwork) {
			if(activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
				return TYPE_WIFI;

			if(activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
				return TYPE_MOBILE;
		}
		return TYPE_NOT_CONNECTED;
	}

	public static String getConnectivityStatusString(Context context) {
		int conn = NetworkUtil.getConnectivityStatus(context);
		String status = null;
		if (conn == NetworkUtil.TYPE_WIFI) {
			status = "Wifi enabled";
		} else if (conn == NetworkUtil.TYPE_MOBILE) {
			status = "Mobile data enabled";
		} else if (conn == NetworkUtil.TYPE_NOT_CONNECTED) {
			status = "Not connected to Internet";
		}
		return status;
	}

	public static boolean isConnected(Context context){
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = cm.getActiveNetworkInfo();
		return info != null && info.isConnected();
	}

	public static boolean Conectado(Context context) {

		final String TAGS = context.getClass().getName();

		try {

			ConnectivityManager cm = (ConnectivityManager)
					context.getSystemService(Context.CONNECTIVITY_SERVICE);
			String LogSync = null;
			String LogToUserTitle = null;
			if (cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnected()) {
				LogSync += "\nConectado a Internet 3G ";
				LogToUserTitle += "Conectado a Internet 3G ";
				Log.d(TAGS,"Status de conexão 3G: "+cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnected());
				return true;
			} else if(cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnected()){
				LogSync += "\nConectado a Internet WIFI ";
				LogToUserTitle += "Conectado a Internet WIFI ";
				Log.d(TAGS,"Status de conexão Wifi: "+cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnected());
				return true;
			} else {
				LogSync += "\nNão possui conexão com a internet ";
				LogToUserTitle += "Não possui conexão com a internet ";
				Log.e(TAGS,"Status de conexão Wifi: "+cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnected());
				Log.e(TAGS,"Status de conexão 3G: "+cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnected());
				return false;
			}
		} catch (Exception e) {
			Log.e(TAGS,e.getMessage());
			return false;
		}
	}

}

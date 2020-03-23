package com.badsocket.app;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * Created by skyrim on 2017/10/15.
 */

public abstract class PermissionChecker {

	protected static String[] permissions = {
			Manifest.permission.READ_EXTERNAL_STORAGE,
			Manifest.permission.WRITE_EXTERNAL_STORAGE,
			Manifest.permission.ACCESS_NETWORK_STATE,
			Manifest.permission.INTERNET,
			Manifest.permission.BLUETOOTH,
			Manifest.permission.ACCESS_WIFI_STATE,
			Manifest.permission.CHANGE_NETWORK_STATE,
			Manifest.permission.CHANGE_WIFI_STATE,
			Manifest.permission.CHANGE_WIFI_MULTICAST_STATE
	};

	public static void requestPermissions(Activity activity) {
		for (String permission : permissions) {
			if (isPermissionDenied(activity, permission)) {
				requestPermission(activity, permission);
			}
		}
	}

	public static boolean isPermissionDenied(Activity activity, String permission) {
		int permissionCheck = ContextCompat.checkSelfPermission(activity,
				permission);
		return permissionCheck == PackageManager.PERMISSION_DENIED;
	}

	public static void requestPermission(Activity activity, String permission) {
		ActivityCompat.requestPermissions(activity, new String[]{permission}, 0xff);
	}

}

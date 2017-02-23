package com.example.gattu.androidapp;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Locale;

import android.location.Geocoder;
import android.location.Location;
import android.widget.TextView;


public class MainActivity extends Activity {

	private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
	private Button btnSelect;
	private ImageView ivImage;
	private String userChoosenTask;
	public ImageView profile;
	public Geocoder geocoder;
	public double latitute = 0, longitude = 0;
	public LocationManager locationManager;
	public EditText address;



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.registration);
		//btnSelect = (Button) findViewById(R.id.upload);
		//View v = new View();
		ivImage = (ImageView) findViewById(R.id.imageButton5);
		address = (EditText) findViewById(R.id.txt_address);
		//fillAddress(v);

	}

	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		switch (requestCode) {
			case Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
				if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
					if (userChoosenTask.equals("Take Photo"))
						cameraIntent();
					else if (userChoosenTask.equals("Choose from Library"))
						galleryIntent();
				} else {
					//code for deny
				}
				break;
		}
	}

	public void selectImage(View v) {
		final CharSequence[] items = {"Take Photo", "Choose from Library",
				"Cancel"};

		AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
		builder.setTitle("Add Photo!");
		builder.setItems(items, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int item) {
				boolean result = Utility.checkPermission(MainActivity.this);

				if (items[item].equals("Take Photo")) {
					userChoosenTask = "Take Photo";
					if (result)
						cameraIntent();

				} else if (items[item].equals("Choose from Library")) {
					userChoosenTask = "Choose from Library";
					if (result)
						galleryIntent();

				} else if (items[item].equals("Cancel")) {
					dialog.dismiss();
				}
			}
		});
		builder.show();
	}

	private void galleryIntent() {
		Intent intent = new Intent();
		intent.setType("image/*");
		intent.setAction(Intent.ACTION_GET_CONTENT);//
		startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
	}

	private void cameraIntent() {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		startActivityForResult(intent, REQUEST_CAMERA);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == Activity.RESULT_OK) {
			if (requestCode == SELECT_FILE)
				onSelectFromGalleryResult(data);
			else if (requestCode == REQUEST_CAMERA)
				onCaptureImageResult(data);
		}
	}

	private void onCaptureImageResult(Intent data) {
		Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

		File destination = new File(Environment.getExternalStorageDirectory(),
				System.currentTimeMillis() + ".jpg");

		FileOutputStream fo;
		try {
			destination.createNewFile();
			fo = new FileOutputStream(destination);
			fo.write(bytes.toByteArray());
			fo.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		ivImage.setImageBitmap(thumbnail);
	}

	@SuppressWarnings("deprecation")
	private void onSelectFromGalleryResult(Intent data) {

		Bitmap bm = null;
		if (data != null) {
			try {
				bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		ivImage.setImageBitmap(bm);
	}

	public void fillAddress(View v) {
		locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
		if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
				|| ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

		latitute = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER).getLatitude();
		longitude = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER).getLongitude();
			//geocoder = new Geocoder(context, Locale.getDefault());
			Context context=new Context() {
				@Override
				public AssetManager getAssets() {
					return null;
				}

				@Override
				public Resources getResources() {
					return null;
				}

				@Override
				public PackageManager getPackageManager() {
					return null;
				}

				@Override
				public ContentResolver getContentResolver() {
					return null;
				}

				@Override
				public Looper getMainLooper() {
					return null;
				}

				@Override
				public Context getApplicationContext() {
					return null;
				}

				@Override
				public void setTheme(int resid) {

				}

				@Override
				public Resources.Theme getTheme() {
					return null;
				}

				@Override
				public ClassLoader getClassLoader() {
					return null;
				}

				@Override
				public String getPackageName() {
					return null;
				}

				@Override
				public ApplicationInfo getApplicationInfo() {
					return null;
				}

				@Override
				public String getPackageResourcePath() {
					return null;
				}

				@Override
				public String getPackageCodePath() {
					return null;
				}

				@Override
				public SharedPreferences getSharedPreferences(String name, int mode) {
					return null;
				}

				@Override
				public boolean moveSharedPreferencesFrom(Context sourceContext, String name) {
					return false;
				}

				@Override
				public boolean deleteSharedPreferences(String name) {
					return false;
				}

				@Override
				public FileInputStream openFileInput(String name) throws FileNotFoundException {
					return null;
				}

				@Override
				public FileOutputStream openFileOutput(String name, int mode) throws FileNotFoundException {
					return null;
				}

				@Override
				public boolean deleteFile(String name) {
					return false;
				}

				@Override
				public File getFileStreamPath(String name) {
					return null;
				}

				@Override
				public File getDataDir() {
					return null;
				}

				@Override
				public File getFilesDir() {
					return null;
				}

				@Override
				public File getNoBackupFilesDir() {
					return null;
				}

				@Nullable
				@Override
				public File getExternalFilesDir(String type) {
					return null;
				}

				@Override
				public File[] getExternalFilesDirs(String type) {
					return new File[0];
				}

				@Override
				public File getObbDir() {
					return null;
				}

				@Override
				public File[] getObbDirs() {
					return new File[0];
				}

				@Override
				public File getCacheDir() {
					return null;
				}

				@Override
				public File getCodeCacheDir() {
					return null;
				}

				@Nullable
				@Override
				public File getExternalCacheDir() {
					return null;
				}

				@Override
				public File[] getExternalCacheDirs() {
					return new File[0];
				}

				@Override
				public File[] getExternalMediaDirs() {
					return new File[0];
				}

				@Override
				public String[] fileList() {
					return new String[0];
				}

				@Override
				public File getDir(String name, int mode) {
					return null;
				}

				@Override
				public SQLiteDatabase openOrCreateDatabase(String name, int mode, SQLiteDatabase.CursorFactory factory) {
					return null;
				}

				@Override
				public SQLiteDatabase openOrCreateDatabase(String name, int mode, SQLiteDatabase.CursorFactory factory, DatabaseErrorHandler errorHandler) {
					return null;
				}

				@Override
				public boolean moveDatabaseFrom(Context sourceContext, String name) {
					return false;
				}

				@Override
				public boolean deleteDatabase(String name) {
					return false;
				}

				@Override
				public File getDatabasePath(String name) {
					return null;
				}

				@Override
				public String[] databaseList() {
					return new String[0];
				}

				@Override
				public Drawable getWallpaper() {
					return null;
				}

				@Override
				public Drawable peekWallpaper() {
					return null;
				}

				@Override
				public int getWallpaperDesiredMinimumWidth() {
					return 0;
				}

				@Override
				public int getWallpaperDesiredMinimumHeight() {
					return 0;
				}

				@Override
				public void setWallpaper(Bitmap bitmap) throws IOException {

				}

				@Override
				public void setWallpaper(InputStream data) throws IOException {

				}

				@Override
				public void clearWallpaper() throws IOException {

				}

				@Override
				public void startActivity(Intent intent) {

				}

				@Override
				public void startActivity(Intent intent, Bundle options) {

				}

				@Override
				public void startActivities(Intent[] intents) {

				}

				@Override
				public void startActivities(Intent[] intents, Bundle options) {

				}

				@Override
				public void startIntentSender(IntentSender intent, Intent fillInIntent, int flagsMask, int flagsValues, int extraFlags) throws IntentSender.SendIntentException {

				}

				@Override
				public void startIntentSender(IntentSender intent, Intent fillInIntent, int flagsMask, int flagsValues, int extraFlags, Bundle options) throws IntentSender.SendIntentException {

				}

				@Override
				public void sendBroadcast(Intent intent) {

				}

				@Override
				public void sendBroadcast(Intent intent, String receiverPermission) {

				}

				@Override
				public void sendOrderedBroadcast(Intent intent, String receiverPermission) {

				}

				@Override
				public void sendOrderedBroadcast(Intent intent, String receiverPermission, BroadcastReceiver resultReceiver, Handler scheduler, int initialCode, String initialData, Bundle initialExtras) {

				}

				@Override
				public void sendBroadcastAsUser(Intent intent, UserHandle user) {

				}

				@Override
				public void sendBroadcastAsUser(Intent intent, UserHandle user, String receiverPermission) {

				}

				@Override
				public void sendOrderedBroadcastAsUser(Intent intent, UserHandle user, String receiverPermission, BroadcastReceiver resultReceiver, Handler scheduler, int initialCode, String initialData, Bundle initialExtras) {

				}

				@Override
				public void sendStickyBroadcast(Intent intent) {

				}

				@Override
				public void sendStickyOrderedBroadcast(Intent intent, BroadcastReceiver resultReceiver, Handler scheduler, int initialCode, String initialData, Bundle initialExtras) {

				}

				@Override
				public void removeStickyBroadcast(Intent intent) {

				}

				@Override
				public void sendStickyBroadcastAsUser(Intent intent, UserHandle user) {

				}

				@Override
				public void sendStickyOrderedBroadcastAsUser(Intent intent, UserHandle user, BroadcastReceiver resultReceiver, Handler scheduler, int initialCode, String initialData, Bundle initialExtras) {

				}

				@Override
				public void removeStickyBroadcastAsUser(Intent intent, UserHandle user) {

				}

				@Nullable
				@Override
				public Intent registerReceiver(BroadcastReceiver receiver, IntentFilter filter) {
					return null;
				}

				@Nullable
				@Override
				public Intent registerReceiver(BroadcastReceiver receiver, IntentFilter filter, String broadcastPermission, Handler scheduler) {
					return null;
				}

				@Override
				public void unregisterReceiver(BroadcastReceiver receiver) {

				}

				@Nullable
				@Override
				public ComponentName startService(Intent service) {
					return null;
				}

				@Override
				public boolean stopService(Intent service) {
					return false;
				}

				@Override
				public boolean bindService(Intent service, ServiceConnection conn, int flags) {
					return false;
				}

				@Override
				public void unbindService(ServiceConnection conn) {

				}

				@Override
				public boolean startInstrumentation(ComponentName className, String profileFile, Bundle arguments) {
					return false;
				}

				@Override
				public Object getSystemService(String name) {
					return null;
				}

				@Override
				public String getSystemServiceName(Class<?> serviceClass) {
					return null;
				}

				@Override
				public int checkPermission(String permission, int pid, int uid) {
					return 0;
				}

				@Override
				public int checkCallingPermission(String permission) {
					return 0;
				}

				@Override
				public int checkCallingOrSelfPermission(String permission) {
					return 0;
				}

				@Override
				public int checkSelfPermission(String permission) {
					return 0;
				}

				@Override
				public void enforcePermission(String permission, int pid, int uid, String message) {

				}

				@Override
				public void enforceCallingPermission(String permission, String message) {

				}

				@Override
				public void enforceCallingOrSelfPermission(String permission, String message) {

				}

				@Override
				public void grantUriPermission(String toPackage, Uri uri, int modeFlags) {

				}

				@Override
				public void revokeUriPermission(Uri uri, int modeFlags) {

				}

				@Override
				public int checkUriPermission(Uri uri, int pid, int uid, int modeFlags) {
					return 0;
				}

				@Override
				public int checkCallingUriPermission(Uri uri, int modeFlags) {
					return 0;
				}

				@Override
				public int checkCallingOrSelfUriPermission(Uri uri, int modeFlags) {
					return 0;
				}

				@Override
				public int checkUriPermission(Uri uri, String readPermission, String writePermission, int pid, int uid, int modeFlags) {
					return 0;
				}

				@Override
				public void enforceUriPermission(Uri uri, int pid, int uid, int modeFlags, String message) {

				}

				@Override
				public void enforceCallingUriPermission(Uri uri, int modeFlags, String message) {

				}

				@Override
				public void enforceCallingOrSelfUriPermission(Uri uri, int modeFlags, String message) {

				}

				@Override
				public void enforceUriPermission(Uri uri, String readPermission, String writePermission, int pid, int uid, int modeFlags, String message) {

				}

				@Override
				public Context createPackageContext(String packageName, int flags) throws PackageManager.NameNotFoundException {
					return null;
				}

				@Override
				public Context createConfigurationContext(Configuration overrideConfiguration) {
					return null;
				}

				@Override
				public Context createDisplayContext(Display display) {
					return null;
				}

				@Override
				public Context createDeviceProtectedStorageContext() {
					return null;
				}

				@Override
				public boolean isDeviceProtectedStorage() {
					return false;
				}
			};
			geocoder = new Geocoder(context,Locale.getDefault());



			try {
				List<Address> addressList = geocoder.getFromLocation(
						latitute, longitude, 1);
				if (addressList != null && addressList.size() > 0) {
					Address address1 = addressList.get(0);
					StringBuilder sb = new StringBuilder();
					for (int i = 0; i <= address1.getMaxAddressLineIndex(); i++) {
						sb.append(address1.getAddressLine(i)).append("\n");
					}
					String result = sb.toString();
					address.setText(result);
				}
			}

		catch (IOException e) {
			// Handle IOException
		} catch (NullPointerException e) {
			// Handle NullPointerException
		}
		LocationListener listener = new LocationListener() {
			@Override
			public void onLocationChanged(Location location) {
				double lat = location.getLatitude();
				double lng = location.getLongitude();

				StringBuilder builder = new StringBuilder();
				try {
					List<Address> addressList = geocoder.getFromLocation(lat, lng, 1);
					int maxLines = addressList.get(0).getMaxAddressLineIndex();
					for (int i = 0; i < maxLines; i++) {
						String addressStr = addressList.get(0).getAddressLine(i);
						builder.append(addressStr);
						builder.append(" ");
					}

					String finalAddress = builder.toString(); //This is the complete address.
					address.setText(finalAddress);

				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onStatusChanged(String provider, int status, Bundle extras) {

			}

			@Override
			public void onProviderEnabled(String provider) {

			}

			@Override
			public void onProviderDisabled(String provider) {

			}

		};
	}
	}

	public void SignUp(View v)
	{
		Intent intent = new Intent(MainActivity.this, Home.class);
		ivImage.buildDrawingCache();
		Bitmap bitProfile = ivImage.getDrawingCache();
		Bundle extra =  new Bundle();
		extra.putParcelable("profileBitmap",bitProfile);
		intent.putExtras(extra);
		startActivity(intent);
	}
}

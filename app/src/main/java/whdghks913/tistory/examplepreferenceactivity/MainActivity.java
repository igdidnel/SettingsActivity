package whdghks913.tistory.examplepreferenceactivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends Activity {
	private SharedPreferences mPref;
	private TextView userNameEnable, userName, userNameOpen, autoUpdateEnable,
			updateNofiti, notifiSound;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mPref = PreferenceManager.getDefaultSharedPreferences(this);

		userNameEnable = (TextView) findViewById(R.id.userNameEnable);
		userName = (TextView) findViewById(R.id.userName);
		userNameOpen = (TextView) findViewById(R.id.userNameOpen);
		autoUpdateEnable = (TextView) findViewById(R.id.autoUpdateEnable);
		updateNofiti = (TextView) findViewById(R.id.updateNofiti);
		notifiSound = (TextView) findViewById(R.id.notifiSound);

		getPreferencesData();
	}

	@Override
	protected void onResume() {
		super.onResume();

		Log.d("onResume", "onResume()");

		getPreferencesData();
	}

	private void getPreferencesData() {
		userNameEnable.setText("" + mPref.getBoolean("useUserName", false));
		userName.setText(mPref.getString("userName", "???????? ????"));

		autoUpdateEnable.setText("" + mPref.getBoolean("autoUpdate", false));
		updateNofiti.setText("" + mPref.getBoolean("useUpdateNofiti", false));

		String[] array = getResources().getStringArray(R.array.userNameOpen);
		int index = getArrayIndex(R.array.userNameOpen_values,
				mPref.getString("userNameOpen", "0"));
		if (index != -1) {
			String userNameOpenString = array[index];
			userNameOpen.setText(userNameOpenString);
		} else {
			userNameOpen.setText("");
		}

		String Sound = mPref.getString("autoUpdate_ringtone", "???????? ????");
		if (TextUtils.isEmpty(Sound)) {
			notifiSound.setText("???????? ??????");

		} else {
			Ringtone ringtone = RingtoneManager.getRingtone(getBaseContext(),
					Uri.parse(Sound));

			if (ringtone == null) {
				notifiSound.setText(null);

			} else {
				String name = ringtone.getTitle(this);
				notifiSound.setText(name);
			}
		}
	}

	private int getArrayIndex(int array, String findIndex) {
		String[] arrayString = getResources().getStringArray(array);
		for (int e = 0; e < arrayString.length; e++) {
			if (arrayString[e].equals(findIndex))
				return e;
		}
		return -1;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int itemId = item.getItemId();

		if (itemId == R.id.action_settings) {
			Intent SettingActivity = new Intent(this, SettingsActivity.class);
			startActivity(SettingActivity);
		}

		return super.onOptionsItemSelected(item);
	}

}

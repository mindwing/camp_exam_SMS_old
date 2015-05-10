package kr.mindwing.sms;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

	private Button sendButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		sendButton = (Button) findViewById(R.id.button_send);
		sendButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				SmsManager manager = SmsManager.getDefault();
				manager.sendTextMessage("01020242501", "01026602501", "테스트",
						PendingIntent
								.getBroadcast(MainActivity.this, 2501,
										new Intent("sent"),
										PendingIntent.FLAG_ONE_SHOT),
						PendingIntent.getBroadcast(MainActivity.this, 2502,
								new Intent("delivery"),
								PendingIntent.FLAG_ONE_SHOT));
			}
		});

		registerReceiver(new BroadcastReceiver() {

			@Override
			public void onReceive(Context context, Intent intent) {
				String msg;

				switch (getResultCode()) {
				case Activity.RESULT_OK:
					msg = "SMS sent, RESULT_OK";
					break;

				case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
					msg = "SMS sent, RESULT_ERROR_GENERIC_FAILURE";
					break;

				case SmsManager.RESULT_ERROR_NO_SERVICE:
					msg = "SMS sent, RESULT_ERROR_NO_SERVICE";
					break;

				case SmsManager.RESULT_ERROR_NULL_PDU:
					msg = "SMS sent, RESULT_ERROR_NULL_PDU";
					break;

				case SmsManager.RESULT_ERROR_RADIO_OFF:
					msg = "SMS sent, RESULT_ERROR_RADIO_OFF";
					break;

				default:
					msg = "알 수 없는 응답코드";
					break;
				}

				Toast.makeText(MainActivity.this, "sent: " + msg,
						Toast.LENGTH_SHORT).show();
			}
		}, new IntentFilter("sent"));

		// XXX 우리나라 이통사들은 지원하지 않는 듯...
		// http://stackoverflow.com/questions/15324977/android-smsmanager-sendtextmessage-i-get-sentintent-but-never-get-deliveryinte
		registerReceiver(new BroadcastReceiver() {

			@Override
			public void onReceive(Context context, Intent intent) {
				String msg;

				switch (getResultCode()) {
				case Activity.RESULT_OK:
					msg = "SMS delivery, RESULT_OK";
					break;

				case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
					msg = "SMS delivery, RESULT_ERROR_GENERIC_FAILURE";
					break;

				case SmsManager.RESULT_ERROR_NO_SERVICE:
					msg = "SMS delivery, RESULT_ERROR_NO_SERVICE";
					break;

				case SmsManager.RESULT_ERROR_NULL_PDU:
					msg = "SMS delivery, RESULT_ERROR_NULL_PDU";
					break;

				case SmsManager.RESULT_ERROR_RADIO_OFF:
					msg = "SMS delivery, RESULT_ERROR_RADIO_OFF";
					break;

				default:
					msg = "알 수 없는 응답코드";
					break;
				}

				Toast.makeText(MainActivity.this, "delivery: " + msg,
						Toast.LENGTH_SHORT).show();
			}
		}, new IntentFilter("delivery"));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}

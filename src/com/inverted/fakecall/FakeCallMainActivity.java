package com.inverted.fakecall;

import java.util.Random;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.inverted.fakecall.R;

@SuppressLint("ClickableViewAccessibility")
public class FakeCallMainActivity extends Activity implements OnTouchListener {

	Ringtone ringtone;
	int callerImgId[] = { R.drawable.ic_chancellor, R.drawable.ic_gf,
			R.drawable.ic_president, R.drawable.ic_prime_minister,
			R.drawable.ic_regular };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.fake_call_activity);
		
		// get Imageview object
		ImageView imgV = (ImageView) findViewById(R.id.callerImg);
		
		// generate random index
		Random rnd = new Random();
		int randomIdx = rnd.nextInt(callerImgId.length);
		
		// set random Imag resource to Imageview
		imgV.setBackgroundResource(callerImgId[randomIdx]);
		imgV.setOnTouchListener(this);
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// kill application
		onBackPressed();
		return false;
	}

	@Override
	protected void onResume() {
		super.onResume();
		// give user time of 10 seconds
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// turn on NORMAL_MODE
		AudioManager mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		mAudioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
		// get Ringtone
		Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
		ringtone = RingtoneManager.getRingtone(FakeCallMainActivity.this, uri);
		// play ringtone
		ringtone.play();
	}

	private void turnOnPhoneVibrate() {
		// turn on VIBRATE_MODE
		AudioManager mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		mAudioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
	}

	@Override
	protected void onPause() {
		ringtone.stop();
		super.onPause();
	}

	@Override
	public void onBackPressed() {
		ringtone.stop();
		turnOnPhoneVibrate();
		super.onBackPressed();
	}
}
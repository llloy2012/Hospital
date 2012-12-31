package com.android.hospital.ui.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.client.ClientProtocolException;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import com.android.hospital.HospitalApp;
import com.android.hospital.util.DebugUtil;
import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;

public class CheckVersionActivity extends Activity {
	public HospitalApp app;
	
	public static final String COM_LEMAX_HIS_USER_CLICK = "com.lemaxhis.userClick";
	private AQuery aq;
	private String Client_version;
	private String Server_version_desc;
	private static final int NOTIFICATION_ID = 0x12;
	private static final int DIALOG_SIMPLE = 2;
	private static final int DOWNLOAD_FAIL = 3;
	private String message;
	private int _progress = 0;
	private NotificationManager manager = null;
	private Notification notif;
	int fileSize;
	int downLoadFileSize;
	String filename;
	Boolean isanzhang;
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if (!Thread.currentThread().isInterrupted()) {
				switch (msg.what) {
				case 0:
					notif.contentView.setProgressBar(R.id.progressbar_load,
							100, _progress, false);
					break;
				case 1:
					_progress = downLoadFileSize * 100 / fileSize;
					notif.contentView.setProgressBar(R.id.progressbar_load,
							100, _progress, false);
					notif.contentView.setTextViewText(R.id.txtview_title,
							"��ǰ����:" + _progress + "%");
					manager.notify(NOTIFICATION_ID, notif);
					break;
				case 2:
					anZhuang();
					manager.notify(NOTIFICATION_ID, notif);

					break;
				case DOWNLOAD_FAIL:
					notif.contentView = new RemoteViews(getApplication()
							.getPackageName(), R.layout.installation);
					notif.contentView.setTextViewText(R.id.show_failedReason,
							message);
					manager.notify(NOTIFICATION_ID, notif);
					break;
				}
			}
			super.handleMessage(msg);
		}
	};

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);//ȥ��������
		app = (HospitalApp)this.getApplication();
		manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		Intent i = getIntent();
		Bundle bundle = i.getExtras();
		if (bundle != null) {
			if (bundle.getInt(COM_LEMAX_HIS_USER_CLICK) == 0) {
				install();
				manager.cancel(NOTIFICATION_ID);
				finish();
			}

		} else {
			setContentView(R.layout.splash);
			TextView tvDesc = (TextView)findViewById(R.id.tvDesc);
			tvDesc.setText("���ڼ��汾��Ϣ�����Ժ�......");
			Client_version = getAppVersionName(this);
			new VersionDownloadTask().execute(null, null, null);
			notif = new Notification(R.drawable.ic_launcher, "�ƶ��鷿�����������°汾���£�",
					System.currentTimeMillis());
			notif.contentView = new RemoteViews(getApplication()
					.getPackageName(), R.layout.custom_dialog);
			notif.contentIntent = PendingIntent.getActivity(this, 0,
					new Intent(this, APKTask.class), 0);
		}
	}

	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DIALOG_SIMPLE:
			return new AlertDialog.Builder(CheckVersionActivity.this)
					.setTitle("������°汾��")
					.setIcon(R.drawable.ic_launcher)
					.setMessage(Server_version_desc)
					.setCancelable(false)
					.setPositiveButton("��������",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									new APKTask().execute(null, null, null);
									CheckVersionActivity.this.finish();
								};
							})
					.setNegativeButton("�Ժ���˵",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton) {
									manager.cancel(NOTIFICATION_ID);
									CheckVersionActivity.this.finish();
								}
							}).create();
		}
		return null;
	}

	class APKTask extends AsyncTask<Void, Void, Void> {
		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try {
				down_file("http://"+"192.168.0.10"+":"+"8888"+"/WebServiceServer/download/Hospital.apk",//�Ƿ������apk��
						"/sdcard/");
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				message = e.getMessage();
				sendMsg(DOWNLOAD_FAIL);
			}
			return null;
		}

	}

	class VersionDownloadTask extends AsyncTask<Void, Void, Void> {
		Boolean version_Judgment = false;

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			version_Judgment = false;
			aq = new AQuery(CheckVersionActivity.this.getApplicationContext());
			AjaxCallback<String> callback = new AjaxCallback<String>();
			callback.type(String.class).url(
					"http://"+"192.168.0.10"+":"+"8888"+"/WebServiceServer/servlet/AndroidVersionServlet");
			aq.sync(callback);
			String str = callback.getResult();
			if (str!=null){
				String str_version = "";
				String str_desc = "";
				String[] str_list = str.split("<;>");
				str_version = str_list[0].trim();
				str_desc = str_list[1].trim();
				if (!Client_version.equals(str_version)){
					version_Judgment = true;
				}
				
				str_list = str_desc.split("#");
				Server_version_desc = "";
				for (int i = 0; i<str_list.length;i++){
					Server_version_desc = Server_version_desc + str_list[i]+ "\n\n";
				}
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			if (version_Judgment == false) {
				Toast.makeText(CheckVersionActivity.this, "ĿǰΪ���°汾��",
						Toast.LENGTH_SHORT).show();
				finish();
			} else {

				showDialog(DIALOG_SIMPLE);
			}
			super.onPostExecute(result);
		}
	}

	public static String getAppVersionName(Context context) {
		String versionName = "";
		try {
			PackageManager pm = context.getPackageManager();
			PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
			versionName = pi.versionName;
			if (versionName == null || versionName.length() <= 0) {
				return "  ";
			}
		} catch (Exception e) {
			//Log.e("VersionInfo", "Exception", e);
		}
		return versionName;
	}

	public void down_file(String url, String path) throws IOException {

		filename = url.substring(url.lastIndexOf("/") + 1);

		URL myURL;
		try {
			myURL = new URL(url);
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			throw new IOException("�������1");
		}
		URLConnection conn;
		try {
			conn = myURL.openConnection();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			throw new IOException("�������2");
		}

		try {
			conn.connect();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			throw new IOException("�������3");
		}
		InputStream is;
		try {
			is = conn.getInputStream();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			DebugUtil.debug("�쳣-->"+e1);
			throw new IOException("�������4");
		}

		this.fileSize = conn.getContentLength();
		if (this.fileSize <= 0) {

			throw new IOException("�������5");
		}

		FileOutputStream fos;
		try {

			fos = new FileOutputStream(path + filename);
		} catch (FileNotFoundException e1) {
			throw new IOException("�������6");
		}
		byte buf[] = new byte[1024 * 1024];

		downLoadFileSize = 0;
		sendMsg(0);

		do {

			int numread;
			try {
				numread = is.read(buf);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				throw new IOException("�������7");
			}
			if (numread == -1) {
				break;
			}
			try {
				fos.write(buf, 0, numread);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				throw new IOException("�������8");
			}
			downLoadFileSize += numread;

			sendMsg(1);

		} while (true);
		try {
			Thread.sleep(400);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			throw new IOException("�������9");
		}
		sendMsg(2);
		try {
			is.close();
		} catch (Exception ex) {
			throw new IOException("�������10");
		}

	}

	private void sendMsg(int flag) {
		Message msg = new Message();
		msg.what = flag;
		handler.handleMessage(msg);
	}

	private void anZhuang() {
		Intent intent = new Intent(getApplicationContext(),
				CheckVersionActivity.class);
		intent.putExtra(COM_LEMAX_HIS_USER_CLICK, 0);
		notif.setLatestEventInfo(this, "�ƶ��鷿ϵͳ", "������ɣ�������װ",
				PendingIntent.getActivity(this, 0, intent, 0));

	}
	private void install() {
		String str = "/Hospital.apk";
		String fileName = Environment.getExternalStorageDirectory() + str;
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.fromFile(new File(fileName)),
				"application/vnd.android.package-archive");
		startActivity(intent);
	}

}
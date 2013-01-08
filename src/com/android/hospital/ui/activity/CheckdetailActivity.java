package com.android.hospital.ui.activity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import org.kobjects.base64.Base64;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import com.android.hospital.adapter.CheckImageAdapter;
import com.android.hospital.entity.CheckEntity;
import com.android.hospital.entity.DataEntity;
import com.android.hospital.util.DebugUtil;
import com.android.hospital.webservice.WebServiceHelper;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 
* @ClassName: CheckdetailActivity 
* @Description: TODO(���ͼƬ�б����) 
* @author wanghailong 81813780@qq.com 
* @date 2012-12-28 ����3:26:40 
*
 */
public class CheckdetailActivity extends Activity{

	public String sFilePath = "";
	
	public String sFileName = "";
	
	private String exam_no="";//������
	
	
	static class MyHandler extends Handler{
		WeakReference<CheckdetailActivity> mActivity;
		
		MyHandler(CheckdetailActivity activity){
			mActivity = new WeakReference<CheckdetailActivity>(activity);
		}
		
		@Override
		public void handleMessage(Message msg) {
			CheckdetailActivity theActivity = mActivity.get();
			if (theActivity.myDialog != null) {
				theActivity.myDialog.dismiss();
				theActivity.myDialog = null;
			}
			if (msg.what == 11){
				File jpgFile = new File(SDPATH + theActivity.sFileName + ".jpg");
				theActivity.openFile(jpgFile);
			}else if (msg.what == 10){
				Toast.makeText(theActivity,
						"ͼ���ȡʧ�ܣ�", Toast.LENGTH_SHORT)
						.show();
			}else if (msg.what == 21){
				File txtFile = new File(SDPATH + theActivity.sFileName + ".txt");
				theActivity.openFile(txtFile);
			}else if (msg.what == 20){
				Toast.makeText(theActivity,
						"�ı���ȡʧ�ܣ�", Toast.LENGTH_SHORT)
						.show();
			}else{
				Toast.makeText(theActivity,
						"��������", Toast.LENGTH_SHORT)
						.show();
			}
		}
		
	}
	
	public MyHandler handle;
	public ProgressDialog myDialog = null;
	
	private static String SDPATH = Environment.getExternalStorageDirectory() + "/HisTemp/";
	
	List<String> data = new ArrayList<String>();
	private ListView listView;
	private View mProcessView;
	
	private List<String> getData(){
		data.add("C:\\dcm\\agfacr\\1335805255\\1335805259_0.DCM");
		data.add("C:\\dcm\\agfacr\\1335805255\\1335805259_1.DCM");
		data.add("C:\\dcm\\agfacr\\1335805255\\1335805259_2.DCM");
		return data;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		handle = new MyHandler(this);
		setContentView(R.layout.activity_checkdetail_photolist);
		
		Intent intent=getIntent();
		CheckEntity entity=(CheckEntity) intent.getSerializableExtra("check");
		exam_no=entity.exam_no;
		
		File file = new File(SDPATH);
		if (!file.exists()){
			file.mkdir();
		}
		listView = (ListView)findViewById(R.id.lvPath);
		mProcessView=findViewById(R.id.progressContainer);
		//listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1,getData()));
		mProcessView.setVisibility(View.VISIBLE);
		new FilePathTask().execute("");//��ȡ�ļ�·��
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				//TextView tv = (TextView) arg1.findViewById(android.R.id.text1);
				//Log.e(String.valueOf(arg2), tv.getText().toString());
				final String[] items = {"�鿴ͼ��","�鿴�ı�"};
				final AlertDialog.Builder builder = new AlertDialog.Builder(CheckdetailActivity.this);
				sFilePath = data.get(position);
				sFileName = sFilePath.substring(sFilePath.lastIndexOf("\\")+1); 
				myDialog = ProgressDialog.show(CheckdetailActivity.this, "��ȡͼƬ",
						"���ڻ�ȡͼƬ�����Ժ�");
				myDialog.setCancelable(true);
				Thread thread = new Thread(new getDcm2JpgThread());
				thread.start();
				/*builder.setTitle("��ѡ�������").setItems(items, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						switch (which){
						case 0:
							Log.e("sFilePath", sFilePath);
							Log.e("sFileName", sFileName);
							myDialog = ProgressDialog.show(CheckdetailActivity.this, "��ȡͼƬ",
									"���ڻ�ȡͼƬ�����Ժ�");
							myDialog.setCancelable(true);
							Thread thread = new Thread(new getDcm2JpgThread());
							thread.start();
							break;
						case 1:
							Log.e("sFilePath", sFilePath);
							Log.e("sFileName", sFileName);
							myDialog = ProgressDialog.show(CheckdetailActivity.this, "��ȡ�ı�",
									"���ڻ�ȡ�ı������Ժ�");
							myDialog.setCancelable(true);
							Thread thread1 = new Thread(new getDcm2TxtThread());
							thread1.start();
							break;
						}
					}
				});
				builder.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				});
				AlertDialog ad = builder.create();
				ad.show();*/
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.layout.photo_menu,menu);
		return true;
	}
	
	//��ȥDcm2Txt���ı�
	class getDcm2TxtThread implements Runnable {
		public void run(){
			int what = -1;
			try {
				String URL = "http://192.168.0.3:8888/DCMConvertService/DCMPort?wsdl";
				String NAMESPACE = "http://webservice.lemax.com/";
				String METHOD_NAME = "getDcmTxt";
				
				SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
				request.addProperty("arg0", sFilePath);				
				SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
						SoapEnvelope.VER11);
				envelope.setOutputSoapObject(request);
				HttpTransportSE ht = new HttpTransportSE(URL);
				ht.debug = true;
				try {
					ht.call("http://webservice.lemax.com/getDcmTxt", envelope);
					if (envelope.getResponse() != null) {
						Object ret = (Object)envelope.getResponse();
						String retString = String.valueOf(ret);
						Log.e("retString", retString);
						byte[] retByte = (byte[]) Base64.decode(retString);
						File txtFile = new File(SDPATH + sFileName + ".txt");
						if (txtFile.exists()){
							txtFile.delete();
						}
						txtFile.createNewFile();
						OutputStream output = new FileOutputStream(txtFile);
						output.write(retByte,0,retByte.length);
						output.flush();
						output.close();
						what = 21;
					} else {
						what = 20;
					}
					
				} catch (IOException e) {
					e.printStackTrace();
				} catch (XmlPullParserException e) {
					e.printStackTrace();
				}
								
			} catch (Exception e) {
				e.printStackTrace();
			}
			handle.sendEmptyMessage(what);
		}
	}
	
	//��ȡDcm2Jpg��ͼƬ
	class getDcm2JpgThread implements Runnable {
		public void run(){
			int what = -1;
			try {
				String URL = "http://192.168.0.3:8888/DCMConvertService/DCMPort?wsdl";
				String NAMESPACE = "http://webservice.lemax.com/";
				String METHOD_NAME = "getDcmJpg";
				SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
				DebugUtil.debug("·��--->"+sFilePath);
				//request.addProperty("arg0", sFilePath);
				//request.addProperty("arg0", "C:\\dcm\\agfacr\\1335805255\\1335805259_0.DCM");
				request.addProperty("arg0", sFilePath);
				SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
						SoapEnvelope.VER11);
				envelope.setOutputSoapObject(request);
				HttpTransportSE ht = new HttpTransportSE(URL);
				ht.debug = true;
				try {
					ht.call("http://webservice.lemax.com/getDcmJpg", envelope);
					if (envelope.getResponse() != null) {
						DebugUtil.debug("����");
						Object ret = (Object)envelope.getResponse();
						String retString = String.valueOf(ret);
						DebugUtil.debug("retString--->"+retString);
						Log.e("retString", retString);
						byte[] retByte = (byte[]) Base64.decode(retString);
						File jpgFile = new File(SDPATH + sFileName + ".jpg");
						if (jpgFile.exists()){
							jpgFile.delete();
						}
						jpgFile.createNewFile();
						OutputStream output = new FileOutputStream(jpgFile);
						output.write(retByte,0,retByte.length);
						output.flush();
						output.close();
						what = 11;
					} else {
						what = 10;
					}
					
				} catch (IOException e) {
					e.printStackTrace();
				} catch (XmlPullParserException e) {
					e.printStackTrace();
				}
								
			} catch (Exception e) {
				e.printStackTrace();
			}
			handle.sendEmptyMessage(what);
		}
	}
	
	/**
	 * ���ļ�
	 * @param file
	 */ 
	private void openFile(File file){ 
	     
	    Intent intent = new Intent(); 
	    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
	    //����intent��Action���� 
	    intent.setAction(Intent.ACTION_VIEW); 
	    //��ȡ�ļ�file��MIME���� 
	    String type = getMIMEType(file); 
	    //����intent��data��Type���ԡ� 
	    intent.setDataAndType(Uri.fromFile(file), type); 
	    //��ת 
	    startActivity(intent);
	} 
	
	/**
	 * �����ļ���׺����ö�Ӧ��MIME���͡�
	 * @param file
	 */ 
	private String getMIMEType(File file) { 
	     
	    String type="*/*"; 
	    String fName = file.getName(); 
	    //��ȡ��׺��ǰ�ķָ���"."��fName�е�λ�á� 
	    int dotIndex = fName.lastIndexOf("."); 
	    if(dotIndex < 0){ 
	        return type; 
	    } 
	    /* ��ȡ�ļ��ĺ�׺��*/ 
	    String end=fName.substring(dotIndex,fName.length()).toLowerCase(); 
	    if(end=="")return type; 
	    //��MIME���ļ����͵�ƥ������ҵ���Ӧ��MIME���͡� 
	    for(int i=0;i<MIME_MapTable.length;i++){ //MIME_MapTable??��������һ�������ʣ����MIME_MapTable��ʲô�� 
	        if(end.equals(MIME_MapTable[i][0])) 
	            type = MIME_MapTable[i][1]; 
	    }        
	    return type; 
	} 
	
	private final String[][] MIME_MapTable={ 
            //{��׺����MIME����} 
            {".3gp",    "video/3gpp"}, 
            {".apk",    "application/vnd.android.package-archive"}, 
            {".asf",    "video/x-ms-asf"}, 
            {".avi",    "video/x-msvideo"}, 
            {".bin",    "application/octet-stream"}, 
            {".bmp",    "image/bmp"}, 
            {".c",  "text/plain"}, 
            {".class",  "application/octet-stream"}, 
            {".conf",   "text/plain"}, 
            {".cpp",    "text/plain"}, 
            {".doc",    "application/msword"}, 
            {".docx",   "application/vnd.openxmlformats-officedocument.wordprocessingml.document"}, 
            {".xls",    "application/vnd.ms-excel"},  
            {".xlsx",   "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"}, 
            {".exe",    "application/octet-stream"}, 
            {".gif",    "image/gif"}, 
            {".gtar",   "application/x-gtar"}, 
            {".gz", "application/x-gzip"}, 
            {".h",  "text/plain"}, 
            {".htm",    "text/html"}, 
            {".html",   "text/html"}, 
            {".jar",    "application/java-archive"}, 
            {".java",   "text/plain"}, 
            {".jpeg",   "image/jpeg"}, 
            {".jpg",    "image/jpeg"}, 
            {".js", "application/x-javascript"}, 
            {".log",    "text/plain"}, 
            {".m3u",    "audio/x-mpegurl"}, 
            {".m4a",    "audio/mp4a-latm"}, 
            {".m4b",    "audio/mp4a-latm"}, 
            {".m4p",    "audio/mp4a-latm"}, 
            {".m4u",    "video/vnd.mpegurl"}, 
            {".m4v",    "video/x-m4v"},  
            {".mov",    "video/quicktime"}, 
            {".mp2",    "audio/x-mpeg"}, 
            {".mp3",    "audio/x-mpeg"}, 
            {".mp4",    "video/mp4"}, 
            {".mpc",    "application/vnd.mpohun.certificate"},        
            {".mpe",    "video/mpeg"},   
            {".mpeg",   "video/mpeg"},   
            {".mpg",    "video/mpeg"},   
            {".mpg4",   "video/mp4"},    
            {".mpga",   "audio/mpeg"}, 
            {".msg",    "application/vnd.ms-outlook"}, 
            {".ogg",    "audio/ogg"}, 
            {".pdf",    "application/pdf"}, 
            {".png",    "image/png"}, 
            {".pps",    "application/vnd.ms-powerpoint"}, 
            {".ppt",    "application/vnd.ms-powerpoint"}, 
            {".pptx",   "application/vnd.openxmlformats-officedocument.presentationml.presentation"}, 
            {".prop",   "text/plain"}, 
            {".rc", "text/plain"}, 
            {".rmvb",   "audio/x-pn-realaudio"}, 
            {".rtf",    "application/rtf"}, 
            {".sh", "text/plain"}, 
            {".tar",    "application/x-tar"},    
            {".tgz",    "application/x-compressed"},  
            {".txt",    "text/plain"}, 
            {".wav",    "audio/x-wav"}, 
            {".wma",    "audio/x-ms-wma"}, 
            {".wmv",    "audio/x-ms-wmv"}, 
            {".wps",    "application/vnd.ms-works"}, 
            {".xml",    "text/plain"}, 
            {".z",  "application/x-compress"}, 
            {".zip",    "application/x-zip-compressed"}, 
            {"",        "*/*"}   
        }; 
	
	/**
	 * 
	* @ClassName: FilePathTask 
	* @Description: TODO(��ȡ�ļ�����·��) 
	* @author wanghailong 81813780@qq.com 
	* @date 2012-12-31 ����4:59:57 
	*
	 */
	private class FilePathTask extends AsyncTask<String, Void, String>{
		
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String sql="select filename from dicomtemp@pacsdbserver where examno='"+exam_no+"'";
			ArrayList<DataEntity> dataList=WebServiceHelper.getWebServiceData(sql);
			for (int i = 0; i < dataList.size(); i++) {
				//String path=dataList.get(i).get("filename").replace("\\Dcmtemp", "");
				String path=dataList.get(i).get("filename");
				data.add(path);
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			mProcessView.setVisibility(View.GONE);
			if (data.size()==0) {
				TextView emptyView=(TextView) CheckdetailActivity.this.findViewById(R.id.empty);
				listView.setEmptyView(emptyView);
			}else {
				CheckImageAdapter adapter=new CheckImageAdapter(CheckdetailActivity.this, data);
//				listView.setAdapter(new ArrayAdapter<String>(CheckdetailActivity.this, android.R.layout.simple_expandable_list_item_1,data));
				listView.setAdapter(adapter);
			}
		}
	}
}

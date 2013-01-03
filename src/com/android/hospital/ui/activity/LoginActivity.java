package com.android.hospital.ui.activity;



import java.util.ArrayList;

import com.android.hospital.HospitalApp;
import com.android.hospital.asyntask.UpdateDBTask;
import com.android.hospital.asyntask.add.DrugOrNonDrugTask;
import com.android.hospital.entity.DataEntity;
import com.android.hospital.entity.GroupOrderEntity;
import com.android.hospital.service.MyService;
import com.android.hospital.util.DebugUtil;
import com.android.hospital.webservice.WebServiceHelper;
import com.android.hospital.widgets.MyProssDialog;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;
/**
 * 
* @ClassName: LoginActivity 
* @Description: TODO(��¼����) 
* @author wanghailong 81813780@qq.com 
* @date 2012-12-14 ����11:37:58 
*
 */
public class LoginActivity extends Activity implements OnClickListener{

	private SharedPreferences sp;
	private EditText mUserEditText,mPwdEditText;
	private CheckBox mCheckBox;
	private Button mOkBut,mCancleBut;
	private ArrayList<GroupOrderEntity> groupOrderList;//�ײ�ҽ������
	private HospitalApp app;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		app=(HospitalApp) getApplication();
		
		sp=getSharedPreferences("UserInfo", 0);
		initView();
		isAutoLogin();
		/*Intent intent=new Intent();
		intent.setClass(this, MyService.class);
		startService(intent);*/
		
		new UpdateDBTask(this).execute();//��������
	}

	
    /**
     * 
    * @Title: initView 
    * @Description: TODO(��ʼ���ؼ�) 
    * @param     �趨�ļ� 
    * @return void    �������� 
    * @throws
     */
	private void initView() {
		mUserEditText=(EditText) findViewById(R.id.login_username);
		mPwdEditText=(EditText) findViewById(R.id.login_password);
		mCheckBox=(CheckBox) findViewById(R.id.login_checkBox1);
		mOkBut=(Button) findViewById(R.id.login_ok);
		mCancleBut=(Button) findViewById(R.id.login_cancel);
		
		mOkBut.setOnClickListener(this);
		mCancleBut.setOnClickListener(this);
		mCheckBox.setOnCheckedChangeListener(new MyCheckedChangeListener());
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.login_ok:
			String username=mUserEditText.getText().toString();
			String pwd=mPwdEditText.getText().toString();
			if (username.equals("")||pwd.equals("")) {
				Toast.makeText(getApplicationContext(), "�û��������벻��Ϊ��!", Toast.LENGTH_SHORT).show();
			}else {
				new LoginTask().execute();
			}
			break;
		case R.id.login_cancel:
			finish();
			break;

		}
	}
	
	//��ס��������¼��ڲ���
    private class MyCheckedChangeListener implements OnCheckedChangeListener{

			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				sp=getSharedPreferences("UserInfo", 0);
				sp.edit().putBoolean("ischoose", isChecked).commit();
				
			}
			
	}
	/**
	 * 
	* @Title: login 
	* @Description: TODO(��¼��ʼ��) 
	* @param     �趨�ļ� 
	* @return void    �������� 
	* @throws
	 */
	private void login(){	
		if (mCheckBox.isChecked()) {
			sp=getSharedPreferences("UserInfo", Context.MODE_WORLD_WRITEABLE|Context.MODE_WORLD_READABLE); 
			sp.edit().putString("username", mUserEditText.getText().toString()).commit();
			sp.edit().putString("password", mPwdEditText.getText().toString()).commit();		
		}
		
	}
	
	/**
	 * 
	* @Title: isAutoLogin 
	* @Description: TODO(�Ƿ��ס����) 
	* @param     �趨�ļ� 
	* @return void    �������� 
	* @throws
	 */
	private void isAutoLogin(){
		boolean flag=sp.getBoolean("ischoose", false);
		if (flag) {
			String username=sp.getString("username", "");
			String pwd=sp.getString("password", "");
			mUserEditText.setText(username);
			mPwdEditText.setText(pwd);
			mCheckBox.setChecked(flag);
		}
	}
	
	/**
	 * 
	* @ClassName: LoginTask 
	* @Description: TODO(��¼����) 
	* @author wanghailong 81813780@qq.com 
	* @date 2012-12-18 ����11:06:46 
	*
	 */
	private class LoginTask extends AsyncTask<Void, Void, String>{
		
		private MyProssDialog mDialog;
		
		@Override
		protected void onPreExecute() {
            mDialog=new MyProssDialog(LoginActivity.this, "��¼", "���ڵ�¼�����Ժ�...");
		}

		@Override
		protected String doInBackground(Void... params) {
			// TODO Auto-generated method stub
			String username=mUserEditText.getText().toString().trim().toUpperCase();
			String pwd=mPwdEditText.getText().toString().trim().toUpperCase();
			String result=WebServiceHelper.getUserName(username, pwd);
			app.setLoginName(username);
			startGroupOrderTask();
			return result;
		}
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			mDialog.cancel();
			if (null==result||result.equals("��")) {
				Toast.makeText(getApplicationContext(), "��¼ʧ�ܣ��û������������!", Toast.LENGTH_SHORT).show();
			}else {
				String[] strArr=result.split("<;>");
				app.setDoctor(strArr[0]);//����ҽ������
				endGroupOrderTask();
				login();
				Intent intent=new Intent();
				intent.setClass(LoginActivity.this, MainActivity.class);
				startActivity(intent);
				finish();
			}
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
        MenuItem actionItem = menu.add(Menu.NONE, Menu.FIRST, 0, "�汾����");

        // Items that show as actions should favor the "if room" setting, which will
        // prevent too many buttons from crowding the bar. Extra items will show in the
        // overflow area.
        actionItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        // Items that show as actions are strongly encouraged to use an icon.
        // These icons are shown without a text description, and therefore should
        // be sufficiently descriptive on their own.
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		//Toast.makeText(this, "Selected Item: " + item.getTitle(), Toast.LENGTH_SHORT).show();
		Intent intentCheckVersion = new Intent();
		intentCheckVersion.setClass(LoginActivity.this,
				CheckVersionActivity.class);
		startActivity(intentCheckVersion);
		return true;
	}
	
	/**
	 * 
	* @Title: startGroupOrderTask 
	* @Description: TODO(�ײ�ҽ������) 
	* @param     �趨�ļ� 
	* @return void    �������� 
	* @throws
	 */
	private void startGroupOrderTask(){
		String sql = "select group_order_master.group_order_id ,group_order_master.title from group_order_master,group_order_selection" +
				     " where group_order_master.group_order_id=group_order_selection.group_order_id" +
				     " and group_order_selection.user_name='"+app.getLoginName()+"'";
		ArrayList<DataEntity> dataList=WebServiceHelper.getWebServiceData(sql);
		groupOrderList=new ArrayList<GroupOrderEntity>();
		for (int i = 0; i < dataList.size(); i++) {
			GroupOrderEntity entity=new GroupOrderEntity();
			entity.group_order_id=dataList.get(i).get("group_order_id");
			entity.title=dataList.get(i).get("title");
			groupOrderList.add(entity);
		}
	}
	
	private void endGroupOrderTask(){
		app.setGroupOrderList(groupOrderList);
	}
}

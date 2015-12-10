package com.tonggou.andclient;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tonggou.andclient.app.TongGouApplication;
import com.tonggou.andclient.myview.AbsViewHolderAdapter;
import com.tonggou.andclient.myview.DoubleListPopupWindow;
import com.tonggou.andclient.myview.SimpleTitleBar;
import com.tonggou.andclient.myview.SingleListPopupWindow;
import com.tonggou.andclient.network.API;
import com.tonggou.andclient.network.AsyncRequestHandler;
import com.tonggou.andclient.network.NetworkState;
import com.tonggou.andclient.network.RequestClient;
import com.tonggou.andclient.parse.VehicleListParser;
import com.tonggou.andclient.util.INFO;
import com.tonggou.andclient.util.PreferenceUtil;
import com.tonggou.andclient.vo.JuheTransgress;
import com.tonggou.andclient.vo.JuheTransgressArea;
import com.tonggou.andclient.vo.JuheTransgressSearchCondition;
import com.tonggou.andclient.vo.VehicleInfo;
import com.tonggou.andclient.vo.VehicleType;

/**
 * Υ�²�ѯ
 * @author lwz
 *
 */
public class TransgressQueryActivity extends AbsBackableActivity implements View.OnClickListener {
	
	private final String EMPTY_VEHICLE_MSG = "�������ӳ���";
	private final String PREF_NAME_TRANSGRES = "pref_transgress";
	private final String PREF_KEY_LAST_SELECTED_CITY = "last_selected_city";
	private final String PREF_KEY_LAST_SELECTED_VEHICLE = "last_selected_vehicle";
	
	
	public final int REQUEST_CODE_CHANGE_VEHICLE_INFO = 0x123;
	
	private TextView mVehicleTypeSelectText;	// ����ѡ���
	private TextView mVehicleSelectText;	// ����ѡ���
	private TextView mCitySelectText;		// ����ѡ���
	private ViewGroup mTotalDeductScoreContainer;	// �ۻ��۷ָ�����
	private TextView mTotalDeductScoreText;			// �ۻ��۷�
	private TextView mTotalForfeitText;				// �ۻ�����
	private ListView mQueryResultList;				// ��ѯ����б�
	private Button mQueryButton;					// ��ѯ��ť
	private SingleListPopupWindow mVehicleTypeSelectPopupWindow;	// ����ѡ�񵯳���
	private SingleListPopupWindow mVehicleSelectPopupWindow;		// ����ѡ�񵯳���
	private DoubleListPopupWindow mCitySelectPopupWindow;			// ����ѡ�񵯳���
	
	private VehicleTypeAdapter mVehicleTypeAdapter;
	private VehicleAadpter mVehicleAdapter;
	private AreaAdapter mProvinceAdapter;
	private AreaAdapter mCityAdapter;
	private TransgressAdapter mTransgressAdapter;
	
	private VehicleType mVehicleType;				// ѡ�е� ��������
	private VehicleInfo mVehicleInfo;				// ѡ�еĳ�������ѯ��
	
	private int mScore = 0;		// �ۻ��۷�
	private int mMoney = 0;		// �ۻ�����
	
	private int mCurrentRequestQueryTime = 0; // ��ѯ����Ĵ���������������ѡ�г��еĸ���ʱ��Ҫ����Ϊ 0
	private int mRequestQueryTime = 0;	// ��Ҫ��ѯ�Ĵ�������ֵ�����ÿ�β�ѯѡ����еĸ���
	
	private SelectedAreaList mSelectedCity = new SelectedAreaList();

	@Override
	protected int getContentLayout() {
		return R.layout.activity_transgress_query;
	}
	
	@Override
	protected void afterTitleBarCreated(SimpleTitleBar titleBar, Bundle saveInstanceState) {
		super.afterTitleBarCreated(titleBar, saveInstanceState);
		titleBar.setTitle(R.string.transgress_query_title);
		titleBar.setRightButtonText(R.string.set_bindcar_title);
		titleBar.setOnRightButtonClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				turnToBindCarsActivity();
			}
		});
	}
	
	/**
	 * ��ת��������������
	 */
	private void turnToBindCarsActivity() {
		Intent intent = new Intent(this, BindCarsActivity.class);
		startActivity(intent);
	}

	@Override
	protected void findViews(Bundle saveInstanceState) {
		super.findViews(saveInstanceState);
		
		mVehicleTypeSelectText = (TextView) findViewById(R.id.select_vehicle_type_text);
		mVehicleSelectText = (TextView) findViewById(R.id.select_vehicle_text);
		mCitySelectText = (TextView) findViewById(R.id.select_city_text);
		mTotalDeductScoreContainer = (ViewGroup) findViewById(R.id.total_deduct_container);
		mTotalDeductScoreText = (TextView) findViewById(R.id.total_deduct_score);
		mTotalForfeitText = (TextView) findViewById(R.id.total_forfeit);
		mQueryResultList = (ListView) findViewById(R.id.query_result_list);
		mQueryButton = (Button) findViewById(R.id.query_btn);
		afterViews();
	}

	private void afterViews() {
		createAdapter();
		setDefaultVehicleType();
		updateVehicleType();
		mQueryResultList.setEmptyView(findViewById(R.id.listview_empty_view));
		mQueryResultList.setAdapter(mTransgressAdapter);
		setListener();
	}

	private void setListener() {
		mVehicleTypeSelectText.setOnClickListener(this);
		mVehicleSelectText.setOnClickListener(this);
		mCitySelectText.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.select_vehicle_type_text: onSelectVehicleType(); break;
			case R.id.select_vehicle_text: onSelectVehicle(); break;
			case R.id.select_city_text: onSelectCity(); break;
		}
	}
	
	public void onQueryBtnClick(View v) {
		String vehicleNo = mVehicleSelectText.getText().toString().trim();
		if( TextUtils.isEmpty(vehicleNo) ) {
			TongGouApplication.showToast("��ѡ���ƺ�");
			return;
		}
		if( mSelectedCity.isEmpty() ) {
			TongGouApplication.showToast("����ѡ��Ҫ��ѯ�ĳ���");
			return;
		}
		if( ! isCanQuery(mVehicleInfo, mSelectedCity) ) {
			TongGouApplication.showToast("���ĳ�����Ϣ���������벹ȫ");
			turnToChangeBindCarActivity(mVehicleInfo);
			return;
		}
		
		mTransgressAdapter.clear();
		doRequestQuery();
	}
	
	private void doRequestQuery() {
		mRequestQueryTime = mSelectedCity.size();
		mQueryButton.setEnabled(false);
		for( JuheTransgressArea city : mSelectedCity ) {
			requestQueryResult(city);
		}
	}

	private void createAdapter() {
		mVehicleTypeAdapter = new VehicleTypeAdapter(this, new ArrayList<VehicleType>(), R.layout.popview_item);
		mVehicleAdapter = new VehicleAadpter(this, new ArrayList<VehicleInfo>(), R.layout.popview_item);
		mProvinceAdapter = new AreaAdapter(this, new ArrayList<JuheTransgressArea>(), R.layout.popview_item);
		mCityAdapter = new AreaAdapter(this, new ArrayList<JuheTransgressArea>(), R.layout.popview_item);
		mTransgressAdapter = new TransgressAdapter(this, new ArrayList<JuheTransgress>(), R.layout.item_list_transgress);
	}
	
	private void setDefaultVehicleType() {
		mVehicleType = new VehicleType();
		mVehicleType.setCar("С�ͳ�");
		mVehicleType.setId("02");
		mVehicleTypeSelectText.setText(mVehicleType.getCar());
	}
	
	private void onSelectVehicleType() {
		mVehicleTypeSelectPopupWindow = new SingleListPopupWindow(this, mVehicleTypeAdapter);
		mVehicleTypeSelectPopupWindow.setOnListItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				VehicleType type = mVehicleTypeAdapter.getData().get(position);
				mVehicleType = type;
				mVehicleTypeSelectText.setText(type.getCar());
			}
		});
		mVehicleTypeSelectPopupWindow.showAsDropDown(mVehicleTypeSelectText);
	}

	/**
	 * ѡ����û��󶨵�����
	 */
	private void onSelectVehicle() {
		mVehicleSelectPopupWindow = new SingleListPopupWindow(this, mVehicleAdapter);
		mVehicleSelectPopupWindow.setOnListItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				VehicleInfo vehicleInfo = mVehicleAdapter.getData().get(position);
				if( EMPTY_VEHICLE_MSG.equals(vehicleInfo.getVehicleNo()) ) {
					turnToAddBindCarActivit();
					return;
				} else {
					setDefaultSelectVehicle(vehicleInfo);
				}
			}
		});
		mVehicleSelectPopupWindow.showAsDropDown(mVehicleSelectText);
	}
	
	private void turnToAddBindCarActivit() {
		Intent intent = new Intent();
		intent.setClass(this, AddBindCarActivity.class);
		startActivity(intent);
	}
	
	private void turnToChangeBindCarActivity(VehicleInfo vehicleInfo) {
		Intent intent = new Intent();
		intent.setClass(this, ChangeTransgressQueryConditionActivity.class);
		intent.putExtra(ChangeTransgressQueryConditionActivity.KEY_PARAM_VEHICLE_INFO, vehicleInfo);
		intent.putExtra(ChangeTransgressQueryConditionActivity.KEY_PARAM_SELECTED_AREA_LIST, mSelectedCity);
		startActivityForResult(intent, REQUEST_CODE_CHANGE_VEHICLE_INFO);
	}
	
	/**
	 * ѡ���ѯΥ�µĳ���
	 */
	private void onSelectCity() {
		mCitySelectPopupWindow = new DoubleListPopupWindow(this, mProvinceAdapter, mCityAdapter);
		mCitySelectPopupWindow.setListItemWidthAndHeight(-1, 10000);
		mCitySelectPopupWindow.setOnLeftListItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				mCityAdapter.updateAreaByProvince( mProvinceAdapter.getData().get(position) );
			}
		});
		
		mCitySelectPopupWindow.setOnRightListItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				JuheTransgressArea area = mCityAdapter.getData().get(position);
				if( mSelectedCity.contains( area ) ) {
					mSelectedCity.remove(area);
				} else {
					mSelectedCity.add(area);
				}
				mCitySelectText.setText(getSelectedCityNames());
			}
		});
		mCitySelectPopupWindow.showAsDropDown(mCitySelectText);
	}
	
	private String getSelectedCityNames() {
		String names = mSelectedCity.toString();
		return names.substring(1, names.length() - 1);
	}
	
	/**
	 * ��ʾ�ۼƿ۷ּ�����Ĳ���
	 * @param deductScore �۷���
	 * @param forfeit ������
	 */
	@SuppressWarnings("unused")
	private void showTotalDeductContainer(String deductScore, String forfeit) {
		mTotalDeductScoreContainer.setVisibility(View.VISIBLE);
		mTotalDeductScoreText.setText(deductScore);
		mTotalForfeitText.setText(forfeit);
	}
	
	/**
	 * �����ۼƿ۷ּ�����Ĳ���
	 */
	@SuppressWarnings("unused")
	private void hideTotalDeductContainer() {
		mTotalDeductScoreContainer.setVisibility(View.GONE);
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		requestBindVehiclesData();
		requestJuheSupportCity();
		if( mSelectedCity == null ) {
			mSelectedCity = new SelectedAreaList();
		}
		mSelectedCity.addAll( restoreLastSelectedCities() );
		mCitySelectText.setText(getSelectedCityNames());
	}
	
	@Override
	protected void onPause() {
		storeLastSelectedCities(mSelectedCity);
		super.onPause();
	}
	
	private void updateVehicleType() {
		String vehicleJsonTypeStr = "[{\"car\":\"���ͳ�\",\"id\":\"01\"},{\"car\":\"С�ͳ�\",\"id\":\"02\"},{\"car\":\"ʹ������\",\"id\":\"03\"},{\"car\":\"�������\",\"id\":\"04\"},{\"car\":\"��������\",\"id\":\"05\"},{\"car\":\"�⼮����\",\"id\":\"06\"},{\"car\":\"��������\",\"id\":\"16\"}]";
		Gson gson = new Gson();
		List<VehicleType> vehicleTypeData = 
				gson.fromJson(vehicleJsonTypeStr, new TypeToken<List<VehicleType>>(){}.getType());
		mVehicleTypeAdapter.update(vehicleTypeData);
	}
	
	private void requestBindVehiclesData() {
		String url = INFO.HTTP_HEAD+INFO.HOST_IP+"/vehicle/list/userNo/"+ sharedPreferences.getString(NAME, "");
		RequestClient client = new RequestClient(this);
		client.get(url, new AsyncRequestHandler() {

			@Override
			public void onStart() {
				onRequestStart();
			}

			@Override
			public void onSuccess(String result) {
				TongGouApplication.showLog(result);
				if( mVehicleSelectPopupWindow != null ) {
					mVehicleSelectPopupWindow.dismiss();
				}
				VehicleListParser vehicleListParser = new VehicleListParser();
				vehicleListParser.parsing(result);
				if( vehicleListParser.isSuccessfull()) {
					List<VehicleInfo> data = vehicleListParser.getVehicleListResponse().getVehicleList();
					checkDefaultVehicle(data);
					mVehicleAdapter.update(data);
				} else {
					TongGouApplication.showToast(vehicleListParser.getVehicleListResponse().getMessage());
				}
			}

			@Override
			public void onFailure(String msg) {
				onRequestFilure(msg);
			}

			@Override
			public void onFinish() {
				onRequestFinish();
			}
			
		});
	}
	
	private void checkDefaultVehicle(List<VehicleInfo> vehicleInfos) {
		String selectedVehicleStr = PreferenceUtil.getString(this, PREF_NAME_TRANSGRES, getPrefKeyByUser(PREF_KEY_LAST_SELECTED_VEHICLE));
		if( !TextUtils.isEmpty( selectedVehicleStr)  ) {
			Gson gson = new Gson();
			VehicleInfo selectedVehicle = gson.fromJson(selectedVehicleStr, VehicleInfo.class);
			if( vehicleInfos.contains(selectedVehicle) ) {
				// ���ݿ��ܱ仯������������
				selectedVehicle = vehicleInfos.get( vehicleInfos.indexOf(selectedVehicle) );
				setDefaultSelectVehicle(selectedVehicle);
				return;
			}
		}
		
		for( VehicleInfo vehicleInfo : vehicleInfos ) {
			if( "YES".equals( vehicleInfo.getIsDefault() )) {
				setDefaultSelectVehicle(vehicleInfo);
				break;
			}
		}
	}
	
	private void setDefaultSelectVehicle(VehicleInfo v) {
		mVehicleInfo = v;
		mVehicleSelectText.setText(mVehicleInfo.getVehicleNo());
		PreferenceUtil.putString(this, PREF_NAME_TRANSGRES, getPrefKeyByUser(PREF_KEY_LAST_SELECTED_VEHICLE), new Gson().toJson(v));
	}
	
	/**
	 * �ó����Ƿ�ɲ�ѯ����Ϣ�Ƿ�������
	 * @param vehicleInfo
	 * @return
	 */
	private boolean isCanQuery(VehicleInfo vehicleInfo, SelectedAreaList selectCity) {
		
		boolean[] isNeed = getQueryCondition(selectCity);
		if( isNeed[0] && TextUtils.isEmpty( vehicleInfo.getEngineNo() )) {
			return false;
		}
		if( isNeed[1] && TextUtils.isEmpty(vehicleInfo.getVehicleVin()) ) {
			return false;
		} 
		if( isNeed[2] && TextUtils.isEmpty( vehicleInfo.getRegistNo() ) ) {
			return false;
		}
		
		return true;
	}
	
	/**
	 * �õ���ѯ����
	 * @return boolean[0] = �������ţ�boolean[1] = ���ܺţ� boolean[2] = �ȼ�֤��ţ���Ϊ true ʱ����Ҫ
	 */
	public static boolean[] getQueryCondition(ArrayList<JuheTransgressArea> selectCity) {
		boolean[] isNeed = new boolean[3];
		for(  JuheTransgressArea area : selectCity ) {
			JuheTransgressSearchCondition condition = area.getJuheViolateRegulationCitySearchCondition();
			isNeed[0] = isNeed[0] || ( condition.getEngine() != 0);
			isNeed[1] = isNeed[1] || ( condition.getClassa() != 0);
			isNeed[2] = isNeed[2] || ( condition.getRegist() != 0);
		}
		return isNeed;
	}
	
	
	private void requestJuheSupportCity() {
		RequestClient request = new RequestClient(this);
		
		String url = INFO.HTTP_HEAD+INFO.HOST_IP+ "/violateRegulations/juhe/area/list";
			request.get(url, new AsyncRequestHandler() {

			@Override
			public void onStart() {
				super.onStart();
				onRequestStart();
			}
			
			@Override
			public void onSuccess(String result) {
				super.onSuccess(result);
				try {
					JSONObject root = new JSONObject(result);
					if( "SUCCESS".equalsIgnoreCase(root.getString("status")) ) {
						String areaList = root.getJSONArray("result").toString();
						parseArea(areaList);
					} else {
						TongGouApplication.showToast( root.getString("message") );
						TongGouApplication.showLog( root.getString("message") );
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(String msg) {
				onRequestFilure(msg);
			} 

			@Override
			public void onFinish() {
				onRequestFinish();
			}

		});
	}
	
	private void parseArea(String areaList){
		Gson gson = new Gson();
		List<JuheTransgressArea> data = gson.fromJson(areaList,
				new TypeToken<List<JuheTransgressArea>>(){}.getType());
		mProvinceAdapter.update(data);
		mCityAdapter.updateAreaByProvince( data.get(0) );
	}
	
	private void requestQueryResult(JuheTransgressArea city) {
		StringBuilder params = new StringBuilder();
		params.append("&city=" + city.getJuheCityCode());
		params.append("&hphm=" + mVehicleInfo.getVehicleNo());
		params.append("&hpzl=" + mVehicleType.getId());
		
		JuheTransgressSearchCondition condition = city.getJuheViolateRegulationCitySearchCondition();
		
		String engineNo = mVehicleInfo.getEngineNo();
		TongGouApplication.showLog("JSON -- " + new Gson().toJsonTree(mVehicleInfo));
		TongGouApplication.showLog( "engineNo " + engineNo);
		if( !TextUtils.isEmpty( engineNo ) && engineNo.length() >= condition.getEngineno() ) {
			params.append("&engineno=" + subString( engineNo, condition.getEngineno()));
			TongGouApplication.showLog( "engineNo " + params.toString());
		}
		
		String classno = mVehicleInfo.getVehicleVin();
		TongGouApplication.showLog( "classno " + classno);
		if( !TextUtils.isEmpty( classno ) && classno.length() >= condition.getClassno() ) {
			params.append("&classno=" + subString( classno, condition.getClassno()));
			TongGouApplication.showLog( "classno " + params.toString());
		}
		String registno = mVehicleInfo.getRegistNo();
		TongGouApplication.showLog( "registno " + registno);
		if( !TextUtils.isEmpty( registno ) && registno.length() >= condition.getRegistno() ) {
			params.append("&registno=" + subString( registno, condition.getRegistno()));
			TongGouApplication.showLog( "registno " + params.toString());
		}
		
		
		RequestClient client = new RequestClient(this);
		client.get(API.JUHE_TRANSGRESS.TRANSGRESS_QUERY + params.toString(), new AsyncRequestHandler() {
//		client.get("http://v.juhe.cn/wz/query?city=GZ&hphm=%E8%B4%B5AVG902&hpzl=02&engineno=180914&regist=12&key=60ad2a9b3c7bcda13b781dabe01fe843&page=2", new AsyncRequestHandler() {

			@Override
			public void onStart() {
				onRequestStart();
			}

			@Override
			public void onSuccess(String result) {
				super.onSuccess(result);
				
				try {
					JSONObject resultJsonObj = new JSONObject(result);
					if( "200".equals( resultJsonObj.getString("resultcode")) ) {
						Gson gson = new Gson();
						List<JuheTransgress> data = 
								gson.fromJson(resultJsonObj.getJSONObject("result").getJSONArray("lists").toString(), 
												new TypeToken<List<JuheTransgress>>(){}.getType());
						mTransgressAdapter.append(data);
					}
//					TongGouApplication.showToast( resultJsonObj.getString("reason") );
					
				} catch (JSONException e) {
					e.printStackTrace();
				}
				
				
				TongGouApplication.showLog(result);
			}

			@Override
			public void onFailure(String msg) {
//				if( NetworkState.ERROR_CLIENT_ERROR_SOCKETTIMEOUT_MESSAGE.equals(msg) ) {
//					onRequestFilure("����������ά��");
//				} else {
//					onRequestFilure(msg);
//				}
			}

			@Override
			public void onFinish() {
				onRequestQueryFinish();
			}
			
		});
	}
	
	private String subString(String originStr, int backwardsLength) {
		if( backwardsLength <= 0 ) {
			return originStr;
		}
		return originStr.substring( originStr.length() - backwardsLength , originStr.length());
	}
	
	private synchronized void onRequestStart() {
		TongGouApplication.showLog("onStart");
		getSimpleTitle().showLoadingIndicator();
	}
	
	private synchronized void onRequestFilure(String msg) {
		TongGouApplication.showLog("onFailure  - " + msg);
		TongGouApplication.showToast(msg);
	}
	
	private synchronized void onRequestFinish() {
		TongGouApplication.showLog("onFinish");
		getSimpleTitle().hideLoadingIndicator();
	}
	
	private synchronized void onRequestQueryFinish() {
		TongGouApplication.showLog("onRequestQueryFinish");
		mCurrentRequestQueryTime ++;
		if( mCurrentRequestQueryTime >= mRequestQueryTime ) {
			// ���в�ѯ�Ѿ�������������
			mCurrentRequestQueryTime = 0;
			mQueryButton.setEnabled(true);
			getSimpleTitle().hideLoadingIndicator();
			if( mTransgressAdapter.isEmpty() ) {
				TongGouApplication.showToast("��û��Υ�¼�¼");
			}
		}
	}
	
	private void storeLastSelectedCities(SelectedAreaList cities) {
		Gson gson = new Gson();
		PreferenceUtil.putString(this, PREF_NAME_TRANSGRES, getPrefKeyByUser(PREF_KEY_LAST_SELECTED_CITY), gson.toJson(cities));
	}
	
	@SuppressWarnings("unchecked")
	private ArrayList<JuheTransgressArea> restoreLastSelectedCities() {
		String cities = PreferenceUtil.getString(this, PREF_NAME_TRANSGRES, getPrefKeyByUser(PREF_KEY_LAST_SELECTED_CITY));
		TongGouApplication.showLog("restoreLastSelectedCities | " + cities);
		if( !TextUtils.isEmpty( cities ) && !"null".equalsIgnoreCase(cities) ) {
			Gson gson = new Gson();
			ArrayList<JuheTransgressArea> data = (ArrayList<JuheTransgressArea>)gson.fromJson(cities, new TypeToken<ArrayList<JuheTransgressArea>>() {}.getType() );
			if( data != null ) {
				return data;
			}
		}
		
		return new SelectedAreaList();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if( resultCode == RESULT_OK && resultCode == REQUEST_CODE_CHANGE_VEHICLE_INFO) {
			mCitySelectText.setText("");
			mVehicleInfo = null;
		}
	}
	
	@Override
	protected void onStop() {
		RequestClient.cancle();
		super.onStop();
	}
	
	/**
	 * ͨ���û������õ� sharedPreferences �ļ��������û��󶨣�
	 * @param prefKey
	 * @return
	 */
	private String getPrefKeyByUser( String prefKey ) {
		return prefKey + sharedPreferences.getString(NAME, "");
	}
	
	@Override
	public void onBackPressed() {
		Intent intent = new Intent();
		intent.setClass(this, HomePageActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
		super.onBackPressed();
	}
	
	class VehicleTypeAdapter extends AbsViewHolderAdapter<VehicleType> {

		public VehicleTypeAdapter(Context context, List<VehicleType> data, int layoutRes) {
			super(context, data, layoutRes);
		}

		@Override
		protected void setData(int pos, View convertView, VehicleType itemData) {
			TextView name = getViewFromHolder(convertView, R.id.popview_name);
			name.setText(itemData.getCar());
		}
		
	}
	
	class VehicleAadpter extends AbsViewHolderAdapter<VehicleInfo> {

		public VehicleAadpter(Context context, List<VehicleInfo> data, int layoutRes) {
			super(context, data, layoutRes);
		}

		@Override
		protected void setData(int pos, View convertView, VehicleInfo itemData) {
			TextView name = getViewFromHolder(convertView, R.id.popview_name);
			name.setText(itemData.getVehicleNo());
		}
		
		@SuppressWarnings("unchecked")
		@Override
		public synchronized void update( Collection<? extends VehicleInfo> newData) {
			if(newData.isEmpty()) {
				VehicleInfo emptyVehicle = new VehicleInfo();
				emptyVehicle.setVehicleNo(EMPTY_VEHICLE_MSG);
				((ArrayList<VehicleInfo>)newData).add(emptyVehicle);
			}
			super.update(newData);
		}
		
	}
	
	class AreaAdapter extends AbsViewHolderAdapter<JuheTransgressArea> {

		public AreaAdapter(Context context, List<JuheTransgressArea> data, int layoutRes) {
			super(context, data, layoutRes);
		}

		@Override
		protected void setData(int pos, View convertView, JuheTransgressArea itemData) {
			TextView name = getViewFromHolder(convertView, R.id.popview_name);
			name.setText(itemData.getName());
			if( "ACTIVE".equals(itemData.getJuheStatus()) && mSelectedCity.contains(itemData) ) {
				name.setBackgroundResource(R.drawable.cityback);
			} else {
				name.setBackgroundColor(Color.TRANSPARENT);
			}
		}
		
		public void updateAreaByProvince(JuheTransgressArea province) {
			List<JuheTransgressArea> childrenArea = province.getChildren();
			if( childrenArea.isEmpty() ) {
				// ��ֻ��ʡ��û�г���ʱ����ô��ʡ���ǳ��С������Ϻ�
				childrenArea = new ArrayList<JuheTransgressArea>();
				JuheTransgressArea singleChild = province.clone();
				singleChild.setJuheStatus("ACTIVE");
				province.setJuheStatus("IN_ACTIVE");
				childrenArea.add( singleChild );
				
			}
			update(childrenArea);
		}
		
	}
	
	class SelectedAreaList extends ArrayList<JuheTransgressArea> {

		private static final long serialVersionUID = -3055107039811859485L;
		
		@Override
		public boolean add(JuheTransgressArea object) {
			if(size() >= 3) {
				TongGouApplication.showToast("һ�����ֻ��ѡ�� 3 ������");
				return true;
			}
			remove(object);
			return super.add(object);
		}
		
		@Override
		public boolean addAll( Collection<? extends JuheTransgressArea> collection) {
			clear();
			Iterator<? extends JuheTransgressArea> it = collection.iterator();
			boolean flag = true;
			while( it.hasNext() ) {
				flag = flag && add(it.next());
			}
			return flag;
		}
	}
	
	class TransgressAdapter extends AbsViewHolderAdapter<JuheTransgress> {

		public TransgressAdapter(Context context, List<JuheTransgress> data, int layoutRes) {
			super(context, data, layoutRes);
		}

		@Override
		protected void setData(int pos, View convertView, JuheTransgress itemData) {
			TextView time = getViewFromHolder(convertView, R.id.transgress_date);
			TextView area = getViewFromHolder(convertView, R.id.transgress_area);
			TextView act = getViewFromHolder(convertView, R.id.transgress_act);
			TextView code = getViewFromHolder(convertView, R.id.transgress_code);
			TextView score = getViewFromHolder(convertView, R.id.transgress_score);
			TextView money = getViewFromHolder(convertView, R.id.transgress_money);
			
			time.setText(itemData.getDate());
			area.setText(itemData.getArea());
			act.setText(itemData.getAct());
			code.setText("# " + itemData.getCode());
			score.setText(itemData.getFen());
			money.setText("�� " + itemData.getMoney());
		}
		
		@Override
		public synchronized void append(Collection<? extends JuheTransgress> appendData) {
			super.append(appendData);
			for( JuheTransgress jt : appendData ) {
				int fen = 0;
				int money = 0;
				try{
					fen = Integer.valueOf( jt.getFen() );
				} catch (NumberFormatException e) {
					;
				}
				try{
					money = Integer.valueOf( jt.getMoney() );
				} catch (NumberFormatException e) {
					;
				}
				mScore += fen;
				mMoney += money;
			}
			mTotalDeductScoreText.setText( mScore + "��");
			mTotalForfeitText.setText( "��" + mMoney);
		}

		@Override
		public synchronized void update(Collection<? extends JuheTransgress> newData) {
			mScore = 0;
			mMoney = 0;
			mTotalDeductScoreText.setText( mScore + "��");
			mTotalForfeitText.setText( "��" + mMoney);
			
			super.update(newData);
		}
		
	}
}
/*
 * Copyright (C) 2013  WhiteCat 白猫 (www.thinkandroid.cn)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package hssc.androidview.utils.netutil;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.Locale;

/**
 * @Title NetWorkUtil
 * @Package cn.trinea.android.common.netstate
 * @Description 是检测网络的一个工具包
 * @author 白猫
 * @date 2013-1-22 下午 9:35
 * @version V1.0
 */
public class NetWorkUtil {
	public static enum netType {
		WIFI, CMNET, CMWAP, noneNet
	}

	/**
	 * 网络是否可用
	 * @param context
	 * @return
	 */
	public static boolean isNetworkAvailable(Context context) {
		ConnectivityManager mgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo[] info = mgr.getAllNetworkInfo();
		if (info != null) {
			for (int i = 0; i < info.length; i++) {
				if (info[i].getState() == NetworkInfo.State.CONNECTED) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 判断是否有网络连接
	 * @param context
	 * @return
	 */
	public static boolean isNetworkConnected(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
			if (mNetworkInfo != null) {
				return mNetworkInfo.isAvailable();
			}
		}
		return false;
	}
	/**
	 * 网络没有连接
	 */
	public static boolean noNetWork(Context context){
		return !isNetworkConnected(context);
	}

	/**
	 * 判断WIFI网络是否可用
	 * @param context
	 * @return
	 */
	public static boolean isWifiConnected(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkINfo = cm.getActiveNetworkInfo();
		if (networkINfo != null
				&& networkINfo.getType() == ConnectivityManager.TYPE_WIFI) {
			return true;
		}
		return false;
	}

	/**
	 * 判断MOBILE网络是否可用
	 * @param context
	 * @return
	 */
	public static boolean isMobileConnected(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mMobileNetworkInfo = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
			if (mMobileNetworkInfo != null) {
				return mMobileNetworkInfo.isAvailable();
			}
		}
		return false;
	}

	/**
	 * 获取当前网络连接的类型信息
	 * @param context
	 * @return
	 */
	public static int getConnectedType(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
			if (mNetworkInfo != null && mNetworkInfo.isAvailable()) {
				return mNetworkInfo.getType();
			}
		}
		return -1;
	}

	/**
	 * @author 白猫 获取当前的网络状态 -1：没有网络 1：WIFI网络2：wap 网络3：net网络
	 * @param context
	 * @return
	 */
	public static netType getAPNType(Context context) {
		ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		if (networkInfo == null) {
			return netType.noneNet;
		}
		int nType = networkInfo.getType();

		if (nType == ConnectivityManager.TYPE_MOBILE) {
			if (networkInfo.getExtraInfo().toLowerCase(Locale.getDefault()).equals("cmnet")) {
				return netType.CMNET;
			}

			else {
				return netType.CMWAP;
			}
		} else if (nType == ConnectivityManager.TYPE_WIFI) {
			return netType.WIFI;
		}
		return netType.noneNet;

	}
	
	/**
	 * 获取Inet地址
	 * @return
	 */
	public static InetAddress getLocalInetAddress() {
		InetAddress ip = null;
		try {
			Enumeration<NetworkInterface> en_netInterface = NetworkInterface.getNetworkInterfaces();
			while (en_netInterface.hasMoreElements()) {
				NetworkInterface ni = (NetworkInterface) en_netInterface.nextElement();
				Enumeration<InetAddress> en_ip = ni.getInetAddresses();
				while (en_ip.hasMoreElements()) {
					ip = en_ip.nextElement();
					if (!ip.isLoopbackAddress() && ip.getHostAddress().indexOf(":") == -1)
						break;
					else
						ip = null;
				}

				if (ip != null) {
					break;
				}
			}
		} catch (SocketException e) {
			e.printStackTrace();
		}
		return ip;
	}

	/**
	 * 获取Mac地址
	 * @return
	 */
	public static String getMacAddress() /* throws UnknownHostException */{
		String strMacAddr = null;
		try {
			InetAddress ip = getLocalInetAddress();

			byte[] b = NetworkInterface.getByInetAddress(ip).getHardwareAddress();
			StringBuffer buffer = new StringBuffer();
			for (int i = 0; i < b.length; i++) {
				if (i != 0) {
					buffer.append(':');
				}

				String str = Integer.toHexString(b[i] & 0xFF);
				buffer.append(str.length() == 1 ? 0 + str : str);
			}
			strMacAddr = buffer.toString().toUpperCase(Locale.getDefault());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return strMacAddr;
	}
//
//	/***
//	 * 判断能都做任务
//	 * @return
//	 */
//	public static boolean canDoTask(Context context){
//		SharedPreferences preferences = context.getSharedPreferences(UserInfoConstant.USER_INFO, Activity.MODE_PRIVATE);
//		boolean isOpen = preferences.getBoolean(UserInfoConstant.HAS_NETWORK,true);
//		if (isOpen){
//			if (isNetworkAvailable(context)){
//				return true;
//			}
//		}else {
//			if (isWifiConnected(context)){
//				return true;
//			}
//		}
//		return false;
//	}
}

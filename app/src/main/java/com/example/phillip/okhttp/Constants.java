package com.example.phillip.okhttp;

public class Constants {
	/**
	 * ip+端口号
	 */



	public static final String IP = "http://mtecc.oicp.net:8090";
//	public static final String IP = "http://172.17.139.7";

	/**
	 * ip+端口号+项目名
	 */


//	 public static final String IPPRO = "http://phillip0527.u1.luyouxia.net:55427/FSAMS";
	 public static final String IPPRO = "http://47.100.27.75/FSAMS";
	 // public static final String IPPRO = "http://172.17.136.173/FSAMS";


	/**
	 * 图片资源路径
	 */


//   public static final String IMAGEURL = "http://phillip0527.u1.luyouxia.net:55427/image/";
	 public static final String IMAGEURL = "http://47.100.27.75/image/";
   //	public static final String IMAGEURL = "http://172.17.136.173/image/";


	/**
	 * URL前缀0
	 */
	 /* 下载包安装路径 */ 

//	public static final String updateURL = "http://phillip0527.u1.luyouxia.net:55427/image/MMP.apk"; 
	public static final String updateURL = "http://47.100.27.75/image/MMP.apk";
//	 public static final String updateURL = "http://172.17.136.173/image/MMP.apk";  

    //下载进度
    public static final int DOWNLOAD_COMPLETE=1;//下载成功
    public static final int DOWNLOAD_FAIL=0;//下载失败
    public static final int DOWNLOAD_NOMEMORY=-1;//内存不足
    //sd卡路径
    public static final String SDURI = "/storage/emulated/0/Pictures/";
}


//package com.eleven.httpvolleypromiser;
//
//
//import com.eleven.httpvolleypromiser.proxy.ProviderContext;
//import com.eleven.httpvolleypromiser.proxy.base.BaseHttpGetModelDto;
//import com.eleven.httpvolleypromiser.proxy.base.BaseModelDto;
//import com.eleven.httpvolleypromiser.utils.GsonHelper;
//import com.eleven.httpvolleypromiser.proxy.event.IHttpHelperListener;
//import com.eleven.jpromiser.core.Promiser;
//
//import java.io.ByteArrayOutputStream;
//import java.io.InputStream;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.util.Iterator;
//
///**
// * Created by Eleven on 2016/4/13.
// */
//public class WebApiURLHttpProvider extends BaseProvider implements IProxyDataProvider {
//
//    public  final String CHARSET = "utf-8";
//
//
//    @Override
//    public Promiser post(ProviderContext pContext, BaseHttpGetModelDto gInput, BaseModelDto input, IHttpHelperListener l) {
//        String host;
//        if(gInput != null){
//            host = gInput.getQueryStr(pContext.getHost());
//        }else{
//            host = pContext.getHost();
//        }
//        return doHttpPost(pContext,host,"POST",input,l);
//    }
//
//    @Override
//    public Promiser get(ProviderContext pContext, BaseHttpGetModelDto gInput, BaseModelDto input, IHttpHelperListener l) {
//        String host;
//        if(gInput != null){
//            host = gInput.getQueryStr(pContext.getHost());
//        }else{
//            host = pContext.getHost();
//        }
//        return doHttpGet(host,"GET",input,l);
//    }
//
//    private Promiser doHttpPost(ProviderContext pContext, String host, String method, BaseModelDto input, IHttpHelperListener l){
//
//        String inputStr = "";
//        if(input != null)
//            inputStr = GsonHelper.createGson().toJson(input);
//
//        System.err.println("host:"+host);
//        System.err.println("inputStr:"+inputStr);
//
////        if(true){
////            return null;
////        }
//        try {
//            URL url = new URL(host);
//            HttpURLConnection urlConnection = null;
//            urlConnection = (HttpURLConnection) url
//                    .openConnection();
//
////            urlConnection.setRequestMethod("POST");
//            urlConnection.setRequestMethod(method);
//            urlConnection.setDoOutput(true);
//            urlConnection.setConnectTimeout(10000);
//            urlConnection.setReadTimeout(10000);
//	        urlConnection.setRequestProperty("Content-type", "application/json");
////            urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
//            urlConnection.setRequestProperty("Charset", CHARSET);
//            urlConnection.setRequestProperty("Accept-Charset",CHARSET);
//            urlConnection.setRequestProperty("contentType", CHARSET);
//
//            if(pContext.getHeaderList() != null){
//                for (Iterator<String> iter = pContext.getHeaderList().keySet().iterator();iter.hasNext();){
//                    String key = iter.next();
//                    String value = pContext.getHeaderList().get(key);
//                    System.err.println("header_key:"+key + " header_value:"+value);
//                    urlConnection.setRequestProperty(key, value);
//                }
//            }
//
//            urlConnection.connect();
//
//            if(input != null){
////                String param = //"op="+action +
////                        ModelDtoHelper.convertDto2PostParamStr(input);
//                byte[] bts = inputStr.getBytes(CHARSET);
//                urlConnection.getOutputStream().write(bts);// 输入参数
//            }
//
//            int status = urlConnection.getResponseCode();
//            if (status != 200) {
//                l.onError(status, "服务器未连接");
//            }
//
//            InputStream is = urlConnection.getInputStream();
//            int bytetotal = (int) urlConnection.getContentLength();
//
//            String result = inStream2String(is);
//            System.err.println("result:"+result);
//            l.onSuccess(result);
//
//        }catch (Exception e) {
//            e.printStackTrace();
//            l.onError(-1, e.getMessage());
//        }
//        return null;
//    }
//
//
//    private Promiser doHttpGet(String host, String method, BaseModelDto input, IHttpHelperListener l){
//
//        System.err.println("host:"+host);
//        try {
//            URL url = new URL(host);
//            HttpURLConnection urlConnection = null;
//            urlConnection = (HttpURLConnection) url
//                    .openConnection();
//
////            urlConnection.setRequestMethod("POST");
//            urlConnection.setRequestMethod(method);
//            urlConnection.setDoOutput(true);
//            urlConnection.setConnectTimeout(10000);
//            urlConnection.setReadTimeout(10000);
////	        urlConnection.setRequestProperty("Content-type", "application/json");
//            urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
//            urlConnection.setRequestProperty("Charset", CHARSET);
//            urlConnection.setRequestProperty("Accept-Charset",CHARSET);
//            urlConnection.setRequestProperty("contentType", CHARSET);
//
//            urlConnection.connect();
//
//            if(input != null){
//                String param = //"op="+action +
//                        convertDto2PostParamStr(input);
//
//                byte[] bts = param.getBytes(CHARSET);
//                urlConnection.getOutputStream().write(bts);// 输入参数
//            }
//
//            int status = urlConnection.getResponseCode();
//            if (status != 200) {
//                l.onError(status, "服务器未连接");
//            }
//
//            InputStream is = urlConnection.getInputStream();
//            int bytetotal = (int) urlConnection.getContentLength();
//
//            String result = inStream2String(is);
//            System.err.println("result:"+result);
//            l.onSuccess(result);
//
//        }catch (Exception e) {
//            e.printStackTrace();
//            l.onError(-1, e.getMessage());
//        }
//        return null;
//    }
//
//
//    private  String  inStream2String(InputStream is) throws Exception {
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        byte[] buf = new byte[1024];
//        int len = -1;
//        while ((len = is.read(buf)) != -1) {
//            baos.write(buf, 0, len);
//        }
//
//        String defineStr = new String(baos.toByteArray(),CHARSET);
//
//        if(is != null){
//            is.close();
//        }
//
//        if(baos != null){
//            baos.close();
//        }
//        return defineStr;
//    }
//}

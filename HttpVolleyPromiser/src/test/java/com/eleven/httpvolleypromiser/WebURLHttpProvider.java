//package com.eleven.httpvolleypromiser.proxy.handler;
//
//
//import com.eleven.httpvolleypromiser.proxy.base.BaseModelDto;
//import com.eleven.httpvolleypromiser.BaseProvider;
//import com.eleven.httpvolleypromiser.proxy.event.IHttpHelperListener;
//import com.eleven.jpromiser.core.JPromiser;
//
//import java.io.ByteArrayOutputStream;
//import java.io.InputStream;
//import java.net.HttpURLConnection;
//import java.net.URL;
//
///**
// * Created by Eleven on 2016/3/30.
// */
//@Deprecated
//public class WebURLHttpProvider extends BaseProvider implements IDataProvider {
//    public  final String CHARSET = "utf-8";
//    @Override
//    public JPromiser Post(String host, String action, BaseModelDto input, IHttpHelperListener l) {
//        try {
//            URL url = new URL(host);
//            HttpURLConnection urlConnection = null;
//            urlConnection = (HttpURLConnection) url
//                    .openConnection();
//
//
//            urlConnection.setRequestMethod("POST");
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
//            String param = "op="+action +
//                    convertDto2PostParamStr(input);
//
//            byte[] bts = param.getBytes(CHARSET);
//            urlConnection.getOutputStream().write(bts);// 输入参数
//
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

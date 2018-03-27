import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringEscapeUtils;

import com.baidu.translate.demo.TransApi;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class Main {

    // 在平台申请的APP_ID 详见 http://api.fanyi.baidu.com/api/trans/product/desktop?req=developer
    private static final String APP_ID = "20180327000140403";
    private static final String SECURITY_KEY = "3L7bXb0911IU6biziLt2";

    public static void main(String[] args) throws IOException {
        TransApi api = new TransApi(APP_ID, SECURITY_KEY);
        long start = System.currentTimeMillis();
//      读取文件，并存入字符串缓存
        BufferedReader br = new BufferedReader(new InputStreamReader(Main.class.getClassLoader().getResourceAsStream("translateSrc.txt")));
        StringBuffer sb = new StringBuffer();
        String str = "";
        while((str=br.readLine())!=null) {
        	str=str.replaceAll("", "QAQ");
        	sb.append(str+" ");
        }
        System.out.println("src:" + sb.toString());
        String query = sb.substring(0, sb.length());
        String responseString = api.getTransResult(query, "en", "zh");
        JSONObject jObject = JSONObject.fromObject(responseString);
        boolean has_error = jObject.has("error_code");
        if(!has_error) {
	        JSONArray jArray=jObject.getJSONArray("trans_result");
	        JSONObject src_dst=jArray.getJSONObject(0);
	        String dst = src_dst.getString("dst");
	        System.out.println("dst:" + dst);
        }else {
        	String error_code = jObject.getString("error_code");
        	switch(error_code) {
        		case "52001":
        			System.out.println("请求超时");
        			break;
        		case "52002":
        			System.out.println("API接口错误");
        			break;
        		default:
        			System.out.println("其他系统错误");
        	}
        }
        long end = System.currentTimeMillis();
        System.out.println("耗时:"+ (end-start) +"ms");
    }

}

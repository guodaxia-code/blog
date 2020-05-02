package xyz.worldzhile.blog.util;

import org.apache.commons.lang3.StringUtils;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MapUrl {

    /**
     * 只要确保你的编码输入是正确的,就可以忽略掉 UnsupportedEncodingException
     */
    public static String asUrlParams(Map<String, String> source){
        if (source==null||source.size()==0)
            return "";
        Iterator<String> it = source.keySet().iterator();
        StringBuilder paramStr = new StringBuilder();
        while (it.hasNext()){
            String key = it.next();
            String value = source.get(key);
            if (StringUtils.isBlank(value)){
                continue;
            }
            paramStr.append("&").append(key).append("=").append(value);
        }
        // 去掉第一个&

        return paramStr.length()>=1? paramStr.substring(1) :"";
    }

    public static void main(String[] args) {
        HashMap<String, String> stringStringHashMap = new HashMap<>();
                stringStringHashMap.put("title","");
                stringStringHashMap.put("id","");
    }

}

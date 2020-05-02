package xyz.worldzhile.blog.util;

import java.util.Random;
import java.util.UUID;

/**
 * 产生UUID随机字符串工具类
 */
public final class UuidUtil {
	private UuidUtil(){}

	/**
	 * 获取32位uuid
	 * @return
	 */
	public static String getUuid(){

		return UUID.randomUUID().toString().replace("-","");
	}

	/**
	 * 随机生成4位验证码
	 * @return
	 */
	public static String getCheckCode() {
		String base = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
		int size = base.length();
		Random r = new Random();
		StringBuilder sb = new StringBuilder();
		for(int i=1;i<=4;i++){
			//产生0到size-1的随机值
			int index = r.nextInt(size);
			//在base字符串中获取下标为index的字符
			char c = base.charAt(index);
			//将c放入到StringBuffer中去
			sb.append(c);
		}
		return sb.toString();
	}



}

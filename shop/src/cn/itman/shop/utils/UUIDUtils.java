package cn.itman.shop.utils;

import java.util.UUID;

/**
 * 生成唯一随机字符串的工具类
 * @author Deng
 *
 */
public class UUIDUtils {
	
	public static String getUUID() {
		return UUID.randomUUID().toString().replace("-", "");
	}
	
	
}

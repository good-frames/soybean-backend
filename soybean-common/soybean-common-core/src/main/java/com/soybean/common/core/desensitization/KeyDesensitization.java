package com.soybean.common.core.desensitization;


/**
 * 秘钥脱敏
 *
 * @author lijx
 * @date 2022/5/31
 */
public class KeyDesensitization extends AbstractDesensitization {

	@Override
	public String desensitize(String value) {
		return value.replaceAll("(?<=\\w{1})\\w(?=\\w{3})", "*");
	}

}

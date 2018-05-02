package com.ximo.springbootblogmaster.handler;

import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import static com.ximo.springbootblogmaster.constant.CommonConstant.SEMICOLON;

/**
 * @author 朱文赵
 * @date 2018/4/8
 * @description 获得bean验证的信息.
 */
public class ConstraintViolationExceptionHandler {

	/**
	 * 获取bean验证批量异常信息
	 *
	 * @param e #{@link ConstraintViolationException} 违反约束异常
	 * @return bean验证的错误信息
	 */
	public static String joinMessage(ConstraintViolationException e) {
		return StringUtils.join(e.getConstraintViolations().stream().map(ConstraintViolation::getMessage).toArray(), SEMICOLON);
	}

}

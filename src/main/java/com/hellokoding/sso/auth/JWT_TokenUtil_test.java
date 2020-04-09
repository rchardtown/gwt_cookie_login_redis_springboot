package com.hellokoding.sso.auth;

public class JWT_TokenUtil_test {


		public static final String USERNAME = "https://blog.csdn.net/yuan444075705/article/details/11375969  ?test    ";
		public static final String PASSWORD = " 11111111_  _";

		public static void main(String[] args) {
			String token = JWT_TokenUtil.sign(USERNAME, PASSWORD);

			System.out.println("加密后的token为：" + token);

			boolean flag = JWT_TokenUtil.verfiy(token);

			if(flag){
				System.out.println("校验成功");
			} else {
				System.out.println("校验失败");
			}
		}
	}
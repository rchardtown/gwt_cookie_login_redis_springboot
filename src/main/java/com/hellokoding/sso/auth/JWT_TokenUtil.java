package com.hellokoding.sso.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JWT_TokenUtil {

	//token过期时长30分钟
	private static final long EXPIRE_TIME = 30 * 60 * 1000;
	//token私钥
	private static final String TOKEN_SECRET = "fdafaagagaerregffgtrgergewrgsFgergr";

	/**
	 * JWT认证
	 * @param userName
	 * @param password
	 * @return
	 */
	public static String sign(String userName, String password) {

		String signData = "";
		//过期时间
		Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);

		//step1:设置头信息
		Map<String, Object> header = new HashMap();
		header.put("typ", "JWT");
		header.put("alg", "HS256");

		//step2: 设置payload 和 step3:签名signature
		//指定密钥和加密算法

		Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);//此算法内部做了签名（自动完成了step3）


		signData = JWT.create()
				.withHeader(header)
				.withClaim("userName", userName)
				.withClaim("password", password)
				.withExpiresAt(date)
				.sign(algorithm);

		return signData;
	}
	/**
	 * @Description token解码校验
	 * @param token
	 * @return
	 * @Create 2020-03-03 by jjy
	 */
	public static boolean verfiy(String token) {

		try {
			//指定密钥和加密算法
			Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
			JWTVerifier jwtVerifier = JWT.require(algorithm).build();
			//验证token
			DecodedJWT decodedJWT = jwtVerifier.verify(token);
			String userName = decodedJWT.getClaim("userName").asString();
			System.out.println("userName : "+userName);
			String password = decodedJWT.getClaim("password").asString();
			System.out.println("password : "+password);
			//这里可以自己改改
			if(!JWT_TokenUtil_test.USERNAME.equals(userName) || !JWT_TokenUtil_test.PASSWORD.equals(password)) {
				return false;
			}
			//验证是否过期
			if(new Date().getTime() - decodedJWT.getExpiresAt().getTime() > EXPIRE_TIME){
				return false;
			}

		} catch (Exception e) {
			return false;
		}

		return true;
	}
}

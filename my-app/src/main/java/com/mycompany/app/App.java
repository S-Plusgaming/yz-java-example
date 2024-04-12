package com.mycompany.app;

import java.nio.charset.StandardCharsets;
//import java.security.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.json.JSONObject;


/**
 * 加解密範例
 *
 */
public class App
{
    public static void main( String[] args )
    {
        String jwtToken = encode();
        Decode(jwtToken);
    }

    public static String encode(){
        //  jwt.header
        Map<String, Object> header = new HashMap<>();
        header.put("alg", "HS256");
        header.put("typ", "JWT");
        //  jwt.payload
        Map<String, Object> payload = new HashMap<>();
        Map<String, String> request = new HashMap<>();
        request.put("username", "username");
        request.put("password", "password");
        payload.put("Request", request);
        payload.put("Action", "AGLogin");

        String headerString = JSONObject.valueToString(header);
        System.out.println("------------ JWT header ------------");
        System.out.println(headerString);

        String bodyString = JSONObject.valueToString(payload);
        System.out.println("------------ JWT payload ------------");
        System.out.println(bodyString);

        //  jwt.Signature
        String jwtSecret = "jwtSecret";
        byte[] bytes = jwtSecret.getBytes(StandardCharsets.UTF_8);
        SecretKeySpec key = new SecretKeySpec(bytes, "HmacSHA256");
        String base64UrlEncodeBody = Base64.getUrlEncoder().encodeToString(bodyString.getBytes(StandardCharsets.UTF_8));
        String base64UrlEncodeHeader = Base64.getUrlEncoder().encodeToString(headerString.getBytes(StandardCharsets.UTF_8));
        String message = base64UrlEncodeHeader+"."+base64UrlEncodeBody;
        try {
            Mac hmacSHA256 = Mac.getInstance("HmacSHA256");
            hmacSHA256.init(key);
            byte [] macData = hmacSHA256.doFinal(message.getBytes(StandardCharsets.UTF_8));
            String signature = Base64.getUrlEncoder().withoutPadding().encodeToString(macData);
            System.out.println("------------ JWT Verify Signature ------------");
            System.out.println(signature);

            System.out.println("------------ JWT ------------");
            System.out.println(base64UrlEncodeHeader+"."+base64UrlEncodeBody+"."+signature);
            return base64UrlEncodeHeader+"."+base64UrlEncodeBody+"."+signature;
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }

    }

    public static void Decode(String jwtToken)
    {
        System.out.println("------------ Decode JWT ------------");
        String[] split_string = jwtToken.split("\\.");
        String base64EncodedHeader = split_string[0];
        String base64EncodedBody = split_string[1];
        String base64EncodedSignature = split_string[2];
//        System.out.println(base64EncodedBody);

        System.out.println("~~~~~~~~~ JWT Header ~~~~~~~");
        String header = new String(Base64.getMimeDecoder().decode(base64EncodedHeader.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        System.out.println("JWT Header : " + header);


        System.out.println("~~~~~~~~~ JWT Body ~~~~~~~");
        String body = new String(Base64.getMimeDecoder().decode(base64EncodedBody.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        System.out.println("JWT Body : "+body);
    }
}

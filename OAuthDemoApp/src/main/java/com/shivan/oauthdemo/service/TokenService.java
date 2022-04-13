package com.shivan.oauthdemo.service;

import com.shivan.oauthdemo.dto.SimpleLogin;
import com.shivan.oauthdemo.dto.TokenDetails;
import com.shivan.oauthdemo.dto.WSO2TokenResponseDto;
import com.shivan.oauthdemo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Date;

import static org.apache.tomcat.util.codec.binary.Base64.encodeBase64;

@Service
public class TokenService {

    @Autowired
    Environment env;

    @Value("${client_Id}")
    private String clientId;

    @Value("${client_secret}")
    private String clientSecret;



    RestTemplate restTemplate = new RestTemplate();

    TokenDetails tokenDetails = new TokenDetails();

    public WSO2TokenResponseDto getToken(SimpleLogin simpleLogin, String type, String userId){
        String authString = clientId + ":" + clientSecret;
        byte[] authEncBytes = encodeBase64(authString.getBytes());
        String authorizationHeader = "Basic " + new String(authEncBytes);
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        requestHeaders.add("Authorization", authorizationHeader);
        requestHeaders.add("grant_type", "password");
        requestHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        String createTokenEndpoint = "https://localhost:9443"+"/oauth2/token";

        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
        map.add("scope", "openid");
        map.add("grant_type", "password");
        map.add("userName", simpleLogin.getUsername());
        map.add("password", simpleLogin.getPassword());
        HttpEntity<Object> requestEntity = new HttpEntity(map, requestHeaders);

        ResponseEntity<WSO2TokenResponseDto> responsebody = restTemplate.exchange(
                createTokenEndpoint,
                HttpMethod.POST,
                requestEntity,
                WSO2TokenResponseDto.class
        );
        long tokenGenerationTime = new Date().getTime();
        tokenDetails.setAccess_token(responsebody.getBody().getAccessToken());
        tokenDetails.setType(type);
        tokenDetails.setForUserId(userId);
        return responsebody.getBody();
    }

    public WSO2TokenResponseDto getToken(SimpleLogin simpleLogin, String type){
        type = simpleLogin.getType();
        String authString = clientId + ":" + clientSecret;
        byte[] authEncBytes = encodeBase64(authString.getBytes());
        String authorizationHeader = "Basic " + new String(authEncBytes);
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        requestHeaders.add("Authorization", authorizationHeader);
        requestHeaders.add("grant_type", "password");
        requestHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        String createTokenEndpoint = "https://localhost:9443"+"/oauth2/token";

        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
        map.add("scope", type);
        map.add("grant_type", "password");
        map.add("username", simpleLogin.getUsername());
        map.add("password", simpleLogin.getPassword());
        HttpEntity<Object> requestEntity = new HttpEntity(map, requestHeaders);

        ResponseEntity<WSO2TokenResponseDto> responsebody = restTemplate.exchange(
                createTokenEndpoint,
                HttpMethod.POST,
                requestEntity,
                WSO2TokenResponseDto.class
        );
        long tokenGenerationTime = new Date().getTime();
        tokenDetails.setAccess_token(responsebody.getBody().getAccessToken());
        tokenDetails.setType(type);

        return responsebody.getBody();
    }


    public HttpHeaders setHeader(String accessToken){
        String authorizationHeader = "Bearer " + accessToken;
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        requestHeaders.add("Authorization", authorizationHeader);
        requestHeaders.add("grant_type", "password");
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        return requestHeaders;
    }


}

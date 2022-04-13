package com.shivan.oauthdemo.controller;


import com.shivan.oauthdemo.dto.*;
import com.shivan.oauthdemo.model.Roles;
import com.shivan.oauthdemo.model.ShowAllUsers;
import com.shivan.oauthdemo.model.User;
import com.shivan.oauthdemo.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.MultiValueMapAdapter;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.*;

import static org.apache.tomcat.util.codec.binary.Base64.encodeBase64;

@RestController
@RequestMapping("/umt")
@PropertySource("classpath:application.properties")
public class MainController {

    @Autowired
    Environment env;

    @Value("${client_Id}")
    private String clientId;

    @Value("${client_secret}")
    private String clientSecret;

    @Value("{default_role_id}")
    private String defaultRoleId;

    @Autowired
    TokenService tokenService;

    RestTemplate restTemplate = new RestTemplate();

    Map<String, String> resultMap = new HashMap<>();

    TokenDetails generatedTokenDetails = new TokenDetails();

    WSO2TokenResponseDto tokenDetails = new WSO2TokenResponseDto();

    @PostMapping("/login")
    public ResponseEntity<Map> getToken(@RequestBody SimpleLogin simpleLogin, String clientId) throws Exception {
        Map<String, String> result1 = new HashMap<>();
        Map<String, Object> result = new HashMap<>();
        try{
        generatedTokenDetails.setAccess_token(tokenService.getToken(simpleLogin, simpleLogin.getType()).getAccessToken());
        HttpHeaders requestHeaders = tokenService.setHeader(tokenService.getToken(simpleLogin, "login").getAccessToken());
        HttpEntity<Object> requestEntity = new HttpEntity<>(simpleLogin, requestHeaders);
        ResponseEntity<Object> response = restTemplate.exchange(
                "https://localhost:9443/wso2/scim/Users/me",
                HttpMethod.GET,
                requestEntity,
                Object.class
        );
        String userId = response.toString().substring(response.toString().indexOf("id=") + 3, response.toString().indexOf("id") + 39);
        generatedTokenDetails.setForUserId(userId);
        tokenDetails = new WSO2TokenResponseDto();
        tokenDetails.setUserId(userId);
        tokenDetails.setAccessToken(tokenService.getToken(simpleLogin, "login").getAccessToken());
        tokenDetails.setRefreshToken(tokenService.getToken(simpleLogin, "login").getRefreshToken());
        tokenDetails.setExpiresIn(tokenService.getToken(simpleLogin, "login").getExpiresIn());
        tokenDetails.setTokenId(tokenService.getToken(simpleLogin, "login").getTokenId());
        tokenDetails.setUserName(tokenService.getToken(simpleLogin, "login").getUserName());
    }
        catch (HttpClientErrorException.BadRequest badRequest){
            result1.put("message", "Invalid username / password");
            return new ResponseEntity<Map>(result1,HttpStatus.BAD_REQUEST);
        }

        result.put("user_id", generatedTokenDetails.getForUserId());
        result.put("access_token", tokenService.getToken(simpleLogin, "login").getAccessToken());
        result.put("refresh_token", tokenService.getToken(simpleLogin, "login").getRefreshToken());
        result.put("expires_in", tokenService.getToken(simpleLogin, "login").getExpiresIn());
        return new ResponseEntity<Map>(result,HttpStatus.OK);
    }

    @PostMapping("/Users")
    public ResponseEntity<Object> createUser(@RequestBody User user) {
        String generatedTokenResponse = generatedTokenDetails.getAccess_token();
        HttpHeaders httpHeaders = tokenService.setHeader(generatedTokenResponse);
        String url = "https://localhost:9443/wso2/scim/Users";
        HttpEntity<Object> requestEntity = new HttpEntity(user, httpHeaders);
        ResponseEntity<Object> responsebody;
        ResponseEntity<Object> roleResponseBody;
        try {
            responsebody = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    requestEntity,
                    Object.class
            );
        } catch (HttpClientErrorException.Conflict conflictException) {
            resultMap.put("message", "username unavailable");
            return new ResponseEntity<Object>(resultMap, HttpStatus.CONFLICT);
        }
//        Map<String, Object> map = new HashMap<>();
//        String schemas[] = {"urn:ietf:params:scim:schemas:extension:2.0:Role"};
//        map.put("schemas",schemas);
//        List<Map<String, String>> list = new ArrayList<>();
//        Map<String, String> map1 = new HashMap<>();
//        map1.put("value",responsebody.toString().substring(responsebody.toString().indexOf("id=") + 3, responsebody.toString().indexOf("id") + 39));
//        list.add(map1);
//        map.put("users",list);
//        List<String> permissionsList = new ArrayList<>();
//        map.put("displayName","updatedDefault");
//        HttpEntity<Object> requestEntity1 = new HttpEntity<Object>(map,httpHeaders);
//        roleResponseBody = restTemplate.exchange(
//                "https://localhost:9443/t/carbon.super/scim2/Roles/"+defaultRoleId,
//                HttpMethod.PUT,
//                requestEntity1,
//                Object.class
//        );

        return responsebody;
    }

    @PutMapping("/Users/{userId}")
    public ResponseEntity<Object> updateUser(@RequestBody UserUpdate userUpdate, @PathVariable String userId){
        String generatedTokenResponse = generatedTokenDetails.getAccess_token();
        HttpHeaders httpHeaders = tokenService.setHeader(generatedTokenResponse);
        String url = "https://localhost:9443/wso2/scim/Users/"+userId;
        HttpEntity<Object> requestEntity = new HttpEntity(userUpdate, httpHeaders);
        ResponseEntity<Object> responsebody;
        try {
            responsebody = restTemplate.exchange(
                    url,
                    HttpMethod.PUT,
                    requestEntity,
                    Object.class
            );
        } catch (HttpClientErrorException.NotFound notFound) {
            resultMap.put("message", "No user available");
            return new ResponseEntity<Object>(resultMap, HttpStatus.NOT_FOUND);
        }
        catch (HttpServerErrorException.InternalServerError serverError){
            resultMap.put("message","username can not be updated");
            return new ResponseEntity<>(resultMap,HttpStatus.BAD_REQUEST);
        }
        catch (HttpClientErrorException.Unauthorized unauthorized){
            resultMap.put("message","Please login with proper permissions");
            return new ResponseEntity<>(resultMap,HttpStatus.UNAUTHORIZED);
        }
        return responsebody;
    }

    @GetMapping("/Users")
    public Object getAllUsers(){
        String generatedTokenResponse = generatedTokenDetails.getAccess_token();
        HttpHeaders httpHeaders = tokenService.setHeader(generatedTokenResponse);
        String url = "https://localhost:9443/wso2/scim/Users";
        HttpEntity<Object> requestEntity = new HttpEntity(httpHeaders);
        ResponseEntity<Object> responsebody;
        try {
            responsebody = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    requestEntity,
                    Object.class
            );
        } catch (HttpClientErrorException.Unauthorized unauthorized) {
            resultMap.put("message", "Unauthorized");
            return new ResponseEntity<Object>(resultMap, HttpStatus.UNAUTHORIZED);
        }
        return responsebody;
    }

    @GetMapping("/Users/{userId}")
    public Object getUserByUserId(@PathVariable String userId){
        String generatedTokenResponse = generatedTokenDetails.getAccess_token();
        HttpHeaders httpHeaders = tokenService.setHeader(generatedTokenResponse);
        String url = "https://localhost:9443/wso2/scim/Users/"+userId;
        HttpEntity<Object> requestEntity = new HttpEntity(httpHeaders);
        ResponseEntity<Object> responsebody;
        try {
            responsebody = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    requestEntity,
                    Object.class
            );
            System.out.println(responsebody);
        } catch (HttpClientErrorException.Unauthorized unauthorized) {
            resultMap.put("message", "Unauthorized");
            return new ResponseEntity<Object>(resultMap, HttpStatus.UNAUTHORIZED);
        }
        return responsebody;
    }

    @DeleteMapping("/Users/{userId}")
    public Object deleteUserByUserId(@PathVariable String userId){
        String generatedTokenResponse = generatedTokenDetails.getAccess_token();
        HttpHeaders httpHeaders = tokenService.setHeader(generatedTokenResponse);
        String url = "https://localhost:9443/wso2/scim/Users/"+userId;
        HttpEntity<Object> requestEntity = new HttpEntity(httpHeaders);
        ResponseEntity<Object> responsebody;
        try {
            responsebody = restTemplate.exchange(
                    url,
                    HttpMethod.DELETE,
                    requestEntity,
                    Object.class
            );
            System.out.println(responsebody);
        } catch (HttpClientErrorException.Unauthorized unauthorized) {
            resultMap.put("message", "Unauthorized");
            return new ResponseEntity<Object>(resultMap, HttpStatus.UNAUTHORIZED);
        }
        resultMap.put("message", "user deleted");
        return new ResponseEntity<Object>(resultMap, HttpStatus.OK);
    }

    @GetMapping("/Roles")
    public Object getAllRoles(){
        String generatedTokenResponse = generatedTokenDetails.getAccess_token();
        HttpHeaders httpHeaders = tokenService.setHeader(generatedTokenResponse);
        String url = "https://localhost:9443/t/carbon.super/scim2/Roles";
        HttpEntity<Object> requestEntity = new HttpEntity(httpHeaders);
        Object responsebody;
        try {
            responsebody = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    requestEntity,
                    Object.class
            );
        } catch (HttpClientErrorException.Unauthorized unauthorized) {
            resultMap.put("message", "Unauthorized");
            return new ResponseEntity<Object>(resultMap, HttpStatus.UNAUTHORIZED);
        }
        return responsebody;
    }


    @PostMapping("/Roles")
    public Object createRole(@RequestBody Roles roles){
        String generatedTokenResponse = generatedTokenDetails.getAccess_token();
        HttpHeaders httpHeaders = tokenService.setHeader(generatedTokenResponse);
        String url = "https://localhost:9443/t/carbon.super/scim2/Roles";
        HttpEntity<Object> requestEntity = new HttpEntity(roles,httpHeaders);
        Object responsebody;
        try {
            responsebody = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    requestEntity,
                    Object.class
            );
        } catch (HttpClientErrorException.Unauthorized unauthorized) {
            resultMap.put("message", "Unauthorized");
            return new ResponseEntity<Object>(resultMap, HttpStatus.UNAUTHORIZED);
        }
        catch (HttpClientErrorException.Forbidden forbidden){
            resultMap.put("message", "invalid scope");
            return new ResponseEntity<Object>(resultMap, HttpStatus.FORBIDDEN);
        }
        return responsebody;
    }

    @PutMapping("/Roles/{roleId}")
    public Object assignRole(@RequestBody UpdateRole updateRole,@PathVariable String roleId){
        String generatedTokenResponse = generatedTokenDetails.getAccess_token();
        HttpHeaders httpHeaders = tokenService.setHeader(generatedTokenResponse);
        String url = "https://localhost:9443/t/carbon.super/scim2/Roles/"+roleId;
        HttpEntity<Object> requestEntity = new HttpEntity(updateRole,httpHeaders);
        Object responsebody;
        try {
            responsebody = restTemplate.exchange(
                    url,
                    HttpMethod.PUT,
                    requestEntity,
                    Object.class
            );
        } catch (HttpClientErrorException.Unauthorized unauthorized) {
            resultMap.put("message", "Unauthorized");
            return new ResponseEntity<Object>(resultMap, HttpStatus.UNAUTHORIZED);
        }
        catch (HttpClientErrorException.Forbidden forbidden){
            resultMap.put("message", "invalid scope");
            return new ResponseEntity<Object>(resultMap, HttpStatus.FORBIDDEN);
        }
        return responsebody;
    }

    @GetMapping("/Roles/{roleId}")
    public Object viewRoleByRoleId(@PathVariable String roleId){
        String generatedTokenResponse = generatedTokenDetails.getAccess_token();
        HttpHeaders httpHeaders = tokenService.setHeader(generatedTokenResponse);
        String url = "https://localhost:9443/t/carbon.super/scim2/Roles/"+roleId;
        HttpEntity<Object> requestEntity = new HttpEntity(httpHeaders);
        Object responsebody;
        try {
            responsebody = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    requestEntity,
                    Object.class
            );
        } catch (HttpClientErrorException.Unauthorized unauthorized) {
            resultMap.put("message", "Unauthorized");
            return new ResponseEntity<Object>(resultMap, HttpStatus.UNAUTHORIZED);
        }
        catch (HttpClientErrorException.Forbidden forbidden){
            resultMap.put("message", "invalid scope");
            return new ResponseEntity<Object>(resultMap, HttpStatus.FORBIDDEN);
        }
        return responsebody;
    }

    @DeleteMapping("/Roles/{roleId}")
    public Object deleteRoleByRoleId(@PathVariable String roleId){
        String generatedTokenResponse = generatedTokenDetails.getAccess_token();
        HttpHeaders httpHeaders = tokenService.setHeader(generatedTokenResponse);
        String url = "https://localhost:9443/t/carbon.super/scim2/Roles/"+roleId;
        HttpEntity<Object> requestEntity = new HttpEntity(httpHeaders);
        Object responsebody;
        try {
            responsebody = restTemplate.exchange(
                    url,
                    HttpMethod.DELETE,
                    requestEntity,
                    Object.class
            );
        } catch (HttpClientErrorException.Unauthorized unauthorized) {
            resultMap.put("message", "Unauthorized");
            return new ResponseEntity<Object>(resultMap, HttpStatus.UNAUTHORIZED);
        }
        catch (HttpClientErrorException.Forbidden forbidden){
            resultMap.put("message", "invalid scope");
            return new ResponseEntity<Object>(resultMap, HttpStatus.FORBIDDEN);
        }
        resultMap.put("message", "deleted");
        return new ResponseEntity<Object>(resultMap,HttpStatus.OK);
    }

    @GetMapping("/Groups")
    public Object getAllGroups(){
        String generatedTokenResponse = generatedTokenDetails.getAccess_token();
        HttpHeaders httpHeaders = tokenService.setHeader(generatedTokenResponse);
        String url = "https://localhost:9443/t/carbon.super/scim2/Groups";
        HttpEntity<Object> requestEntity = new HttpEntity(httpHeaders);
        Object responsebody;
        try {
            responsebody = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    requestEntity,
                    Object.class
            );
        } catch (HttpClientErrorException.Unauthorized unauthorized) {
            resultMap.put("message", "Unauthorized");
            return new ResponseEntity<Object>(resultMap, HttpStatus.UNAUTHORIZED);
        }
        return responsebody;
    }

    @GetMapping("/Groups/{groupId}")
    public Object getGroupByGroupId(@PathVariable String groupId){
        String generatedTokenResponse = generatedTokenDetails.getAccess_token();
        HttpHeaders httpHeaders = tokenService.setHeader(generatedTokenResponse);
        String url = "https://localhost:9443/t/carbon.super/scim2/Groups/"+groupId;
        HttpEntity<Object> requestEntity = new HttpEntity(httpHeaders);
        Object responsebody;
        try {
            responsebody = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    requestEntity,
                    Object.class
            );
        } catch (HttpClientErrorException.Unauthorized unauthorized) {
            resultMap.put("message", "Unauthorized");
            return new ResponseEntity<Object>(resultMap, HttpStatus.UNAUTHORIZED);
        }
        return responsebody;
    }

}

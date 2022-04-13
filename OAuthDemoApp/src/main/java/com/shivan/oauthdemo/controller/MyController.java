package com.shivan.oauthdemo.controller;
import java.util.*;

import javax.management.AttributeNotFoundException;
import javax.naming.AuthenticationException;


import com.shivan.oauthdemo.dto.*;

import com.shivan.oauthdemo.model.Emails;
import com.shivan.oauthdemo.model.Name;
import com.shivan.oauthdemo.model.ShowAllUsers;
import com.shivan.oauthdemo.model.User;
import org.apache.coyote.Response;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import static org.apache.tomcat.util.codec.binary.Base64.encodeBase64;

@RestController
@RequestMapping("/ums")
public class MyController {


    RestTemplate restTemplate = new RestTemplate();
	String accessToken = null;
	long expiresIn = 0;
	long tokenGenerationTime = 0;


	//@CrossOrigin(origins = "http://localhost:9443")


	//Get Access token AKA login API
    @PostMapping("/login")
	public WSO2TokenResponseDto getUser(@RequestBody LoginRequest loginRequest) throws Exception {
		if(loginRequest.getUserName().isEmpty()){
			throw new Exception("username not found!!!");
		}
		else if(loginRequest.getPassword().isEmpty()){
			throw  new AttributeNotFoundException("Password is required");
		}

		String oauthClientId = loginRequest.getClientId();
		String oauthClientSecret = loginRequest.getClientSecret();
		String authString = oauthClientId + ":" + oauthClientSecret;
		byte[] authEncBytes = encodeBase64(authString.getBytes());
		String authorizationHeader = "Basic " + new String(authEncBytes);
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		requestHeaders.add("Authorization", authorizationHeader);
		requestHeaders.add("grant_type", "password");
		requestHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		String createTokenEndpoint = "https://localhost:9443"+"/oauth2/token";

		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
		//map.add("scope", !StringUtils.isBlank(loginRequest.getScope()) ? loginRequest.getScope() : properties.getOauthScope());
		map.add("scope", "openid");
		map.add("grant_type", "password");
		map.add("username", loginRequest.getUserName());
		map.add("password", loginRequest.getPassword());
		HttpEntity<Object> requestEntity = new HttpEntity(map, requestHeaders);

		ResponseEntity<WSO2TokenResponseDto> responsebody = restTemplate.exchange(
				createTokenEndpoint,
				HttpMethod.POST,
				requestEntity,
				WSO2TokenResponseDto.class
		);
		tokenGenerationTime = new Date().getTime();
		accessToken = responsebody.getBody().getAccessToken();
		expiresIn = responsebody.getBody().getExpiresIn();
		//return new ResponseEntity<WSO2TokenResponseDto>(HttpStatus.OK);
		System.out.println("Token generation time " + tokenGenerationTime);
		System.out.println("Token expires in "+expiresIn);
		return responsebody.getBody();

	}

	//Get User by User Id
	@GetMapping("/Users/{userId}")
	public User testMethod(@PathVariable String userId) throws Exception {
		//List<Emails> userEmailList = new ArrayList<>();
		String authorizationHeader = "Bearer " + accessToken;
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		requestHeaders.add("Authorization", authorizationHeader);
		requestHeaders.add("grant_type", "password");
		requestHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		String createTokenEndpoint = "https://localhost:9443/wso2/scim/Users/"+userId;

		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
		//map.add("scope", !StringUtils.isBlank(loginRequest.getScope()) ? loginRequest.getScope() : properties.getOauthScope());
		map.add("scope", "all");
		map.add("grant_type", "password");
		HttpEntity<Object> requestEntity = new HttpEntity(map, requestHeaders);
		long currentTime = new Date().getTime();
		if(currentTime - tokenGenerationTime <= 3600) {
			/*ResponseEntity<User> responsebody = restTemplate.exchange(
					createTokenEndpoint,
					HttpMethod.GET,
					requestEntity,
					User.class
			);*/
			ResponseEntity<User> responsebody = restTemplate.exchange(
					createTokenEndpoint,
					HttpMethod.GET,
					requestEntity,
					User.class
			);



			User user = new User();
			System.out.println(responsebody.getBody());

			user.setEmails(responsebody.getBody().getEmails());
			user.setUserName(responsebody.getBody().getUserName());
			user.setName(responsebody.getBody().getName());
			user.setId(responsebody.getBody().getId());
			user.setSchemas(responsebody.getBody().getSchemas());

			return responsebody.getBody();
		}
		else{
			throw new Exception("Token Expired");
		}
	}

	//Show All Users
	@GetMapping("/Users")
	public Object getAllUsers(){
		String authorizationHeader = "Bearer " + accessToken;
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		requestHeaders.add("Authorization", authorizationHeader);
		requestHeaders.add("grant_type", "password");
		requestHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		String createTokenEndpoint = "https://localhost:9443/wso2/scim/Users";

		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
		//map.add("scope", !StringUtils.isBlank(loginRequest.getScope()) ? loginRequest.getScope() : properties.getOauthScope());
		map.add("scope", "all");
		map.add("grant_type", "password");
		HttpEntity<Object> requestEntity = new HttpEntity(map, requestHeaders);
		ResponseEntity<Object> responsebody = restTemplate.exchange(
				createTokenEndpoint,
				HttpMethod.GET,
				requestEntity,
				Object.class
		);
		System.out.println(responsebody.toString());
		return responsebody;
		//return new ShowAllUsers(responsebody.getBody().getTotalResults(),responsebody.getBody().getSchemas(), responsebody.getBody().getResources());
	}

	//Show All Groups
	@GetMapping("/Groups")
	public ResponseEntity<Object> getAllGroups(){
		String authorizationHeader = "Bearer " + accessToken;
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		requestHeaders.add("Authorization", authorizationHeader);
		requestHeaders.add("grant_type", "password");
		requestHeaders.setContentType(MediaType.APPLICATION_JSON);
		String createTokenEndpoint = "https://localhost:9443/t/carbon.super/scim2/Groups";

		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
		//map.add("scope", !StringUtils.isBlank(loginRequest.getScope()) ? loginRequest.getScope() : properties.getOauthScope());
		map.add("scope", "internal_role_mgt_view");
		map.add("grant_type", "password");
		HttpEntity<Object> requestEntity = new HttpEntity(map, requestHeaders);
		ResponseEntity<Object> responsebody = restTemplate.exchange(
				createTokenEndpoint,
				HttpMethod.GET,
				requestEntity,
				Object.class
		);
		System.out.println(responsebody.toString());
		if(responsebody.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
			return new ResponseEntity<Object>("No Groups", HttpStatus.NO_CONTENT);
		}
		else{
			return responsebody;
		}
	}


	//Update User by User id
	@PostMapping("/Users/{userId}")
	public Object updateUsers(@PathVariable String userId, @RequestBody UserUpdate userUpdate){

		String authorizationHeader = "Bearer " + accessToken;
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		requestHeaders.add("Authorization", authorizationHeader);
		requestHeaders.add("grant_type", "password");
		requestHeaders.setContentType(MediaType.APPLICATION_JSON);
		String createTokenEndpoint = "https://localhost:9443/wso2/scim/Users/"+userId;

		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
		//map.add("scope", !StringUtils.isBlank(loginRequest.getScope()) ? loginRequest.getScope() : properties.getOauthScope());
		map.add("scope", "all");
		map.add("grant_type", "password");
		//HttpEntity<Object> requestEntity = new HttpEntity(map, requestHeaders);
		HttpEntity<Object> requestEntity1 = new HttpEntity(map, requestHeaders);
		ResponseEntity<User> responseUserDetails = restTemplate.exchange(
				"https://localhost:9443/wso2/scim/Users/"+userId,
				HttpMethod.GET,
				requestEntity1,
				User.class
		);


		User updatedUser = new User();
		updatedUser.setId(responseUserDetails.getBody().getId());
		updatedUser.setUserName(responseUserDetails.getBody().getUserName());
		updatedUser.setSchemas(userUpdate.getSchemas());
		Name name = new Name();
		name.setGivenName(userUpdate.getName().getGivenName());
		name.setFamilyName(userUpdate.getName().getFamilyName());
		updatedUser.setName(name);
		updatedUser.setEmails(userUpdate.getEmails().toArray(new Emails[0]));
		HttpEntity<Object> requestEntity = new HttpEntity(updatedUser, requestHeaders);
		ResponseEntity<Object> responsebody = restTemplate.exchange(
				createTokenEndpoint,
				HttpMethod.PUT,
				requestEntity,
				Object.class
		);


		System.out.println(responsebody.getBody());
		return responsebody;
	}


	//Delete user by user id
	@DeleteMapping("/Users/{userId}")
	public ResponseEntity<Map> deleteUser(@PathVariable String userId){
		String authorizationHeader = "Bearer " + accessToken;
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		requestHeaders.add("Authorization", authorizationHeader);
		requestHeaders.add("grant_type", "password");
		requestHeaders.setContentType(MediaType.APPLICATION_JSON);
		String createTokenEndpoint = "https://localhost:9443/wso2/scim/Users/"+userId;

		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
		//map.add("scope", !StringUtils.isBlank(loginRequest.getScope()) ? loginRequest.getScope() : properties.getOauthScope());
		map.add("scope", "all");
		map.add("grant_type", "password");
		HttpEntity<Object> requestEntity = new HttpEntity(map, requestHeaders);
		try {
			ResponseEntity<Object> responseBody = restTemplate.exchange(
					createTokenEndpoint,
					HttpMethod.DELETE,
					requestEntity,
					Object.class

			);
			Map<String, String> map1 = new HashMap<>();
			map1.put("message", "deleted");
			return new ResponseEntity<Map>(map1, HttpStatus.OK);
		}
		catch (HttpClientErrorException.NotFound e){
			Map<String,String> map1 = new HashMap<>();
			map1.put("message","not found");
			return  new ResponseEntity<Map>(map1, HttpStatus.NOT_FOUND);
		}
	}

	//Create user API
	@PostMapping("/User")
	public Object createUsers(@RequestBody User user) {
		String authorizationHeader = "Bearer " + accessToken;
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		requestHeaders.add("Authorization", authorizationHeader);
		requestHeaders.add("grant_type", "password");
		requestHeaders.setContentType(MediaType.APPLICATION_JSON);
		String createTokenEndpoint = "https://localhost:9443/wso2/scim/Users";
		Map<String, String> resultingMap = new HashMap<>();
		List<Map<String, String>> listOfAttributes = new ArrayList<>();
		Map<String, List<Emails>> emails = new HashMap<>();
		Map<String, Name> nameMap = new HashMap<>();
		emails.put("emails", Arrays.asList(user.getEmails()));
		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
		//map.add("scope", !StringUtils.isBlank(loginRequest.getScope()) ? loginRequest.getScope() : properties.getOauthScope());
		map.add("scope", "all");
		map.add("grant_type", "password");
		map.add("userName", user.getUserName());
		map.add("password", user.getPassword());
		HttpEntity<Object> requestEntity = new HttpEntity(user, requestHeaders);
		Map<String, String> map2 = new HashMap<>();
		map2.put("scope", "all");
		map2.put("grant_type", "password");
		map2.put("userName", user.getUserName());
		HttpEntity<Object> requestEntity2 = new HttpEntity(map2, requestHeaders);

		ResponseEntity<Object> responseBody = null;
		try {
			ResponseEntity<Object> responseOfUserPresence = restTemplate.exchange("https://localhost:9443/wso2/scim/Users/?filter=userName+Eq+%22" + user.getUserName() + "%22", HttpMethod.GET, requestEntity2, Object.class);

		} catch (HttpClientErrorException.NotFound errorException) {

			try {
				responseBody = restTemplate.exchange(
						createTokenEndpoint,
						HttpMethod.POST,
						requestEntity,
						Object.class);
			} catch (HttpClientErrorException.Conflict c) {
				resultingMap.put("message", "Already exists");
				return new ResponseEntity<Object>(resultingMap, HttpStatus.CONFLICT);
			}


		}
		return responseBody;
	}




	}






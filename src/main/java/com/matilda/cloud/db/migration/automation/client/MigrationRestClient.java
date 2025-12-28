package com.matilda.cloud.db.migration.automation.client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Rest client for all migration related rest template operations
 * 
 * @author lokesh babu cherukuri
 *
 */
@Component
public class MigrationRestClient {
	
	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private ObjectMapper objectMapper;

	/**
	 * The simple get request
	 * 
	 * @param url - url need to call
	 * @return ResponseEntity
	 */
	public ResponseEntity<String> get(String url) {
		return restTemplate.getForEntity(url, String.class);
	}

	/**
	 *
	 * @param builder - contain url and request parameter
	 * @return ResponseEntity
	 */
	public ResponseEntity<String> get(UriComponentsBuilder builder) {
		return restTemplate.getForEntity(builder.build().toUri(), String.class);
	}

	/**
	 * The get request with request parameters
	 * 
	 * @param builder     - Uri builder contain url and request parameter
	 * @param httpHeaders - header need to send
	 * @return ResponseEntity
	 */
	public ResponseEntity<String> get(UriComponentsBuilder builder, HttpHeaders httpHeaders) {

		HttpEntity<?> entity = new HttpEntity<>(httpHeaders);
		return restTemplate.exchange(builder.build().toUri(), HttpMethod.GET, entity, String.class);
	}

	/**
	 * The get request with Uri builder and header parameters with response type
	 * 
	 * @param builder     - Uri builder contain url and request parameter
	 * @param httpHeaders - header need to send
	 * @param Class<T> - responseType
	 * 
	 * @return ResponseEntity
	 */
	public <T> ResponseEntity<T> get(UriComponentsBuilder builder, HttpHeaders httpHeaders, Class<T> responseType) {
		HttpEntity<HttpHeaders> entity = new HttpEntity<>(httpHeaders);
		return restTemplate.exchange(builder.build().toUri(), HttpMethod.GET, entity, responseType);
	}

	/**
	 * The get method can be use for sending http get request with header parameter
	 * 
	 * @param url     - request url that need to call
	 * @param headers - header that need to send.
	 * @return ResponseEntity
	 */
	public ResponseEntity<String> get(String url, HttpHeaders headers) {
		HttpEntity<String> entity = new HttpEntity<>(headers);
		return restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
	}

	/**
	 * The get method can be use for sending http get request with url parameter and
	 * class type
	 * 
	 * @param url   - request url that need to call
	 * @param Class<T> - response type
	 */
	public <T> T get(String url, Class<T> t) {
		return restTemplate.getForObject(url, t);
	}

	/**
	 * The get method can be use for sending http get request with url parameter and
	 * class type and return the response list
	 * 
	 * @param url   - request url that need to call
	 * @param Class - response type
	 */
	public <T> List<T> getList(String url, Class<T> t) {
		ResponseEntity<List<T>> getResponse = restTemplate.exchange(url, HttpMethod.GET, getHttpEntity(null),
				new ParameterizedTypeReference<List<T>>() {
				});
		List<T> response = getResponse.getBody();
		List<T> mappedResponse = new ArrayList<>();
		for (T responseItem : response) {
			mappedResponse.add(objectMapper.convertValue(responseItem, t));
		}
		return mappedResponse;
	}

	private <U> HttpEntity<U> getHttpEntity(U payload) {
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setContentType(MediaType.APPLICATION_JSON);
		requestHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		return new HttpEntity<>(payload, requestHeaders);
	}

	/**
	 * The post method can use for post request with Request entity and Header The
	 * response will be return in response type class For passing header we need
	 * pass header in HttpEntity along with class that has to be send in request.
	 * example HttpEntity(object,header) For calling post with header only then we
	 * need to pass HttpEntity with header example HttpEntity(header);
	 * 
	 * @param builder
	 * @param entity
	 * @param response
	 * @param <T>
	 * @param <P>
	 * @return
	 */
	public <T, P> T post(UriComponentsBuilder builder, HttpEntity<P> entity, Class<T> response) {

		return restTemplate.postForObject(builder.build().toUri(), entity, response);
	}

	public <T, U> T post(String url, U payload, Class<T> t) {
		return restTemplate.postForObject(url, payload, t);
	}

	public <T, U> List<T> postList(String url, U payload, Class<T> t) {
		ResponseEntity<List<T>> getResponse = restTemplate.exchange(url, HttpMethod.POST, getHttpEntity(payload),
				new ParameterizedTypeReference<List<T>>() {
				});
		List<T> response = getResponse.getBody();
		List<T> mappedResponse = new ArrayList<>();
		for (T responseItem : response) {
			mappedResponse.add(objectMapper.convertValue(responseItem, t));
		}
		return mappedResponse;
	}

	/**
	 * The update method can be use for sending http put request with header
	 * parameter
	 * 
	 * @param url     - request url that need to call
	 * @param headers - header that need to send.
	 * @return ResponseEntity
	 */
	public <T> ResponseEntity<T> update(String url, HttpHeaders headers, Class<T> responseType ) {
		HttpEntity<String> entity = new HttpEntity<>(headers);
		return restTemplate.exchange(url, HttpMethod.PUT, entity, responseType);
	}
	
	/**
	 * The update method can be used for sending http put request with header
	 * parameter and additional uri parameters as Object
	 * 
	 * @param url     - request url that need to call
	 * @param HttpEntity - http entity which is having header and object.
	 * @param Class - responseType
	 * @param Object - additional uri parameters
	 * @return ResponseEntity
	 */
	public <T,P> ResponseEntity<T> update(String url, HttpEntity<P> entity, Class<T> responseType, Object uriVariables) {
		return restTemplate.exchange(url, HttpMethod.PUT, entity, responseType,uriVariables);
	}
	
	/**
	 * The delete method can be use for sending http delete request with header
	 * parameter
	 * 
	 * @param url     - request url that need to call
	 * @param headers - header that need to send.
	 * @return ResponseEntity
	 */
	public <T> ResponseEntity<T> delete(String url, HttpHeaders headers, Class<T> responseType ) {
		HttpEntity<String> entity = new HttpEntity<>(headers);
		return restTemplate.exchange(url, HttpMethod.DELETE, entity, responseType);
	}
	
	public void delete(String url, Object... uriVariables){
		restTemplate.delete(url, uriVariables);
	}
}



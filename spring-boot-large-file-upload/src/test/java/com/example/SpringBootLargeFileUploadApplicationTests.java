package com.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import me.desair.tus.server.HttpHeader;
import me.desair.tus.server.HttpMethod;
import me.desair.tus.server.download.DownloadGetRequestHandler;
import me.desair.tus.server.upload.UploadId;
import me.desair.tus.server.upload.UploadInfo;
import me.desair.tus.server.upload.UploadStorageService;
import me.desair.tus.server.util.TusServletRequest;
import me.desair.tus.server.util.TusServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.*;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.nullable;
import static org.mockito.Mockito.*;

@SpringBootTest
class SpringBootLargeFileUploadApplicationTests {
	@Value("${user.default.pwd:111111}")
	private String defaultPwd;
	@BeforeEach
	void contextLoads() {
		servletRequest = new MockHttpServletRequest();
		servletResponse = new MockHttpServletResponse();
	}
	@Value("${map}")
	private Map<String, String> map = new HashMap<>();
	@Test
	void test(){
		System.err.println("map:"+map.toString());
		
	}
	private RestTemplate restTemplate=new RestTemplate();
	@Test
	void  setDefaultPwd(){
		System.out.println(defaultPwd);
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2MTY2MzQ1OTAsImlkIjoiYWRtaW4iLCJvcmlnX2lhdCI6MTYxNjYzMDk5MH0.NiIXwS9UEDyfbLSRXtmSYooQfeETauoI8BgynqhOnPo");
		headers.add("Cookie","jwt=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2MTY2MzQ1OTAsImlkIjoiYWRtaW4iLCJvcmlnX2lhdCI6MTYxNjYzMDk5MH0.NiIXwS9UEDyfbLSRXtmSYooQfeETauoI8BgynqhOnPo");
		MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<String, Object>();
		HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<MultiValueMap<String, Object>>(paramMap,headers);
		
		String forObject = restTemplate.exchange("https://shizeying.top/trojan/user", org.springframework.http.HttpMethod.GET,httpEntity,
				String.class).getBody();
		System.out.println(forObject);
	}
	@Test
	void  setDefaultPwd2(){
		   String apk="dfa7df9216474047a21b602c26ec5521";
		UriComponents build = UriComponentsBuilder.newInstance()
		                                          .scheme("http")
		                                          .host("192.168.4.151")
		                                          .port("80")
		                                          .pathSegment("sso", "login",apk).build();
		HttpHeaders headers = new HttpHeaders();
		//headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("single-token","123");
		MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<String, Object>();
		paramMap.add("username","15812345678");
		paramMap.add("password","111111");
		ObjectNode personJsonObject = objectMapper.createObjectNode();
		personJsonObject.put("username","15812345678");
		personJsonObject.put("password","111111");
		HttpEntity<String> request = new HttpEntity<String>("", headers);
		 String uir="http://192.168.4.151/kgms/kg";
		System.out.println( restTemplate.exchange(uir, org.springframework.http.HttpMethod.GET,null,
				String.class));
		System.out.println((UUID.randomUUID().toString().replace("-", "")));
	}
	
	@Autowired
	private DownloadGetRequestHandler handler;
	
	private MockHttpServletRequest servletRequest;
	
	private MockHttpServletResponse servletResponse;
	
	@Mock
	private UploadStorageService uploadStorageService;
	@Autowired
	private ObjectMapper objectMapper;
	
	@Test
	void setObjectMapper(){
		String json="{\n" +
				            "    \"status\":0,\n" +
				            "    \"data\":{\n" +
				            "        \"userName\":\"zhangsan\",\n" +
				            "        \"chineseName\":\"张三\"\n" +
				            "    }\n" +
				            "}";
		try {
			JsonNode jsonNode= objectMapper.readTree(json);
			System.out.println(jsonNode.findValue("status").asText());
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		System.out.println(json);
		String hash="{bcrypt}$2a$10$eeMrQLdbu6HaP.91n40rueeEg5aVjKeeGxm6EG8Xei2fmfDrB7sFy";
		String oriPwd = "111111";
		System.out.println(genOauthEncodePwd(oriPwd));
		System.out.println(bcryptEncode(oriPwd));
		System.out.println(bcryptEncoder.matches(oriPwd,genOauthEncodePwd(oriPwd)));
		System.out.println(bcryptEncoder.matches(oriPwd,hash));
	}
	private static final PasswordEncoder bcryptEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();;
	
	public static String bcryptEncode(String password) {
		return bcryptEncoder.encode(password);
	}
	
	public static String genOauthEncodePwd(String password) {
		return bcryptEncode(password);
	}
	

	
	@Test
	public void supports() throws Exception {
		
		assertThat(handler.supports(HttpMethod.GET), is(true));
		assertThat(handler.supports(HttpMethod.POST), is(false));
		assertThat(handler.supports(HttpMethod.PUT), is(false));
		assertThat(handler.supports(HttpMethod.DELETE), is(false));
		assertThat(handler.supports(HttpMethod.HEAD), is(false));
		assertThat(handler.supports(HttpMethod.OPTIONS), is(false));
		assertThat(handler.supports(HttpMethod.PATCH), is(false));
		assertThat(handler.supports(null), is(false));
	}
	
	@Test
	public void testWithCompletedUploadWithMetadata() throws Exception {
		final UploadId id = new UploadId(UUID.randomUUID());
		
		UploadInfo info = new UploadInfo();
		info.setId(id);
		info.setOffset(10L);
		info.setLength(10L);
		info.setEncodedMetadata("name dGVzdC5qcGc=,type aW1hZ2UvanBlZw==");
		when(uploadStorageService.getUploadInfo(nullable(String.class), nullable(String.class))).thenReturn(info);
		
		handler.process(HttpMethod.GET, new TusServletRequest(servletRequest),
				new TusServletResponse(servletResponse), uploadStorageService, null);
		
		verify(uploadStorageService, times(1))
				.copyUploadTo(any(UploadInfo.class), any(OutputStream.class));
		assertThat(servletResponse.getStatus(), is(HttpServletResponse.SC_OK));
		assertThat(servletResponse.getHeader(HttpHeader.CONTENT_LENGTH), is("10"));
		assertThat(servletResponse.getHeader(HttpHeader.CONTENT_DISPOSITION),
				is("attachment;filename=\"IMG_20200806_192445.jpg\""));
		assertThat(servletResponse.getHeader(HttpHeader.CONTENT_TYPE),
				is("image/jpeg"));
		assertThat(servletResponse.getHeader(HttpHeader.UPLOAD_METADATA),
				is("name dGVzdC5qcGc=,type aW1hZ2UvanBlZw=="));
	}
	
	@Test
	public void testWithCompletedUploadWithoutMetadata() throws Exception {
		final UploadId id = new UploadId(UUID.randomUUID());
		
		UploadInfo info = new UploadInfo();
		info.setId(id);
		info.setOffset(10L);
		info.setLength(10L);
		when(uploadStorageService.getUploadInfo(nullable(String.class), nullable(String.class))).thenReturn(info);
		
		handler.process(HttpMethod.GET, new TusServletRequest(servletRequest),
				new TusServletResponse(servletResponse), uploadStorageService, null);
		
		verify(uploadStorageService, times(1))
				.copyUploadTo(any(UploadInfo.class), any(OutputStream.class));
		assertThat(servletResponse.getStatus(), is(HttpServletResponse.SC_OK));
		assertThat(servletResponse.getHeader(HttpHeader.CONTENT_LENGTH), is("10"));
		assertThat(servletResponse.getHeader(HttpHeader.CONTENT_DISPOSITION),
				is("attachment;filename=\"" + id.toString() + "\""));
		assertThat(servletResponse.getHeader(HttpHeader.CONTENT_TYPE), is("application/octet-stream"));
	}
	
	@Test
	public void testWithInProgressUpload() throws Exception {
		final UploadId id = new UploadId(UUID.randomUUID());
		
		UploadInfo info = new UploadInfo();
		info.setId(id);
		info.setOffset(8L);
		info.setLength(10L);
		info.setEncodedMetadata("name dGVzdC5qcGc=,type aW1hZ2UvanBlZw==");
		when(uploadStorageService.getUploadInfo(nullable(String.class), nullable(String.class))).thenReturn(info);
		
		handler.process(HttpMethod.GET, new TusServletRequest(servletRequest),
				new TusServletResponse(servletResponse), uploadStorageService, null);
	}
	
	@Test
	public void testWithUnknownUpload() throws Exception {
		when(uploadStorageService.getUploadInfo(nullable(String.class), nullable(String.class))).thenReturn(null);
		
		handler.process(HttpMethod.GET, new TusServletRequest(servletRequest),
				new TusServletResponse(servletResponse), uploadStorageService, null);
		
		verify(uploadStorageService, never()).copyUploadTo(any(UploadInfo.class), any(OutputStream.class));
		assertThat(servletResponse.getStatus(), is(HttpServletResponse.SC_NO_CONTENT));
	}
}

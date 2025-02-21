package hk.gov.ehr.service.esam.uam.uam.config;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {
	
	@Value("${CONNECT_TIME_OUT}")
	private int CONNECT_TIME_OUT;
	
	@Value("${READ_TIME_OUT}")
	private int READ_TIME_OUT;
	
    @Bean
    public RestTemplate restTemplate(ClientHttpRequestFactory factory) {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setDefaultCharset(StandardCharsets.UTF_8);
        
        List<MediaType> mediaTypes = new ArrayList<MediaType>();
        mediaTypes.add(MediaType.APPLICATION_JSON);

        converter.setSupportedMediaTypes(mediaTypes);
        RestTemplate restTemplate = new RestTemplate(factory);
        restTemplate.getMessageConverters().add(converter);
        return restTemplate;
    }

    @Bean
    public ClientHttpRequestFactory simpleClientHttpRequestFactory() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(CONNECT_TIME_OUT);
        factory.setReadTimeout(READ_TIME_OUT);
        factory.setBufferRequestBody(false);
        return factory;
    }
}

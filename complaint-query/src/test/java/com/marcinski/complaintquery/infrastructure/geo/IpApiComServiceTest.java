package com.marcinski.complaintquery.infrastructure.geo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@ExtendWith(MockitoExtension.class)
public class IpApiComServiceTest {

    private static final String IP = "8.8.8.8";
    @Mock
    private RestTemplate restTemplate;
    @Mock
    private RestTemplateBuilder builder;

    private IpApiComService ipApiComService;

    @BeforeEach
    public void setUp() {
        builder = Mockito.mock(RestTemplateBuilder.class);
        Mockito.when(builder.build()).thenReturn(restTemplate);
        ipApiComService = new IpApiComService(builder);
    }

    @Test
    public void testGetCityByIp_SuccessfulResponse() {
        String expectedCountry = "POLAND";
        IpApiResponse response = new IpApiResponse(expectedCountry, IP, "success");

        Mockito.when(restTemplate.getForObject(Mockito.anyString(), Mockito.eq(IpApiResponse.class)))
                .thenReturn(response);

        String result = ipApiComService.getCityByIp(IP);
        Mockito.verify(restTemplate).getForObject(String.format("http://ip-api.com/json/%s", IP), IpApiResponse.class);
        assert expectedCountry.equals(result);
    }

    @Test
    public void testGetCityByIp_FailureResponse() {
        IpApiResponse response = new IpApiResponse("UNKNOWN", IP, "fail");

        Mockito.when(restTemplate.getForObject(Mockito.anyString(), Mockito.eq(IpApiResponse.class)))
                .thenReturn(response);

        String result = ipApiComService.getCityByIp(IP);
        Mockito.verify(restTemplate).getForObject(String.format("http://ip-api.com/json/%s", IP), IpApiResponse.class);
        assert "UNKNOWN".equals(result);
    }

    @Test
    public void testGetCityByIp_RestClientException() {

        Mockito.when(restTemplate.getForObject(Mockito.anyString(), Mockito.eq(IpApiResponse.class)))
                .thenThrow(new RestClientException("Rest Client Exception"));

        String result = ipApiComService.getCityByIp(IP);
        Mockito.verify(restTemplate).getForObject(String.format("http://ip-api.com/json/%s", IP), IpApiResponse.class);
        assert "UNKNOWN".equals(result);
    }
}
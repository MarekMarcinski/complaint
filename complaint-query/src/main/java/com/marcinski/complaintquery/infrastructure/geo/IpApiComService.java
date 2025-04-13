package com.marcinski.complaintquery.infrastructure.geo;

import com.marcinski.complaintquery.domain.geo.CityLocator;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
class IpApiComService implements CityLocator {
    private static final String UNKNOWN = "UNKNOWN";
    private static final String IP_API_API_ARL = "http://ip-api.com/json/%s";
    private static final String SUCCESS_CODE = "success";

    private final RestTemplate restTemplate;

    public IpApiComService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @Override
    public String getCityByIp(String ip) {
        String url = String.format(IP_API_API_ARL, ip);
        String country = UNKNOWN;
        try {
            IpApiResponse response = restTemplate.getForObject(url, IpApiResponse.class);
            if (response != null && SUCCESS_CODE.equalsIgnoreCase(response.status())) {
                country = response.country();
            }
        } catch (RestClientException e) {
            // log something
        }
        return country;
    }

}

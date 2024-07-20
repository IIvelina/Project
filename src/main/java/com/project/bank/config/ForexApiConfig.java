package com.project.bank.config;

import jakarta.annotation.PostConstruct;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "forex.api")
public class ForexApiConfig {

    private String key;

    private String url;

    private String base;
    //forex:
    //  api:
    //    key: ${FOREX_API_KEY:}
    //    url: "https://openexchangerates.org/api/latest.json?app_id={app_id}"
    //    base: "USD"


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    @PostConstruct
    public void checkConfiguration(){
        verifyNotNullOrEmpty("key", key);
        verifyNotNullOrEmpty("base", base);
        verifyNotNullOrEmpty("url", url);
        if (!"USD".equals(base)){
            throw new IllegalStateException("Sorry, but the free API does not support base currencies different than USD.");
        }
    }
    private static void verifyNotNullOrEmpty(String name, String value){
        if(value == null || value.isBlank()){
            throw new IllegalArgumentException("Property " + name + " cannot be empty.");
        }
    }
}

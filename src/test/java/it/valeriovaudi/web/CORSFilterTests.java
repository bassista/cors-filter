package it.valeriovaudi.web;

import it.valeriovaudi.web.filter.CORSFilter;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.Test;

import javax.servlet.ServletException;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 Copyright 2015 Valerio Vaudi

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 */
public class CORSFilterTests {

    /*
        <init-param>
            <param-name>Access-Control-Allow-Origin</param-name>
            <param-value>Access-Control-Allow-Origin</param-value>
        </init-param>

        <init-param>
            <param-name>Access-Control-Allow-Methods</param-name>
            <param-value>Access-Control-Allow-Methods</param-value>
        </init-param>

        <init-param>
            <param-name>Access-Control-Max-Age</param-name>
            <param-value>Access-Control-Max-Age</param-value>
        </init-param>

        <init-param>
            <param-name>Access-Control-Allow-Headers</param-name>
            <param-value>Access-Control-Allow-Headers</param-value>
        </init-param>
    */
    @Test
    public void filterWithParamiters() throws LifecycleException, ServletException, IOException {
        String webappDirLocation = "src/test/webapp-withParam";
        Tomcat tomcat = TomcatInstanceTestFactory.newTomcatInstance("/testContext", webappDirLocation);
        tomcat.start();

        HttpGet httpGet = new HttpGet("http://localhost:8080/testContext/test");
        try(CloseableHttpClient httpClient = HttpClients.createDefault();
            CloseableHttpResponse execute = httpClient.execute(httpGet)){

            assertEquals(CORSFilter.ACCESS_CONTROL_ALLOW_ORIGIN_NAME, execute.getFirstHeader(CORSFilter.ACCESS_CONTROL_ALLOW_ORIGIN_NAME).getValue());
            assertEquals(CORSFilter.ACCESS_CONTROL_ALLOW_METHDOS_NAME, execute.getFirstHeader(CORSFilter.ACCESS_CONTROL_ALLOW_METHDOS_NAME).getValue());
            assertEquals(CORSFilter.ACCESS_CONTROL_MAX_AGE_NAME, execute.getFirstHeader(CORSFilter.ACCESS_CONTROL_MAX_AGE_NAME).getValue());
            assertEquals(CORSFilter.ACCESS_CONTROL_ALLOW_HEADERS_NAME, execute.getFirstHeader(CORSFilter.ACCESS_CONTROL_ALLOW_HEADERS_NAME).getValue());
        }
        tomcat.stop();
    }


    @Test
    public void filterWithoutParamiters() throws LifecycleException, ServletException, IOException {
        String webappDirLocation = "src/test/webapp-withoutParam";
        Tomcat tomcat = TomcatInstanceTestFactory.newTomcatInstance("/testContext", webappDirLocation);

        tomcat.start();

        HttpGet httpGet = new HttpGet("http://localhost:8080/testContext/test");
        try(CloseableHttpClient httpClient = HttpClients.createDefault();
            CloseableHttpResponse execute = httpClient.execute(httpGet)){

            assertEquals(CORSFilter.DEFAULT_ACCESS_CONTROL_ALLOW_ORIGIN_VALUE, execute.getFirstHeader(CORSFilter.ACCESS_CONTROL_ALLOW_ORIGIN_NAME).getValue());
            assertEquals(CORSFilter.DEFAULT_ACCESS_CONTROL_ALLOW_METHDOS_VALUE, execute.getFirstHeader(CORSFilter.ACCESS_CONTROL_ALLOW_METHDOS_NAME).getValue());
            assertEquals(CORSFilter.DEFAULT_ACCESS_CONTROL_MAX_AGE_VALUE, execute.getFirstHeader(CORSFilter.ACCESS_CONTROL_MAX_AGE_NAME).getValue());
            assertEquals(CORSFilter.DEFAULT_ACCESS_CONTROL_ALLOW_HEADERS_VALUE, execute.getFirstHeader(CORSFilter.ACCESS_CONTROL_ALLOW_HEADERS_NAME).getValue());
        }
        tomcat.stop();
    }
}

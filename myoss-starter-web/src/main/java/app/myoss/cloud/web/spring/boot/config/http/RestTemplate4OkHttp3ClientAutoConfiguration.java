/*
 * Copyright 2018-2018 https://github.com/myoss
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package app.myoss.cloud.web.spring.boot.config.http;

import static app.myoss.cloud.web.spring.boot.config.FastJsonWebConfig.fastJsonHttpMessageConverter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerRequestTransformer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;

import app.myoss.cloud.core.constants.MyossConstants;
import app.myoss.cloud.core.lang.json.JsonApi;
import app.myoss.cloud.core.spring.boot.config.FastJsonAutoConfiguration;
import app.myoss.cloud.web.constants.WebConstants;
import app.myoss.cloud.web.http.loadbalancer.LoadBalancerClientRequestFactory;
import app.myoss.cloud.web.http.loadbalancer.LoadBalancerClientRequestInterceptor;
import okhttp3.ConnectionPool;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.OkHttpClient.Builder;

/**
 * 自动配置RestTemplate，使用 OkHttp3 连接池。
 * <p>
 * 需要在项目中的 {@code application.yml} 中添加下面的属性启用此配置<br>
 *
 * <pre>
 * myoss-cloud.ok-http3.connection-pool.enabled = false
 * </pre>
 *
 * @author Jerry.Chen
 * @since 2018年5月21日 上午11:06:44
 * @see OkHttp3ConnectionPoolProperties
 */
@AutoConfigureAfter(name = "org.springframework.cloud.client.loadbalancer.LoadBalancerAutoConfiguration")
@ConditionalOnClass({ ConnectionPool.class, RestTemplate.class })
@EnableConfigurationProperties(OkHttp3ConnectionPoolProperties.class)
@ConditionalOnProperty(prefix = WebConstants.OK_HTTP3_CONNECTION_CONFIG_PREFIX, value = "enabled", matchIfMissing = false)
@ConditionalOnWebApplication
@Configuration
public class RestTemplate4OkHttp3ClientAutoConfiguration {
    private final OkHttp3ConnectionPoolProperties properties;

    /**
     * 初始化 OkHttp3连接池属性配置
     *
     * @param properties OkHttp3连接池属性配置
     */
    public RestTemplate4OkHttp3ClientAutoConfiguration(OkHttp3ConnectionPoolProperties properties) {
        this.properties = properties;
    }

    /**
     * HTTP连接池管理器，用于{@link #restTemplate4OkHttp3}，使用 spring 管理，方便项目中替换此对象或者获取此对象
     *
     * @return HTTP连接池对象
     */
    @ConditionalOnMissingBean(name = "restTemplate4OkHttp3ConnectionPool")
    @Bean(name = "restTemplate4OkHttp3ConnectionPool")
    public ConnectionPool restTemplate4OkHttp3ConnectionPool() {
        return new ConnectionPool(properties.getMaxIdleConnections(), properties.getKeepAliveDuration(),
                TimeUnit.MINUTES);
    }

    /**
     * 应用拦截器，用于{@link #restTemplate4OkHttp3}，使用 spring 管理，方便项目中替换此对象或者获取此对象
     *
     * @return 应用拦截器对象，默认为空
     */
    @ConditionalOnMissingBean(name = "restTemplate4OkHttp3Interceptor")
    @Bean(name = "restTemplate4OkHttp3Interceptor")
    public List<Interceptor> restTemplate4OkHttp3Interceptor() {
        return Collections.emptyList();
    }

    /**
     * 网络拦截器，用于{@link #restTemplate4OkHttp3}，使用 spring 管理，方便项目中替换此对象或者获取此对象
     *
     * @return 网络拦截器对象，默认为空
     */
    @ConditionalOnMissingBean(name = "restTemplate4OkHttp3NetworkInterceptor")
    @Bean(name = "restTemplate4OkHttp3NetworkInterceptor")
    public List<Interceptor> restTemplate4OkHttp3NetworkInterceptor() {
        return Collections.emptyList();
    }

    /**
     * 创建OkHttp3 RestTemplate，使用HTTP连接池
     *
     * @param defaultFastJsonConfig 参考：
     *            {@link FastJsonAutoConfiguration#defaultFastJsonConfig()}
     * @param restTemplate4OkHttp3ConnectionPool 参考：
     *            {@link #restTemplate4OkHttp3ConnectionPool()}
     * @param restTemplate4OkHttp3Interceptor 参考：
     *            {@link #restTemplate4OkHttp3Interceptor()}
     * @param restTemplate4OkHttp3NetworkInterceptor 参考：
     *            {@link #restTemplate4OkHttp3NetworkInterceptor()}
     * @param loadBalancerClientRequestInterceptorProvider RestTemplate拦截器
     * @return OkHttp3 RestTemplate 对象
     */
    @ConditionalOnMissingBean(name = WebConstants.REST_TEMPLATE4_OK_HTTP3_BEAN_NAME)
    @Bean(name = WebConstants.REST_TEMPLATE4_OK_HTTP3_BEAN_NAME)
    public RestTemplate restTemplate4OkHttp3(@Qualifier("defaultFastJsonConfig") ObjectProvider<?> defaultFastJsonConfig,
                                             ConnectionPool restTemplate4OkHttp3ConnectionPool,
                                             List<Interceptor> restTemplate4OkHttp3Interceptor,
                                             List<Interceptor> restTemplate4OkHttp3NetworkInterceptor,
                                             ObjectProvider<LoadBalancerClientRequestInterceptor> loadBalancerClientRequestInterceptorProvider) {
        Builder builder = new OkHttpClient().newBuilder();
        if (!CollectionUtils.isEmpty(restTemplate4OkHttp3Interceptor)) {
            for (Interceptor item : restTemplate4OkHttp3Interceptor) {
                builder.addInterceptor(item);
            }
        }
        if (!CollectionUtils.isEmpty(restTemplate4OkHttp3NetworkInterceptor)) {
            for (Interceptor item : restTemplate4OkHttp3NetworkInterceptor) {
                builder.addNetworkInterceptor(item);
            }
        }
        OkHttpClient httpClient = builder.connectTimeout(properties.getConnectTimeout(), TimeUnit.MILLISECONDS)
                .readTimeout(properties.getReadTimeout(), TimeUnit.MILLISECONDS)
                .writeTimeout(properties.getWriteTimeout(), TimeUnit.MILLISECONDS)
                .connectionPool(restTemplate4OkHttp3ConnectionPool)
                .build();

        // httpClient连接配置，底层是配置RequestConfig
        OkHttp3ClientHttpRequestFactory clientHttpRequestFactory = new OkHttp3ClientHttpRequestFactory(httpClient);

        // 定义附加的HTTP消息转换器
        // 支持UTF-8或者自定义的编码，StringHttpMessageConverter默认编码会使用ISO-8859-1
        StringHttpMessageConverter stringHttpMessageConverter = new StringHttpMessageConverter(
                MyossConstants.DEFAULT_CHARSET);
        stringHttpMessageConverter.setWriteAcceptCharset(false);

        RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory);
        LoadBalancerClientRequestInterceptor loadBalancerClientRequestInterceptor = loadBalancerClientRequestInterceptorProvider
                .getIfAvailable();
        if (loadBalancerClientRequestInterceptor != null) {
            List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>(restTemplate.getInterceptors());
            interceptors.add(0, loadBalancerClientRequestInterceptor);
            restTemplate.setInterceptors(interceptors);
        }
        List<HttpMessageConverter<?>> messageConverters = restTemplate.getMessageConverters();
        messageConverters.add(1, stringHttpMessageConverter);
        if (JsonApi.FASTJSON_PRESENT) {
            com.alibaba.fastjson.support.config.FastJsonConfig fastJsonConfig = (com.alibaba.fastjson.support.config.FastJsonConfig) defaultFastJsonConfig
                    .getIfAvailable();
            if (fastJsonConfig != null) {
                FastJsonHttpMessageConverter fastJsonHttpMessageConverter = fastJsonHttpMessageConverter(
                        fastJsonConfig);
                messageConverters.add(3, fastJsonHttpMessageConverter);
            }
        }
        return restTemplate;
    }

    /**
     * Spring Cloud 项目，RestTemplate 自动配置
     * LoadBalancer拦截器，默认会注册：{@link LoadBalancerClientRequestInterceptor}
     */
    @ConditionalOnBean(LoadBalancerClient.class)
    @Configuration
    public static class SpringCloudProjectAutoConfiguration {
        @Autowired
        private LoadBalancerClient                   loadBalancerClient;
        @Autowired(required = false)
        private List<LoadBalancerRequestTransformer> transformers = Collections.emptyList();

        @ConditionalOnMissingBean
        @Bean
        public LoadBalancerClientRequestFactory loadBalancerClientRequestFactory() {
            return new LoadBalancerClientRequestFactory(loadBalancerClient, transformers);
        }

        /**
         * RestTemplate LoadBalancer拦截器，用于{@link #restTemplate4OkHttp3}，使用
         * spring 管理，方便项目中替换此对象或者获取此对象
         *
         * @param loadBalancerClientRequestFactory
         *            LoadBalancerClientRequestFactory
         * @return 应用拦截器对象，默认为空
         */
        @ConditionalOnMissingBean(name = "loadBalancerClientRequestInterceptor")
        @Bean(name = "loadBalancerClientRequestInterceptor")
        public LoadBalancerClientRequestInterceptor loadBalancerClientRequestInterceptor(LoadBalancerClientRequestFactory loadBalancerClientRequestFactory) {
            return new LoadBalancerClientRequestInterceptor(loadBalancerClient, loadBalancerClientRequestFactory);
        }
    }
}

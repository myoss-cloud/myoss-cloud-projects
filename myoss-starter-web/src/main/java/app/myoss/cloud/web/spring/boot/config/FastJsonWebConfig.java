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

package app.myoss.cloud.web.spring.boot.config;

import java.util.List;

import org.springframework.http.MediaType;

import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.google.common.collect.Lists;

import app.myoss.cloud.core.constants.MyossConstants;

/**
 * Fast Json的 web 配置
 *
 * @author Jerry.Chen
 * @since 2018年4月12日 下午5:27:16
 */
public class FastJsonWebConfig {
    /**
     * 使用Fast Json输出json
     * <p>
     * 如果直接注入，此转换器会添加在“转换器集合”的最前面，在输出/输入String类型的数据的时候，会将<code>"</code>添加转义符
     * <p>
     * 正因为添加了转义符，导致“服务端响应”将String类型的数据发送给“业务系统”、回写给“客户端”，出现了本不应该出现的转义符
     * <p>
     * 解决办法：不直接注入，使用
     * {@code app.myoss.cloud.web.spring.boot.config.AbstractWebMvcConfigurer#extendMessageConverters}
     * 添加
     *
     * @param fastJsonConfig Fast Json的配置信息
     * @return Fast Json SpringMVC转换器
     */
    public static FastJsonHttpMessageConverter fastJsonHttpMessageConverter(FastJsonConfig fastJsonConfig) {
        FastJsonHttpMessageConverter jsonHttpMessageConverter = new FastJsonHttpMessageConverter();
        jsonHttpMessageConverter.setDefaultCharset(MyossConstants.DEFAULT_CHARSET);
        List<MediaType> mediaTypes = Lists.newArrayList(MediaType.APPLICATION_JSON);
        jsonHttpMessageConverter.setSupportedMediaTypes(mediaTypes);
        jsonHttpMessageConverter.setFastJsonConfig(fastJsonConfig);
        return jsonHttpMessageConverter;
    }
}

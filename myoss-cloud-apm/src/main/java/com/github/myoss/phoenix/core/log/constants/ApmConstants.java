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

package com.github.myoss.phoenix.core.log.constants;

/**
 * TODO 类实现描述
 *
 * @author Jerry.Chen
 * @since 2018年8月30日 上午10:14:38
 */
public class ApmConstants {
    /**
     * add property to MDC context "spanExportable"
     *
     * @see org.springframework.cloud.sleuth.log.Slf4jCurrentTraceContext
     */
    public static final String SPAN_EXPORTABLE_NAME   = "spanExportable";
    /**
     * add property to MDC context "parentId"
     *
     * @see org.springframework.cloud.sleuth.log.Slf4jCurrentTraceContext
     */
    public static final String PARENT_ID_NAME         = "parentId";
    /**
     * add property to MDC context "traceId"
     *
     * @see org.springframework.cloud.sleuth.log.Slf4jCurrentTraceContext
     */
    public static final String TRACE_ID_NAME          = "traceId";
    /**
     * add property to MDC context "spanId"
     *
     * @see org.springframework.cloud.sleuth.log.Slf4jCurrentTraceContext
     */
    public static final String SPAN_ID_NAME           = "spanId";

    /**
     * adding legacy "X-B3" entries to MDC context "X-B3-Export"
     *
     * @see org.springframework.cloud.sleuth.log.Slf4jCurrentTraceContext
     */
    public static final String LEGACY_EXPORTABLE_NAME = "X-Span-Export";
    /**
     * adding legacy "X-B3" entries to MDC context "X-B3-ParentSpanId"
     *
     * @see org.springframework.cloud.sleuth.log.Slf4jCurrentTraceContext
     */
    public static final String LEGACY_PARENT_ID_NAME  = "X-B3-ParentSpanId";
    /**
     * adding legacy "X-B3" entries to MDC context "X-B3-TraceId"
     *
     * @see org.springframework.cloud.sleuth.log.Slf4jCurrentTraceContext
     */
    public static final String LEGACY_TRACE_ID_NAME   = "X-B3-TraceId";
    /**
     * adding legacy "X-B3" entries to MDC context "X-B3-SpanId"
     *
     * @see org.springframework.cloud.sleuth.log.Slf4jCurrentTraceContext
     */
    public static final String LEGACY_SPAN_ID_NAME    = "X-B3-SpanId";
}

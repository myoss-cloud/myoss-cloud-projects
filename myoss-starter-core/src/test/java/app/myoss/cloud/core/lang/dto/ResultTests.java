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

package app.myoss.cloud.core.lang.dto;

import org.junit.Assert;
import org.junit.Test;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import app.myoss.cloud.core.lang.json.JsonApi;

/**
 * {@link Result} 测试类
 *
 * @author Jerry.Chen
 * @since 2018年5月14日 上午11:14:43
 */
public class ResultTests {
    @Test
    public void test1() {
        Result<Long> result = new Result<>();
        result.setSuccess(true);
        result.setValue(12345L);
        result.setErrorMsg("it's ok");
        result.setErrorCode("NONE");
        String json = new GsonBuilder().disableHtmlEscaping().create().toJson(result);
        Assert.assertEquals("{\"value\":12345,\"success\":true,\"errorCode\":\"NONE\",\"errorMsg\":\"it's ok\"}", json);
    }

    @Test
    public void test2() {
        Result<Long> result = new Result<>();
        result.setSuccess(false);
        result.setErrorMsg("field value is blank");
        result.setErrorCode("blankValue");
        String json = JsonApi.toJson(result);
        Assert.assertEquals("{\"success\":false,\"errorCode\":\"blankValue\",\"errorMsg\":\"field value is blank\"}",
                json);
    }

    @Test
    public void test3() {
        Result<Long> result = new Result<>(123456L);
        String json = JsonApi.toJson(result);
        Assert.assertEquals("{\"value\":123456,\"success\":true}", json);
    }

    @Test
    public void test4() {
        Result<Long> result = new Result<>(123456L);
        result.addExtraInfo("name", "HanMeiMei");
        result.addExtraInfo("age", 18);
        String json = JsonApi.toJson(result);
        Result<Long> actual = JsonApi.fromJson(json, new TypeToken<Result<Long>>() {
        }.getType());
        Assert.assertEquals(result, actual);
        Assert.assertEquals(18, result.getExtraInfo("age"));
        Assert.assertEquals("HanMeiMei", result.getExtraInfo("name"));
    }

    @Test
    public void test5() {
        Result<Long> result = new Result<>();
        result.setSuccess(true);
        result.setValue(12345L);
        result.setErrorMsg("it's ok");
        result.setErrorCode("NONE");
        result.addExtraInfo("name", "HanMeiMei");
        result.addExtraInfo("age", 18);
        String json = new GsonBuilder().disableHtmlEscaping().create().toJson(result);
        Result<Long> actual = JsonApi.fromJson(json, new TypeToken<Result<Long>>() {
        }.getType());
        Assert.assertEquals(result, actual);
    }

    @Test
    public void constructorTest1() {
        Result<Long> result = new Result<>(6666L);
        String toString = result.toString();
        String expected = "{\"value\":6666,\"success\":true}";
        Assert.assertEquals(expected, toString);
    }

    @Test
    public void constructorTest2() {
        Result<Long> result = new Result<>(6666L);
        result.setValue(777L);
        result.setSuccess(false);
        result.setErrorMsg("it's ok");
        result.setErrorCode("NONE");
        result.addExtraInfo("name", "HanMeiMei");
        result.addExtraInfo("age", 18);
        String toString = result.toString();
        Result<Long> actual = JsonApi.fromJson(toString, new TypeToken<Result<Long>>() {
        }.getType());
        Assert.assertEquals(result, actual);
    }

    @Test
    public void constructorTest3() {
        Result<Long> result = new Result<>("it's ok", "NONE");
        result.setValue(777L);
        result.addExtraInfo("name", "HanMeiMei");
        result.addExtraInfo("age", 18);
        String toString = result.toString();
        Result<Long> actual = JsonApi.fromJson(toString, new TypeToken<Result<Long>>() {
        }.getType());
        Assert.assertEquals(result, actual);
    }

    @Test
    public void constructorTest4() {
        Result<Long> result = new Result<>("NONE", "it's ok");
        String toString = result.toString();
        String expected = "{\"success\":false,\"errorCode\":\"NONE\",\"errorMsg\":\"it's ok\"}";
        Assert.assertEquals(expected, toString);
    }

    @Test
    public void constructorTest5() {
        Result<Long> result = new Result<>(true, "it's ok", "NONE");
        result.setValue(777L);
        result.addExtraInfo("name", "HanMeiMei");
        result.addExtraInfo("age", 18);
        String toString = result.toString();
        Result<Long> actual = JsonApi.fromJson(toString, new TypeToken<Result<Long>>() {
        }.getType());
        Assert.assertEquals(result, actual);
    }

    @Test
    public void constructorTest6() {
        Result<Long> result = new Result<>(false, "it's ok", "NONE");
        result.setValue(777L);
        result.addExtraInfo("name", "HanMeiMei");
        result.addExtraInfo("age", 18);
        String toString = result.toString();
        Result<Long> actual = JsonApi.fromJson(toString, new TypeToken<Result<Long>>() {
        }.getType());
        Assert.assertEquals(result, actual);
    }
}

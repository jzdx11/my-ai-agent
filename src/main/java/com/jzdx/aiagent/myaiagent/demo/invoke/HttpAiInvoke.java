package com.jzdx.aiagent.myaiagent.demo.invoke;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.alibaba.dashscope.aigc.generation.Generation;
import com.alibaba.dashscope.aigc.generation.GenerationParam;
import com.alibaba.dashscope.aigc.generation.GenerationResult;
import com.alibaba.dashscope.common.Message;
import com.alibaba.dashscope.common.Role;
import com.alibaba.dashscope.exception.ApiException;
import com.alibaba.dashscope.exception.InputRequiredException;
import com.alibaba.dashscope.exception.NoApiKeyException;
import com.alibaba.dashscope.utils.JsonUtils;


import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class HttpAiInvoke {
    final static String url = "https://dashscope.aliyuncs.com/api/v1/services/aigc/text-generation/generation";
    final static String model = "qwen-plus";

    public static void main(String[] args) {
        // 设置请求头
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Authorization", "Bearer " + System.getenv("DASHSCOPE_API_KEY"));
        headers.put("Content-Type", "application/json");



        // messages
        JSONArray messages = JSONUtil.createArray();
        messages.add(JSONUtil.createObj()
                .put("role", "system")
                .put("content", "You are a helpful assistant."));
        messages.add(JSONUtil.createObj()
                .put("role", "user")
                .put("content", "你是谁？"));

        // input
        JSONObject input = JSONUtil.createObj()
               .put("messages", messages);

        // parameters
        JSONObject parameters = JSONUtil.createObj()
                        .put("result_format", "message");

        // 设置请求体
        JSONObject requestBody = JSONUtil.createObj()
                .put("model", model)
                .put("input", input)
                .put("parameters", parameters);
//        System.out.println(requestBody.toString());
        HttpResponse response  = HttpRequest.post(url)
                .addHeaders(headers)
                .body(requestBody.toString())
                .execute();

        // 处理响应
        if (response.isOk()) {
            System.out.println("请求成功，响应内容：");
            System.out.println(response.body());
        } else {
            System.out.println("请求失败，状态码：" + response.getStatus());
            System.out.println("响应内容：" + response.body());
        }

    }
}

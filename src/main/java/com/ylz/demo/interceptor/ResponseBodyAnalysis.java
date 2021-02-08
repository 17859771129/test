package com.ylz.demo.interceptor;

import com.ylz.demo.tool.Constants;
import com.ylz.demo.tool.ResponseBean;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

/*
ResponseBodyAdvice可以在注解@ResponseBody将返回值处理成相应格式之前操作返回值。
实现这个接口即可完成相应操作。可用于返回值加密
 */
@ControllerAdvice
public class ResponseBodyAnalysis implements ResponseBodyAdvice {

    public static final List<String> excludedMethod = Arrays.asList("login", "logout", "getPatient");

    public static final List<String> includedMethod = Arrays.asList("searchData", "searchSeniorData", "getDoc");

    /*
     * Invoked after an {@code HttpMessageConverter} is selected and just before
     * its write method is invoked.
     * @param body the body to be written（需要写操作的body）
     * @param returnType the return type of the controller method
     * @param selectedContentType the content type selected through content negotiation
     * @param selectedConverterType the converter type selected to write to the response
     * @param request the current request
     * @param response the current response
     * @return the body that was passed in or a modified (possibly new) instance
     */
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter arg1,
                                  MediaType arg2, Class arg3, ServerHttpRequest arg4,
                                  ServerHttpResponse arg5) {
        ResponseBean responseBean = (ResponseBean) JSONObject.toBean(JSONObject.fromObject(body), ResponseBean.class);
        if (StringUtils.equals(responseBean.getCode(), Constants.ERROR_CODE) || StringUtils.equals(responseBean.getCode(), "50014")) {
            return body;
        }
        Object object = responseBean.getObject();
        if (null != object) {
            JSONObject jsonObject = JSONObject.fromObject(object);
            putSenseData(jsonObject);
            responseBean.setObject(jsonObject);
        }
        List listJson = responseBean.getRows();
        JSONArray jsonArray = new JSONArray();
        if (CollectionUtils.isNotEmpty(listJson)) {
            for (Object json : listJson) {
                JSONObject jsonObject = JSONObject.fromObject(json);
                putSenseData(jsonObject);
                jsonArray.add(jsonObject);
            }
            responseBean.setRows(jsonArray);
        }
        return responseBean.toString();
    }

    /**
     * 数据加密展示
     *
     * @param jsonObject
     */
    private void putSenseData(JSONObject jsonObject) {
        if (jsonObject.containsKey("patientName") && !jsonObject.getString("patientName").isEmpty()) {
            String patientName = jsonObject.getString("patientName");
            patientName = getPatientName(patientName);
            jsonObject.put("patientName", patientName);
        }
        if (jsonObject.containsKey("identifyNo") && !jsonObject.getString("identifyNo").isEmpty()) {
            String idNum = jsonObject.getString("identifyNo");
            int length = idNum.length();
            if (length > 3) {
                idNum = idNum.substring(0, 3) + "************" + idNum.substring(length - 3, length);
            }
            jsonObject.put("identifyNo", idNum);
        }
        if (jsonObject.containsKey("identityNo") && !jsonObject.getString("identityNo").isEmpty()) {
            String idNo = jsonObject.getString("identityNo");
            int length = idNo.length();
            if (length > 3) {
                idNo = idNo.substring(0, 3) + "************" + idNo.substring(length - 3, length);
            }
            jsonObject.put("identityNo", idNo);
        }
        if (jsonObject.containsKey("idNo") && !jsonObject.getString("idNo").isEmpty()) {
            String idNo = jsonObject.getString("idNo");
            int length = idNo.length();
            if (length > 3) {
                idNo = idNo.substring(0, 3) + "************" + idNo.substring(length - 3, length);
            }
            jsonObject.put("idNo", idNo);
        }
        if (jsonObject.containsKey("phoneNo") && !jsonObject.getString("phoneNo").isEmpty()) {
            String telephone = jsonObject.getString("phoneNo");
            if (telephone.length() >= 3) {
                telephone = telephone.substring(0, 3) + "********";
            }
            jsonObject.put("phoneNo", telephone);
        }
        if (jsonObject.containsKey("telephone") && !jsonObject.getString("telephone").isEmpty()) {
            String telephone = jsonObject.getString("telephone");
            if (telephone.length() >= 3) {
                telephone = telephone.substring(0, 3) + "********";
            }
            jsonObject.put("telephone", telephone);
        }
    }

    private String getPatientName(String patientName) {
        int length = patientName.length();
        if (length <= 3) {
            return patientName.substring(0, 1) + "*" + patientName.substring(2, length);
        } else if (length > 3 && length <= 6) {
            return patientName.substring(0, 1) + "**" + patientName.substring(3, length);
        } else if (length > 6) {
            return patientName.substring(0, 2) + "****" + patientName.substring(6, length);
        }
        return null;
    }

    @Override
    public boolean supports(MethodParameter paramMethodParameter, Class arg1) {
        boolean isIntercept = false;
        //不拦截
        Method method = paramMethodParameter.getMethod();

        //拦截指定方法
        if (includedMethod.contains(method.getName())) {
            isIntercept = true;
        }
        return isIntercept;
    }

}
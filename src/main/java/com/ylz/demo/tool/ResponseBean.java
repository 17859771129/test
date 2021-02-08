package com.ylz.demo.tool;

import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xy.tang on 2018/5/8.
 */
public class ResponseBean {

    private String code;
    private String message;
    private int total;
    private List rows;
    private Object object;

    public ResponseBean() {
        this.code = Constants.ERROR_CODE;
        this.total = 0;
        this.rows = new ArrayList<>();
        this.message = "";
    }

    public ResponseBean(String handleCode, String message) {
        this.code = handleCode;
        this.message = message;
    }

    public ResponseBean(String handleCode, String message, List rows, int total) {
        this.code = handleCode;
        this.message = message;
        this.total = total;
        this.rows = rows;
    }

    public ResponseBean(String handleCode, String message, Object object) {
        this.code = handleCode;
        this.message = message;
        this.object = object;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List getRows() {
        return rows;
    }

    public void setRows(List rows) {
        this.rows = rows;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    @Override
    public String toString() {
        return JSONObject.fromObject(this).toString();
    }
}

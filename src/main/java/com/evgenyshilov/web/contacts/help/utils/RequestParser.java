package com.evgenyshilov.web.contacts.help.utils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

/**
 * Created by Evgeny Shilov on 20.09.2016.
 */
public class RequestParser {

    private HttpServletRequest request;

    public RequestParser(HttpServletRequest request) {
        this.request = request;
    }

    public ArrayList<Long> getCheckedIdList(String checkboxGroupName) {
        String[] checkedValues = request.getParameterValues(checkboxGroupName);
        ArrayList<Long> checkedIdList = new ArrayList<>();

        if (checkedValues != null) {
            for (String value : checkedValues) {
                checkedIdList.add(Long.parseLong(value));
            }
        }

        return checkedIdList;
    }
}

package com.evgenyshilov.web.contacts.help;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Evgeny Shilov on 20.09.2016.
 */
public class RequestParser {

    private HttpServletRequest request;

    public RequestParser(HttpServletRequest request) {
        this.request = request;
    }

    public ArrayList<Integer> getCheckedIdList(String checkboxGroupName) {
        String[] checkedValues = request.getParameterValues(checkboxGroupName);
        ArrayList<Integer> checkedIdList = new ArrayList<>();

        System.out.println(Arrays.toString(checkedValues));

        if (checkedValues != null) {
            for (String value : checkedValues) {
                checkedIdList.add(Integer.parseInt(value));
            }
        }

        return checkedIdList;
    }
}

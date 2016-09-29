package com.evgenyshilov.web.contacts.commands.search;

import com.evgenyshilov.web.contacts.exceptions.CustomException;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;

/**
 * Created by Evgeny Shilov on 29.09.2016.
 */
public class SearchParamsDTOBuilder {
    public SearchParamsDTO createFromRequest(HttpServletRequest request) throws CustomException {
        SearchParamsDTO paramsDTO = new SearchParamsDTO();

        paramsDTO.setFirstName(request.getParameter("first-name"));
        paramsDTO.setLastName(request.getParameter("last-name"));
        paramsDTO.setPatronymic(request.getParameter("patronymic"));
        try {
            paramsDTO.setBirthday(createDate(request.getParameter("year"),
                    request.getParameter("day"), request.getParameter("day")));
        } catch (CustomException e) {
            throw new CustomException("Can't create search params DTO: ", e);
        }
        paramsDTO.setDateSearchParam(getDateSearchParam(request.getParameter("date-param")));
        paramsDTO.setSex(request.getParameter("sex"));
        paramsDTO.setMaritalStatus(Integer.parseInt(request.getParameter("marital-status")));
        paramsDTO.setNationality(request.getParameter("nationality"));
        paramsDTO.setState(request.getParameter("state"));
        paramsDTO.setCity(request.getParameter("city"));
        paramsDTO.setStreet(request.getParameter("street"));
        paramsDTO.setHouse(request.getParameter("house"));
        paramsDTO.setFlat(request.getParameter("flat"));
        paramsDTO.setZipcode(request.getParameter("zipcode"));

        return paramsDTO;
    }

    private DateSearchParam getDateSearchParam(String dateSearchParamString) {
        switch(dateSearchParamString) {
            case "older":
                return DateSearchParam.OLDER;
            case "younger":
                return DateSearchParam.YOUNGER;
            case "equal":
            default:
                return DateSearchParam.EQUAL;
        }
    }

    private Date createDate(String year, String day, String month) throws CustomException {
        if (isCorrectDate(year, day, month)) {
            DateTime tempDate = new DateTime();
            try {
                tempDate = tempDate.withDate(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day));
                return new Date(tempDate.getMillis());
            } catch (NumberFormatException e) {
                throw new CustomException("Can't create date from input values: ", e);
            }
        } else {
            return null;
        }
    }

    private boolean isCorrectDate(String year, String day, String month) {
        return !(StringUtils.isEmpty(year) || StringUtils.isEmpty(month) || StringUtils.isEmpty(day));
    }
}

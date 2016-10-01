package com.evgenyshilov.web.contacts.help;

import com.evgenyshilov.web.contacts.exceptions.CustomException;
import org.apache.commons.lang3.StringUtils;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

/**
 * Created by Evgeny Shilov on 01.10.2016.
 */
public class StatementUtils {
    public static void setStatementDateValue(PreparedStatement statement, int index, Date value) throws CustomException {
        try {
            if (value != null) {
                statement.setDate(index, value);
            } else {
                statement.setNull(index, Types.DATE);
            }
        } catch (SQLException e) {
            throw new CustomException("Can't set statement date value: ", e);
        }
    }

    public static void setStatementStringValue(PreparedStatement statement, int index, String value) throws CustomException {
        try {
            if (!StringUtils.isEmpty(value)) {
                statement.setString(index, value);
            } else {
                statement.setNull(index, Types.VARCHAR);
            }
        } catch (SQLException e) {
            throw new CustomException("Can't set statement string value: ", e);
        }
    }

    public static void setStatementLongValue(PreparedStatement statement, int index, Long value) throws CustomException {
        try {
            if (value != null) {
                statement.setLong(index, value);
            } else {
                statement.setNull(index, Types.BIGINT);
            }
        } catch (SQLException e) {
            throw new CustomException("Can't set statement long value: " ,e);
        }
    }
}

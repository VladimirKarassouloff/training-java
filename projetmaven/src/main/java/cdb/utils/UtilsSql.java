package cdb.utils;

import cdb.mapper.MapperDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * Created by vkarassouloff on 18/05/17.
 */
public class UtilsSql {

    public static final Date MOST_ANCIENT_DATE = MapperDate.dateFromString("1970-01-01");

    private static final Logger LOGGER = LoggerFactory.getLogger(UtilsSql.class);


    /**
     * Close safely a result set.
     *
     * @param resultSet closed
     */
    public static void closeResultSetSafely(ResultSet resultSet) {
        if (resultSet == null) {
            return;
        }

        try {
            resultSet.close();
        } catch (SQLException e) {
            LOGGER.info("Error while closing result set");
        }
    }

    /**
     * Close safely a statement.
     *
     * @param statement closed
     */
    public static void closeStatementSafely(PreparedStatement statement) {
        if (statement == null) {
            return;
        }

        try {
            statement.close();
        } catch (SQLException e) {
            LOGGER.info("Error while closing statement");
        }
    }


}
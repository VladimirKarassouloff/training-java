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
public class UtilsValidation {

    public static final Date MOST_ANCIENT_DATE = MapperDate.dateFromString("1970-01-01");

}

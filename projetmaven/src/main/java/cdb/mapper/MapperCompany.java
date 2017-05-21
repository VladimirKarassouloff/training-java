package cdb.mapper;

import cdb.model.Company;
import cdb.utils.SqlNames;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;

@Service
public class MapperCompany implements RowMapper<Company> {

    @Override
    public Company mapRow(ResultSet resultSet, int i) throws SQLException {
        int companyId = resultSet.getInt(SqlNames.COMPANY_COL_COMPANY_ID);
        String companyName = resultSet.getString(SqlNames.COMPANY_COL_COMPANY_NAME);
        return new Company(companyId, companyName);
    }

}

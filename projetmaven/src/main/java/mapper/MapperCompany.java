package mapper;

import model.Company;
import persistence.CompanyDAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MapperCompany {

    /**
     * Map resultset to company.
     * @param rs resultset
     * @return company
     */
    public static Company mapResultSetToObject(ResultSet rs) {
        try {
            rs.next();
            Company com = mapResultSetToObjectAux(rs);
            rs.close();
            return com;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Map Resultset to companies.
     * @param rs resultset
     * @return list of companies
     */
    public static List<Company> mapResultSetToObjects(ResultSet rs) {
        List<Company> list = new ArrayList<>();
        try {
            while (rs.next()) {
                list.add(mapResultSetToObjectAux(rs));
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Map result set to company without closing the resultset.
     * @param rs resultset
     * @return company
     */
    public static Company mapResultSetToObjectAux(ResultSet rs) {
        int companyId;
        try {
            companyId = rs.getInt(CompanyDAO.COL_COMPANY_ID);
            String companyName = rs.getString(CompanyDAO.COL_COMPANY_NAME);
            return new Company(companyId, companyName);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}

package persistence.filter;

import utils.SqlNames;

/**
 * Created by vkara on 01/05/2017.
 */
public class FilterSelectComputer extends FilterSelect {


    @Override
    public String colNumToName(int num) {
        switch (num) {
            case 0:
                return SqlNames.COMPUTER_TABLE_NAME + "." + SqlNames.COMPUTER_COL_COMPUTER_NAME;
            case 1:
                return SqlNames.COMPUTER_TABLE_NAME + "." + SqlNames.COMPUTER_COL_COMPUTER_INTRODUCED;
            case 2:
                return SqlNames.COMPUTER_TABLE_NAME + "." + SqlNames.COMPUTER_COL_COMPUTERDISCONTINUED;
            case 3:
                return SqlNames.COMPANY_TABLE_NAME + "." + SqlNames.COMPANY_COL_COMPANY_NAME;
            default:
                return SqlNames.COMPUTER_TABLE_NAME + "." + SqlNames.COMPUTER_COL_COMPUTER_ID;
        }
    }


    public static class Builder extends FilterSelect.Builder {

        @Override
        protected FilterSelect initFilter() {
            return new FilterSelectComputer();
        }
    }

}

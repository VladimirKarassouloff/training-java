package persistence.filter;

import persistence.operator.Filter;

import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

/**
 * Created by vkarassouloff on 27/04/17.
 */
public abstract class FilterSelect {

    public static final int DEFAULT_NUMBER_OF_RESULT = 8;

    // Pagination
    protected boolean paginated;
    protected int page;
    protected int numberOfResult;

    // Research filter
    protected HashMap<String, Filter> filters;

    // Order filter
    protected String orderOnColumn;
    protected boolean asc;

    /**
     * Constructor.
     */
    public FilterSelect() {
        this.paginated = true;
        this.numberOfResult = DEFAULT_NUMBER_OF_RESULT;
        this.filters = new HashMap<>();
    }

    /**
     * Get which column are being filtered.
     * @return names of the column being filtered
     */
    public Set<String> getFilteredColumns() {
        return filters.keySet();
    }

    /**
     * Get all values.
     * @return values
     */
    public Collection<Filter> getFilterValues() {
        return filters.values();
    }

    /**
     * Get the value for a filter.
     * @param col filtered
     * @return filter
     */
    public Filter getFilterValue(String col) {
        return filters.get(col);
    }

    public abstract String colNumToName(int num);


    // Getters & Setters

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getNumberOfResult() {
        return numberOfResult;
    }

    public void setNumberOfResult(int numberOfResult) {
        this.numberOfResult = numberOfResult;
    }

    public HashMap<String, Filter> getFilters() {
        return filters;
    }

    public void setFilters(HashMap<String, Filter> filters) {
        this.filters = filters;
    }

    public String getOrderOnColumn() {
        return orderOnColumn;
    }

    public void setOrderOnColumn(String orderOnColumn) {
        this.orderOnColumn = orderOnColumn;
    }

    public boolean isAsc() {
        return asc;
    }

    public void setAsc(boolean asc) {
        this.asc = asc;
    }

    public boolean isPaginated() {
        return paginated;
    }

    public void setPaginated(boolean paginated) {
        this.paginated = paginated;
    }

    public abstract static class Builder {

        protected FilterSelect filter;

        /**
         * Builder for FilterSelect.
         */
        public Builder() {
            filter = initFilter();
        }

        /**
         * Create instance of the filter being built.
         */
        protected abstract FilterSelect initFilter();

        /**
         * Builder for FilterSelect.
         * @param colname colname
         * @param op filter
         * @return builder
         */
        public Builder withSearch(String colname, Filter op) {
            filter.filters.put(colname, op);
            return this;
        }

        /**
         * Builder for FilterSelect.
         * @param p page you are trying to select
         * @return builder
         */
        public Builder withPage(int p) {
            filter.page = p;
            return this;
        }

        /**
         * Builder for FilterSelect.
         * @param nb length of page
         * @return builder
         */
        public Builder withResultsPerPage(int nb) {
            filter.numberOfResult = nb;
            return this;
        }

        /**
         * Builder for FilterSelect.
         * @param numCol num of the column filtered
         * @param asc if filtering asc or desc
         * @return builder
         */
        public Builder withOrder(int numCol, boolean asc) {
            filter.orderOnColumn = filter.colNumToName(numCol);
            filter.asc = asc;
            return this;
        }

        /**
         * Builder for FilterSelect.
         * @param i new length
         * @return builder
         */
        public Builder withLengthPage(int i) {
            filter.numberOfResult = i;
            return this;
        }

        /**
         * Get the FilterSelect built.
         * @return filter built
         */
        public FilterSelect build() {
            return filter;
        }
    }
}

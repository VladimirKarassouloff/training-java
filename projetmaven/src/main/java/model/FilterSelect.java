package model;

import persistence.operator.Filter;

import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

/**
 * Created by vkarassouloff on 27/04/17.
 */
public class FilterSelect {

    public static final int DEFAULT_NUMBER_OF_RESULT = 8;

    // Pagination
    private boolean paginated;
    private int page;
    private int numberOfResult;

    // Research filter
    private HashMap<String, Filter> filters;

    // Order filter
    private String orderOnColumn;
    private boolean asc;

    /**
     * Constructor.
     */
    public FilterSelect() {
        this.paginated = true;
        this.numberOfResult = DEFAULT_NUMBER_OF_RESULT;
        this.filters = new HashMap<>();
    }

    // Methods
    public Set<String> getFilteredColumns() {
        return filters.keySet();
    }

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

    public static class Builder {

        private FilterSelect filter;

        /**
         * Builder for FilterSelect.
         */
        public Builder() {
            filter = new FilterSelect();
        }

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
         * @param s column filtered
         * @param asc if filtering asc or desc
         * @return builder
         */
        public Builder withOrder(String s, boolean asc) {
            filter.orderOnColumn = s;
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

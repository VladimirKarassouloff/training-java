package model;

import persistence.operator.Operator;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
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
    private HashMap<String,Operator> filters;

    // Order filter
    private String orderOnColumn;
    private boolean asc;

    public FilterSelect() {
        this.paginated = true;
        this.numberOfResult = DEFAULT_NUMBER_OF_RESULT;
        this.filters = new HashMap<>();
    }

    // Methods
    public Set<String> getFilteredColumns() {
        return filters.keySet();
    }

    public Collection<Operator> getFilterValues() {
        return filters.values();
    }

    public Operator getFilterValue(String col) {
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

    public HashMap<String, Operator> getFilters() {
        return filters;
    }

    public void setFilters(HashMap<String, Operator> filters) {
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

        public Builder() {
            filter = new FilterSelect();
        }

        public Builder withSearch(String s, Operator op) {
            filter.filters.put(s,op);
            return this;
        }

        public Builder withPage(int p) {
            filter.page = p;
            return this;
        }

        public Builder withResultsPerPage(int nb) {
            filter.numberOfResult = nb;
            return this;
        }

        public Builder withOrder(String s, boolean asc) {
            filter.orderOnColumn = s;
            filter.asc = asc;
            return this;
        }

        public Builder withLengthPage(int i) {
            filter.numberOfResult = i;
            return this;
        }

        public FilterSelect build() {
            return filter;
        }
    }
}

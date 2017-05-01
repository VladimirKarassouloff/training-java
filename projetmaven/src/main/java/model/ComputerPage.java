package model;

import dto.ComputerDTO;
import persistence.filter.FilterSelectComputer;

import java.util.List;

/**
 * Created by vkara on 01/05/2017.
 */
public class ComputerPage {

    private int totalCount;
    private int displayedPage;
    private int lengthPage;
    private List<ComputerDTO> results;

    public ComputerPage() {

    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<ComputerDTO> getResults() {
        return results;
    }

    public void setResults(List<ComputerDTO> results) {
        this.results = results;
    }

    public int getDisplayedPage() {
        return displayedPage;
    }

    public void setDisplayedPage(int displayedPage) {
        this.displayedPage = displayedPage;
    }

    public int getLengthPage() {
        return lengthPage;
    }

    public void setLengthPage(int lengthPage) {
        this.lengthPage = lengthPage;
    }

    public static class Builder {
        private ComputerPage cp;

        public Builder() {
            cp = new ComputerPage();
        }

        public Builder withDisplayedPage(int page) {
            cp.setDisplayedPage(page);
            return this;
        }

        public Builder withCount(int count) {
            cp.setTotalCount(count);
            return this;
        }

        public Builder withLengthPage(int length) {
            cp.setLengthPage(length);
            return this;
        }

        public ComputerPage build() {
            return cp;
        }
    }

}

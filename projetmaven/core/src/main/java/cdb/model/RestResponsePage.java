package cdb.model;

import org.springframework.data.domain.Page;

import java.util.List;

public class RestResponsePage<T> {

    private int size;
    private int totalPages;
    private int numberOfElements;
    private long totalElements;
    private List<T> content;

    /**
     * Def const.
     */
    public RestResponsePage() {

    }

    /**
     * Cons.
     * @param page fkzpf
     */
    public RestResponsePage(Page<T> page) {
        this.size = page.getSize();
        this.totalPages = page.getTotalPages();
        this.numberOfElements = page.getNumberOfElements();
        this.totalElements = page.getTotalElements();
        this.content = page.getContent();
    }


    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getNumberOfElements() {
        return numberOfElements;
    }

    public void setNumberOfElements(int numberOfElements) {
        this.numberOfElements = numberOfElements;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }
}
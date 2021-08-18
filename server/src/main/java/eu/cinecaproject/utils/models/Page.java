package eu.cinecaproject.utils.models;

import lombok.Data;

@Data
public class Page {
    private int size;
    private int totalElements;
    private int totalPages;
    private int number;
}

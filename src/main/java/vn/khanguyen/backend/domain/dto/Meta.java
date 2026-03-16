package vn.khanguyen.backend.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Meta {
    // page hien tai
    private int page;
    // limit cua page
    private int pageSize;
    // tong so page
    private int pages;
    // tong so object
    private long total;
}

package com.app14.standard.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class Page<T> {
    private final int pageSize;
    private final int totalPage;
    private final int currentPage;
    private final List<T> pageItems;
}

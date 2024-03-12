package com.springboot.courses.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassResponse {
    private List<?> content;
    private int pageNo;
    private int pageSize;
    private long totalElements;
    private int totalPage;
    private boolean last;


    public static ClassResponse convertToClassResponse(Page<?> pages, List<?> lists){
        ClassResponse classResponse = new ClassResponse();
        classResponse.setContent(lists);
        classResponse.setPageNo(pages.getNumber());
        classResponse.setPageSize(pages.getSize());
        classResponse.setTotalElements(pages.getTotalElements());
        classResponse.setTotalPage(pages.getTotalPages());
        classResponse.setLast(pages.isLast());

        return classResponse;
    }
}

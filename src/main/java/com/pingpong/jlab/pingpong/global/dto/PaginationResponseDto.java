package com.pingpong.jlab.pingpong.global.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaginationResponseDto<T> {
    private List<T> dataList = new ArrayList<>();

    private int currentPage;
    private int totalPages;
    private long totalCount;
    private PaginationRequestDto pagingInfo;

    public PaginationResponseDto(List<T> dataList, long totalCount, PaginationRequestDto pagingInfo){

        this.dataList = dataList;
        this.totalCount = totalCount;
        this.pagingInfo = pagingInfo;
        this.currentPage = pagingInfo.getPage();
        this.calcTotalPage();
    }

    public void calcTotalPage(){
        this.setTotalPages((int)Math.ceil((double)totalCount/pagingInfo.getLimit()));
    }
}

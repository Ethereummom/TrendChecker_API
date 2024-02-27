package com.pingpong.jlab.pingpong.global.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaginationRequestDto {

    private int page;
    private int limit;
    private int offset;
    private String category;
    private String keyword;
    

    //기본값 정의
    private static final int DEFAULT_PAGE = 1;
    private static final int DEFAULT_LIMIT = 10;

    public PaginationRequestDto(){
        this(DEFAULT_PAGE,DEFAULT_LIMIT);

    }

    public PaginationRequestDto(int page){
        this.page = page;
        this.limit = DEFAULT_LIMIT;

    }

    public PaginationRequestDto(int page, int limit){
        this.page = page;
        this.limit = limit;
    }

    public PaginationRequestDto(String category){

        this.page = DEFAULT_PAGE;
        this.limit = DEFAULT_LIMIT;
        this.category = category;
    }

    public PaginationRequestDto(String category , String keyword){
        
        this.page = DEFAULT_PAGE;
        this.limit = DEFAULT_LIMIT;
        this.category = category;
        this.keyword = keyword;
    }
    public PaginationRequestDto(int page, int limit, String category, String keyword){
        
        this.page = page;
        this.limit = limit;
        this.category = category;
        this.keyword = keyword;

    }

    public void setPageNumber(int page){
        this.page = page < 1 ? 1 : page;
        this.calcOffset();
    }

    public void setLimit(int limit){
        this.limit = limit;
        this.calcOffset();
    }

    public void calcOffset(){
        this.setOffset(((int)this.page - 1 )* ((int)this.limit));
    }
}

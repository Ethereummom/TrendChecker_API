 package com.pingpong.jlab.pingpong.domain.post.repository;

 import java.util.List;
 import java.util.Objects;

 import com.pingpong.jlab.pingpong.domain.post.converter.PostDtoConverter;
 import com.pingpong.jlab.pingpong.domain.post.dto.PostResponseDto;
 import com.pingpong.jlab.pingpong.global.dto.PaginationResponseDto;
 import lombok.extern.slf4j.Slf4j;
 import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

 import com.pingpong.jlab.pingpong.domain.post.entity.Post;
 import com.pingpong.jlab.pingpong.global.dto.PaginationRequestDto;
 import com.querydsl.core.types.dsl.BooleanExpression;
 import com.querydsl.jpa.JPQLQuery;


 import static com.pingpong.jlab.pingpong.domain.post.entity.QPost.*;

 @Slf4j
 public class PostRepositoryCustomImpl extends QuerydslRepositorySupport implements PostRepositoryCustom {

     public PostRepositoryCustomImpl(){super(Post.class);}
    
    
     @Override
     public PaginationResponseDto<PostResponseDto> getPostListWithSearchAndPaging(PaginationRequestDto dto){
         JPQLQuery<Post> query = from(post)
             .where(searchWithCondition(dto))
                 .offset(dto.getOffset())
                 .limit(dto.getLimit());
                    query.orderBy(post.createdAt.desc());

             List<Post> postList = query.fetch();
             long count = query.fetchCount();
             PaginationResponseDto<PostResponseDto> postListWithPaging = new PaginationResponseDto<>(PostDtoConverter.convert(postList),count,dto);
             log.info("datalist " + postListWithPaging);
             return postListWithPaging;

     }

     private BooleanExpression searchWithCondition(PaginationRequestDto dto){
         String category = dto.getCategory();
         log.info("category : : : " + category + "keyword : : : " + dto.getKeyword() + "page : : : " + dto.getPage());
         if(Objects.isNull(category)){
             return null;
         }

         switch(category.toUpperCase()){

             case "ALL":
                 return post.title.contains(dto.getKeyword())
                         .or(post.content.contains(dto.getKeyword())
                                 .or(post.user.email.contains(dto.getKeyword())));
             case "TITLE":
                 return post.title.contains(dto.getKeyword());
            
             case "CONTENT":
                 return post.content.contains(dto.getKeyword());
            
             case "TITLEANDCONTENT":
                 return post.title.contains(dto.getKeyword())
                     .or(post.content.contains(dto.getKeyword()));

             case "AUTHOR":
                 return post.user.email.contains(dto.getKeyword());

             default:
                 return null;
         }
     }

     private BooleanExpression sortWithRank(PaginationRequestDto dto){
         String category = dto.getCategory();
         if(Objects.isNull(category)){
             return null;
         }
         return null;
     }
 }

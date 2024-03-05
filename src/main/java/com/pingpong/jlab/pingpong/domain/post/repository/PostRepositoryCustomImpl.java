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

     @Override
     public List<PostResponseDto> getPostListByCategoryAndRank(String category){
         JPQLQuery<Post> query = from(post)
                 .where(post.category.eq(category))
                 .orderBy(post.recommend.desc())
                 .limit(5);

         List<Post> postList = query.fetch();
         return PostDtoConverter.convert(postList);
     }

     private BooleanExpression searchWithCondition(PaginationRequestDto dto){
         String category = dto.getCategory();
         log.info("category : : : " + category + " | keyword : : : " + dto.getKeyword() + " | page : : : " + dto.getPage());
         if(Objects.isNull(category)){
             return post.title.contains(dto.getKeyword())
                     .or(post.content.contains(dto.getKeyword())
                             .or(post.user.nickname.contains(dto.getKeyword())));
         }

         return switch (category.toUpperCase()) {
             case "ALL" -> post.title.contains(dto.getKeyword())
                     .or(post.content.contains(dto.getKeyword())
                             .or(post.user.nickname.contains(dto.getKeyword())));
             case "TITLE" -> post.title.contains(dto.getKeyword());
             case "CONTENT" -> post.content.contains(dto.getKeyword());
             case "TITLEANDCONTENT" -> post.title.contains(dto.getKeyword())
                     .or(post.content.contains(dto.getKeyword()));
             case "AUTHOR" -> post.user.nickname.contains(dto.getKeyword());
             default -> null;
         };
     }

     private BooleanExpression sortWithRank(PaginationRequestDto dto){
         String category = dto.getCategory();
         if(Objects.isNull(category)){
             return null;
         }
         return null;
     }
 }

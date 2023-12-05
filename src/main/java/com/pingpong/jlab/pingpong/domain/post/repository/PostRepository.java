package com.pingpong.jlab.pingpong.domain.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pingpong.jlab.pingpong.domain.post.entity.Post;
import com.pingpong.jlab.pingpong.global.dto.PaginationRequestDto;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post,Long>{

    @Query(value = "select * from post LIMIT :offset, :pageSize",nativeQuery = true)
    List<Post> getPostListWithPaging(@Param("offset") int offset, @Param("pageSize") int pageSize);

}

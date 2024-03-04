package com.pingpong.jlab.pingpong.domain.follow.entity;

import com.pingpong.jlab.pingpong.domain.user.entity.User;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Table
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Follow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long followSeq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_user")
    private User toUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_user")
    private User fromUser;

    @Builder
    public Follow(User toUser , User fromUser){
        this.toUser = toUser;
        this.fromUser = fromUser;
    }

}

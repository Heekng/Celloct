package com.heekng.celloct.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "join_request_member_id_shop_id_unique",
                        columnNames = {"member_id", "shop_id"}
                )
        }
)
public class JoinRequest extends BaseTimeEntity{

    @Id @GeneratedValue
    @Column(name = "join_request_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "shop_id", nullable = false)
    private Shop shop;

    @Builder
    public JoinRequest(Member member, Shop shop) {
        this.member = member;
        this.shop = shop;
    }
}

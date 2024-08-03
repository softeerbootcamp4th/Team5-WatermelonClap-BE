package com.watermelon.server.randomevent.domain;

import com.watermelon.server.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Link extends BaseEntity {

    @Id @GeneratedValue
    private Long id;

    private int viewCount;

    private String link;

    @OneToOne
    private LotteryApplier lotteryApplier;

}

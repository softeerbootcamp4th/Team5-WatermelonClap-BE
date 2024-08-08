package com.watermelon.server.event.lottery.parts.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Parts {

    @Id @GeneratedValue
    private Long id;

    private String name;

    private String description;

    @Enumerated(EnumType.STRING)
    private PartsCategory category;

    private String imgSrc;

    @OneToMany(mappedBy = "parts")
    private List<LotteryApplierParts> lotteryApplierParts;

    public static Parts createTestCategoryParts(PartsCategory category){
        return Parts.builder()
                .category(category)
                .build();
    }

    public static List<Parts> createAllParts(){
        return List.of(
                Parts.builder()
                        .name("홀로그램")
                        .category(PartsCategory.COLOR)
                        .description("홀로그램입니다.")
                        .imgSrc("www.s3.com/hg")
                        .build(),
                Parts.builder()
                        .name("레이싱")
                        .category(PartsCategory.COLOR)
                        .description("레이싱입니다.")
                        .imgSrc("www.s3.com/rc")
                        .build(),
                Parts.builder()
                        .name("퍼플")
                        .category(PartsCategory.COLOR)
                        .description("퍼플입니다.")
                        .imgSrc("www.s3.com/rc")
                        .build(),
                Parts.builder()
                        .name("형광 노랑")
                        .category(PartsCategory.COLOR)
                        .description("형광 노랑입니다.")
                        .imgSrc("www.s3.com/rc")
                        .build(),
                Parts.builder()
                        .name("피치")
                        .category(PartsCategory.COLOR)
                        .description("피치입니다.")
                        .imgSrc("www.s3.com/rc")
                        .build(),
                Parts.builder()
                        .name("연두")
                        .category(PartsCategory.COLOR)
                        .description("연두입니다.")
                        .imgSrc("www.s3.com/rc")
                        .build(),
                Parts.builder()
                        .name("스플래시")
                        .category(PartsCategory.COLOR)
                        .description("스플래시입니다.")
                        .imgSrc("www.s3.com/rc")
                        .build(),
                Parts.builder()
                        .name("카본 리어 스포일러")
                        .category(PartsCategory.REAR)
                        .description("카본 리어 스포일러입니다.")
                        .imgSrc("www.s3.com/rc")
                        .build(),
                Parts.builder()
                        .name("CFRP 리어 스포일러")
                        .category(PartsCategory.REAR)
                        .description("CFRP 리어 스포일러입니다.")
                        .imgSrc("www.s3.com/rc")
                        .build(),
                Parts.builder()
                        .name("ECO")
                        .category(PartsCategory.DRIVE_MODE)
                        .description("ECO입니다.")
                        .imgSrc("www.s3.com/rc")
                        .build(),
                Parts.builder()
                        .name("NORMAL")
                        .category(PartsCategory.DRIVE_MODE)
                        .description("NORMAL입니다.")
                        .imgSrc("www.s3.com/rc")
                        .build(),
                Parts.builder()
                        .name("SPORT")
                        .category(PartsCategory.DRIVE_MODE)
                        .description("SPORT입니다.")
                        .imgSrc("www.s3.com/rc")
                        .build(),
                Parts.builder()
                        .name("N 모드")
                        .category(PartsCategory.DRIVE_MODE)
                        .description("N 모드입니다.")
                        .imgSrc("www.s3.com/rc")
                        .build(),
                Parts.builder()
                        .name("단조 휠 화이트")
                        .category(PartsCategory.WHEEL)
                        .description("단조 휠 화이트입니다.")
                        .imgSrc("www.s3.com/rc")
                        .build(),
                Parts.builder()
                        .name("실버")
                        .category(PartsCategory.WHEEL)
                        .description("실버입니다.")
                        .imgSrc("www.s3.com/rc")
                        .build(),
                Parts.builder()
                        .name("매트 블랙")
                        .category(PartsCategory.WHEEL)
                        .description("매트 블랙입니다.")
                        .imgSrc("www.s3.com/rc")
                        .build()
        );
    }

}

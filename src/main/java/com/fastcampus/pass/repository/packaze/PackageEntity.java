package com.fastcampus.pass.repository.packaze;

import com.fastcampus.pass.BaseEntity;
import lombok.*;

import javax.persistence.*;

@Getter
@ToString
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "package")
public class PackageEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer packageSeq;

    private String packageName;
    private Integer count;
    private Integer period;

    @Builder(access = AccessLevel.PRIVATE)
    private PackageEntity(String packageName, Integer count, Integer period) {
        this.packageName = packageName;
        this.count = count;
        this.period = period;
    }

    public static PackageEntity toPackage(String packageName, Integer count, Integer period) {
        return PackageEntity.builder()
                .packageName(packageName)
                .count(count)
                .period(period)
                .build();
    }

    public static PackageEntity toPackage(String packageName, Integer period) {
        return PackageEntity.builder()
                .packageName(packageName)
                .period(period)
                .build();
    }

}

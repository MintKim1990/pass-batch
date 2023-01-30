package com.fastcampus.pass.repository;

import com.fastcampus.pass.packaze.PackageEntity;
import com.fastcampus.pass.packaze.PackageRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
@ActiveProfiles("test")
public class PackageRepositoryTest {

    @Autowired
    private PackageRepository packageRepository;

    @Test
    void test_save() {

        // given
        PackageEntity packageEntity = PackageEntity.toPackage("바디 챌린지 PT 12주", 84);

        // when
        packageRepository.save(packageEntity);

        // then
        assertNotNull(packageEntity.getPackageSeq());

    }

    @Test
    void test_findByCreatedAtAfter() {

        // given
        LocalDateTime dateTime = LocalDateTime.now().minusMinutes(1);

        PackageEntity studentPackage3Month = PackageEntity.toPackage("학생 전용 3개월", 90);
        packageRepository.save(studentPackage3Month);

        PackageEntity studentPackage6Month = PackageEntity.toPackage("학생 전용 6개월", 180);
        packageRepository.save(studentPackage6Month);

        // when
        final List<PackageEntity> packageEntities = packageRepository.findByCreatedAtAfter(dateTime, PageRequest.of(0, 1, Sort.by("packageSeq").descending()));

        // then
        assertEquals(1, packageEntities.size());
        assertEquals(studentPackage6Month.getPackageSeq(), packageEntities.get(0).getPackageSeq());
    }

    @Test
    void test_updateCountAndPeriod() {

        // given
        PackageEntity packageEntity = PackageEntity.toPackage("학생 전용 4개월", 90);
        packageRepository.save(packageEntity);

        // when
        int updatedCount = packageRepository.updateCountAndPeriod(packageEntity.getPackageSeq(), 30, 120);
        final PackageEntity updatedPackageEntity = packageRepository.findById(packageEntity.getPackageSeq()).get();

        // then
        assertEquals(1, updatedCount);
        assertEquals(30, updatedPackageEntity.getCount());
        assertEquals(120, updatedPackageEntity.getPeriod());

    }

    @Test
    void test_delete() {

        // given
        PackageEntity packageEntity = PackageEntity.toPackage("제거할 이용권", 1);
        packageRepository.save(packageEntity);

        // when
        packageRepository.deleteById(packageEntity.getPackageSeq());

        // then
        assertTrue(packageRepository.findById(packageEntity.getPackageSeq()).isEmpty());
    }

}

package com.techdevsolutions.shared.service;

import com.techdevsolutions.shared.beans.CollectionEvent;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.math3.random.RandomDataGenerator;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

@Service
public class RandomService {
    public static Integer getRandomIntegerInRange(int min, int max) {
        return new RandomDataGenerator().nextInt(min, max);
    }

    public static Long getRandomLongInRange(long min, long max) {
        return new RandomDataGenerator().nextLong(min, max);
    }

    public static Double getRandomDoubleInRange(double min, double max) {
        return new RandomDataGenerator().nextUniform(min, max);
    }

    public static String getRandomStringInRange(int length, boolean useLetters, boolean useNumbers) {
        return RandomStringUtils.random(length, useLetters, useNumbers);
    }

    public static CollectionEvent getRandomCollectionEvent() {
        CollectionEvent i = new CollectionEvent()
                .setSelector(StringUtils.leftPad(RandomService.getRandomIntegerInRange(0,1000000000).toString(), 10, '0'))
                .setSelectorType("selector type " + RandomService.getRandomIntegerInRange(1,3))
                .setType("collection type " + RandomService.getRandomIntegerInRange(1,3))
                .setSubType("collection subType " + RandomService.getRandomIntegerInRange(1,3))
                .setDate(new Date())
                .setLatitude(BigDecimal.valueOf(RandomService.getRandomDoubleInRange(30.2237693,45.111324))
                        .setScale(6, RoundingMode.HALF_UP)
                        .doubleValue())
                .setLongitude(BigDecimal.valueOf(RandomService.getRandomDoubleInRange(-117.8735531,-78.6768723))
                        .setScale(6, RoundingMode.HALF_UP)
                        .doubleValue())
                .setData(RandomService.getRandomIntegerInRange(1,3).toString());
        return i.setSelectorTyped(i.getSelector() + "<" + i.getSelectorType() + ">")
                .setId(HashUtils.md5(i.toString()));
    }
}

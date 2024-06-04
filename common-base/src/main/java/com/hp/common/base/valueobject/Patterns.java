package com.hp.common.base.valueobject;

import cn.hutool.core.lang.RegexPool;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import static com.hp.common.base.valueobject.Patterns.RegEx.*;

/**
 * @author hp
 */
public interface Patterns {
    Pattern YEAR_PATTERN = Pattern.compile(YEAR);
    Pattern SIX_DIGIT_CODE_PATTERN = Pattern.compile(SIX_DIGIT_CODE);
    Pattern ID_NUMBER_PATTER = Pattern.compile(ID_NUMBER);
    Pattern CREDIT_CODE_PATTERN = Pattern.compile(CREDIT_CODE);
    Pattern IP_PATTERN = Pattern.compile(IP);
    Pattern PROVINCE_CODE_PATTER = Pattern.compile(PROVINCE_CODE);
    Pattern AREA_CODE_PATTERN = Pattern.compile(AREA_CODE);
    Pattern EMAIL_PATTERN = Pattern.compile(EMAIL);
    Pattern PHONE_PATTERN = Pattern.compile(PHONE);
    Pattern CEIL_PHONE_PATTERN = Pattern.compile(CEIL_PHONE);
    Pattern TEL_PATTERN = Pattern.compile(TEL);
    Pattern FAX_PATTERN = Pattern.compile(FAX);
    Pattern QQ_PATTERN = Pattern.compile(QQ);
    List<Pattern> REMARK_PATTERN_GROUP = new ArrayList<>(Arrays.asList(CEIL_PHONE_PATTERN, TEL_PATTERN, FAX_PATTERN, EMAIL_PATTERN, QQ_PATTERN));

    interface RegEx {
        /**
         * 用接口主要是方便IDE识别
         */
        String YEAR = "^(((1[7-9])|([2][0]))[0-9]{2})$";
        String SIX_DIGIT_CODE = "^(\\d{6})$";
        String ID_NUMBER = "^"+RegexPool.CITIZEN_ID + "$";
        String CREDIT_CODE = RegexPool.CREDIT_CODE;
        String IP = RegexPool.IPV4;
        String PROVINCE_CODE = "^([1-6][0-9]0000)$";
        String AREA_CODE = "^([1-6]\\d{5})$";
        String EMAIL = "^" + RegexPool.EMAIL + "$";
        String PHONE = "^" + RegexPool.MOBILE + "$";
        String TEL = "^" + RegexPool.TEL + "$";
        String CEIL_PHONE = "^" + RegexPool.MOBILE + "$";
        String FAX = "^(0\\d{2}\\d{8}(\\d{1,4})?)|(0\\d{3}\\d{7,8}(\\d{1,4})?)$";
        String QQ = "^(?:[1-9]\\d{4,14})(?!\\d)$";

    }
}


package com.luban.common.base.mapper;


import com.luban.common.base.valueobject.AbstractSingleValueObject;
import com.luban.common.base.valueobject.areacode.AreaCode;
import com.luban.common.base.valueobject.areacode.ProvinceCode;
import com.luban.common.base.valueobject.contact.EmailAddress;
import com.luban.common.base.valueobject.contact.PhoneNumber;
import com.luban.common.base.valueobject.contact.TelNumber;
import com.luban.common.base.valueobject.id.IdNumber;
import com.luban.common.base.valueobject.ip.Ip;
import com.luban.common.base.valueobject.number.SixDigitCode;
import com.luban.common.base.valueobject.qq.Qq;
import com.luban.common.base.valueobject.socialcreditcode.SocialCreditCode;
import com.luban.common.base.valueobject.time.Year;

import java.util.Optional;

public class GenericValueObjectMapper {

    public AreaCode stringToAreaCode(String value) {
        return AreaCode.of(value);
    }

    public String areaCodeToString(AreaCode valueObject) {
        return Optional.ofNullable(valueObject).map(AbstractSingleValueObject::value).orElse(null);
    }

    public ProvinceCode stringToProvinceCode(String value) {
        return ProvinceCode.of(value);
    }

    public String provinceCodeToString(ProvinceCode valueObject) {
        return Optional.ofNullable(valueObject).map(AbstractSingleValueObject::value).orElse(null);
    }

    public EmailAddress stringToEmailAddress(String value) {
        return EmailAddress.of(value);
    }

    public String emailAddressToString(EmailAddress valueObject) {
        return Optional.ofNullable(valueObject).map(AbstractSingleValueObject::value).orElse(null);
    }

    public TelNumber stringToTelNumber(String string) {
        return TelNumber.of(string);
    }

    public String telNumberToString(TelNumber telNumber) {
        return Optional.ofNullable(telNumber).map(AbstractSingleValueObject::value).orElse(null);
    }

    public IdNumber stringToIdNumber(String string) {
        return IdNumber.of(string);
    }

    public String idNumberToString(IdNumber idNumber) {
        return Optional.ofNullable(idNumber).map(AbstractSingleValueObject::value).orElse(null);
    }

    public Ip stringToIp(String string) {
        return Ip.of(string);
    }

    public String ipToString(Ip ip) {
        return Optional.ofNullable(ip).map(AbstractSingleValueObject::value).orElse(null);
    }

    public SixDigitCode stringToSixDigitCode(String string) {
        return SixDigitCode.of(string);
    }

    public String sixDigitCodeToString(SixDigitCode sixDigitCode) {
        return Optional.ofNullable(sixDigitCode).map(AbstractSingleValueObject::value).orElse(null);
    }

    public Qq stringToQq(String string) {
        return Qq.of(string);
    }

    public String qqToString(Qq qq) {
        return Optional.ofNullable(qq).map(AbstractSingleValueObject::value).orElse(null);
    }

    public Year stringToYear(String string) {
        return Year.of(string);
    }

    public String yearToString(Year year) {
        return Optional.ofNullable(year).map(AbstractSingleValueObject::value).orElse(null);
    }

    public SocialCreditCode stringToSocialCreditCode(String value) {
        return SocialCreditCode.of(value);
    }

    public String socialCreditCodeToString(SocialCreditCode socialCreditCode) {
        return Optional.ofNullable(socialCreditCode).map(AbstractSingleValueObject::value).orElse(null);
    }

    public PhoneNumber stringToPhoneNumber(String value) {
        return PhoneNumber.of(value);
    }

    public String phoneNumberToString(PhoneNumber valueObject) {
        return Optional.ofNullable(valueObject).map(AbstractSingleValueObject::value).orElse(null);
    }

    public PhoneNumber longToPhoneNumber(Long value) {
        return PhoneNumber.of(value);
    }
}

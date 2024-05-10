package com.luban.common.base.encryption;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.stereotype.Component;

import java.security.Security;

/**
 * @author hp
 */
@Component
@ConditionalOnClass(BouncyCastleProvider.class)
public class PKCS7PaddingLoader implements IEncryptionPaddingLoader {

    // PKCS7Padding support
    static {
        Security.addProvider(new BouncyCastleProvider());
    }

}

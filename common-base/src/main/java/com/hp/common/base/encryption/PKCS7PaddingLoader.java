package com.hp.common.base.encryption;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;

import java.security.Security;

/**
 * @author hp
 */
@Configuration
@ConditionalOnClass(BouncyCastleProvider.class)
public class PKCS7PaddingLoader implements IEncryptionPaddingLoader {

    // PKCS7Padding support
    static {
        Security.addProvider(new BouncyCastleProvider());
    }

}

package com.luban.common.base.encryption;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.asymmetric.AsymmetricCrypto;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import com.luban.common.base.exception.EncryptFailedException;
import lombok.extern.slf4j.Slf4j;

import java.security.KeyFactory;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

/**
 * @author hp
 */
@Slf4j
public abstract class AbstractRSA implements IEncryption {
    private static final String PUBLIC_KEY_PREFIX = "-----BEGIN PUBLIC KEY-----";
    private static final String PUBLIC_KEY_SUFFIX = "-----END PUBLIC KEY-----";
    private final AsymmetricCrypto asymmetricCrypto;

    public AbstractRSA(String publicKey) {
        try {
            final String key = publicKey.replace(PUBLIC_KEY_PREFIX, StrUtil.EMPTY).replace(PUBLIC_KEY_SUFFIX, StrUtil.EMPTY);
            asymmetricCrypto = new RSA().setPublicKey(getRsaPublicKeyByBase64(key));
        } catch (Exception e) {
            throw new EncryptFailedException("Creating RSA encryption failed.", e);
        }
    }

    private static RSAPublicKey getRsaPublicKeyByBase64(String base64s) throws Exception {
        try {
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.decode(base64s));
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return (RSAPublicKey) keyFactory.generatePublic(keySpec);
        } catch (InvalidKeySpecException e) {
            throw new EncryptFailedException("Creating RSA public key failed.", e);
        }
    }

    @Override
    public String encrypt(String plain) {
        return asymmetricCrypto.encryptBase64(plain, KeyType.PublicKey);
    }
}

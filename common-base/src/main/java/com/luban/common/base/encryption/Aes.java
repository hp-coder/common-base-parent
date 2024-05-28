package com.luban.common.base.encryption;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.StrUtil;
import com.google.common.base.Preconditions;
import com.luban.common.base.exception.EncryptFailedException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * @author hp
 */
public class Aes implements IEncryption, IDecryption {

    private final Cipher cipher;

    public static Aes newInstance(String secretKey) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        return new Aes("ECB", "PKCS5Padding", secretKey);
    }

    public static Aes newInstance(String mode, String padding, String secretKey) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        return new Aes(mode, padding, secretKey);
    }

    private Aes(String mode, String padding, String secret) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {
        Preconditions.checkArgument(StrUtil.isNotEmpty(secret));
        this.cipher = Cipher.getInstance("AES/%s/%s".formatted(mode, padding));
        this.cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(secret.getBytes(), "AES"));
    }

    @Override
    public String encrypt(String plain) {
        try {
            final byte[] b = cipher.doFinal(plain.getBytes(StandardCharsets.UTF_8));
            return Base64.encodeStr(b, false, false);
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            throw new EncryptFailedException("AES encryption failed.", e);
        }
    }

    @Override
    public String decrypt(String encrypted) {
        return null;
    }
}

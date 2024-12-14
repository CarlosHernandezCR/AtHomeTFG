package org.tfg.athometfgcarloshernandez.spring.common.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.tfg.athometfgcarloshernandez.spring.common.constantes.ConstantesError;
import org.tfg.athometfgcarloshernandez.spring.common.constantes.ConstantesSpring;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Base64;

@Component
@RequiredArgsConstructor
public class Security {
    private final PasswordEncoder encoder;
    @Value(ConstantesSpring.KEY_STORE_PATH)
    private String keyStorePath;

    @Value(ConstantesSpring.PASSWORD_KEYSTORE)
    private String keyStorePass;

    @Value(ConstantesSpring.SERVER_ACCESS)
    private String serverAccess;

    @Value(ConstantesSpring.SERVER_PASSWORD)
    private String serverPassword;

    public X509Certificate readCertificateFromKeyStoreServer() throws IOException, KeyStoreException, CertificateException, NoSuchAlgorithmException {
        String username = serverAccess;
        KeyStore keyStore = KeyStore.getInstance(ConstantesSpring.PKCS_12);
        FileInputStream fis = new FileInputStream(keyStorePath);
        keyStore.load(fis, keyStorePass.toCharArray());

        if (keyStore.containsAlias(username)) {
            Certificate cert = keyStore.getCertificate(username);
            return (X509Certificate) cert;
        } else {
            throw new KeyStoreException(ConstantesError.ERROR_INICIAR_SESION);
        }

    }

    public PrivateKey readPrivateKeyFromKeyStoreServer() throws KeyStoreException, IOException, CertificateException, NoSuchAlgorithmException, UnrecoverableEntryException {
        String username = serverAccess;
        KeyStore keyStore = KeyStore.getInstance(ConstantesSpring.PKCS_12);
        File ksFile = new File(keyStorePath);
        try (FileInputStream fis = new FileInputStream(ksFile)) {
            keyStore.load(fis, keyStorePass.toCharArray());
        }
        if (keyStore.containsAlias(username)) {
            KeyStore.PrivateKeyEntry privateKeyEntry = (KeyStore.PrivateKeyEntry) keyStore.getEntry(username, new KeyStore.PasswordProtection(serverPassword.toCharArray()));
            return privateKeyEntry.getPrivateKey();
        } else {
            throw new KeyStoreException(ConstantesError.ERROR_INICIAR_SESION_CON_NOMBRE + username);
        }
    }

    public String generarCodigoSeguro() {
        SecureRandom sr = new SecureRandom();
        byte[] bits = new byte[32];
        sr.nextBytes(bits);
        return Base64.getUrlEncoder().encodeToString(bits);
    }

    public String encriptarContra(String contra) {
        return encoder.encode(contra);
    }

}
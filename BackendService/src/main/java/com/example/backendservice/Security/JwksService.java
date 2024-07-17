package com.example.backendservice.Security;

import com.auth0.jwk.*;
import com.auth0.jwt.interfaces.RSAKeyProvider;

import java.net.URL;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

public class JwksService implements RSAKeyProvider {
    private final JwkProvider provider;
    
    public JwksService(String jwksUrl) throws Exception {
        this.provider = new JwkProviderBuilder(new URL(jwksUrl)).build();
    }
    
    @Override
    public RSAPublicKey getPublicKeyById(String keyId) {
        try {
            return (RSAPublicKey) provider.get(keyId).getPublicKey();
        } catch (JwkException e) {
            throw new RuntimeException("Could not find the public key with keyId: " + keyId, e);
        }
    }
    
    @Override
    public RSAPrivateKey getPrivateKey() {
        return null;
    }
    
    @Override
    public String getPrivateKeyId() {
        return null;
    }
}
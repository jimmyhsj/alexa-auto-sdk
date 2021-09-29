package com.amazon.alexa.auto.lwa;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Optional;

/**
 * Store for Auth Refresh Token in Encrypted Preferences.
 */
public class TokenStore {
    private static final String REFRESH_TOKEN_KEY = "com.amazon.alexa.auth.refreshToken.key";

    /**
     * Fetch refresh token if available.
     *
     * @param context Android Context.
     * @return Refresh token if available, null otherwise.
     */
    static Optional<String> getRefreshToken(@NonNull Context context) throws GeneralSecurityException, IOException{
        String masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC);
        SharedPreferences sharedPreferences = EncryptedSharedPreferences.create(
                REFRESH_TOKEN_KEY,
                masterKeyAlias,
                context,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        );

        String refreshToken = sharedPreferences.getString(REFRESH_TOKEN_KEY, null);

        return refreshToken != null ? Optional.of(refreshToken) : Optional.empty();
    }

    static void saveRefreshToken(@NonNull Context context, @NonNull String refreshToken) throws GeneralSecurityException, IOException {
        String masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC);
        SharedPreferences sharedPreferences = EncryptedSharedPreferences.create(
                REFRESH_TOKEN_KEY,
                masterKeyAlias,
                context,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        );

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(REFRESH_TOKEN_KEY, refreshToken);
        editor.apply();
    }

    static void resetRefreshToken(@NonNull Context context) throws GeneralSecurityException, IOException {
        String masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC);
        SharedPreferences sharedPreferences = EncryptedSharedPreferences.create(
                REFRESH_TOKEN_KEY,
                masterKeyAlias,
                context,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        );

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(REFRESH_TOKEN_KEY);
        editor.apply();
    }
}

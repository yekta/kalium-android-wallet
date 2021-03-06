package com.banano.kaliumwallet;

/*
  Utilities for crypto functions
 */

import com.rotilho.jnano.commons.NanoAccounts;
import com.rotilho.jnano.commons.NanoAmount;
import com.rotilho.jnano.commons.NanoBaseAccountType;
import com.rotilho.jnano.commons.NanoBlocks;
import com.rotilho.jnano.commons.NanoHelper;
import com.rotilho.jnano.commons.NanoKeys;
import com.rotilho.jnano.commons.NanoSignatures;

import java.security.SecureRandom;

import com.banano.kaliumwallet.util.SecureRandomUtil;

public class KaliumUtil {
    /**
     * Generate a new Wallet Seed
     *
     * @return Wallet Seed
     */
    public static String generateSeed() {
        int numchars = 64;
        SecureRandom random = SecureRandomUtil.secureRandom();
        byte[] randomBytes = new byte[numchars / 2];
        random.nextBytes(randomBytes);
        StringBuilder sb = new StringBuilder(numchars);
        for (byte b : randomBytes) {
            sb.append(String.format("%02X", b));
        }
        return sb.toString();
    }

    /**
     * Check if a seed is valid
     *
     * @return true if valid, false otherwise
     */
    public static boolean isValidSeed(String seed) {
        if(seed.length() != 64) {
            return false;
        }

        char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f', 'A', 'B', 'C', 'D', 'E', 'F' };

        for (char symbol : seed.toCharArray()) {
            boolean found = false;
            for (char hexDigit : hexDigits) {
                if (symbol == hexDigit) {
                    found = true;
                    break;
                }
            }
            if(!found) {
                return false;
            }
        }
        return true;
    }

    /**
     * Convert a wallet seed to private key
     *
     * @param seed Wallet seed
     * @return private key
     */
    public static String seedToPrivate(String seed, int index) {
        return NanoHelper.toHex(NanoKeys.createPrivateKey(NanoHelper.toByteArray(seed), index));
    }

    /**
     * Convert a private key to a public key
     *
     * @param privateKey private key
     * @return public key
     */
    public static String privateToPublic(String privateKey) {
        return NanoHelper.toHex(NanoKeys.createPublicKey(NanoHelper.toByteArray(privateKey)));
    }

    /**
     * Compute hash to use to generate an open work block
     *
     * @param source         Source address
     * @param representative Representative address
     * @param account        Account address
     * @return Open Hash
     */
    public static String computeOpenHash(String source, String representative, String account) {
        return NanoBlocks.hashOpenBlock(NanoBaseAccountType.BANANO, source, representative, account);
    }

    /**
     * Compute hash to use to generate a receive work block
     *
     * @param previous Previous transation
     * @param source   Source address
     * @return String of hash
     */
    public static String computeReceiveHash(String previous, String source) {
        return NanoBlocks.hashReceiveBlock(previous, source);
    }

    /**
     * Compute hash to use to generate a send work block
     *
     * @param previous    Previous transation
     * @param destination Destination address
     * @param balance     Raw NANO balance
     * @return String of hash
     */
    public static String computeSendHash(String previous, String destination, String balance) {
        return NanoBlocks.hashSendBlock(NanoBaseAccountType.BANANO, previous, destination, NanoAmount.ofRaw(balance));
    }

    /**
     * Compute hash for a universal (state) block
     *
     * @param account        This account's ban_ address.
     * @param previous       Previous head block on account; 0 if open block.
     * @param representative Representative ban_ address.
     * @param balance        Resulting balance
     * @param link           Multipurpose Field
     * @return String of hash
     */
    public static String computeStateHash(String account,
                                          String previous,
                                          String representative,
                                          String balance,
                                          String link) {
        return NanoBlocks.hashStateBlock(NanoBaseAccountType.BANANO, account, previous, representative, NanoAmount.ofRaw(balance), link);
    }

    /**
     * Compute hash to use to generate a change work block
     *
     * @param previous       Previous transaction
     * @param representative Representative address
     * @return String of hash
     */
    public static String computeChangeHash(String previous, String representative) {
        return NanoBlocks.hashChangeBlock(NanoBaseAccountType.BANANO, previous, representative);
    }

    /**
     * Sign a message with a private key
     *
     * @param private_key Private Key
     * @param data        Message
     * @return Signed message
     */
    public static String sign(String private_key, String data) {
        return NanoSignatures.sign(NanoHelper.toByteArray(private_key), data);
    }

    /**
     * Convert a Public Key to an Address
     *
     * @param publicKey Public Key
     * @return ban address
     */
    public static String publicToAddress(String publicKey) {
        return NanoAccounts.createAccount(NanoBaseAccountType.BANANO, NanoHelper.toByteArray(publicKey));

    }

    /**
     * Convert an address to a public key
     *
     * @param encodedAddress encoded Address
     * @return Public Key
     */
    public static String addressToPublic(String encodedAddress) {
        return NanoHelper.toHex(NanoAccounts.toPublicKey(NanoBaseAccountType.BANANO, encodedAddress));
    }
}
package com.datsenko.yevhenii.model;

import java.math.BigInteger;
import java.util.Random;

/**
 * Created by Yevhenii on 3/22/2017.
 */
public class User {
    private final int mBitLength;
    private BigInteger p;
    private BigInteger q;
    private BigInteger n;
    private int e = 65537; // or 17, 257, 65537 Fermat number
    private BigInteger d;
    private BigInteger fi_n;
    private BigInteger m;
    private BigInteger k;

    public User(int bitLength) {
        this.mBitLength = bitLength;
        Random random = new Random();
        p = BigInteger.probablePrime(bitLength, random);
        q = BigInteger.probablePrime(bitLength, random);
        n = p.multiply(q);
        fi_n = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
        d = (new BigInteger(String.valueOf(e))).modInverse(fi_n);
    }

    public BigInteger getMessage() {
        if (m == null) {
            generateMessage();
        }
        return m;
    }

    public void setMessage(BigInteger m) {
        this.m = m;
    }

    public void generateMessage() {
        m = BigInteger.probablePrime(mBitLength, new Random());
        System.out.println("start m = " + m.toString());
    }

    public BigInteger getN() {
        return n;
    }

    public int getE() {
        return e;
    }

    public void generateK(BigInteger n) {
        while (true) {
            k = BigInteger.probablePrime(mBitLength, new Random());
            BigInteger gcdValue = k.gcd(n);
            if (gcdValue.equals(BigInteger.ONE) || gcdValue.equals(new BigInteger("-1")))
                break;
        }
    }

    public BigInteger getK() {
        return k;
    }

    public BigInteger powTtoD(BigInteger t) {
        return t.modPow(d, n);
    }
}

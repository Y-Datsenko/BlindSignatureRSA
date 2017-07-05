package com.datsenko.yevhenii;

import com.datsenko.yevhenii.model.User;

import java.math.BigInteger;

public class Main {
    private static int mBitLength = 512;

    public static void main(String[] args) {
        startProtocol(512);
        startProtocol(2048);
        startFailProtocol(512);
        startFailProtocol(2048);
    }

    private static void startProtocol(int bitLength) {
        long timeF = System.currentTimeMillis();
        User alisa = new User(bitLength);
        User bob = new User(bitLength);
        long timeE = System.currentTimeMillis();
        System.out.println(bitLength + " time for deploy " + (timeE - timeF));
        timeF = System.currentTimeMillis();
        //start
        alisa.generateMessage();
        alisa.generateK(bob.getN());
        BigInteger alisaT = alisa.getMessage().multiply(alisa.getK().pow(bob.getE())).mod(bob.getN());
        //send t to Bob
        BigInteger t_powD = bob.powTtoD(alisaT);
        //send to Alisa
        BigInteger s = t_powD.multiply(alisa.getK().modInverse(bob.getN())).mod(bob.getN());
        System.out.println(bitLength + " signed m = " + s.toString());
        timeE = System.currentTimeMillis();
        System.out.println(bitLength + " time for protocol " + (timeE - timeF));

        timeF = System.currentTimeMillis();
        BigInteger m = s.pow(bob.getE()).mod(bob.getN());
        timeE = System.currentTimeMillis();
        System.out.println(bitLength + " time for verify " + (timeE - timeF));

        System.out.println(bitLength + " end m = " + m.toString());
        System.out.println(bitLength + " is equals " + m.equals(alisa.getMessage()));
    }

    private static void startFailProtocol(int bitLength) {
        long timeF = System.currentTimeMillis();
        User alisa = new User(bitLength);
        User bob = new User(bitLength);
        long timeE = System.currentTimeMillis();
        System.out.println(bitLength + " WRONG time for deploy " + (timeE - timeF));
        timeF = System.currentTimeMillis();
        //start
        alisa.generateMessage();
        alisa.generateK(bob.getN());
        BigInteger alisaT = alisa.getMessage().multiply(alisa.getK().pow(bob.getE())).mod(bob.getN());
        //send t to Bob
        BigInteger t_powD2 = bob.powTtoD(alisaT);
        BigInteger t_powD = t_powD2.subtract(BigInteger.TEN);
        //send to Alisa
        timeE = System.currentTimeMillis();
        BigInteger s = t_powD.multiply(alisa.getK().modInverse(bob.getN())).mod(bob.getN());
        System.out.println(bitLength + " WRONG signed m = " + s.toString());
        System.out.println(bitLength + " WRONG time for protocol " + (timeE - timeF));

        timeF = System.currentTimeMillis();
        BigInteger m = s.pow(bob.getE()).mod(bob.getN());
        timeE = System.currentTimeMillis();
        System.out.println(bitLength + " WRONG time for verify " + (timeE - timeF));

        System.out.println(bitLength + " WRONG end m = " + m.toString());
        System.out.println(bitLength + " WRONG is equals " + m.equals(alisa.getMessage()));
    }
}

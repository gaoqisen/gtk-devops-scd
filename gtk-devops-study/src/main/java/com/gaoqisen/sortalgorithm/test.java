package com.gaoqisen.sortalgorithm;

public class test {

    public static void main(String[] args) {

        for (int i = 6; i <= 100; i+=2) {
            for (int j = 2; j < 100; j++) {
                if(isPrime(j) && isPrime(i - j)) {
                    System.out.println(i + "=" + j + " + " + (i - j));
                    break;
                }
            }
        }

        // -----

        int l, j, k, sum;
        for (l = 1; l <= 1000; l++) {
            j = l;
            sum = l;
            while (sum < 1000){
                sum += ++j;
                if(sum == 1000) {
                    for (k = l; k <= j; k++) {
                        System.out.print(k + ",");
                        System.out.println();
                    }
                }
            }

        }

    }

    private static boolean isRN(int year) {
        if((year % 4 == 0 && year % 100 != 0) || (year%400==0)) {
            return true;
        }
        return false;
    }

    private static boolean isPrime(int n) {
        for(int i = 2; i < n; i++) {
            if(n % i == 0) {
                return false;
            }
        }
        return true;
    }

}

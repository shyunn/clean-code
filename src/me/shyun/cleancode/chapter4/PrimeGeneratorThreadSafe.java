package me.shyun.cleancode.chapter4;

/**
 * 이 클래스는 사용자가 지정한 최대 값까지 소수를 구한다.
 * 알고리즘은 에라스토테네스의 체다.
 * 2에서 시작하는 정수 배열을 대상으로한다.
 * 처음으로 남아 있는 정수를 찾아 배수를 모두 제거한다.
 * 배열에 더이상 배수가 없을 때까지 반복한다.
 */
public class PrimeGeneratorThreadSafe {

  // thread Safe 하지 않다.
  // 때문에 리펙토링 하기전의 GeneratePrimes 를 완벽하게 대체하지 못한다.

  public static int[] generatePrimes(int maxValue) {

    if (maxValue < 2) {
      return new int[0];
    } else {

      boolean[] crossedOut = uncrossIntegerUpTo(maxValue);
      crossedOutMultiples(crossedOut);
      return putUnCrossedIntegersIntoResult(crossedOut);

    }
  }

  private static boolean[] uncrossIntegerUpTo(int maxValue) {

    boolean[] crossedOut = new boolean[maxValue + 1];
    for (int i = 2; i < crossedOut.length; i++) {
      crossedOut[i] = false;
    }

    return crossedOut;
  }

  private static void crossedOutMultiples(boolean[] crossedOut) {

    int limit = determineIterationLimit(crossedOut.length);
    for (int i = 2; i <= limit; i++) {
      if (!crossedOut[i]) { // notCrossed
        crossOutMultipleOf(crossedOut,i);
      }
    }
  }

  private static int determineIterationLimit(int length) {

    // 배열에 있는 모든 수는 배열 크기의 제곱근 보다 작은 소수의 인수다
    // 따라서 이 제곱근보다 더 큰 숫자의 배수는 제거할 필요가 없다.
    double iterationLimit = Math.sqrt(length);
    return (int) iterationLimit;
  }

  private static void crossOutMultipleOf(boolean[] crossedOut, int i) {
    for (int multiple = 2 * i; multiple < crossedOut.length; multiple += i) {
      crossedOut[multiple] = true;
    }
  }


  private static int[] putUnCrossedIntegersIntoResult(boolean[] crossedOut) {

    int[] result = new int[numberOfUncrossedIntegers(crossedOut)];
    for (int i = 0 ,j = 2; j < crossedOut.length; j++) {
      if(!crossedOut[j]){ // notCrossed
        result[i++]=j;
      }
    }
    return result;
  }

  private static int numberOfUncrossedIntegers(boolean[] crossedOut) {
    int count = 0;

    for (int i = 2; i < crossedOut.length; i++) {
      if(!crossedOut[i]){ // notCrossed
        count++;
      }

    }
    return count;
  }


}

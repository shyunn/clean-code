package me.shyun.cleancode.chapter4;

/**
 * 이 클래스는 사용자가 지정한 최대 값까지 소수를 구한다.
 * 알고리즘은 에라스토테네스의 체다.
 * 2에서 시작하는 정수 배열을 대상으로한다.
 * 처음으로 남아 있는 정수를 찾아 배수를 모두 제거한다.
 * 배열에 더이상 배수가 없을 때까지 반복한다.
 */
public class PrimeGenerator {

  // thread Safe 하지 않다.
  // 때문에 리펙토링 하기전의 GeneratePrimes 를 완벽하게 대체하지 못한다.
  private static boolean[] crossedOut;
  private static int[] result;

  public static int[] generatePrimes(int maxValue) {

    if (maxValue < 2) {
      return new int[0];
    } else {

      uncrossIntegerUpTo(maxValue);
      crossedOutMultiples();
      putUnCrossedIntegersIntoResult();

      return result;
    }
  }

  private static void uncrossIntegerUpTo(int maxValue) {

    crossedOut = new boolean[maxValue + 1];
    for (int i = 2; i < crossedOut.length; i++) {
      crossedOut[i] = false;
    }

  }

  private static void crossedOutMultiples() {

    int limit = determineIterationLimit();
    for (int i = 2; i <= limit; i++) {
      if (notCrossed(i)) {
        crossOutMultipleOf(i);
      }

    }
  }

  private static int determineIterationLimit() {

    // 배열에 있는 모든 수는 배열 크기의 제곱근 보다 작은 소수의 인수다
    // 따라서 이 제곱근보다 더 큰 숫자의 배수는 제거할 필요가 없다.
    double iterationLimit = Math.sqrt(crossedOut.length);
    return (int) iterationLimit;
  }

  private static boolean notCrossed(int i) {
    return !crossedOut[i];
  }

  private static void crossOutMultipleOf(int i) {
    for (int multiple = 2 * i; multiple < crossedOut.length; multiple += i) {
      crossedOut[multiple] = true;
    }
  }


  private static void putUnCrossedIntegersIntoResult() {
    result = new int[numberOfUncrossedIntegers()];
    for (int i = 0 ,j = 2; j < crossedOut.length; j++) {
      if(notCrossed(j)){
        result[i++]=j;
      }
    }
  }

  private static int numberOfUncrossedIntegers() {
    int count = 0;

    for (int i = 2; i < crossedOut.length; i++) {
      if(notCrossed(i)){
        count++;
      }

    }
    return count;
  }


}

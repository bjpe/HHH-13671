package org.hibernate.hhh13761;

import java.math.BigDecimal;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

  private static final BigDecimal ORACLE_MIN_VALUE = new BigDecimal("-999999999999999999999999999");

  private static final BigDecimal ORACLE_MAX_VALUE = new BigDecimal("9999999999999999999999999999");

  private static int digits(BigDecimal bigDecimal) {
    return bigDecimal.precision() + bigDecimal.scale();
  }

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @Bean
  public CommandLineRunner runOnStartup() {
    return args -> {
      System.out.println("Oracle min value: " + ORACLE_MIN_VALUE);
      System.out.println("Oracle max value: " + ORACLE_MAX_VALUE);
      System.out.println("Oracle min value digits: " + digits(ORACLE_MIN_VALUE));
      System.out.println("Oracle max value digits: " + digits(ORACLE_MAX_VALUE));
      System.out.println("Oracle min value as Long: " + ORACLE_MIN_VALUE.longValue());
      System.out.println("Oracle max value as Long: " + ORACLE_MAX_VALUE.longValue());
      System.out.println("Oracle min value as BigInteger: " + ORACLE_MIN_VALUE.toBigInteger());
      System.out.println("Oracle max value as BigInteger: " + ORACLE_MAX_VALUE.toBigInteger());
    };
  }

}

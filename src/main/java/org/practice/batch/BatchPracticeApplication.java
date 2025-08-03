package org.practice.batch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BatchPracticeApplication {

    public static void main(String[] args) {
        System.exit(SpringApplication.exit(SpringApplication.run(BatchPracticeApplication.class, args)));

        // Batch Application에서는 SpringApplication.run()의 결과를 System.exit()로 처리하는 것을 권장함
        // SpringApplication.run(BatchPracticeApplication.class, args);
    }

}

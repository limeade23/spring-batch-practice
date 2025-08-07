package org.practice.batch.config;

import org.practice.batch.tasklet.ZombieProcessCleanupTasklet;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.ResourceTransactionManager;

@Configuration
public class ZombieBatchConfig {
    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;

    public ZombieBatchConfig(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        this.jobRepository = jobRepository;
        this.transactionManager = transactionManager;
    }

    @Bean
    public Tasklet zombieProcessCleanupTasklet() {
        return new ZombieProcessCleanupTasklet();
    }

    @Bean
    public Step zombieCleanupStep() {
        return new StepBuilder("zombieCleanupStep", jobRepository)
                // Tasklet과 transactionManager 설정
                // tasklet() 메서드를 호출하면, 스텝 빌더는 태스크릿 지향 처리 방식의 Step을 생성
                .tasklet(zombieProcessCleanupTasklet(), transactionManager)
                // DB 트랜잭션이 필요 없는 Tasklet일 경우 PlatformTransactionManager 대신 ResourcelessTransactionManager를 고려
                // .tasklet(zombieProcessCleanupTasklet(), new ResourceTransactionManager())
                .build();
    }

    @Bean
    public Job zombieCleanupJob() {
        return new JobBuilder("zombieCleanupJob", jobRepository)
                .start(zombieCleanupStep())  // Step 등록
                .build();
    }
}

package config;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class DbConfig {
    @Bean(destroyMethod = "close")
    public DataSource dataSource() {
        DataSource ds = new DataSource();
        ds.setDriverClassName("org.mariadb.jdbc.Driver");
        ds.setUrl("jdbc:mariadb://localhost/spring5fs?characterEncoding=utf8");
        ds.setUsername("spring5");
        ds.setPassword("spring5");
        ds.setInitialSize(2); // 초기 커넥션 개수
        ds.setMaxActive(10); // 동시 가져 올 수 있는 커넥션 개수
        ds.setTestWhileIdle(true); // 유휴 커넥션 검사
        ds.setMinEvictableIdleTimeMillis(1000 * 60 * 3); // 최소 유휴 시간 3분
        ds.setTimeBetweenEvictionRunsMillis(1000 * 10); // 10초 주기
        return ds;
    }
}

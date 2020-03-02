package com.apress.spring.service;

import com.apress.spring.domain.Journal;
import com.apress.spring.domain.JournalDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class JournalService {
    private static final Logger log = LoggerFactory.getLogger(JournalService.class);

    @Autowired
    JdbcTemplate jdbcTemplate;

    public void insertData() {
        log.info("> 테이블 생성");
        jdbcTemplate.execute("DROP TABLE JOURNAL IF EXISTS");
        jdbcTemplate.execute("CREATE TABLE JOURNAL(id SERIAL, title VARCHAR(255), summary VARCHAR (255), created TIMESTAMP )");
        log.info("> 데이터 생성");
        jdbcTemplate.execute("INSERT INTO JOURNAL (title, summary, created) VALUES ('스프링부트 입문', '오늘부터 스프링부트를 배우다.', '2020-03-03 00:00:00.00')");
        log.info("> 완료");
    }

    public List<JournalDto> findAll() {
        List<JournalDto> entries = new ArrayList<>();
        jdbcTemplate.query("SELECT * FROM JOURNAL",
                new Object[]{},
                (rs, row) -> new JournalDto(rs.getLong("id"),
                        rs.getString("title"),
                        rs.getString("summary"),
                        new Date(rs.getTimestamp("created").getTime())))
                .forEach(entry ->entries.add(entry));

        return entries;
    }
}

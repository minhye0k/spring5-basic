package spring;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

public class MemberDao {
    private JdbcTemplate jdbcTemplate;
    private RowMapper<Member> memRowMapper =
            (resultSet, i) -> {
                Member member = new Member(
                        resultSet.getString("EMAIL"),
                        resultSet.getString("PASSWORD"),
                        resultSet.getString("NAME"),
                        resultSet.getTimestamp("REGDATE").toLocalDateTime());
                member.setId(resultSet.getLong("ID"));
                return member;
            };

    public MemberDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Member selectByEmail(String email) {
        List<Member> results = jdbcTemplate.query(
                "select * from MEMBER where EMAIL = ?",
                memRowMapper, email);
        return results.isEmpty() ? null : results.get(0);
    }

    public void insert(Member member) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement pstmt = connection.prepareStatement(
                        "insert into MEMBER (EMAIL, PASSWORD, NAME, REGDATE) " +
                                "values (?,?,?,?)",
                        new String[]{"ID"}
                );
                pstmt.setString(1, member.getEmail());
                pstmt.setString(2, member.getPassword());
                pstmt.setString(3, member.getName());
                pstmt.setTimestamp(4, Timestamp.valueOf(member.getRegisterDateTime()));
                return pstmt;
            }
        }, keyHolder);
        Number keyValue = keyHolder.getKey();
        member.setId(keyValue.longValue());
    }

    public void update(Member member) {
        jdbcTemplate.update(
                "update MEMBER set NAME = ?, PASSWORD = ? where EMAIL = ?",
                member.getName(), member.getPassword(), member.getEmail()
        );
    }

    public List<Member> selectAll() {
        List<Member> results = jdbcTemplate.query(
                "select * from MEMBER", memRowMapper
        );

        return results;
    }

    public List<Member> selectByRegdate(
            LocalDateTime from, LocalDateTime to) {
        List<Member> results = jdbcTemplate.query(
                "select * from MEMBER where REGDATE between ? and ? " +
                        "order by REGDATE desc",
                memRowMapper, from, to
        );
        return results;
    }

    public Member selectById(Long memId) {
        List<Member> results = jdbcTemplate.query(
                "select * from MEMBER where ID = ?",
                memRowMapper, memId
        );

        return results.isEmpty() ? null : results.get(0);
    }

    public int count() {
        Integer count = jdbcTemplate.queryForObject(
                "select count(*) from MEMBER", Integer.class);

        return count;
    }

}

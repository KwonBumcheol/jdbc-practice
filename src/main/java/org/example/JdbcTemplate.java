package org.example;

import org.springframework.jdbc.core.PreparedStatementSetter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcTemplate { // 라이브러리

    public void executeUpdate(User user, String sql, org.example.PreparedStatementSetter pss) throws SQLException { // 외부에서 sql 받아옴
        Connection con = null;
        PreparedStatement pstmt = null;

        try { // try with resource - 자원 할당 해제
            con = ConnectionManager.getConnection();
//            String sql = "INSERT INTO USERS VALUES (?, ?, ?, ?)";
            pstmt = con.prepareStatement(sql);
            pss.setter(pstmt);

            pstmt.executeUpdate();
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }

            if (con != null) {
                con.close();
            }
        }
    }

    public Object executeQuery(String sql, org.example.PreparedStatementSetter pss, RowMapper rowMapper) throws SQLException {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = ConnectionManager.getConnection();
            pstmt = con.prepareStatement(sql);
            pss.setter(pstmt);

            rs = pstmt.executeQuery();

            Object obj = null;
            if (rs.next()) { // 데이터베이스로부터 값을 받아와서 유저 객체 생성
                return rowMapper.map(rs);
            }

            return obj;
        } finally { // 자원 해제 - 밑에서부터 역순으로(가장 나중에 한 것부터)
            if (rs != null) {
                rs.close();
            }

            if (pstmt != null) {
                pstmt.close();
            }

            if (con != null) {
                con.close();
            }
        }
    }
}

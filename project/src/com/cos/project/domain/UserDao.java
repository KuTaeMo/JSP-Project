package com.cos.project.domain;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.cos.project.config.DB;
import com.cos.project.domain.dto.JoinReqDto;
import com.cos.project.domain.dto.LoginReqDto;

public class UserDao {
	
	public int deleteById(int id) {
		String sql = "DELETE FROM user WHERE id = ?";
		Connection conn = DB.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			int result = pstmt.executeUpdate();
			return result;

		} catch (Exception e) {
			e.printStackTrace();
		} finally { // 무조건 실행
			DB.close(conn, pstmt);
		}
		return -1;
	}
	
	public List<User> findAll(){
		String sql = "SELECT * FROM  user ORDER BY id DESC";
		Connection conn = DB.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs  = null;
		List<User> users = new ArrayList<>();
		try {
			pstmt = conn.prepareStatement(sql);
			rs =  pstmt.executeQuery();

			// Persistence API
			while(rs.next()) { // 커서를 이동하는 함수
				User user = User.builder()
						.id(rs.getInt("id"))
						.username(rs.getString("username"))
						.email(rs.getString("email"))
						.role(rs.getString("role"))
						.build();
				users.add(user);	
			}
			return users;
		} catch (Exception e) {
			e.printStackTrace();
		} finally { // 무조건 실행
			DB.close(conn, pstmt, rs);
		}

		return null;
	}
	
	public int count() {
		String sql = "SELECT count(*), id FROM user";
		Connection conn = DB.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs  = null;

		try {
			pstmt = conn.prepareStatement(sql);
			rs =  pstmt.executeQuery();
			if(rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally { // 무조건 실행
			DB.close(conn, pstmt, rs);
		}
		return -1;
	}
	
	public int save(JoinReqDto dto) { // 회원가입
		String sql = "INSERT INTO user(username, password, email,role) VALUES(?,?,?,?)";
		Connection conn = DB.getConnection();
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getUsername());
			pstmt.setString(2, dto.getPassword());
			pstmt.setString(3, dto.getEmail());
			pstmt.setString(4, dto.getRole());
			int result = pstmt.executeUpdate();
			System.out.println("db "+result);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		} finally { // 무조건 실행
			DB.close(conn, pstmt);
		}
		return -1;
	}
	
	public User findByUsernameAndPassword(LoginReqDto dto) {
		String sql = "SELECT id,username,email,role FROM user WHERE username = ?AND password=?";
		Connection conn = DB.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs  = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getUsername());
			pstmt.setString(2, dto.getPassword());
			rs =  pstmt.executeQuery();

			if(rs.next()) {
				User user=User.builder()
						.id(rs.getInt("id"))
						.username(rs.getString("username"))
						.email(rs.getString("email"))
						.role(rs.getString("role"))
						.build();
				return user;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally { // 무조건 실행
			DB.close(conn, pstmt, rs);
		}
		return null;
	}
}

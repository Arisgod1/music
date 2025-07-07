package dao;

import entity.Music;
import util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 唐明迪
 * @date 2025-07-07
 * @description 音乐数据访问对象，负责数据库增删改查操作
 */
public class MusicDAO {

    /**
     * 新增音乐
     */
    public boolean addMusic(Music music) {
        String sql = "INSERT INTO music (title, artist, release_date, genre, recommender, file_path) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, music.getTitle());
            ps.setString(2, music.getArtist());
            if (music.getReleaseDate() != null) {
                ps.setDate(3, new java.sql.Date(music.getReleaseDate().getTime()));
            } else {
                ps.setDate(3, null);
            }
            ps.setString(4, music.getGenre());
            ps.setString(5, music.getRecommender());
            ps.setString(6, music.getFilePath());
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 根据条件查询音乐列表（模糊查询）
     */
    public List<Music> findMusic(String title, String artist, String genre) {
        List<Music> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM music WHERE 1=1");
        if (title != null && !title.isEmpty()) {
            sql.append(" AND title LIKE ?");
        }
        if (artist != null && !artist.isEmpty()) {
            sql.append(" AND artist LIKE ?");
        }
        if (genre != null && !genre.isEmpty()) {
            sql.append(" AND genre LIKE ?");
        }

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            int index = 1;
            if (title != null && !title.isEmpty()) {
                ps.setString(index++, "%" + title + "%");
            }
            if (artist != null && !artist.isEmpty()) {
                ps.setString(index++, "%" + artist + "%");
            }
            if (genre != null && !genre.isEmpty()) {
                ps.setString(index++, "%" + genre + "%");
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Music m = new Music();
                m.setId(rs.getInt("id"));
                m.setTitle(rs.getString("title"));
                m.setArtist(rs.getString("artist"));
                m.setReleaseDate(rs.getDate("release_date"));
                m.setGenre(rs.getString("genre"));
                m.setRecommender(rs.getString("recommender"));
                m.setFilePath(rs.getString("file_path"));
                m.setAvgScore(rs.getDouble("avg_score"));
                m.setScoreCount(rs.getInt("score_count"));
                list.add(m);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 根据ID获取音乐对象
     */
    public Music getMusicById(int id) {
        String sql = "SELECT * FROM music WHERE id=?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Music m = new Music();
                m.setId(rs.getInt("id"));
                m.setTitle(rs.getString("title"));
                m.setArtist(rs.getString("artist"));
                m.setReleaseDate(rs.getDate("release_date"));
                m.setGenre(rs.getString("genre"));
                m.setRecommender(rs.getString("recommender"));
                m.setFilePath(rs.getString("file_path"));
                m.setAvgScore(rs.getDouble("avg_score"));
                m.setScoreCount(rs.getInt("score_count"));
                return m;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 添加评分并更新平均分和评分次数
     */
    public boolean addScore(int musicId, String studentName, int score) {
        String insertScoreSql = "INSERT INTO music_score (music_id, student_name, score) VALUES (?, ?, ?)";
        String updateMusicSql = "UPDATE music SET avg_score = ?, score_count = ? WHERE id = ?";
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            conn.setAutoCommit(false);

            // 插入评分记录
            try (PreparedStatement ps = conn.prepareStatement(insertScoreSql)) {
                ps.setInt(1, musicId);
                ps.setString(2, studentName);
                ps.setInt(3, score);
                ps.executeUpdate();
            }

            // 计算新的平均分和评分次数
            double avgScore = 0;
            int scoreCount = 0;
            String avgSql = "SELECT AVG(score) as avg_score, COUNT(*) as count_score FROM music_score WHERE music_id = ?";
            try (PreparedStatement ps = conn.prepareStatement(avgSql)) {
                ps.setInt(1, musicId);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    avgScore = rs.getDouble("avg_score");
                    scoreCount = rs.getInt("count_score");
                }
            }

            // 更新music表
            try (PreparedStatement ps = conn.prepareStatement(updateMusicSql)) {
                ps.setDouble(1, avgScore);
                ps.setInt(2, scoreCount);
                ps.setInt(3, musicId);
                ps.executeUpdate();
            }

            conn.commit();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }
}

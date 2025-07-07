package service;

import dao.MusicDAO;
import entity.Music;

import java.util.List;

/**
 * @author 唐明迪
 * @date 2025-07-07
 * @description 音乐业务处理类，调用DAO实现业务逻辑
 */
public class MusicService {
    private MusicDAO musicDAO = new MusicDAO();

    /**
     * 上传音乐
     */
    public boolean uploadMusic(Music music) {
        return musicDAO.addMusic(music);
    }

    /**
     * 查找音乐
     */
    public List<Music> searchMusic(String title, String artist, String genre) {
        return musicDAO.findMusic(title, artist, genre);
    }

    /**
     * 获取音乐详情
     */
    public Music getMusicById(int id) {
        return musicDAO.getMusicById(id);
    }

    /**
     * 给音乐打分
     */
    public boolean scoreMusic(int musicId, String studentName, int score) {
        if (score < 1 || score > 5) {
            System.out.println("评分必须在1到5之间。");
            return false;
        }
        return musicDAO.addScore(musicId, studentName, score);
    }
}

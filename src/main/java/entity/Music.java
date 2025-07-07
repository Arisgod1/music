package entity;

import java.util.Date;

/**
 * @author 唐明迪
 * @date 2025-07-07
 * @description 音乐实体类，封装音乐的属性信息
 */
public class Music {
    private int id;
    private String title;
    private String artist;
    private Date releaseDate;
    private String genre;
    private String recommender;
    private String filePath;  // 模拟上传文件路径
    private double avgScore;
    private int scoreCount;

    public Music() {}

    public Music(String title, String artist, Date releaseDate, String genre, String recommender, String filePath) {
        this.title = title;
        this.artist = artist;
        this.releaseDate = releaseDate;
        this.genre = genre;
        this.recommender = recommender;
        this.filePath = filePath;
    }

    @Override
    public String toString() {
        return "Music{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", artist='" + artist + '\'' +
                ", releaseDate=" + releaseDate +
                ", genre='" + genre + '\'' +
                ", recommender='" + recommender + '\'' +
                ", avgScore=" + avgScore +
                ", scoreCount=" + scoreCount +
                '}';
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setRecommender(String recommender) {
        this.recommender = recommender;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public void setAvgScore(double avgScore) {
        this.avgScore = avgScore;
    }

    public void setScoreCount(int scoreCount) {
        this.scoreCount = scoreCount;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public String getGenre() {
        return genre;
    }

    public String getRecommender() {
        return recommender;
    }

    public String getFilePath() {
        return filePath;
    }

    public double getAvgScore() {
        return avgScore;
    }

    public int getScoreCount() {
        return scoreCount;
    }
}

package ui;

import entity.Music;
import service.MusicService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

/**
 * @author 唐明迪
 * @date 2025-07-07
 * @description 共享音乐管理系统控制台入口类，负责用户交互
 */
public class MusicApp {
    private static MusicService musicService = new MusicService();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("欢迎使用学校共享音乐管理系统！");
        boolean exit = false;
        while (!exit) {
            showMenu();
            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    uploadMusic();
                    break;
                case "2":
                    searchMusic();
                    break;
                case "3":
                    downloadMusic();
                    break;
                case "4":
                    scoreMusic();
                    break;
                case "0":
                    exit = true;
                    System.out.println("感谢使用，再见！");
                    break;
                default:
                    System.out.println("无效输入，请重新选择。");
            }
        }
    }

    private static void showMenu() {
        System.out.println("\n请选择操作：");
        System.out.println("1. 上传音乐");
        System.out.println("2. 查找音乐");
        System.out.println("3. 下载音乐");
        System.out.println("4. 给音乐打分");
        System.out.println("0. 退出");
        System.out.print("输入选项：");
    }

    private static void uploadMusic() {
        System.out.println("请输入音乐信息：");

        System.out.print("作品名：");
        String title = scanner.nextLine();

        System.out.print("演唱/演奏者：");
        String artist = scanner.nextLine();

        System.out.print("发行时间（格式YYYY-MM-DD，可不填）：");
        String dateStr = scanner.nextLine();
        Date releaseDate = null;
        if (!dateStr.trim().isEmpty()) {
            try {
                releaseDate = new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
            } catch (ParseException e) {
                System.out.println("日期格式错误，上传失败！");
                return;
            }
        }

        System.out.print("音乐类型：");
        String genre = scanner.nextLine();

        System.out.print("推荐者：");
        String recommender = scanner.nextLine();

        System.out.print("音乐文件路径（模拟上传）：");
        String filePath = scanner.nextLine();

        Music music = new Music(title, artist, releaseDate, genre, recommender, filePath);
        boolean success = musicService.uploadMusic(music);
        if (success) {
            System.out.println("音乐上传成功！");
        } else {
            System.out.println("音乐上传失败！");
        }
    }

    private static void searchMusic() {
        System.out.println("查询条件，可不填，直接回车跳过");

        System.out.print("作品名：");
        String title = scanner.nextLine();

        System.out.print("演唱/演奏者：");
        String artist = scanner.nextLine();

        System.out.print("音乐类型：");
        String genre = scanner.nextLine();

        List<Music> results = musicService.searchMusic(
                title.isEmpty() ? null : title,
                artist.isEmpty() ? null : artist,
                genre.isEmpty() ? null : genre);

        if (results.isEmpty()) {
            System.out.println("未找到相关音乐。");
        } else {
            System.out.println("查询结果：");
            for (Music m : results) {
                System.out.println(m);
            }
        }
    }

    private static void downloadMusic() {
        System.out.print("请输入要下载的音乐ID：");
        String idStr = scanner.nextLine();
        int id;
        try {
            id = Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            System.out.println("ID格式错误！");
            return;
        }
        Music music = musicService.getMusicById(id);
        if (music == null) {
            System.out.println("未找到该音乐。");
        } else {
            System.out.println("模拟下载音乐：");
            System.out.println("作品名：" + music.getTitle());
            System.out.println("演唱者：" + music.getArtist());
            System.out.println("文件路径：" + music.getFilePath());
            System.out.println("请自行复制文件路径到播放器播放。");
        }
    }

    private static void scoreMusic() {
        System.out.print("请输入要评分的音乐ID：");
        String idStr = scanner.nextLine();
        int id;
        try {
            id = Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            System.out.println("ID格式错误！");
            return;
        }
        Music music = musicService.getMusicById(id);
        if (music == null) {
            System.out.println("未找到该音乐。");
            return;
        }

        System.out.print("请输入您的学生姓名：");
        String studentName = scanner.nextLine();

        System.out.print("请输入评分（1-5）：");
        String scoreStr = scanner.nextLine();
        int score;
        try {
            score = Integer.parseInt(scoreStr);
        } catch (NumberFormatException e) {
            System.out.println("评分格式错误！");
            return;
        }

        boolean success = musicService.scoreMusic(id, studentName, score);
        if (success) {
            System.out.println("评分成功！当前平均分：" + musicService.getMusicById(id).getAvgScore());
        } else {
            System.out.println("评分失败！");
        }
    }
}

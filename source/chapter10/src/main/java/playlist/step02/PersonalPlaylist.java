package playlist.step02;

public class PersonalPlaylist extends Playlist {
    public void remove(Song song) {
        getTracks().remove(song);
        getSingers().remove(song.getSinger()); // 부모 클래스의 정책 변경으로 인해, 자식 클래스에서도 수정이 발생되었다.
    }
}

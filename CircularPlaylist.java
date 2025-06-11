import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class Song {
    String title;
    String artist;
    int duration; // dalam detik
    Song next;

    public Song(String title, String artist, int duration) {
        this.title = title;
        this.artist = artist;
        this.duration = duration;
        this.next = null; // Awalnya, next menunjuk ke null
    }
}

class CircularPlaylist {
    private Song head; // Head dari circular linked list
    private Song current; // Lagu yang sedang diputar
    private int size; // Jumlah lagu dalam playlist

    public CircularPlaylist() {
        this.head = null; // Awalnya, playlist kosong
        this.current = null; // Tidak ada lagu yang sedang diputar
        this.size = 0; // Ukuran awal adalah 0
    }

    // 1. Tambah lagu ke playlist
    public void addSong(String title, String artist, int duration) {
        Song newSong = new Song(title, artist, duration);
        if (head == null) {
            head = newSong;
            newSong.next = head; // Mengatur next untuk circular
        } else {
            Song temp = head;
            while (temp.next != head) {
                temp = temp.next; // Telusuri hingga node terakhir
            }
            temp.next = newSong; // Arahkan next dari node terakhir ke node baru
            newSong.next = head; // Mengatur next untuk circular
        }
        current = head; // Set current ke head
        size++; // Tingkatkan ukuran playlist
    }

    // 2. Hapus lagu dari playlist
    public void removeSong(String title) {
        if (head == null) {
            System.out.println("Playlist kosong.");
            return;
        }

        Song temp = head;
        Song previous = null;

        // Jika lagu yang akan dihapus adalah head
        if (head.title.equals(title)) {
            if (head.next == head) { // Jika hanya ada satu lagu
                head = null; // Set head ke null
            } else {
                while (temp.next != head) {
                    temp = temp.next; // Telusuri hingga node terakhir
                }
                temp.next = head.next; // Arahkan next dari node terakhir ke node setelah head
                head = head.next; // Pindah head ke node berikutnya
            }
            size--; // Kurangi ukuran playlist
            return;
        }

        // Telusuri untuk menemukan lagu yang akan dihapus
        while (temp != head && !temp.title.equals(title)) {
            previous = temp;
            temp = temp.next;
        }

        if (temp == head) {
            System.out.println("Lagu tidak ditemukan.");
            return;
        }

        previous.next = temp.next; // Hapus node dengan mengubah pointer
        size--; // Kurangi ukuran playlist
    }

    // 3. Pindah ke lagu selanjutnya (circular)
    public void playNext() {
        if (current != null) {
            current = current.next; // Pindah ke lagu berikutnya
        }
    }

    // 4. Pindah ke lagu sebelumnya (circular)
    public void playPrevious() {
        if (current != null) {
            Song temp = head;
            while (temp.next != current) {
                temp = temp.next; // Telusuri hingga menemukan node sebelum current
            }
            current = temp; // Pindah ke lagu sebelumnya
        }
    }

    // 5. Return lagu yang sedang diputar
    public Song getCurrentSong() {
        return current; // Kembalikan lagu yang sedang diputar
    }

    // 6. Acak urutan playlist
    public void shuffle() {
        if (head == null || head.next == head) {
            return; // Jika playlist kosong atau hanya ada satu lagu
        }

        List<Song> songs = new ArrayList<>();
        Song temp = head;

        // Mengumpulkan semua lagu ke dalam list
        do {
            songs.add(temp);
            temp = temp.next;
        } while (temp != head);

        Collections.shuffle(songs); // Mengacak urutan lagu

        // Mengatur kembali circular linked list
        head = songs.get(0);
        current = head;
        for (int i = 0; i < songs.size(); i++) {
            songs.get(i).next = songs.get((i + 1) % songs.size());
        }
    }

    // 7. Return total durasi playlist dalam format mm:ss
    public String getTotalDuration() {
        int totalDuration = 0;
        Song temp = head;

        if (temp == null) {
            return "0:00"; // Jika playlist kosong
        }

        do {
            totalDuration += temp.duration; // Tambahkan durasi setiap lagu
            temp = temp.next;
        } while (temp != head);

        int minutes = totalDuration / 60;
        int seconds = totalDuration % 60;
        return String.format("%d:%02d", minutes, seconds); // Format mm:ss
    }

    // 8. Tampilkan semua lagu dalam playlist
    public void displayPlaylist() {
        if (head == null) {
            System.out.println("Playlist kosong.");
            return;
        }

        Song temp = head;
        System.out.println("=== Current Playlist ===");
        System.out.println("-> Currently Playing: " + current.title + " - " + current.artist + " (" + formatDuration(current.duration) + ")");
        int index = 1;
        do {
            if (temp != current) { // Jangan tampilkan lagu yang sedang diputar
                System.out.println("   " + index + ". " + temp.title + " - " + temp.artist + " (" + formatDuration(temp.duration) + ")");
                index++;
            }
            temp = temp.next;
        } while (temp != head);
        System.out.println("Total Duration: " + getTotalDuration());
    }

    // Helper method untuk format durasi
    private String formatDuration(int duration) {
        int minutes = duration / 60;
        int seconds = duration % 60;
        return String.format("%d:%02d", minutes, seconds); // Format mm:ss
    }

    // Method main untuk contoh penggunaan
    public static void main(String[] args) {
        CircularPlaylist playlist = new CircularPlaylist();

        playlist.addSong("Bohemian Rhapsody", "Queen", 363);
        playlist.addSong("Hotel California", "Eagles", 391);
        playlist.addSong("Stairway to Heaven", "Led Zeppelin", 482);
        playlist.addSong("Imagine", "John Lennon", 183);

        playlist.displayPlaylist(); // Tampilkan playlist

        playlist.playNext(); // Pindah ke lagu berikutnya
        playlist.displayPlaylist(); // Tampilkan playlist setelah pindah

        playlist.removeSong("Hotel California"); // Hapus lagu
        playlist.displayPlaylist(); // Tampilkan playlist setelah penghapusan

        playlist.shuffle(); // Acak urutan playlist
        playlist.displayPlaylist(); // Tampilkan playlist setelah diacak
    }
}

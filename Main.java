public class Main {
    public static void main(String[] args) {
        // Testing StudentLinkedList
        System.out.println("=== Testing StudentLinkedList ===");
        StudentLinkedList studentList = new StudentLinkedList();
        studentList.insertLast("12345", "Andi Pratama", 3.75);
        studentList.insertLast("12346", "Sari Dewi", 3.82);
        studentList.insertLast("12347", "Budi Santoso", 3.65);
        studentList.display();

        // Testing CircularPlaylist
        System.out.println("\n=== Testing CircularPlaylist ===");
        CircularPlaylist playlist = new CircularPlaylist();
        playlist.addSong("Bohemian Rhapsody", "Queen", 363);
        playlist.addSong("Hotel California", "Eagles", 391);
        playlist.addSong("Stairway to Heaven", "Led Zeppelin", 482);
        playlist.displayPlaylist();
        playlist.playNext();
        playlist.displayPlaylist();
        playlist.shuffle();
        playlist.displayPlaylist();

        // Testing TextEditor
        System.out.println("\n=== Testing TextEditor ===");
        TextEditor editor = new TextEditor();
        editor.insertText("Hello World", 0);
        editor.insertText("Beautiful ", 6);
        System.out.println(editor.getCurrentText()); // "Hello Beautiful World"
        
        editor.undo();
        System.out.println(editor.getCurrentText()); // "Hello World"
        
        editor.redo();
        System.out.println(editor.getCurrentText()); // "Hello Beautiful World"
        
        editor.deleteText(6, 9); // Hapus "Beautiful "
        System.out.println(editor.getCurrentText()); // "Hello World"
        
        editor.replaceText(6, 5, "Everyone"); // Ganti "World" dengan "Everyone"
        System.out.println(editor.getCurrentText()); // "Hello Everyone"
        
        editor.getActionHistory(); // Tampilkan riwayat aksi
    }
}

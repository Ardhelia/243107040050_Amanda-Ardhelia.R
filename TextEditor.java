class Action {
    String type; // "INSERT", "DELETE", "REPLACE"
    String text;
    int position;
    String previousText; // untuk undo
    Action next;
    Action prev;

    public Action(String type, String text, int position, String previousText) {
        this.type = type;
        this.text = text;
        this.position = position;
        this.previousText = previousText;
        this.next = null;
        this.prev = null;
    }
}

class TextEditor {
    private StringBuilder currentText; // Menyimpan teks saat ini
    private Action head; // Head dari doubly linked list untuk aksi
    private Action tail; // Tail dari doubly linked list untuk aksi
    private Action currentAction; // Aksi saat ini untuk undo/redo

    public TextEditor() {
        this.currentText = new StringBuilder(); // Inisialisasi teks kosong
        this.head = null; // Inisialisasi head
        this.tail = null; // Inisialisasi tail
        this.currentAction = null; // Tidak ada aksi saat ini
    }

    // 1. Insert text di posisi tertentu
    public void insertText(String text, int position) {
        if (position < 0 || position > currentText.length()) {
            throw new IndexOutOfBoundsException("Position out of bounds");
        }

        String previousText = currentText.toString(); // Simpan teks sebelumnya
        currentText.insert(position, text); // Sisipkan teks baru

        // Buat aksi baru dan tambahkan ke linked list
        Action action = new Action("INSERT", text, position, previousText);
        addAction(action);
    }

    // 2. Hapus text dari posisi start sepanjang length
    public void deleteText(int start, int length) {
        if (start < 0 || start >= currentText.length() || length < 0 || start + length > currentText.length()) {
            throw new IndexOutOfBoundsException("Invalid start or length");
        }

        String previousText = currentText.toString(); // Simpan teks sebelumnya
        String deletedText = currentText.substring(start, start + length); // Simpan teks yang dihapus
        currentText.delete(start, start + length); // Hapus teks

        // Buat aksi baru dan tambahkan ke linked list
        Action action = new Action("DELETE", deletedText, start, previousText);
        addAction(action);
    }

    // 3. Replace text
    public void replaceText(int start, int length, String newText) {
        if (start < 0 || start >= currentText.length() || length < 0 || start + length > currentText.length()) {
            throw new IndexOutOfBoundsException("Invalid start or length");
        }

        String previousText = currentText.toString(); // Simpan teks sebelumnya
        String replacedText = currentText.substring(start, start + length); // Simpan teks yang diganti
        currentText.replace(start, start + length, newText); // Ganti teks

        // Buat aksi baru dan tambahkan ke linked list
        Action action = new Action("REPLACE", newText, start, previousText);
        addAction(action);
    }

    // 4. Batalkan aksi terakhir
    public void undo() {
        if (currentAction == null) {
            System.out.println("No action to undo.");
            return;
        }

        // Kembalikan teks ke keadaan sebelumnya
        currentText = new StringBuilder(currentAction.previousText);
        currentAction = currentAction.prev; // Pindah ke aksi sebelumnya
    }

    // 5. Ulangi aksi yang di-undo
    public void redo() {
        if (currentAction == null || currentAction.next == null) {
            System.out.println("No action to redo.");
            return;
        }

        currentAction = currentAction.next; // Pindah ke aksi berikutnya
        currentText = new StringBuilder(currentAction.previousText); // Kembalikan teks ke keadaan setelah redo
        applyAction(currentAction); // Terapkan aksi
    }

    // 6. Return text saat ini
    public String getCurrentText() {
        return currentText.toString(); // Kembalikan teks saat ini
    }

    // 7. Tampilkan riwayat aksi
    public void getActionHistory() {
        Action temp = head;
        System.out.println("Action History:");
        while (temp != null) {
            System.out.println(temp.type + " at position " + temp.position + ": " + temp.text);
            temp = temp.next;
        }
    }

    // Helper method untuk menambahkan aksi ke linked list
    private void addAction(Action action) {
        if (head == null) {
            head = action; // Jika linked list kosong, set head
            tail = action; // Set tail ke aksi baru
        } else {
            tail.next = action; // Tambahkan aksi baru di akhir
            action.prev = tail; // Set prev untuk aksi baru
            tail = action; // Update tail
        }
        currentAction = action; // Set currentAction ke aksi baru
    }

    // Helper method untuk menerapkan aksi
    private void applyAction(Action action) {
        switch (action.type) {
            case "INSERT":
                currentText.insert(action.position, action.text);
                break;
            case "DELETE":
                currentText.delete(action.position, action.position + action.text.length());
                break;
            case "REPLACE":
                currentText.replace(action.position, action.position + action.previousText.length(), action.text);
                break;
        }
    }

    // Method main untuk contoh penggunaan
    public static void main(String[] args) {
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

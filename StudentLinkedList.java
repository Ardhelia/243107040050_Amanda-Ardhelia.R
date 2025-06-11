class Student {
    String nim;
    String name;
    double gpa;
    Student next;

    public Student(String nim, String name, double gpa) {
        this.nim = nim;
        this.name = name;
        this.gpa = gpa;
        this.next = null; // Awalnya, next menunjuk ke null
    }
}

class StudentLinkedList {
    Student head; // Head dari linked list
    int size; // Menyimpan jumlah node dalam list

    public StudentLinkedList() {
        this.head = null; // Awalnya, list kosong
        this.size = 0; // Ukuran awal adalah 0
    }

    // Method-method sebelumnya...

    // 1. Urutkan mahasiswa berdasarkan IPK secara descending menggunakan bubble sort
    public void sortByGPA() {
        if (isEmpty() || head.next == null) {
            return; // Jika list kosong atau hanya ada satu elemen, tidak perlu diurutkan
        }

        boolean swapped;
        do {
            swapped = false;
            Student current = head;
            while (current.next != null) {
                if (current.gpa < current.next.gpa) { // Bandingkan IPK
                    // Tukar data mahasiswa
                    String tempNim = current.nim;
                    String tempName = current.name;
                    double tempGpa = current.gpa;

                    current.nim = current.next.nim;
                    current.name = current.next.name;
                    current.gpa = current.next.gpa;

                    current.next.nim = tempNim;
                    current.next.name = tempName;
                    current.next.gpa = tempGpa;

                    swapped = true; // Tandai bahwa ada pertukaran
                }
                current = current.next; // Pindah ke node berikutnya
            }
        } while (swapped); // Ulangi hingga tidak ada pertukaran
    }

    // 2. Balik urutan linked list
    public void reverse() {
        if (isEmpty() || head.next == null) {
            return; // Jika list kosong atau hanya ada satu elemen, tidak perlu dibalik
        }

        Student previous = null;
        Student current = head;
        Student next = null;

        while (current != null) {
            next = current.next; // Simpan node berikutnya
            current.next = previous; // Balik arah pointer
            previous = current; // Pindah previous ke current
            current = next; // Pindah current ke next
        }
        head = previous; // Set head ke node terakhir yang diproses
    }

    // 3. Return Student dengan IPK tertinggi
    public Student findHighestGPA() {
        if (isEmpty()) {
            return null; // Jika list kosong, kembalikan null
        }

        Student current = head;
        Student highest = head; // Mulai dengan mahasiswa pertama sebagai yang tertinggi

        while (current != null) {
            if (current.gpa > highest.gpa) {
                highest = current; // Update jika ditemukan IPK yang lebih tinggi
            }
            current = current.next; // Pindah ke node berikutnya
        }
        return highest; // Kembalikan mahasiswa dengan IPK tertinggi
    }

    // 4. Return array/list mahasiswa dengan IPK di atas threshold tertentu
    public Student[] getStudentsAboveGPA(double threshold) {
        if (isEmpty()) {
            return new Student[0]; // Jika list kosong, kembalikan array kosong
        }

        Student[] result = new Student[size]; // Array untuk menyimpan mahasiswa yang memenuhi syarat
        int count = 0; // Hitung jumlah mahasiswa yang memenuhi syarat
        Student current = head;

        while (current != null) {
            if (current.gpa > threshold) {
                result[count++] = current; // Tambahkan mahasiswa ke array
            }
            current = current.next; // Pindah ke node berikutnya
        }

        // Buat array baru dengan ukuran yang tepat
        Student[] finalResult = new Student[count];
        System.arraycopy(result, 0, finalResult, 0, count); // Salin ke array baru
        return finalResult; // Kembalikan array mahasiswa yang memenuhi syarat
    }

    // 5. Gabungkan dua sorted linked list menjadi satu sorted linked list
    public StudentLinkedList mergeSortedList(StudentLinkedList otherList) {
        StudentLinkedList mergedList = new StudentLinkedList(); // Buat list baru untuk hasil penggabungan

        Student current1 = this.head; // Pointer untuk list pertama
        Student current2 = otherList.head; // Pointer untuk list kedua

        while (current1 != null && current2 != null) {
            if (current1.gpa >= current2.gpa) { // Bandingkan IPK
                mergedList.insertLast(current1.nim, current1.name, current1.gpa); // Tambahkan ke list hasil
                current1 = current1.next; // Pindah ke node berikutnya di list pertama
            } else {
                mergedList.insertLast(current2.nim, current2.name, current2.gpa); // Tambahkan ke list hasil
                current2 = current2.next; // Pindah ke node berikutnya di list kedua
            }
        }

        // Tambahkan sisa node dari list pertama jika ada
        while (current1 != null) {
            mergedList.insertLast(current1.nim, current1.name, current1.gpa);
            current1 = current1.next;
        }

        // Tambahkan sisa node dari list kedua jika ada
        while (current2 != null) {
            mergedList.insertLast(current2.nim, current2.name, current2.gpa);
            current2 = current2.next;
        }

        return mergedList; // Kembalikan list hasil penggabungan
    }

    // Method main untuk contoh penggunaan
    public static void main(String[] args) {
        StudentLinkedList list = new StudentLinkedList();

        list.insertLast("12345", "Andi Pratama", 3.75);
        list.insertLast("12346", "Sari Dewi", 3.82);
        list.insertLast("12347", "Budi Santoso", 3.65);

        System.out.println("=== Data Mahasiswa Sebelum Sort ===");
        list.display();

        list.sortByGPA(); // Mengurutkan berdasarkan IPK
        System.out.println("=== Data Mahasiswa Setelah sortByGPA() ===");
        list.display();

        Student highestGPAStudent = list.findHighestGPA(); // Mencari mahasiswa dengan IPK tertinggi
        if (highestGPAStudent != null) {
            System.out.println("Mahasiswa dengan IPK tertinggi: NIM: " + highestGPAStudent.nim + ", Nama: " + highestGPAStudent.name + ", IPK: " + highestGPAStudent.gpa);
        }

        Student[] studentsAboveGPA = list.getStudentsAboveGPA(3.70); // Mencari mahasiswa dengan IPK di atas 3.70
        System.out.println("=== Mahasiswa dengan IPK di atas 3.70 ===");
        for (Student student : studentsAboveGPA) {
            System.out.println("NIM: " + student.nim + ", Nama: " + student.name + ", IPK: " + student.gpa);
        }

        // Contoh penggabungan dua list
        StudentLinkedList anotherList = new StudentLinkedList();
        anotherList.insertLast("12348", "Citra Lestari", 3.90);
        anotherList.insertLast("12349", "Dewi Anggraini", 3.80);

        StudentLinkedList mergedList = list.mergeSortedList(anotherList); // Menggabungkan dua list
        System.out.println("=== Data Mahasiswa Setelah Penggabungan ===");
        mergedList.display();
    }
}

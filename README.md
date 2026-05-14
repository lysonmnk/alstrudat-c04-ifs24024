
# Expedition to the Ancient Network(PROGRAM 1)

## Deskripsi Masalah

Kamu adalah seorang penjelajah yang sedang menelusuri jaringan kota kuno yang terdiri dari **N kota** yang dihubungkan oleh **M jalan satu arah**. Setiap jalan memiliki **biaya perjalanan**.

Selama perjalanan, kamu memiliki akses ke sebuah teknologi kuno bernama **Time Warp Portal**. Portal ini dapat digunakan **maksimal satu kali** selama perjalananmu. Jika portal diaktifkan di sebuah kota, maka **jalan berikutnya yang kamu ambil dari kota tersebut akan memiliki biaya 0**, terlepas dari biaya aslinya.

Namun perjalanan tidak semudah itu.

Beberapa kota memiliki **kutukan (curse)**. Jika kamu **keluar dari kota yang terkutuk**, maka kamu harus membayar **penalti tambahan** sesuai nilai kutukan kota tersebut.

Terdapat satu pengecualian penting:
- Penalti kutukan hanya berlaku **selama portal belum pernah digunakan sebelumnya**.

Tugasmu adalah menentukan **biaya minimum** untuk melakukan perjalanan dari kota **S** ke kota **T**.

Jika tidak ada jalur yang memungkinkan perjalanan dari **S** ke **T**, maka hasilnya adalah **-1**.

---

## Aturan Penting

- Graf bersifat **berarah**.
- Portal hanya dapat digunakan **paling banyak satu kali**.
- Menggunakan portal membuat **satu perjalanan berikutnya memiliki biaya 0**.
- Jika keluar dari kota terkutuk sebelum portal digunakan, **penalti kota tersebut ditambahkan ke biaya perjalanan**.
- Jika portal sudah pernah digunakan sebelumnya, **penalti kutukan tidak lagi berlaku**.

---

## Format Input

Baris pertama berisi tiga bilangan:

N M K

N = jumlah kota (1 sampai N)  
M = jumlah jalan  
K = jumlah kota terkutuk  

Selanjutnya terdapat **K pasangan nilai**:

ci pi

ci = indeks kota terkutuk  
pi = penalti kutukan kota tersebut  

Kemudian terdapat **M baris jalan**:

u v w

yang berarti terdapat jalan **berarah dari kota u ke kota v** dengan biaya **w**.

Baris terakhir berisi:

S T

S = kota awal  
T = kota tujuan  

---

## Format Output

Cetak output:

Minimum cost: X

di mana **X** adalah biaya minimum untuk pergi dari **S ke T**.

Jika tidak ada jalur yang memungkinkan:

Minimum cost: -1

---

## Batasan

1 ≤ N ≤ 100000  
1 ≤ M ≤ 300000  
0 ≤ K ≤ min(N, 1000)  

1 ≤ w ≤ 1,000,000  
1 ≤ pi ≤ 500,000  

1 ≤ S, T ≤ N  
S ≠ T  

Graf dapat memiliki lebih dari satu edge antara dua kota.

---

## Contoh Input

6 8 2  
3 15  
5 10  
1 2 10  
1 3 5  
3 2 4  
3 4 8  
2 4 3  
4 5 2  
5 6 7  
2 6 30  
1 6  

---

## Contoh Output

Minimum cost: 10


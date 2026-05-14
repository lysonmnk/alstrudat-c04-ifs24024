#!/bin/bash

# Hentikan script jika terjadi error fatal
set -e

echo "🔨 Compiling Java program..."
if ! mvn clean package; then
  echo "❌ Build failed! Exiting..."
  exit 1
fi
echo "✅ Build successful!"

# Pastikan folder testcases ada
if [ ! -d "testcases" ]; then
  echo "❌ Error: Folder 'testcases' tidak ditemukan!"
  exit 1
fi

# Pastikan weights.txt ada
if [ ! -f "testcases/weights.txt" ]; then
  echo "❌ Error: File 'testcases/weights.txt' tidak ditemukan!"
  exit 1
fi

total_score=0
index=1

# Baca bobot dari file weights.txt
while read -r weight || [ -n "$weight" ]; do
  # Hilangkan spasi/karakter tersembunyi
  weight=$(echo "$weight" | tr -d '\r')
  
  # Lewati jika baris kosong
  if [ -z "$weight" ]; then continue; fi

  echo "|--------------------------------------------------|"
  echo "🚀 Running Test Case $index (Weight: $weight%)"
  
  input_file="testcases/input$index.txt"
  expected_file="testcases/expected$index.txt"
  output_file="testcases/output$index.txt"

  # Debugging: Cek keberadaan file
  if [ ! -f "$input_file" ]; then
    echo "❌ Error: File $input_file tidak ditemukan!"
    exit 1
  fi
  if [ ! -f "$expected_file" ]; then
    echo "❌ Error: File $expected_file tidak ditemukan!"
    exit 1
  fi

  # Jalankan program menggunakan jar yang sudah di-build
  java -jar target/app.jar < "$input_file" > "$output_file"

  # Bandingkan output (-w mengabaikan spasi/newline)
  if diff -w -q "$output_file" "$expected_file" > /dev/null; then
    echo "✅ Test case $index passed!"
    total_score=$((total_score + weight))
  else
    echo "❌ Test case $index FAILED!"
    echo "--- Perbedaan (Actual < vs > Expected) ---"
    diff -w "$output_file" "$expected_file" || true
  fi

  index=$((index + 1))
done < testcases/weights.txt

echo "|--------------------------------------------------|"
echo "🎯 Final Score: $total_score%"

# Jika nilai tidak 100%, buat skrip error
if [ "$total_score" -ne 100 ]; then
  echo "❌ Error: Final score is $total_score% (Expected 100%)."
  exit 1
fi

echo "✅ Semua tes berhasil!"
#!/bin/bash

# Pastikan script berhenti jika terjadi error fatal
set -e

# Compile Java program dengan Maven
echo "🔨 Compiling Java program..."
if ! mvn clean package; then
  echo "❌ Build failed! Exiting..."
  exit 1
fi
echo "✅ Build successful!"

# Pastikan file weights.txt ada
if [ ! -f testcases/weights.txt ]; then
  echo "❌ Error: File testcases/weights.txt not found!"
  exit 1
fi

total_score=0
index=1

# Baca bobot dari file weights.txt
while read -r weight; do
  echo "|--------------------------------------------------|"
  echo "Test Case $index"
  input_file="testcases/input$index.txt"
  expected_file="testcases/expected$index.txt"
  output_file="testcases/output$index.txt"

  # Pastikan file test case tersedia
  if [ ! -f "$input_file" ] || [ ! -f "$expected_file" ]; then
    echo "⚠️ Warning: Test case $index files missing. Skipping..."
    index=$((index + 1))
    continue
  fi

  echo "🚀 Running test case $index (Weight: $weight%)..."

  # Jalankan program menggunakan jar yang sudah di-build
  # -w pada diff mengabaikan spasi/newline tambahan
  java -jar target/app.jar < "$input_file" > "$output_file"

  # Tampilkan output untuk debugging di log
  echo "🔍 Actual Output:"
  cat "$output_file"
  echo "🔍 Expected Output:"
  cat "$expected_file"

  # Bandingkan output (-w mengabaikan spasi/newline)
  if diff -w -q "$output_file" "$expected_file" > /dev/null; then
    echo "✅ Test case $index passed! (+$weight%)"
    total_score=$((total_score + weight))
  else
    echo "❌ Test case $index failed!"
    echo "Diff (Actual vs Expected):"
    diff -w "$output_file" "$expected_file"
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
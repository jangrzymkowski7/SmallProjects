package com.example.grzymkowskil4

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.grzymkowskil4.R

// Prosta klasa danych do przechowywania liczby zespolonej
data class ComplexNumber(val real: Double, val imag: Double) {

    // Metoda dodawania
    fun add(other: ComplexNumber): ComplexNumber {
        return ComplexNumber(this.real + other.real, this.imag + other.imag)
    }

    // Metoda odejmowania
    fun subtract(other: ComplexNumber): ComplexNumber {
        return ComplexNumber(this.real - other.real, this.imag - other.imag)
    }

    // Ładne formatowanie wyniku do wyświetlenia
    override fun toString(): String {
        return if (imag >= 0) {
            String.format("%.2f + %.2fi", real, imag)
        } else {
            String.format("%.2f - %.2fi", real, Math.abs(imag))
        }
    }
}

class MainActivity : AppCompatActivity() {

    // Deklarujemy elementy UI, aby mieć do nich dostęp w całej klasie
    private lateinit var etRealA: EditText
    private lateinit var etImagA: EditText
    private lateinit var etRealB: EditText
    private lateinit var etImagB: EditText
    private lateinit var btnAdd: Button
    private lateinit var btnSubtract: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 1. Inicjalizacja (mapowanie) elementów UI
        etRealA = findViewById(R.id.etRealA)
        etImagA = findViewById(R.id.etImagA)
        etRealB = findViewById(R.id.etRealB)
        etImagB = findViewById(R.id.etImagB)
        btnAdd = findViewById(R.id.btnAdd)
        btnSubtract = findViewById(R.id.btnSubtract)

        // 2. Obsługa przycisku DODAJ
        btnAdd.setOnClickListener {
            performOperation(Operation.ADD)
        }

        // 3. Obsługa przycisku ODEJMIJ
        btnSubtract.setOnClickListener {
            performOperation(Operation.SUBTRACT)
        }
    }

    // Definiujemy typy operacji, aby funkcja była czystsza
    private enum class Operation { ADD, SUBTRACT }

    // Wspólna funkcja do walidacji i obliczeń
    private fun performOperation(operation: Operation) {

        // 4. Walidacja: Sprawdzamy, czy wszystkie 4 pola są wypełnione
        val inputs = listOf(etRealA, etImagA, etRealB, etImagB)
        if (inputs.any { it.text.isNullOrBlank() }) {
            showToast("Błąd: Wypełnij wszystkie 4 pola!")
            return
        }

        try {
            // 5. Pobieranie wartości i parsowanie na Double
            val realA = etRealA.text.toString().toDouble()
            val imagA = etImagA.text.toString().toDouble()
            val realB = etRealB.text.toString().toDouble()
            val imagB = etImagB.text.toString().toDouble()

            // Tworzenie obiektów liczb zespolonych
            val zA = ComplexNumber(realA, imagA)
            val zB = ComplexNumber(realB, imagB)

            // 6. Obliczenia
            val result: ComplexNumber = if (operation == Operation.ADD) {
                zA.add(zB)
            } else { // (operation == Operation.SUBTRACT)
                zA.subtract(zB)
            }

            // 7. Wyświetlanie wyniku
            showToast("Wynik: $result")

        } catch (e: NumberFormatException) {
            // Obsługa błędu, jeśli ktoś wpisał np. tekst lub samą kropkę
            showToast("Błąd: Niepoprawny format liczby!")
        }
    }

    // Pomocnicza funkcja do pokazywania Toast
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}
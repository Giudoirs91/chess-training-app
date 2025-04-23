package com.example.trainingscacchi

// Enum per i tipi di pezzi
enum class PieceType {
    PAWN, KNIGHT, BISHOP, ROOK, QUEEN, KING, EMPTY
}

// Enum per il colore dei pezzi
enum class PieceColor {
    WHITE, BLACK, NONE
}

// Classe che rappresenta un pezzo sulla scacchiera
data class ChessPiece(
    val type: PieceType,
    val color: PieceColor
) {
    companion object {
        val EMPTY = ChessPiece(PieceType.EMPTY, PieceColor.NONE)
    }
}

// Classe per rappresentare una posizione sulla scacchiera
data class Position(val row: Int, val col: Int) {
    fun isValid(): Boolean = row in 0..7 && col in 0..7
}
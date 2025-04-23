package com.example.trainingscacchi

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

// Classe per gestire lo stato della scacchiera
class ChessModel {
    // La scacchiera è rappresentata come una matrice 8x8 di pezzi
    private val board = Array(8) { Array(8) { ChessPiece.EMPTY } }

    // Posizione del pezzo selezionato
    var selectedPosition by mutableStateOf<Position?>(null)
        private set

    // Lista delle mosse possibili per il pezzo selezionato
    var possibleMoves by mutableStateOf<List<Position>>(emptyList())
        private set

    // Turno attuale (true = bianco, false = nero)
    var isWhiteTurn by mutableStateOf(true)
        private set

    init {
        resetBoard()
    }

    // Restituisce il pezzo alla posizione specificata
    fun getPieceAt(row: Int, col: Int): ChessPiece {
        return if (row in 0..7 && col in 0..7) board[row][col] else ChessPiece.EMPTY
    }

    // Configura la scacchiera nella posizione iniziale
    private fun resetBoard() {
        // Posiziona i pedoni
        for (col in 0..7) {
            board[1][col] = ChessPiece(PieceType.PAWN, PieceColor.BLACK)
            board[6][col] = ChessPiece(PieceType.PAWN, PieceColor.WHITE)
        }

        // Posiziona le torri
        board[0][0] = ChessPiece(PieceType.ROOK, PieceColor.BLACK)
        board[0][7] = ChessPiece(PieceType.ROOK, PieceColor.BLACK)
        board[7][0] = ChessPiece(PieceType.ROOK, PieceColor.WHITE)
        board[7][7] = ChessPiece(PieceType.ROOK, PieceColor.WHITE)

        // Posiziona i cavalli
        board[0][1] = ChessPiece(PieceType.KNIGHT, PieceColor.BLACK)
        board[0][6] = ChessPiece(PieceType.KNIGHT, PieceColor.BLACK)
        board[7][1] = ChessPiece(PieceType.KNIGHT, PieceColor.WHITE)
        board[7][6] = ChessPiece(PieceType.KNIGHT, PieceColor.WHITE)

        // Posiziona gli alfieri
        board[0][2] = ChessPiece(PieceType.BISHOP, PieceColor.BLACK)
        board[0][5] = ChessPiece(PieceType.BISHOP, PieceColor.BLACK)
        board[7][2] = ChessPiece(PieceType.BISHOP, PieceColor.WHITE)
        board[7][5] = ChessPiece(PieceType.BISHOP, PieceColor.WHITE)

        // Posiziona le regine
        board[0][3] = ChessPiece(PieceType.QUEEN, PieceColor.BLACK)
        board[7][3] = ChessPiece(PieceType.QUEEN, PieceColor.WHITE)

        // Posiziona i re
        board[0][4] = ChessPiece(PieceType.KING, PieceColor.BLACK)
        board[7][4] = ChessPiece(PieceType.KING, PieceColor.WHITE)
    }

    // Seleziona un pezzo sulla scacchiera
    fun selectPosition(row: Int, col: Int) {
        val position = Position(row, col)
        val piece = getPieceAt(row, col)

        // Se selezioniamo un pezzo del colore del turno attuale
        if (piece.color == (if (isWhiteTurn) PieceColor.WHITE else PieceColor.BLACK)) {
            selectedPosition = position
            possibleMoves = calculatePossibleMoves(position)
        }
        // Se selezioniamo una mossa possibile, eseguiamo la mossa
        else if (possibleMoves.contains(position)) {
            movePiece(selectedPosition!!, position)
            clearSelection()
        }
        // Altrimenti, deseleziona
        else {
            clearSelection()
        }
    }

    // Cancella la selezione corrente
    private fun clearSelection() {
        selectedPosition = null
        possibleMoves = emptyList()
    }

    // Sposta un pezzo da una posizione a un'altra
    private fun movePiece(from: Position, to: Position) {
        val piece = getPieceAt(from.row, from.col)

        // Aggiorna la scacchiera
        board[to.row][to.col] = piece
        board[from.row][from.col] = ChessPiece.EMPTY

        // Cambia il turno
        isWhiteTurn = !isWhiteTurn
    }

    // Calcola le mosse possibili per un pezzo in una data posizione
    private fun calculatePossibleMoves(position: Position): List<Position> {
        val (row, col) = position
        val piece = getPieceAt(row, col)
        val moves = mutableListOf<Position>()

        // Implementazione semplificata delle mosse - per ora solo mosse base
        when (piece.type) {
            PieceType.PAWN -> {
                // Direzione di movimento (su per il nero, giù per il bianco)
                val direction = if (piece.color == PieceColor.BLACK) 1 else -1

                // Mossa in avanti di una casella
                val forwardPos = Position(row + direction, col)
                if (forwardPos.isValid() && getPieceAt(forwardPos.row, forwardPos.col).type == PieceType.EMPTY) {
                    moves.add(forwardPos)

                    // Mossa iniziale: possibilità di muoversi di due caselle
                    if ((row == 1 && piece.color == PieceColor.BLACK) ||
                        (row == 6 && piece.color == PieceColor.WHITE)) {
                        val doubleForwardPos = Position(row + 2 * direction, col)
                        if (doubleForwardPos.isValid() &&
                            getPieceAt(doubleForwardPos.row, doubleForwardPos.col).type == PieceType.EMPTY) {
                            moves.add(doubleForwardPos)
                        }
                    }
                }

                // Catture diagonali
                val capturePositions = listOf(
                    Position(row + direction, col - 1),
                    Position(row + direction, col + 1)
                )

                for (capturePos in capturePositions) {
                    if (capturePos.isValid()) {
                        val targetPiece = getPieceAt(capturePos.row, capturePos.col)
                        if (targetPiece.type != PieceType.EMPTY && targetPiece.color != piece.color) {
                            moves.add(capturePos)
                        }
                    }
                }
            }

            // Per gli altri pezzi, implementeremo le mosse in seguito
            else -> {
                // Per semplicità, aggiungiamo alcune mosse generiche per ora
                // Le caselle adiacenti dove il pezzo può muoversi
                val adjacentPositions = listOf(
                    Position(row - 1, col),     // sopra
                    Position(row + 1, col),     // sotto
                    Position(row, col - 1),     // sinistra
                    Position(row, col + 1),     // destra
                    Position(row - 1, col - 1), // diagonale alto-sinistra
                    Position(row - 1, col + 1), // diagonale alto-destra
                    Position(row + 1, col - 1), // diagonale basso-sinistra
                    Position(row + 1, col + 1)  // diagonale basso-destra
                )

                for (adjPos in adjacentPositions) {
                    if (adjPos.isValid()) {
                        val targetPiece = getPieceAt(adjPos.row, adjPos.col)
                        // Può muoversi in una casella vuota o catturare un pezzo avversario
                        if (targetPiece.type == PieceType.EMPTY ||
                            targetPiece.color != piece.color) {
                            moves.add(adjPos)
                        }
                    }
                }
            }
        }

        return moves
    }
}
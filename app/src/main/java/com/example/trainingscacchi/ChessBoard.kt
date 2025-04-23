package com.example.trainingscacchi

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Colori della scacchiera
private val lightSquareColor = Color(0xFFE8EDF9)
private val darkSquareColor = Color(0xFF96AFCF)
private val backgroundColor = Color(0xFF1E1E1E)
private val selectedColor = Color(0xFF7FA650)         // Verde per la casella selezionata
private val possibleMoveColor = Color(0x887FA650)     // Verde semi-trasparente per le mosse possibili

// Caratteri Unicode per i pezzi degli scacchi
private val chessPieceChars = mapOf(
    Pair(PieceType.KING, PieceColor.WHITE) to "♔",
    Pair(PieceType.QUEEN, PieceColor.WHITE) to "♕",
    Pair(PieceType.ROOK, PieceColor.WHITE) to "♖",
    Pair(PieceType.BISHOP, PieceColor.WHITE) to "♗",
    Pair(PieceType.KNIGHT, PieceColor.WHITE) to "♘",
    Pair(PieceType.PAWN, PieceColor.WHITE) to "♙",
    Pair(PieceType.KING, PieceColor.BLACK) to "♚",
    Pair(PieceType.QUEEN, PieceColor.BLACK) to "♛",
    Pair(PieceType.ROOK, PieceColor.BLACK) to "♜",
    Pair(PieceType.BISHOP, PieceColor.BLACK) to "♝",
    Pair(PieceType.KNIGHT, PieceColor.BLACK) to "♞",
    Pair(PieceType.PAWN, PieceColor.BLACK) to "♟"
)

@Composable
fun ChessBoard(modifier: Modifier = Modifier, chessModel: ChessModel = ChessModel()) {
    Box(
        modifier = modifier
            .background(backgroundColor)
            .padding(4.dp)
    ) {
        // Griglia della scacchiera con pezzi
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .align(Alignment.Center)
        ) {
            val squareSize = maxWidth / 8

            // Disegna la scacchiera
            for (row in 0 until 8) {
                for (col in 0 until 8) {
                    val isLightSquare = (row + col) % 2 == 0
                    val baseColor = if (isLightSquare) lightSquareColor else darkSquareColor

                    // Determina il colore della casella in base allo stato di selezione
                    val position = Position(row, col)
                    val isSelected = chessModel.selectedPosition == position
                    val isPossibleMove = chessModel.possibleMoves.contains(position)

                    val squareColor = when {
                        isSelected -> selectedColor
                        isPossibleMove -> possibleMoveColor
                        else -> baseColor
                    }

                    Box(
                        modifier = Modifier
                            .offset(x = squareSize * col, y = squareSize * row)
                            .size(squareSize)
                            .background(squareColor)
                            .clickable { chessModel.selectPosition(row, col) }
                    ) {
                        // Disegna il pezzo se presente
                        val piece = chessModel.getPieceAt(row, col)
                        if (piece.type != PieceType.EMPTY) {
                            val pieceChar = chessPieceChars[Pair(piece.type, piece.color)] ?: ""

                            Text(
                                text = pieceChar,
                                fontSize = squareSize.value.sp * 0.7f,
                                color = if (piece.color == PieceColor.WHITE)
                                    Color.White else Color(0xFF5D4037),
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .wrapContentHeight(Alignment.CenterVertically)
                                    .wrapContentWidth(Alignment.CenterHorizontally)
                            )
                        }
                    }
                }
            }
        }

        // Indicatore del turno
        Box(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(8.dp)
                .background(if (chessModel.isWhiteTurn) Color.White else Color(0xFF5D4037),
                    shape = CircleShape)
                .size(16.dp)
                .border(1.dp, Color.White.copy(alpha = 0.5f), shape = CircleShape)
        )
    }
}
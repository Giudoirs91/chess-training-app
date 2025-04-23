package com.example.trainingscacchi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ChessApp()
                }
            }
        }
    }
}

@Composable
fun ChessApp() {
    // Crea e ricorda il modello della scacchiera
    val chessModel = remember { ChessModel() }

    // Rileva l'orientamento del dispositivo
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.screenWidthDp > configuration.screenHeightDp

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1E1E1E)) // Sfondo scuro
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Barra superiore con titolo
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Pulsante indietro (simulato)
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .padding(8.dp)
                ) {
                    Text(
                        text = "←",
                        color = Color.White,
                        fontSize = 20.sp
                    )
                }

                // Titolo
                Text(
                    text = "Allenamento sulle tattiche scacchistiche",
                    color = Color.White,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.weight(1f)
                )
            }

            if (isLandscape) {
                // Layout orizzontale
                Row(
                    modifier = Modifier.fillMaxSize()
                ) {
                    // Scacchiera a sinistra
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                    ) {
                        ChessBoard(
                            modifier = Modifier
                                .align(Alignment.Center)
                                .fillMaxHeight(0.9f)
                                .aspectRatio(1f),
                            chessModel = chessModel
                        )
                    }

                    // Pannello informazioni a destra
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .padding(start = 16.dp)
                    ) {
                        // Sezione "Tocca a te"
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Icona del colore del turno
                            Box(
                                modifier = Modifier
                                    .size(48.dp)
                                    .background(
                                        if (chessModel.isWhiteTurn) Color.White
                                        else Color(0xFF5D4037),
                                        shape = CircleShape
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = if (chessModel.isWhiteTurn) "♟" else "♙",
                                    color = if (chessModel.isWhiteTurn) Color.Black else Color.White,
                                    fontSize = 24.sp
                                )
                            }

                            // Testo
                            Column(
                                modifier = Modifier
                                    .padding(start = 12.dp)
                            ) {
                                Text(
                                    text = "Tocca a te",
                                    color = Color.White,
                                    style = MaterialTheme.typography.titleMedium
                                )
                                Text(
                                    text = "Trova la mossa migliore per il " +
                                            if (chessModel.isWhiteTurn) "bianco" else "nero",
                                    color = Color.White.copy(alpha = 0.7f),
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }

                        Spacer(modifier = Modifier.weight(1f))

                        // Punteggio in basso
                        Text(
                            text = "Punteggio: 0",
                            color = Color.White,
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )
                    }
                }
            } else {
                // Layout verticale
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    ChessBoard(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .fillMaxWidth()
                            .aspectRatio(1f),
                        chessModel = chessModel
                    )
                }

                // Area sotto la scacchiera
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                ) {
                    // Sezione "Tocca a te"
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Icona del colore del turno
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .background(
                                    if (chessModel.isWhiteTurn) Color.White
                                    else Color(0xFF5D4037),
                                    shape = CircleShape
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = if (chessModel.isWhiteTurn) "♟" else "♙",
                                color = if (chessModel.isWhiteTurn) Color.Black else Color.White,
                                fontSize = 24.sp
                            )
                        }

                        // Testo
                        Column(
                            modifier = Modifier
                                .padding(start = 12.dp)
                        ) {
                            Text(
                                text = "Tocca a te",
                                color = Color.White,
                                style = MaterialTheme.typography.titleMedium
                            )
                            Text(
                                text = "Trova la mossa migliore per il " +
                                        if (chessModel.isWhiteTurn) "bianco" else "nero",
                                color = Color.White.copy(alpha = 0.7f),
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }

                    // Punteggio in basso
                    Text(
                        text = "Punteggio: 0",
                        color = Color.White,
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                }
            }
        }
    }
}
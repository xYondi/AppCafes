package com.coffeetrip.presentation.screens.inicio

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.coffeetrip.ui.components.CafeteriaCard
import com.coffeetrip.ui.components.CafeteriaBottomSheetContainer
import com.coffeetrip.ui.components.InicioHeader
import com.coffeetrip.ui.components.DockNavigationBar
import com.coffeetrip.ui.theme.CoffeeTripColors
import com.coffeetrip.ui.theme.IconColors

/**
 * Pantalla principal de inicio que muestra las cafeterías populares
 * Basada en el diseño de Figma INICIO V2
 */
@Composable
fun InicioScreen(
    viewModel: InicioViewModel,
    onCafeteriaClick: (String) -> Unit,
    onMapaClick: () -> Unit,
    onNavigate: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    val cafeteriaSeleccionada by viewModel.cafeteriaSeleccionada.collectAsState()
    
    CafeteriaBottomSheetContainer(
        cafeteriaDetalle = cafeteriaSeleccionada,
        onDismiss = { viewModel.cerrarBottomSheet() },
        onCallClick = { viewModel.llamarCafeteria() },
        onDirectionsClick = { viewModel.obtenerDirecciones() },
        onShareClick = { viewModel.compartirCafeteria() },
        onToggleFavorite = { viewModel.toggleFavorito() },
        modifier = modifier
    ) {
        InicioScreenContent(
            uiState = uiState,
            onCafeteriaClick = { cafeteria -> 
                viewModel.seleccionarCafeteria(cafeteria)
            },
            onMapaClick = onMapaClick,
            onNavigate = onNavigate
        )
    }
}

@Composable
private fun InicioScreenContent(
    uiState: InicioUiState,
    onCafeteriaClick: (com.coffeetrip.domain.model.Cafeteria) -> Unit,
    onMapaClick: () -> Unit,
    onNavigate: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = CoffeeTripColors.bgBase
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 8.dp, vertical = 40.dp),
                contentPadding = PaddingValues(
                    start = 8.dp,
                    end = 8.dp,
                    top = 16.dp,
                    bottom = 130.dp // espacio para el dock elevado
                ),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    InicioHeader(
                        onMapaClick = onMapaClick
                    )
                }
                when (uiState) {
                    is InicioUiState.Loading -> {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(200.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator(
                                    color = CoffeeTripColors.bgSurface
                                )
                            }
                        }
                    }
                    is InicioUiState.Error -> {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(200.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = "Error al cargar las cafeterías",
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Medium,
                                        color = CoffeeTripColors.bgSurface,
                                        textAlign = TextAlign.Center
                                    )
                                    Spacer(modifier = androidx.compose.ui.Modifier.height(8.dp))
                                    Text(
                                        text = (uiState as InicioUiState.Error).message,
                                        fontSize = 14.sp,
                                        color = CoffeeTripColors.darkGray,
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }
                        }
                    }
                    is InicioUiState.Empty -> {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(200.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "No hay cafeterías disponibles",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = CoffeeTripColors.bgSurface,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                    is InicioUiState.Success -> {
                        items((uiState as InicioUiState.Success).cafeterias) { cafeteria ->
                            CafeteriaCard(
                                cafeteria = cafeteria,
                                onClick = { onCafeteriaClick(cafeteria) }
                            )
                        }
                    }
                }
            }

            // Contenedor inferior con esquinas superiores redondeadas, fondo extendido y dock elevado
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp))
                    .background(CoffeeTripColors.navbarBg)
                    .windowInsetsPadding(WindowInsets.navigationBars)
            ) {
                DockNavigationBar(
                    currentRoute = "inicio",
                    onNavigate = onNavigate,
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .fillMaxWidth()
                        .padding(bottom = 14.dp)
                )
                Spacer(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .height(14.dp)
                )
            }
        }
    }
}
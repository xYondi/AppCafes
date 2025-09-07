package com.coffeetrip.presentation.screens.favoritos

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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.coffeetrip.domain.model.CafeteriaDetalle
import com.coffeetrip.ui.components.CafeteriaBottomSheetContainer
import com.coffeetrip.ui.components.CafeteriaCard
import com.coffeetrip.ui.components.DockNavigationBar
import com.coffeetrip.ui.theme.CoffeeTripColors
import com.coffeetrip.ui.theme.EstadoColors
import com.coffeetrip.ui.theme.IconColors

/**
 * Pantalla de Favoritos basada en Favoritos.html
 * Muestra las cafeterías guardadas por el usuario
 */
@Composable
fun FavoritosScreen(
    onNavigateToInicio: () -> Unit,
    onNavigateToMas: () -> Unit,
    onCafeteriaClick: (CafeteriaDetalle) -> Unit,
    onMapaClick: () -> Unit,
    onCallClick: () -> Unit,
    onDirectionsClick: () -> Unit,
    onShareClick: () -> Unit,
    onToggleFavorite: (String) -> Unit,
    viewModel: FavoritosViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val cafeteriaSeleccionada by viewModel.cafeteriaSeleccionada.collectAsState()
    
    CafeteriaBottomSheetContainer(
        cafeteriaDetalle = cafeteriaSeleccionada,
        onDismiss = { viewModel.cerrarBottomSheet() },
        onCallClick = { viewModel.llamarCafeteria() },
        onDirectionsClick = { viewModel.obtenerDirecciones() },
        onShareClick = { viewModel.compartirCafeteria() },
        onToggleFavorite = { viewModel.toggleFavorito() }
    ) {
        FavoritosScreenContent(
            uiState = uiState,
            viewModel = viewModel,
            onCafeteriaClick = onCafeteriaClick,
            onMapaClick = onMapaClick,
            onNavigateToInicio = onNavigateToInicio,
            onNavigateToMas = onNavigateToMas,
            onToggleFavorite = onToggleFavorite
        )
    }
}

@Composable
private fun FavoritosScreenContent(
    uiState: FavoritosUiState,
    viewModel: FavoritosViewModel,
    onCafeteriaClick: (CafeteriaDetalle) -> Unit,
    onMapaClick: () -> Unit,
    onNavigateToInicio: () -> Unit,
    onNavigateToMas: () -> Unit,
    onToggleFavorite: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val favoritosIds by viewModel.favoritosIds.collectAsState()
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
            FavoritosHeader(
                        onMapaClick = onMapaClick
            )
                }
                when {
                    uiState.isLoading -> {
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
                    uiState.isEmpty -> {
                        item {
                        FavoritosEmptyState(
                            onExploreClick = onNavigateToInicio
                        )
                    }
                    }
                    else -> {
                        items(uiState.favoritos) { cafeteriaDetalle ->
                            CafeteriaCard(
                                cafeteria = cafeteriaDetalle.cafeteria,
                                onClick = { 
                                    viewModel.seleccionarCafeteria(cafeteriaDetalle)
                                },
                                isGuardado = favoritosIds.contains(cafeteriaDetalle.cafeteria.id),
                                onToggleGuardar = { viewModel.toggleFavorito(cafeteriaDetalle.cafeteria.id) }
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
                currentRoute = "favoritos",
                onNavigate = { route ->
                    when (route) {
                        "inicio" -> onNavigateToInicio()
                        "favoritos" -> { /* Ya estamos aquí */ }
                        "mas" -> onNavigateToMas()
                    }
                    },
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

/**
 * Header de la pantalla de Favoritos
 */
@Composable
private fun FavoritosHeader(
    onMapaClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 8.dp, end = 16.dp, top = 16.dp, bottom = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
    ) {
        // Texto del header
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = "Favoritos",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = CoffeeTripColors.bgSurface
            )
            Text(
                text = "Tus cafés preferidos",
                fontSize = 14.sp,
                color = CoffeeTripColors.darkGray,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
        
        // Botón del mapa
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            CoffeeTripColors.accentBrown,
                            CoffeeTripColors.activeBrown
                        )
                    )
                )
                .clickable { onMapaClick() },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Filled.Map,
                contentDescription = "Ver mapa",
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

/**
 * Estado vacío cuando no hay favoritos
 */
@Composable
private fun FavoritosEmptyState(
    onExploreClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Ícono de favoritos vacío
        Card(
            modifier = Modifier.size(96.dp),
            shape = CircleShape,
            colors = CardDefaults.cardColors(containerColor = CoffeeTripColors.bgSurface),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.Bookmark,
                    contentDescription = "Favoritos vacíos",
                    tint = CoffeeTripColors.accentBrown,
                    modifier = Modifier.size(48.dp)
                )
            }
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Text(
            text = "No tienes favoritos aún",
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            color = CoffeeTripColors.bgSurface
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            text = "Agrega cafés a tus favoritos para encontrarlos fácilmente",
            fontSize = 14.sp,
            color = CoffeeTripColors.darkGray,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        
        // Botón para explorar
        Button(
            onClick = onExploreClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = CoffeeTripColors.accentBrown
            ),
            shape = RoundedCornerShape(24.dp),
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            Text(
                text = "Explorar cafés",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )
        }
    }
}

/**
 * Lista de cafeterías favoritas
 */
@Composable
private fun FavoritosList(
    favoritos: List<CafeteriaDetalle>,
    onCafeteriaClick: (CafeteriaDetalle) -> Unit,
    onRemoveFavorite: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 24.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        items(favoritos) { cafeteriaDetalle ->
            CafeteriaCard(
                cafeteria = cafeteriaDetalle.cafeteria,
                onClick = { onCafeteriaClick(cafeteriaDetalle) },
                isGuardado = true, // Siempre true en favoritos
                onToggleGuardar = { onRemoveFavorite(cafeteriaDetalle.cafeteria.id) }
            )
        }
    }
}


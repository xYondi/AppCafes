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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.LocationOn
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
    onCallClick: () -> Unit,
    onDirectionsClick: () -> Unit,
    onShareClick: () -> Unit,
    onToggleFavorite: (String) -> Unit,
    viewModel: FavoritosViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    
    Box(modifier = Modifier.fillMaxSize()) {
        // Contenido principal
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(CoffeeTripColors.bgBase)
        ) {
            // Header
            FavoritosHeader(
                onSortClick = { /* Implementar ordenamiento */ }
            )
            
            // Contenido principal
            Box(modifier = Modifier.weight(1f)) {
                when {
                    uiState.isLoading -> {
                        // Estado de carga
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(
                                color = CoffeeTripColors.bgSurface,
                                modifier = Modifier.size(48.dp)
                            )
                        }
                    }
                    
                    uiState.isEmpty -> {
                        // Estado vacío
                        FavoritosEmptyState(
                            onExploreClick = onNavigateToInicio
                        )
                    }
                    
                    else -> {
                        // Lista de favoritos
                        FavoritosList(
                            favoritos = uiState.favoritos,
                            onCafeteriaClick = onCafeteriaClick,
                            onRemoveFavorite = { cafeteriaId ->
                                onToggleFavorite(cafeteriaId)
                            }
                        )
                    }
                }
            }
            
            // Barra de navegación
            DockNavigationBar(
                currentRoute = "favoritos",
                onNavigate = { route ->
                    when (route) {
                        "inicio" -> onNavigateToInicio()
                        "favoritos" -> { /* Ya estamos aquí */ }
                        "mas" -> onNavigateToMas()
                    }
                }
            )
        }
    }
}

/**
 * Header de la pantalla de Favoritos
 */
@Composable
private fun FavoritosHeader(
    onSortClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 32.dp)
            .statusBarsPadding(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
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
        
        // Botón para ordenar
        Card(
            modifier = Modifier
                .size(48.dp)
                .clickable { onSortClick() },
            shape = CircleShape,
            colors = CardDefaults.cardColors(containerColor = CoffeeTripColors.bgSurface),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.FilterList,
                    contentDescription = "Ordenar",
                    tint = CoffeeTripColors.bgBase,
                    modifier = Modifier.size(24.dp)
                )
            }
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
            FavoritoCard(
                cafeteriaDetalle = cafeteriaDetalle,
                onClick = { onCafeteriaClick(cafeteriaDetalle) },
                onRemoveClick = { onRemoveFavorite(cafeteriaDetalle.cafeteria.id) }
            )
        }
    }
}

/**
 * Tarjeta individual de favorito
 */
@Composable
private fun FavoritoCard(
    cafeteriaDetalle: CafeteriaDetalle,
    onClick: () -> Unit,
    onRemoveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val cafeteria = cafeteriaDetalle.cafeteria
    
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = CoffeeTripColors.bgSurface),
        elevation = CardDefaults.cardElevation(defaultElevation = 12.dp)
    ) {
        Column {
            // Imagen de la cafetería
            Box {
                AsyncImage(
                    model = cafeteria.imagenUrl,
                    contentDescription = "Imagen de ${cafeteria.nombre}",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp)
                        .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)),
                    contentScale = ContentScale.Crop
                )
                
                // Botón para quitar de favoritos
                Card(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(12.dp)
                        .size(32.dp)
                        .clickable { onRemoveClick() },
                    shape = CircleShape,
                    colors = CardDefaults.cardColors(containerColor = Color.Red),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Delete,
                            contentDescription = "Quitar de favoritos",
                            tint = Color.White,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }
            
            // Información de la cafetería
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Nombre
                Text(
                    text = cafeteria.nombre,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = CoffeeTripColors.bgBase,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                
                // Descripción
                Text(
                    text = cafeteria.descripcion,
                    fontSize = 14.sp,
                    color = CoffeeTripColors.darkGray,
                    lineHeight = 18.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                
                // Rating y estado
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Rating con estrellas
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        repeat(5) { index ->
                            Icon(
                                imageVector = Icons.Filled.Star,
                                contentDescription = null,
                                tint = if (index < cafeteria.calificacion.toInt()) IconColors.starIcon else IconColors.starIcon.copy(alpha = 0.3f),
                                modifier = Modifier.size(16.dp)
                            )
                        }
                        Text(
                            text = "${cafeteria.calificacion}",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = CoffeeTripColors.bgBase,
                            modifier = Modifier.padding(start = 4.dp)
                        )
                    }
                    
                    // Estado
                    Card(
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = when (cafeteria.estado) {
                                com.coffeetrip.domain.model.EstadoCafeteria.ABIERTO -> CoffeeTripColors.bgBase
                                com.coffeetrip.domain.model.EstadoCafeteria.CERRANDO_PRONTO -> EstadoColors.cerrandoPronto
                                com.coffeetrip.domain.model.EstadoCafeteria.CERRADO -> EstadoColors.cerrado
                            }
                        )
                    ) {
                        Text(
                            text = when (cafeteria.estado) {
                                com.coffeetrip.domain.model.EstadoCafeteria.ABIERTO -> "Abierto"
                                com.coffeetrip.domain.model.EstadoCafeteria.CERRANDO_PRONTO -> "Cerrando"
                                com.coffeetrip.domain.model.EstadoCafeteria.CERRADO -> "Cerrado"
                            },
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = CoffeeTripColors.bgSurface,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }
                
                // Servicios
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    if (cafeteria.servicios.contains(com.coffeetrip.domain.model.ServicioCafeteria.WIFI)) {
                        Icon(
                            imageVector = Icons.Filled.Wifi,
                            contentDescription = "WiFi",
                            tint = CoffeeTripColors.darkGray,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                    
                    if (cafeteria.servicios.contains(com.coffeetrip.domain.model.ServicioCafeteria.PET_FRIENDLY)) {
                        Icon(
                            imageVector = Icons.Filled.Pets,
                            contentDescription = "Pet Friendly",
                            tint = CoffeeTripColors.darkGray,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                    
                    Spacer(modifier = Modifier.weight(1f))
                    
                    // Distancia
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.LocationOn,
                            contentDescription = "Distancia",
                            tint = CoffeeTripColors.darkGray,
                            modifier = Modifier.size(14.dp)
                        )
                        Text(
                            text = "${String.format("%.1f km", cafeteria.distancia)}",
                            fontSize = 12.sp,
                            color = CoffeeTripColors.darkGray,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
    }
}

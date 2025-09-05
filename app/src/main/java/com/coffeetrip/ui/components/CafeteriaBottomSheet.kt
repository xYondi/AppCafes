package com.coffeetrip.ui.components

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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Directions
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.coffeetrip.domain.model.CafeteriaDetalle
import com.coffeetrip.domain.model.Resena
import com.coffeetrip.ui.theme.CoffeeTripColors
import com.coffeetrip.ui.theme.EstadoColors
import com.coffeetrip.ui.theme.IconColors

/**
 * Componente que muestra la información de una cafetería en estado colapsado
 * Información básica: imagen, nombre, calificación y botones de acción
 */
@Composable
fun CafeteriaBottomSheetCollapsed(
    cafeteriaDetalle: CafeteriaDetalle,
    onCallClick: () -> Unit,
    onDirectionsClick: () -> Unit,
    onToggleFavorite: () -> Unit,
    onExpandClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val cafeteria = cafeteriaDetalle.cafeteria
    
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 0.dp, vertical = 0.dp),
        shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp, bottomStart = 0.dp, bottomEnd = 0.dp),
        colors = CardDefaults.cardColors(containerColor = CoffeeTripColors.bgBase),
        elevation = CardDefaults.cardElevation(defaultElevation = 16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp, bottom = 16.dp, start = 20.dp, end = 20.dp)
        ) {
            // Drag handle visual
            Box(
                modifier = Modifier
                    .width(60.dp)
                    .height(4.dp)
                    .background(
                        color = CoffeeTripColors.darkGray.copy(alpha = 0.3f),
                        shape = RoundedCornerShape(2.dp)
                    )
                    .align(Alignment.CenterHorizontally)
            )
            
            Spacer(modifier = Modifier.height(20.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Imagen de la cafetería con sombra mejorada
                Card(
                    modifier = Modifier.size(90.dp),
                    shape = RoundedCornerShape(20.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    AsyncImage(
                        model = cafeteria.imagenUrl,
                        contentDescription = "Imagen de ${cafeteria.nombre}",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }
                
                Spacer(modifier = Modifier.width(20.dp))
                
                // Información básica
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = cafeteria.nombre,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = CoffeeTripColors.bgSurface,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    
                    Spacer(modifier = Modifier.height(6.dp))
                    
                    // Calificación con mejores íconos
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(2.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Star,
                                contentDescription = "Calificación",
                                tint = IconColors.starIcon,
                                modifier = Modifier.size(18.dp)
                            )
                            Text(
                                text = "${cafeteria.calificacion}",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = CoffeeTripColors.bgSurface
                            )
                        }
                        
                        Text(
                            text = "•",
                            fontSize = 14.sp,
                            color = CoffeeTripColors.darkGray,
                            fontWeight = FontWeight.Bold
                        )
                        
                        Text(
                            text = "${String.format("%.1f km", cafeteria.distancia)}",
                            fontSize = 14.sp,
                            color = CoffeeTripColors.darkGray,
                            fontWeight = FontWeight.Medium
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    // Estado con mejor diseño
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .background(
                                    color = when (cafeteria.estado) {
                                        com.coffeetrip.domain.model.EstadoCafeteria.ABIERTO -> CoffeeTripColors.bgBase
                                        com.coffeetrip.domain.model.EstadoCafeteria.CERRANDO_PRONTO -> EstadoColors.cerrandoPronto
                                        com.coffeetrip.domain.model.EstadoCafeteria.CERRADO -> EstadoColors.cerrado
                                    },
                                    shape = CircleShape
                                )
                                .padding(horizontal = 12.dp, vertical = 6.dp)
                        ) {
                            Text(
                                text = when (cafeteria.estado) {
                                    com.coffeetrip.domain.model.EstadoCafeteria.ABIERTO -> "Abierto"
                                    com.coffeetrip.domain.model.EstadoCafeteria.CERRANDO_PRONTO -> "Cerrando"
                                    com.coffeetrip.domain.model.EstadoCafeteria.CERRADO -> "Cerrado"
                                },
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = CoffeeTripColors.bgSurface
                            )
                        }
                        
                        // Servicios como íconos pequeños
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
                    }
                }
                
                Spacer(modifier = Modifier.width(16.dp))
                
                // Botones de acción rediseñados
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Botón de llamar
                    Card(
                        modifier = Modifier
                            .size(48.dp)
                            .clickable { onCallClick() },
                        shape = CircleShape,
                        colors = CardDefaults.cardColors(containerColor = CoffeeTripColors.accentBrown),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Call,
                                contentDescription = "Llamar",
                                tint = Color.White,
                                modifier = Modifier.size(22.dp)
                            )
                        }
                    }
                    
                    // Botón de direcciones
                    Card(
                        modifier = Modifier
                            .size(48.dp)
                            .clickable { onDirectionsClick() },
                        shape = CircleShape,
                        colors = CardDefaults.cardColors(containerColor = CoffeeTripColors.activeBrown),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Directions,
                                contentDescription = "Direcciones",
                                tint = Color.White,
                                modifier = Modifier.size(22.dp)
                            )
                        }
                    }
                    
                    // Botón de guardar
                    IconButton(
                        onClick = onToggleFavorite,
                        modifier = Modifier.size(48.dp)
                    ) {
                        Icon(
                            imageVector = if (cafeteriaDetalle.esFavorito) Icons.Filled.Bookmark else Icons.Outlined.BookmarkBorder,
                            contentDescription = if (cafeteriaDetalle.esFavorito) "Quitar de guardados" else "Guardar",
                            tint = if (cafeteriaDetalle.esFavorito) CoffeeTripColors.activeBrown else CoffeeTripColors.darkGray,
                            modifier = Modifier.size(26.dp)
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // Descripción breve
            Text(
                text = cafeteria.descripcion,
                fontSize = 14.sp,
                color = CoffeeTripColors.darkGray,
                lineHeight = 18.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            
        }
    }
}

/**
 * Componente que muestra toda la información detallada de una cafetería
 * Estado expandido con información completa
 */
@Composable
fun CafeteriaBottomSheetExpanded(
    cafeteriaDetalle: CafeteriaDetalle,
    onCallClick: () -> Unit,
    onDirectionsClick: () -> Unit,
    onShareClick: () -> Unit,
    onToggleFavorite: () -> Unit,
    onBackClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val cafeteria = cafeteriaDetalle.cafeteria
    
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(CoffeeTripColors.bgBase),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Header con botón de vuelta
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Card(
                    modifier = Modifier
                        .size(40.dp)
                        .clickable { onBackClick() },
                    shape = CircleShape,
                    colors = CardDefaults.cardColors(containerColor = CoffeeTripColors.darkGray.copy(alpha = 0.1f)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver",
                            tint = CoffeeTripColors.bgSurface,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
                
                Text(
                    text = "Detalles",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = CoffeeTripColors.bgSurface
                )
                
                // Botones de acción del header
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    IconButton(
                        onClick = onToggleFavorite,
                        modifier = Modifier.size(40.dp)
                    ) {
                        Icon(
                            imageVector = if (cafeteriaDetalle.esFavorito) Icons.Filled.Bookmark else Icons.Outlined.BookmarkBorder,
                            contentDescription = "Guardado",
                            tint = if (cafeteriaDetalle.esFavorito) CoffeeTripColors.activeBrown else CoffeeTripColors.darkGray,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    
                    IconButton(
                        onClick = onShareClick,
                        modifier = Modifier.size(40.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Share,
                            contentDescription = "Compartir",
                            tint = CoffeeTripColors.darkGray,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }
        }
        
        // Imagen principal
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                AsyncImage(
                    model = cafeteria.imagenUrl,
                    contentDescription = "Imagen principal de ${cafeteria.nombre}",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp),
                    contentScale = ContentScale.Crop
                )
            }
        }
        
        // Información básica
        item {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Título y calificación
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = cafeteria.nombre,
                            fontSize = 26.sp,
                            fontWeight = FontWeight.Bold,
                            color = CoffeeTripColors.bgSurface
                        )
                        
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        // Calificación con estrellas
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            repeat(5) { index ->
                                Icon(
                                    imageVector = Icons.Filled.Star,
                                    contentDescription = null,
                                    tint = if (index < cafeteria.calificacion.toInt()) IconColors.starIcon else IconColors.serviceIcons.copy(alpha = 0.3f),
                                    modifier = Modifier.size(22.dp)
                                )
                            }
                            Text(
                                text = "${cafeteria.calificacion}",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = CoffeeTripColors.bgSurface,
                                modifier = Modifier.padding(start = 8.dp)
                            )
                        }
                        
                        Spacer(modifier = Modifier.height(12.dp))
                        
                        // Estado y distancia
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .background(
                                        color = when (cafeteria.estado) {
                                            com.coffeetrip.domain.model.EstadoCafeteria.ABIERTO -> Color(0xFF10B981)
                                            com.coffeetrip.domain.model.EstadoCafeteria.CERRANDO_PRONTO -> Color(0xFFF59E0B)
                                            com.coffeetrip.domain.model.EstadoCafeteria.CERRADO -> Color(0xFFEF4444)
                                        },
                                        shape = CircleShape
                                    )
                                    .padding(horizontal = 12.dp, vertical = 6.dp)
                            ) {
                                Text(
                                    text = when (cafeteria.estado) {
                                        com.coffeetrip.domain.model.EstadoCafeteria.ABIERTO -> "Abierto"
                                        com.coffeetrip.domain.model.EstadoCafeteria.CERRANDO_PRONTO -> "Cerrando pronto"
                                        com.coffeetrip.domain.model.EstadoCafeteria.CERRADO -> "Cerrado"
                                    },
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = CoffeeTripColors.bgSurface
                                )
                            }
                            
                            Text(
                                text = "• ${String.format("%.1f km", cafeteria.distancia)}",
                                fontSize = 14.sp,
                                color = CoffeeTripColors.darkGray
                            )
                        }
                    }
                    
                    // Botones de acción verticales
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Card(
                            modifier = Modifier
                                .size(56.dp)
                                .clickable { onCallClick() },
                            shape = CircleShape,
                            colors = CardDefaults.cardColors(containerColor = CoffeeTripColors.accentBrown),
                            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
                        ) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Call,
                                    contentDescription = "Llamar",
                                    tint = Color.White,
                                    modifier = Modifier.size(26.dp)
                                )
                            }
                        }
                        
                        Card(
                            modifier = Modifier
                                .size(56.dp)
                                .clickable { onDirectionsClick() },
                            shape = CircleShape,
                            colors = CardDefaults.cardColors(containerColor = CoffeeTripColors.activeBrown),
                            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
                        ) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Directions,
                                    contentDescription = "Direcciones",
                                    tint = Color.White,
                                    modifier = Modifier.size(26.dp)
                                )
                            }
                        }
                    }
                }
                
                // Descripción
                Text(
                    text = cafeteria.descripcion,
                    fontSize = 15.sp,
                    color = CoffeeTripColors.darkGray,
                    lineHeight = 22.sp
                )
            }
        }
        
        // Información adicional
        item {
            InformacionSection(cafeteriaDetalle = cafeteriaDetalle)
        }
        
        // Galería
        if (cafeteriaDetalle.galeria.isNotEmpty()) {
            item {
                GaleriaSection(imagenes = cafeteriaDetalle.galeria)
            }
        }
        
        // Reseñas
        if (cafeteriaDetalle.resenas.isNotEmpty()) {
            item {
                ResenasSection(resenas = cafeteriaDetalle.resenas.take(3))
            }
        }
    }
}

/**
 * Sección de información (horarios, dirección, teléfono)
 */
@Composable
private fun InformacionSection(
    cafeteriaDetalle: CafeteriaDetalle,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = CoffeeTripColors.bgBase),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Text(
                text = "Información",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = CoffeeTripColors.bgSurface
            )
            
            // Horarios
            InformacionItem(
                icon = Icons.Filled.AccessTime,
                titulo = "Horario",
                contenido = cafeteriaDetalle.informacionAdicional.horarioTexto
            )
            
            // Dirección
            InformacionItem(
                icon = Icons.Filled.LocationOn,
                titulo = "Dirección",
                contenido = cafeteriaDetalle.cafeteria.ubicacion.direccion
            )
            
            // Teléfono
            cafeteriaDetalle.telefono?.let { telefono ->
                InformacionItem(
                    icon = Icons.Filled.Call,
                    titulo = "Teléfono",
                    contenido = telefono
                )
            }
        }
    }
}

@Composable
private fun InformacionItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    titulo: String,
    contenido: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Card(
            modifier = Modifier.size(44.dp),
            shape = CircleShape,
            colors = CardDefaults.cardColors(containerColor = CoffeeTripColors.bgSurface.copy(alpha = 0.1f))
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = titulo,
                    tint = CoffeeTripColors.bgSurface,
                    modifier = Modifier.size(22.dp)
                )
            }
        }
        
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = titulo,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = CoffeeTripColors.bgSurface
            )
            Text(
                text = contenido,
                fontSize = 14.sp,
                color = CoffeeTripColors.darkGray,
                lineHeight = 18.sp
            )
        }
    }
}

/**
 * Sección de galería de imágenes mejorada
 */
@Composable
private fun GaleriaSection(
    imagenes: List<String>,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = CoffeeTripColors.bgBase),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Text(
                text = "Galería",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = CoffeeTripColors.bgSurface,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.height(280.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(imagenes.take(4)) { imagenUrl ->
                    Card(
                        shape = RoundedCornerShape(20.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                    ) {
                        AsyncImage(
                            model = imagenUrl,
                            contentDescription = "Imagen de la galería",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(130.dp),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }
        }
    }
}

/**
 * Sección de reseñas rediseñada
 */
@Composable
private fun ResenasSection(
    resenas: List<Resena>,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = CoffeeTripColors.bgBase),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Reseñas",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = CoffeeTripColors.bgSurface
                )
                
                Text(
                    text = "Ver todas",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = CoffeeTripColors.accentBrown,
                    modifier = Modifier.clickable { /* Ver todas las reseñas */ }
                )
            }
            
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                resenas.forEach { resena ->
                    ResenaCard(resena = resena)
                }
            }
        }
    }
}

/**
 * Tarjeta individual de reseña rediseñada
 */
@Composable
private fun ResenaCard(
    resena: Resena,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Header con avatar, nombre y calificación
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Avatar más grande y colorido
                Card(
                    modifier = Modifier.size(50.dp),
                    shape = CircleShape,
                    colors = CardDefaults.cardColors(
                        containerColor = when (resena.usuario.take(1).uppercase()) {
                            "M" -> Color(0xFF6366F1)
                            "C" -> Color(0xFF10B981)
                            "A" -> Color(0xFFF59E0B)
                            else -> CoffeeTripColors.accentBrown
                        }
                    )
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = resena.usuario.take(1).uppercase(),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
                
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = resena.usuario,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = CoffeeTripColors.bgSurface
                    )
                    
                    Text(
                        text = "Hace ${resena.fecha}",
                        fontSize = 14.sp,
                        color = CoffeeTripColors.darkGray.copy(alpha = 0.7f),
                        fontWeight = FontWeight.Medium
                    )
                }
                
                // Rating en círculo
                Card(
                    modifier = Modifier.size(44.dp),
                    shape = CircleShape,
                    colors = CardDefaults.cardColors(containerColor = IconColors.starIcon.copy(alpha = 0.1f))
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "${resena.calificacion.toInt()}",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = IconColors.starIcon
                        )
                    }
                }
            }
            
            // Estrellas visuales
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                repeat(5) { index ->
                    Icon(
                        imageVector = Icons.Filled.Star,
                        contentDescription = null,
                        tint = if (index < resena.calificacion.toInt()) IconColors.starIcon else IconColors.starIcon.copy(alpha = 0.2f),
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
            
            // Comentario con mejor formato
            Text(
                text = resena.comentario,
                fontSize = 16.sp,
                color = CoffeeTripColors.darkGray,
                lineHeight = 24.sp,
                fontWeight = FontWeight.Normal
            )
        }
    }
}

/**
 * Componente unificado del bottom sheet que muestra toda la información
 * Se adapta naturalmente al comportamiento de arrastre del ModalBottomSheet
 */
@Composable
fun CafeteriaBottomSheetUnified(
    cafeteriaDetalle: CafeteriaDetalle,
    onCallClick: () -> Unit,
    onDirectionsClick: () -> Unit,
    onShareClick: () -> Unit,
    onToggleFavorite: () -> Unit,
    modifier: Modifier = Modifier
) {
    val cafeteria = cafeteriaDetalle.cafeteria
    
    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .background(CoffeeTripColors.bgBase),
        contentPadding = PaddingValues(
            start = 20.dp,
            end = 20.dp,
            top = 20.dp, // Padding normal ya que la altura está limitada
            bottom = 16.dp
        ),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        // Header con imagen destacada
        item {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Imagen principal centrada y más grande
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    shape = RoundedCornerShape(24.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 12.dp)
                ) {
                    Box {
                        AsyncImage(
                            model = cafeteria.imagenUrl,
                            contentDescription = "Imagen de ${cafeteria.nombre}",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                        
                        // Overlay con estado en la esquina superior derecha
                        Box(
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(16.dp)
                                .background(
                                    color = when (cafeteria.estado) {
                                        com.coffeetrip.domain.model.EstadoCafeteria.ABIERTO -> CoffeeTripColors.bgBase
                                        com.coffeetrip.domain.model.EstadoCafeteria.CERRANDO_PRONTO -> EstadoColors.cerrandoPronto
                                        com.coffeetrip.domain.model.EstadoCafeteria.CERRADO -> EstadoColors.cerrado
                                    },
                                    shape = CircleShape
                                )
                                .padding(horizontal = 12.dp, vertical = 6.dp)
                        ) {
                            Text(
                                text = when (cafeteria.estado) {
                                    com.coffeetrip.domain.model.EstadoCafeteria.ABIERTO -> "Abierto"
                                    com.coffeetrip.domain.model.EstadoCafeteria.CERRANDO_PRONTO -> "Cerrando"
                                    com.coffeetrip.domain.model.EstadoCafeteria.CERRADO -> "Cerrado"
                                },
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = CoffeeTripColors.bgSurface
                            )
                        }
                        
                        // Guardado en esquina superior izquierda
                        IconButton(
                            onClick = onToggleFavorite,
                            modifier = Modifier
                                .align(Alignment.TopStart)
                                .padding(12.dp)
                                .background(
                                    color = Color.Black.copy(alpha = 0.3f),
                                    shape = CircleShape
                                )
                                .size(40.dp)
                        ) {
                            Icon(
                                imageVector = if (cafeteriaDetalle.esFavorito) Icons.Filled.Bookmark else Icons.Outlined.BookmarkBorder,
                                contentDescription = if (cafeteriaDetalle.esFavorito) "Quitar de guardados" else "Guardar",
                                tint = if (cafeteriaDetalle.esFavorito) CoffeeTripColors.activeBrown else Color.White,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                }
            }
        }
        
        // Información principal
        item {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Título
                Text(
                    text = cafeteria.nombre,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = CoffeeTripColors.bgSurface,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                
                // Rating y distancia centrados
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Estrellas de rating
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(2.dp)
                    ) {
                        repeat(5) { index ->
                            Icon(
                                imageVector = Icons.Filled.Star,
                                contentDescription = null,
                                tint = if (index < cafeteria.calificacion.toInt()) IconColors.starIcon else IconColors.serviceIcons.copy(alpha = 0.3f),
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                    
                    Spacer(modifier = Modifier.width(8.dp))
                    
                    Text(
                        text = "${cafeteria.calificacion}",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = CoffeeTripColors.bgSurface
                    )
                    
                    Spacer(modifier = Modifier.width(16.dp))
                    
                    // Distancia
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.LocationOn,
                            contentDescription = "Distancia",
                            tint = CoffeeTripColors.darkGray,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = "${String.format("%.1f km", cafeteria.distancia)}",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = CoffeeTripColors.darkGray
                        )
                    }
                }
                
                // Descripción centrada
                Text(
                    text = cafeteria.descripcion,
                    fontSize = 16.sp,
                    color = CoffeeTripColors.darkGray,
                    lineHeight = 22.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
        
        // Botones de acción prominentes
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Botón de llamar
                Card(
                    modifier = Modifier
                        .weight(1f)
                        .height(56.dp)
                        .clickable { onCallClick() },
                    shape = RoundedCornerShape(28.dp),
                    colors = CardDefaults.cardColors(containerColor = CoffeeTripColors.accentBrown),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Call,
                            contentDescription = "Llamar",
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Llamar",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
                
                Spacer(modifier = Modifier.width(12.dp))
                
                // Botón de direcciones
                Card(
                    modifier = Modifier
                        .weight(1f)
                        .height(56.dp)
                        .clickable { onDirectionsClick() },
                    shape = RoundedCornerShape(28.dp),
                    colors = CardDefaults.cardColors(containerColor = CoffeeTripColors.activeBrown),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Directions,
                            contentDescription = "Direcciones",
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Cómo llegar",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
            }
        }
        
        // Información adicional (aparece al arrastrar)
        item {
            InformacionSection(cafeteriaDetalle = cafeteriaDetalle)
        }
        
        // Galería (aparece al arrastrar)
        if (cafeteriaDetalle.galeria.isNotEmpty()) {
            item {
                GaleriaSection(imagenes = cafeteriaDetalle.galeria)
            }
        }
        
        // Reseñas (aparece al arrastrar)
        if (cafeteriaDetalle.resenas.isNotEmpty()) {
            item {
                ResenasSection(resenas = cafeteriaDetalle.resenas.take(3))
            }
        }
        
        // Botón de compartir al final
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onShareClick() },
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = CoffeeTripColors.bgSurface.copy(alpha = 0.1f)),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Filled.Share,
                        contentDescription = "Compartir",
                        tint = CoffeeTripColors.bgSurface,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Compartir cafetería",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = CoffeeTripColors.bgSurface
                    )
                }
            }
        }
    }
}

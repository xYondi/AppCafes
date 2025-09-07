package com.coffeetrip.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.coffeetrip.domain.model.Cafeteria
import com.coffeetrip.domain.model.EstadoCafeteria
import com.coffeetrip.ui.theme.CoffeeTripColors
import com.coffeetrip.ui.theme.EstadoColors
import com.coffeetrip.ui.theme.IconColors

/**
 * Componente reutilizable para mostrar una tarjeta de cafetería
 * Basado en el diseño de Figma INICIO V2
 */
@Composable
fun CafeteriaCard(
    cafeteria: Cafeteria,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isGuardado: Boolean = false,
    onToggleGuardar: () -> Unit = {}
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = CoffeeTripColors.bgSurface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp // Sombra sutil
        )
    ) {
        Box {
            Column {
                // Imagen de la cafetería
                AsyncImage(
                    model = cafeteria.imagenUrl,
                    contentDescription = "Imagen de ${cafeteria.nombre}",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp)
                        .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)),
                    contentScale = ContentScale.Crop
                )
                
                // Contenido de la tarjeta
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    // Nombre de la cafetería
                    Text(
                        text = cafeteria.nombre,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = CoffeeTripColors.bgBase,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    
                    Spacer(modifier = Modifier.height(4.dp))
                    
                    // Descripción
                    Text(
                        text = cafeteria.descripcion,
                        fontSize = 13.sp,
                        color = IconColors.descriptionText,
                        lineHeight = 20.sp
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // Fila con calificación, estado y servicios
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Calificación y estado
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            // Ícono de estrella y calificación (única estrella visible)
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Star,
                                    contentDescription = "Calificación",
                                    tint = IconColors.starIcon,
                                    modifier = Modifier.size(16.dp)
                                )
                                Text(
                                    text = cafeteria.calificacion.toString(),
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = CoffeeTripColors.bgBase
                                )
                            }
                            
                            // Estado de la cafetería
                            Box(
                                modifier = Modifier
                                    .background(
                                        color = when (cafeteria.estado) {
                                            EstadoCafeteria.ABIERTO -> CoffeeTripColors.bgBase
                                            EstadoCafeteria.CERRANDO_PRONTO -> EstadoColors.cerrandoPronto
                                            EstadoCafeteria.CERRADO -> EstadoColors.cerrado
                                        },
                                        shape = CircleShape
                                    )
                                    .padding(horizontal = 8.dp, vertical = 2.dp)
                            ) {
                                Text(
                                    text = when (cafeteria.estado) {
                                        EstadoCafeteria.ABIERTO -> "Abierto"
                                        EstadoCafeteria.CERRANDO_PRONTO -> "Cerrando"
                                        EstadoCafeteria.CERRADO -> "Cerrado"
                                    },
                                    fontSize = 12.sp,
                                    color = CoffeeTripColors.bgSurface
                                )
                            }
                        }
                        
                        // Servicios (Pet Friendly y WiFi)
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Icono de Pet Friendly
                            if (cafeteria.servicios.contains(com.coffeetrip.domain.model.ServicioCafeteria.PET_FRIENDLY)) {
                                Icon(
                                    imageVector = Icons.Default.Pets,
                                    contentDescription = "Pet Friendly",
                                    tint = IconColors.descriptionText, // Color beige
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                            
                            // Icono de WiFi
                            if (cafeteria.servicios.contains(com.coffeetrip.domain.model.ServicioCafeteria.WIFI)) {
                                Icon(
                                    imageVector = Icons.Default.Wifi,
                                    contentDescription = "WiFi disponible",
                                    tint = IconColors.wifiIcon,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        }
                    }
                }
            }
            
            // Icono de guardar en la esquina superior derecha (corazón con trazo)
            IconButton(
                onClick = onToggleGuardar,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(16.dp)
                    .size(36.dp)
            ) {
                Icon(
                    imageVector = if (isGuardado) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                    contentDescription = if (isGuardado) "Quitar de favoritos" else "Agregar a favoritos",
                    tint = if (isGuardado) Color.Red else Color(0xFF440F0E),
                    modifier = Modifier.size(32.dp)
                )
            }
        }
    }
}
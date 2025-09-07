package com.coffeetrip.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.coffeetrip.ui.theme.CoffeeTripColors
import com.coffeetrip.ui.theme.IconColors

@Composable
fun DockNavigationBar(
    currentRoute: String = "inicio",
    onNavigate: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val dockHeight = 74.dp
    
    // Animaciones específicas para favoritos
    val favoritosScale by animateFloatAsState(
        targetValue = if (currentRoute == "favoritos") 1.2f else 1f,
        animationSpec = spring(
            dampingRatio = 0.6f,
            stiffness = 400f
        ),
        label = "favoritosScale"
    )
    
    val favoritosColor by animateFloatAsState(
        targetValue = if (currentRoute == "favoritos") 1f else 0f,
        animationSpec = tween(durationMillis = 300),
        label = "favoritosColor"
    )
    
    // Animación sutil para el ícono de inicio
    val inicioScale by animateFloatAsState(
        targetValue = if (currentRoute == "inicio") 1.05f else 1f,
        animationSpec = spring(
            dampingRatio = 0.7f,
            stiffness = 300f
        ),
        label = "inicioScale"
    )
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(44.dp),
        colors = CardDefaults.cardColors(containerColor = CoffeeTripColors.navbarBg),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(dockHeight),
            contentAlignment = Alignment.Center
        ) {
            // Píldora activa para Inicio
            if (currentRoute == "inicio") {
                Row(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(start = 24.dp, top = ((dockHeight - 56.dp) / 2))
                        .size(width = 140.dp, height = 56.dp)
                        .clip(RoundedCornerShape(9999.dp))
                        .background(CoffeeTripColors.pillBackground)
                        .clickable { onNavigate("inicio") }
                        .padding(horizontal = 18.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Home,
                        contentDescription = "Inicio",
                        tint = CoffeeTripColors.activeBrown,
                        modifier = Modifier
                            .size(24.dp)
                            .scale(inicioScale)
                    )
                    Text(
                        text = "Inicio",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = CoffeeTripColors.activeBrown
                    )
                }
            } else {
                // Ícono de Inicio inactivo
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Inicio",
                    tint = IconColors.inactiveText,
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(start = 24.dp, top = ((dockHeight - 30.dp) / 2))
                        .size(30.dp)
                        .clickable { onNavigate("inicio") }
                )
            }

            // Píldora activa para Favoritos
            if (currentRoute == "favoritos") {
                Row(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(start = 8.dp)
                        .size(width = 160.dp, height = 56.dp)
                        .clip(RoundedCornerShape(9999.dp))
                        .background(CoffeeTripColors.pillBackground)
                        .clickable { onNavigate("favoritos") }
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "Favoritos",
                        tint = CoffeeTripColors.activeBrown,
                        modifier = Modifier
                            .size(24.dp)
                            .scale(favoritosScale)
                    )
                    Text(
                        text = "Favoritos",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = CoffeeTripColors.activeBrown
                    )
                }
            } else {
                // Ícono de Favoritos inactivo
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "Favoritos",
                    tint = IconColors.inactiveText,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .offset(x = 8.dp)
                        .size(30.dp)
                        .scale(favoritosScale)
                        .clickable { onNavigate("favoritos") }
                )
            }

            Icon(
                imageVector = Icons.Default.Settings,
                contentDescription = "Configuración",
                tint = IconColors.inactiveText,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 32.dp)
                    .size(30.dp)
                    .clickable { onNavigate("configuracion") }
            )
        }
    }
}

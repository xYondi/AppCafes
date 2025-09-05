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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Map
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.coffeetrip.ui.theme.CoffeeTripColors
import com.coffeetrip.ui.theme.IconColors

/**
 * Header de la pantalla de inicio
 * Basado en el diseño de Figma INICIO V2
 */
@Composable
fun InicioHeader(
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
                text = "Cafeterías",
                fontSize = 34.sp,
                fontWeight = FontWeight.Bold,
                style = androidx.compose.ui.text.TextStyle(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color(0xFFEDE8DE), // Beige claro
                            Color(0xFF42210B)  // Café oscuro
                        )
                    )
                ),
                lineHeight = 36.sp
            )
            
            Text(
                text = "Populares",
                fontSize = 34.sp,
                fontWeight = FontWeight.Bold,
                style = androidx.compose.ui.text.TextStyle(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color(0xFFEDE8DE), // Beige claro
                            Color(0xFF42210B)  // Café oscuro
                        )
                    )
                ),
                lineHeight = 36.sp
            )
            
            Spacer(modifier = androidx.compose.ui.Modifier.height(10.dp))
            
            Text(
                text = "Descubre tu próximo lugar favorito",
                fontSize = 16.sp,
                color = CoffeeTripColors.darkGray,
                lineHeight = 22.sp
            )
        }
        
        // Botón del mapa
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(CoffeeTripColors.bgSurface)
                .clickable { onMapaClick() },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Map,
                contentDescription = "Ver mapa",
                tint = IconColors.mapIcon,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

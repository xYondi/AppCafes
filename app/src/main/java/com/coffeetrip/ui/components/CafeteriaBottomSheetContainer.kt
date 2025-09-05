package com.coffeetrip.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.coffeetrip.domain.model.CafeteriaDetalle
import com.coffeetrip.ui.theme.CoffeeTripColors
import kotlinx.coroutines.launch

/**
 * Contenedor principal que maneja el estado del BottomSheet usando ModalBottomSheet
 * No interfiere con la navegación principal de la app
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CafeteriaBottomSheetContainer(
    cafeteriaDetalle: CafeteriaDetalle?,
    onDismiss: () -> Unit,
    onCallClick: () -> Unit,
    onDirectionsClick: () -> Unit,
    onShareClick: () -> Unit,
    onToggleFavorite: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    var isExpanded by remember { mutableStateOf(false) }
    val bottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false
    )
    
    // Contenido principal de la pantalla sin modificaciones
    Box(modifier = modifier.fillMaxSize()) {
        content()
    }
    
    // Modal Bottom Sheet que se muestra cuando hay una cafetería seleccionada
    if (cafeteriaDetalle != null) {
        ModalBottomSheet(
            onDismissRequest = onDismiss,
            sheetState = bottomSheetState,
            shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp),
            containerColor = CoffeeTripColors.bgBase,
            contentColor = CoffeeTripColors.bgSurface,
            dragHandle = {
                BottomSheetDefaults.DragHandle(
                    modifier = Modifier.padding(vertical = 12.dp),
                    color = CoffeeTripColors.darkGray.copy(alpha = 0.4f),
                    width = 60.dp,
                    height = 4.dp
                )
            }
        ) {
            // Contenido del bottom sheet con fondo blanco extendido
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(CoffeeTripColors.bgBase)
                    .windowInsetsPadding(WindowInsets.navigationBars)
            ) {
                CafeteriaBottomSheetContent(
                    cafeteriaDetalle = cafeteriaDetalle,
                    onCallClick = onCallClick,
                    onDirectionsClick = onDirectionsClick,
                    onShareClick = onShareClick,
                    onToggleFavorite = onToggleFavorite,
                    onExpandToggle = { isExpanded = it }
                )
            }
        }
    }
}

/**
 * Contenido principal del bottom sheet unificado con altura limitada
 * El ModalBottomSheet maneja automáticamente la expansión/colapso
 */
@Composable
fun CafeteriaBottomSheetContent(
    cafeteriaDetalle: CafeteriaDetalle,
    onCallClick: () -> Unit,
    onDirectionsClick: () -> Unit,
    onShareClick: () -> Unit,
    onToggleFavorite: () -> Unit,
    onExpandToggle: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val maxHeight = screenHeight * 0.75f // Máximo 75% de la altura de pantalla
    
    // Contenido unificado con altura limitada
    CafeteriaBottomSheetUnified(
        cafeteriaDetalle = cafeteriaDetalle,
        onCallClick = onCallClick,
        onDirectionsClick = onDirectionsClick,
        onShareClick = onShareClick,
        onToggleFavorite = onToggleFavorite,
        modifier = modifier.heightIn(max = maxHeight) // Limita la altura máxima
    )
}

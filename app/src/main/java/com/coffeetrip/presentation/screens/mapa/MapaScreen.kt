package com.coffeetrip.presentation.screens.mapa

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Coffee
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ZoomIn
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.maps.MapsInitializer
import androidx.compose.foundation.Image
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import coil.compose.rememberAsyncImagePainter
import coil.compose.AsyncImage
import com.coffeetrip.ui.components.DockNavigationBar
import com.coffeetrip.ui.components.CafeteriaBottomSheetContainer
import com.coffeetrip.ui.theme.CoffeeTripColors
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

/**
 * Crea un BitmapDescriptor personalizado para el marcador contador
 */
private fun createCounterMarker(count: Int): com.google.android.gms.maps.model.BitmapDescriptor {
    val size = 100 // Tamaño más compacto
    val bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    
    // Colores del tema de la app
    val cafeColor = Color(0xFF42210B).toArgb() // Color café oscuro
    val beigeColor = Color(0xFFF5F5DC).toArgb() // Color beige
    val shadowColor = Color.Black.copy(alpha = 0.15f).toArgb() // Sombra sutil
    
    val paint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.FILL
    }
    
    val centerX = size / 2f
    val centerY = size / 2f
    val radius = size * 0.35f
    
    // Sombra del círculo (offset hacia abajo y derecha)
    paint.color = shadowColor
    canvas.drawCircle(centerX + 2f, centerY + 2f, radius, paint)
    
    // Círculo principal con color café
    paint.color = cafeColor
    canvas.drawCircle(centerX, centerY, radius, paint)
    
    // Borde sutil del círculo
    paint.style = Paint.Style.STROKE
    paint.strokeWidth = 2f
    paint.color = Color.White.copy(alpha = 0.2f).toArgb()
    canvas.drawCircle(centerX, centerY, radius, paint)
    
    // Número en color beige con sombra
    paint.style = Paint.Style.FILL
    paint.textSize = size * 0.35f
    paint.textAlign = Paint.Align.CENTER
    paint.typeface = android.graphics.Typeface.DEFAULT_BOLD
    
    // Sombra del texto
    paint.color = Color.Black.copy(alpha = 0.3f).toArgb()
    val textY = centerY + paint.textSize/3
    canvas.drawText(count.toString(), centerX + 1f, textY + 1f, paint)
    
    // Texto principal
    paint.color = beigeColor
    canvas.drawText(count.toString(), centerX, textY, paint)
    
    return BitmapDescriptorFactory.fromBitmap(bitmap)
}

/**
 * Crea un BitmapDescriptor personalizado para el marcador de cafetería
 */
private fun createCustomCafeteriaMarker(): com.google.android.gms.maps.model.BitmapDescriptor {
    val size = 150 // Tamaño del bitmap (más grande)
    val bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    
    // Colores personalizados
    val cafeColor = Color(0xFF42210B).toArgb() // Color café oscuro
    val beigeColor = Color(0xFFF5F5DC).toArgb() // Color beige
    
    val paint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.FILL
    }
    
    // Dibujar pin con forma de gota
    val path = Path()
    val centerX = size / 2f
    val centerY = size / 2f
    val radius = size * 0.25f
    val tailHeight = size * 0.15f
    
    // Círculo superior
    path.addCircle(centerX, centerY - tailHeight/2, radius, Path.Direction.CW)
    
    // Cola puntiaguda
    path.moveTo(centerX, centerY + radius - tailHeight/2)
    path.lineTo(centerX - radius/3, centerY - tailHeight/2)
    path.lineTo(centerX + radius/3, centerY - tailHeight/2)
    path.close()
    
    // Sombra sutil
    paint.color = Color.Black.copy(alpha = 0.15f).toArgb()
    canvas.drawPath(path, paint)
    
    // Pin principal con color café
    paint.color = cafeColor
    canvas.drawPath(path, paint)
    
    // Borde del pin
    paint.style = Paint.Style.STROKE
    paint.strokeWidth = 2f
    paint.color = Color.Black.copy(alpha = 0.3f).toArgb()
    canvas.drawPath(path, paint)
    
    // Ícono de café en color beige (usando símbolo Unicode)
    paint.style = Paint.Style.FILL
    paint.color = beigeColor
    paint.textSize = size * 0.35f
    paint.textAlign = Paint.Align.CENTER
    paint.typeface = android.graphics.Typeface.DEFAULT_BOLD
    
    // Centrar el ícono en el círculo
    val textY = centerY - tailHeight/2 + paint.textSize/3
    canvas.drawText("☕", centerX, textY, paint)
    
    return BitmapDescriptorFactory.fromBitmap(bitmap)
}

/**
 * Pantalla del mapa que muestra cafeterías en Google Maps
 * Basada en el diseño de Mapa.html
 */
@Composable
fun MapaScreen(
    onNavigate: (String) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MapaViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val selectedFilters by viewModel.selectedFilters.collectAsState()
    val cafeteriaSeleccionada by viewModel.cafeteriaSeleccionada.collectAsState()
    val currentZoom by viewModel.currentZoom.collectAsState()
    var mapLoaded by remember { mutableStateOf(false) }
    var mapsInitialized by remember { mutableStateOf(false) }
    val context = LocalContext.current

    // Inicializar Maps de forma asíncrona y rápida
    LaunchedEffect(Unit) {
        try {
            MapsInitializer.initialize(context, MapsInitializer.Renderer.LATEST) {
                mapsInitialized = true
            }
        } catch (_: Exception) { 
            mapsInitialized = true
        }
    }
    
    CafeteriaBottomSheetContainer(
        cafeteriaDetalle = cafeteriaSeleccionada,
        onDismiss = { viewModel.cerrarBottomSheet() },
        onCallClick = { viewModel.llamarCafeteria() },
        onDirectionsClick = { viewModel.obtenerDirecciones() },
        onShareClick = { viewModel.compartirCafeteria() },
        onToggleFavorite = { viewModel.toggleFavorito() },
        modifier = modifier
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = CoffeeTripColors.bgBase
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                // Mapa estático como placeholder mientras carga el interactivo
                if (!mapsInitialized || !mapLoaded) {
                    // Mapa estático de Google Maps como placeholder
                    val staticMapUrl = "https://maps.googleapis.com/maps/api/staticmap?" +
                            "center=10.4920,-66.8590&" +
                            "zoom=12&" +
                            "size=400x400&" +
                            "maptype=roadmap&" +
                            "markers=color:0x42210B%7C10.4850,-66.8750%7C10.5050,-66.8450%7C10.5100,-66.8350%7C10.4920,-66.8650%7C10.4800,-66.8850&" +
                            "key=AIzaSyAU4EG1Dk2B-4NxVuw49vPCha-R6RDlmJk"
                    
                    Image(
                        painter = rememberAsyncImagePainter(staticMapUrl),
                        contentDescription = "Mapa de cafeterías",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                    
                    // Indicador de carga
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(CoffeeTripColors.bgBase.copy(alpha = 0.3f)),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            color = CoffeeTripColors.bgSurface,
                            modifier = Modifier.size(48.dp)
                        )
                    }
                }
                
                // Google Maps interactivo (solo se muestra cuando está listo)
                if (mapsInitialized) {
                    GoogleMapsView(
                        modifier = Modifier.fillMaxSize(),
                        cafeterias = uiState.cafeteriasFiltradas,
                        onCafeteriaClick = { cafeteria -> 
                            viewModel.seleccionarCafeteria(cafeteria)
                        },
                        onMapLoaded = { mapLoaded = true },
                        onZoomChanged = { zoom -> 
                            viewModel.actualizarZoom(zoom)
                        }
                    )
                }
                
                // Controles superiores con padding para evitar notch
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 50.dp, start = 16.dp, end = 16.dp, bottom = 16.dp)
                        .windowInsetsPadding(WindowInsets.statusBars)
                ) {
                    // Barra de búsqueda con diseño mejorado
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(32.dp),
                        colors = CardDefaults.cardColors(containerColor = CoffeeTripColors.bgSurface),
                        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            // Botón de regreso
                            IconButton(
                                onClick = onBackClick,
                                modifier = Modifier.size(40.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = "Regresar",
                                    tint = CoffeeTripColors.bgBase,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                            
                            // Campo de búsqueda
                            OutlinedTextField(
                                value = searchQuery,
                                onValueChange = { viewModel.actualizarBusqueda(it) },
                                placeholder = {
                                    Text(
                                        text = "Buscar cafeterías",
                                        color = Color.White.copy(alpha = 0.7f),
                                        fontSize = 16.sp // Texto más grande
                                    )
                                },
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Filled.Search,
                                        contentDescription = "Buscar",
                                        tint = Color.White,
                                        modifier = Modifier.size(24.dp) // Ícono más grande
                                    )
                                },
                                trailingIcon = {
                                    IconButton(
                                        onClick = { /* Abrir filtros */ }
                                    ) {
                                        Icon(
                                            imageVector = Icons.Filled.FilterList,
                                            contentDescription = "Filtros",
                                            tint = Color.White,
                                            modifier = Modifier.size(24.dp) // Ícono más grande
                                        )
                                    }
                                },
                                modifier = Modifier.weight(1f),
                                textStyle = androidx.compose.ui.text.TextStyle(
                                    fontSize = 16.sp // Texto más grande para el input
                                ),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = Color.Transparent,
                                    unfocusedBorderColor = Color.Transparent,
                                    focusedTextColor = Color.White,
                                    unfocusedTextColor = Color.White,
                                    focusedContainerColor = Color.Transparent,
                                    unfocusedContainerColor = Color.Transparent,
                                    cursorColor = Color.White
                                ),
                                singleLine = true
                            )
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    // Barra de filtros con diseño mejorado
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(28.dp),
                        colors = CardDefaults.cardColors(containerColor = CoffeeTripColors.bgSurface),
                        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
                    ) {
                        LazyRow(
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            contentPadding = PaddingValues(horizontal = 4.dp)
                        ) {
                            items(getMapFilters()) { filter ->
                                FilterChip(
                                    filter = filter,
                                    isSelected = selectedFilters.contains(filter.name),
                                    onClick = { viewModel.toggleFiltro(filter.name) }
                                )
                            }
                        }
                    }
                }
                

                // Botón de ubicación flotante mejorado
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(20.dp)
                ) {
                    Card(
                        modifier = Modifier
                            .size(56.dp)
                            .clickable { viewModel.centrarEnUbicacionActual() },
                        shape = CircleShape,
                        colors = CardDefaults.cardColors(containerColor = CoffeeTripColors.bgSurface),
                        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Filled.MyLocation,
                                contentDescription = "Mi ubicación",
                                tint = CoffeeTripColors.bgBase,
                                modifier = Modifier.size(28.dp)
                            )
                        }
                    }
                }


                // Overlay de entrada: fade mientras el mapa termina de cargar
                AnimatedVisibility(
                    visible = !mapsInitialized,
                    enter = fadeIn(animationSpec = tween(0)),
                    exit = fadeOut(animationSpec = tween(80)),
                    modifier = Modifier
                        .matchParentSize()
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(CoffeeTripColors.bgBase)
                    ) {}
                }
            }
        }
    }
}



/**
 * Componente para mostrar Google Maps con marcadores de cafeterías
 */
@Composable
private fun GoogleMapsView(
    modifier: Modifier = Modifier,
    cafeterias: List<com.coffeetrip.domain.model.Cafeteria> = emptyList(),
    onCafeteriaClick: (com.coffeetrip.domain.model.Cafeteria) -> Unit = {},
    onMapLoaded: () -> Unit = {},
    onZoomChanged: (Float) -> Unit = {}
) {
    // Centro de Caracas optimizado para mostrar todas las cafeterías
    val caracasCenter = remember { LatLng(10.4918, -66.8590) } // Centro actualizado de Caracas
    
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(caracasCenter, 12.5f) // Zoom ajustado para ver todas las cafeterías
    }

    // Detectar cambios de zoom
    LaunchedEffect(cameraPositionState.position.zoom) {
        onZoomChanged(cameraPositionState.position.zoom)
    }
    
    GoogleMap(
        modifier = modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
        properties = MapProperties(
            mapType = MapType.NORMAL,
            isMyLocationEnabled = false // Temporalmente deshabilitado hasta configurar permisos
        ),
        uiSettings = MapUiSettings(
            zoomControlsEnabled = false,
            compassEnabled = false,
            myLocationButtonEnabled = false
        ),
        onMapLoaded = onMapLoaded
    ) {
        // Mostrar marcadores según el nivel de zoom
        if (cameraPositionState.position.zoom <= 10f) {
            // Cuando está muy alejado, mostrar un solo marcador con el número total
            val centerPosition = LatLng(10.4900, -66.8650) // Centro de todas las cafeterías
            Marker(
                state = MarkerState(position = centerPosition),
                title = "${cafeterias.size} cafeterías",
                snippet = "en Caracas",
                icon = createCounterMarker(cafeterias.size), // Marcador con número
                onClick = {
                    false // No hacer nada al hacer click
                }
            )
        } else {
            // Cuando está cerca, mostrar marcadores individuales
            cafeterias.forEach { cafeteria ->
                val position = LatLng(
                    cafeteria.ubicacion.latitud,
                    cafeteria.ubicacion.longitud
                )
                
                Marker(
                    state = MarkerState(position = position),
                    title = cafeteria.nombre,
                    snippet = "${cafeteria.descripcion} • ${cafeteria.ubicacion.direccion}",
                    icon = createCustomCafeteriaMarker(), // Pin personalizado
                    onClick = {
                        onCafeteriaClick(cafeteria)
                        true // Consumir el evento
                    }
                )
            }
        }
    }
}


/**
 * Chip de filtro para el mapa
 */
@Composable
private fun FilterChip(
    filter: MapFilter,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.clickable { onClick() },
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) CoffeeTripColors.accentBrown else CoffeeTripColors.bgBase.copy(alpha = 0.1f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = if (isSelected) 4.dp else 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                imageVector = filter.icon,
                contentDescription = filter.name,
                tint = if (isSelected) Color.White else CoffeeTripColors.bgBase,
                modifier = Modifier.size(18.dp)
            )
            Text(
                text = filter.name,
                fontSize = 13.sp,
                fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Medium,
                color = if (isSelected) Color.White else CoffeeTripColors.bgBase
            )
        }
    }
}

/**
 * Modelo de datos para los filtros del mapa
 */
data class MapFilter(
    val name: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector
)

/**
 * Lista de filtros disponibles en el mapa
 */
private fun getMapFilters(): List<MapFilter> {
    return listOf(
        MapFilter("Guardados", Icons.Filled.Star),
        MapFilter("1 km", Icons.Filled.LocationOn),
        MapFilter("4.5+", Icons.Filled.Star),
        MapFilter("Abierto", Icons.Filled.LocationOn),
        MapFilter("Wi-Fi", Icons.Filled.Wifi),
        MapFilter("Pet Friendly", Icons.Filled.Pets)
    )
}
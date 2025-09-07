package com.coffeetrip.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.coffeetrip.presentation.screens.inicio.InicioScreen
import com.coffeetrip.presentation.screens.inicio.InicioViewModel
import com.coffeetrip.presentation.screens.mapa.MapaScreen
import com.coffeetrip.presentation.screens.favoritos.FavoritosScreen
import com.coffeetrip.presentation.screens.favoritos.FavoritosViewModel
import com.coffeetrip.domain.model.CafeteriaDetalle
import androidx.hilt.navigation.compose.hiltViewModel

/**
 * Navegación principal de la aplicación usando Navigation Compose
 */
@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = "inicio"
    ) {
        composable("inicio") {
            val viewModel: InicioViewModel = hiltViewModel()
            InicioScreen(
                viewModel = viewModel,
                onCafeteriaClick = { _ ->
                    // TODO: Navegar a pantalla de detalle cuando esté implementada
                    // navController.navigate("detalle/$cafeteriaId")
                },
                onMapaClick = {
                    navController.navigate("mapa")
                },
                onNavigate = { route ->
                    navController.navigate(route)
                }
            )
        }
        
        // TODO: Agregar más rutas cuando se implementen las otras pantallas
        // composable("detalle/{cafeteriaId}") { backStackEntry ->
        //     val cafeteriaId = backStackEntry.arguments?.getString("cafeteriaId") ?: ""
        //     DetalleScreen(cafeteriaId = cafeteriaId)
        // }
        
        composable("mapa") {
            MapaScreen(
                onNavigate = { route ->
                    navController.navigate(route)
                },
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
        
        composable("favoritos") {
            val favoritosViewModel: FavoritosViewModel = hiltViewModel()
            FavoritosScreen(
                onNavigateToInicio = {
                    navController.navigate("inicio")
                },
                onNavigateToMas = {
                    // TODO: Implementar pantalla de configuración
                    // navController.navigate("configuracion")
                },
                onCafeteriaClick = { cafeteriaDetalle ->
                    // TODO: Navegar a pantalla de detalle cuando esté implementada
                    // navController.navigate("detalle/${cafeteriaDetalle.cafeteria.id}")
                },
                onMapaClick = {
                    navController.navigate("mapa")
                },
                onCallClick = {
                    // TODO: Implementar funcionalidad de llamada
                },
                onDirectionsClick = {
                    // TODO: Implementar funcionalidad de direcciones
                },
                onShareClick = {
                    // TODO: Implementar funcionalidad de compartir
                },
                onToggleFavorite = { cafeteriaId ->
                    favoritosViewModel.toggleFavorito(cafeteriaId)
                }
            )
        }
        
        // composable("configuracion") {
        //     ConfiguracionScreen()
        // }
    }
}


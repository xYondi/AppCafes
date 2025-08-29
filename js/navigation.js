// Función para cambiar de pantalla y actualizar la navegación
function showScreen(screenId, pushHistory = true) {
    // Esconder todas las pantallas
    screens.forEach(id => {
        const screen = document.getElementById(id);
        if (screen) {
            screen.style.display = 'none';
        }
    });
    
    // Mostrar la pantalla solicitada
    const targetScreen = document.getElementById(screenId);
    if (targetScreen) {
        targetScreen.style.display = 'flex';
        
        // Si la pantalla es el 'listView', volvemos a renderizar
        if (screenId === 'listView') {
            renderCoffeeShopCards('coffee-list', Object.values(coffeeShops));
        }
        // Si la pantalla es 'favoritesView', renderizamos los favoritos
        if (screenId === 'favoritesView') {
            renderFavoritesView();
            renderCustomLists();
        }
        // Si cambiamos a otra pantalla, ocultamos el resumen del marcador
        if (screenId !== 'mapView') {
            hideMarkerSummary();
        }
    }
    
    // Gestionar el historial de navegación
    if (pushHistory) {
        if (history[history.length - 1] !== screenId) {
            history.push(screenId);
        }
    }
    if (!pushHistory) {
        history = history.slice(0, -1);
    }
    
    // Actualizar el estilo de la navegación inferior
    const navElements = document.querySelectorAll('footer nav a');
    navElements.forEach(el => {
        el.classList.remove('nav-pill', 'text-[var(--active-brown)]', 'font-bold');
        el.classList.add('text-gray-600', 'hover:text-gray-900', 'transition-colors', 'duration-200');
        const span = el.querySelector('span');
        if (span) {
            span.classList.remove('font-bold');
            span.classList.add('font-medium');
        }
    });

    const currentNavLinkId = navLinks[screenId];
    if (currentNavLinkId) {
         const activeLink = document.getElementById(currentNavLinkId);
         activeLink.classList.remove('text-gray-600', 'hover:text-gray-900', 'font-medium');
         activeLink.classList.add('nav-pill', 'text-[var(--active-brown)]');
         const span = activeLink.querySelector('span');
         if (span) {
             span.classList.add('font-bold');
             span.classList.remove('font-medium');
         }
    }
}

// Sobrescribe la función de navegación de retroceso del navegador
window.onpopstate = function(event) {
    if (history.length > 1) {
        history.pop();
        const lastScreen = history[history.length - 1];
        showScreen(lastScreen, false);
    } else {
        showScreen('listView', false);
    }
};

// Función para renderizar las tarjetas en las listas
function renderCoffeeShopCards(containerId, list) {
    const container = document.getElementById(containerId);
    if (!container) return;
    
    container.innerHTML = ''; // Limpiar el contenedor
    
    if (list.length === 0) {
         container.innerHTML = `<p class="text-[var(--text-secondary)] text-center mt-8">No se encontraron cafeterías. Intenta con otra búsqueda.</p>`;
         return;
    }
    
    list.forEach(shop => {
        const isSaved = savedCoffeeShops.has(shop.id);
        const saveIconFill = isSaved ? 'fill="white" stroke="none"' : 'fill="none" stroke="currentColor"';

        // Ícono de huella de perro recreado para coincidir con la imagen
        const petIcon = shop.services.includes('Pet-friendly') ? `
            <svg class="w-5 h-5 text-gray-400" viewBox="0 0 100 100" xmlns="http://www.w3.org/2000/svg">
                <path fill="currentColor" d="M60 40a5 5 0 110-10 5 5 0 010 10zM40 40a5 5 0 110-10 5 5 0 010 10zM50 30a5 5 0 110-10 5 5 0 010 10zM70 30a5 5 0 110-10 5 5 0 010 10zM50 80c-15 0-20-10-20-25S40 30 50 30s20 5 20 20-5 25-20 25z"/>
            </svg>
        ` : '';
        
        // Ícono de Wi-Fi recreado para coincidir con la imagen
        const wifiIcon = shop.services.includes('WiFi gratis') || shop.services.includes('Wi-Fi') ? `
            <svg class="w-5 h-5 text-gray-400" viewBox="0 0 100 100" xmlns="http://www.w3.org/2000/svg">
                <path fill="currentColor" d="M50 67.5c-3.75 0-6.25 2.5-6.25 6.25S46.25 80 50 80s6.25-2.5 6.25-6.25S53.75 67.5 50 67.5zM50 50c-15.5 0-20.5 15.5-20.5 15.5S45 55.5 50 55.5s20.5 5.5 20.5 5.5S65.5 50 50 50zM50 20c-19.5 0-30.5 20-30.5 20s11-10 30.5-10 30.5 10 30.5 10S69.5 20 50 20z"/>
            </svg>
        ` : '';

        const card = document.createElement('div');
        card.classList.add('bg-[var(--bg-surface)]', 'rounded-2xl', 'shadow-xl', 'overflow-hidden', 'relative', 'transform', 'hover:scale-105', 'transition-transform', 'duration-300', 'ease-in-out', 'cursor-pointer');
        card.innerHTML = `
            <div class="relative">
                <img src="${shop.photos[0]}" class="w-full h-40 object-cover" alt="Imagen de ${shop.name}">
                <!-- Icono de guardado en la tarjeta -->
                <button onclick="toggleSaveState(event, '${shop.id}')" class="absolute top-2 right-2 w-8 h-8 rounded-full bg-black/40 backdrop-blur-sm flex items-center justify-center text-white transition-colors duration-200 hover:bg-black/60">
                    <svg class="w-5 h-5" ${saveIconFill} viewBox="0 0 24 24" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M5 5a2 2 0 012-2h10a2 2 0 012 2v16l-7-3.5L5 21V5z"></path></svg>
                </button>
            </div>
            <div class="p-4">
                <h3 class="font-bold text-xl text-[var(--text-primary)]">${shop.name}</h3>
                <p class="text-sm text-[var(--text-secondary)] mt-1">${shop.description.substring(0, 50)}...</p>
                <div class="flex items-center justify-between mt-3">
                    <div class="flex">
                        <svg class="w-4 h-4 text-yellow-400" fill="currentColor" viewBox="0 0 20 20"><path d="M9.049 2.927c.3-.921 1.603-.921 1.902 0l1.07 3.292a1 1 0 00.95.69h3.462c.969 0 1.371 1.24.588 1.81l-2.8 2.034a1 1 0 00-.364 1.118l1.07 3.292c.3.921-.755 1.688-1.54 1.118l-2.8-2.034a1 1 0 00-1.175 0l-2.8 2.034c-.784.57-1.838-.197-1.539-1.118l1.07-3.292a1 1 0 00-.364-1.118L2.98 8.72c-.783-.57-.38-1.81.588-1.81h3.461a1 1 0 00.951-.69l1.07-3.292z"></path></svg>
                        <span class="ml-1 text-sm font-medium text-[var(--text-primary)]">${shop.rating}</span>
                        <span class="bg-gray-700 text-[var(--text-secondary)] text-xs font-semibold px-2 py-0.5 rounded-full">${shop.distance}</span>
                    </div>
                    <div class="flex items-center space-x-2">
                         ${petIcon}
                         ${wifiIcon}
                    </div>
                </div>
            </div>
        `;
        container.appendChild(card);
    });
}

// Función para alternar el estado de guardado de un café
function toggleSaveState(event, shopId) {
    event.stopPropagation(); // Previene que el clic active la tarjeta completa
    if (savedCoffeeShops.has(shopId)) {
        savedCoffeeShops.delete(shopId);
    } else {
        savedCoffeeShops.add(shopId);
    }
    // Volvemos a renderizar las tarjetas para actualizar el estado visual
    renderCoffeeShopCards('coffee-list', Object.values(coffeeShops));
} 
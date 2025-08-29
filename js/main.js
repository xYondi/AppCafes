// ===== VISTAS Y FUNCIONALIDADES PRINCIPALES =====

// Función para cambiar de pantalla y actualizar la navegación
function showScreen(screenId, pushHistory = true) {
    console.log('showScreen llamado con:', screenId);
    console.log('Pantallas disponibles:', screens);
    
    // Esconder todas las pantallas
    screens.forEach(id => {
        const screen = document.getElementById(id);
        if (screen) {
            screen.style.display = 'none';
            console.log(`Ocultando pantalla: ${id}`);
        } else {
            console.log(`No se encontró la pantalla: ${id}`);
        }
    });
    
    // Mostrar la pantalla solicitada
    const targetScreen = document.getElementById(screenId);
    console.log('Pantalla objetivo:', targetScreen);
    
    if (targetScreen) {
        targetScreen.style.display = 'flex';
        console.log(`Mostrando pantalla: ${screenId}`);
        
        // Si la pantalla es el 'listView', volvemos a renderizar
        if (screenId === 'listView') {
            renderCoffeeShopCards('coffee-list', Object.values(coffeeShops));
        }
        // Si la pantalla es 'favoritesView', renderizamos los favoritos
        if (screenId === 'favoritesView') {
            if (typeof renderFavoritesView === 'function') {
                console.log('Llamando a renderFavoritesView');
                renderFavoritesView();
            } else {
                console.log('renderFavoritesView no está definida');
            }
            if (typeof renderCustomLists === 'function') {
                console.log('Llamando a renderCustomLists');
                renderCustomLists();
            } else {
                console.log('renderCustomLists no está definida');
            }
        }
        // Si cambiamos a otra pantalla, ocultamos el resumen del marcador
        if (screenId !== 'mapView') {
            hideMarkerSummary();
        }
    } else {
        console.error(`No se pudo encontrar la pantalla: ${screenId}`);
        console.log('Elementos en el DOM:', document.querySelectorAll('[id]'));
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

// Función para cambiar la visualización del ícono de guardado en la pantalla de detalle
function updateSaveIcon(isSaved) {
    const saveIcon = document.getElementById('save-icon');
    if (saveIcon) {
        if (isSaved) {
             // Ícono de guardado (libro lleno) - Color blanco puro
             saveIcon.setAttribute('fill', 'white');
             saveIcon.setAttribute('stroke', 'none');
        } else {
             // Ícono de desguardado (libro vacío)
             saveIcon.setAttribute('fill', 'none');
             saveIcon.setAttribute('stroke', 'currentColor');
        }
    }
}

// Muestra la tarjeta de resumen del marcador
function showMarkerSummary(shopId) {
    const summaryCard = document.getElementById('marker-summary');
    if (!summaryCard) return;
    
    const shop = coffeeShops[shopId];
    if (!shop) return;
    
    selectedMarkerId = shopId;
    document.getElementById('summary-img').src = shop.photos[0];
    document.getElementById('summary-name').textContent = shop.name;
    document.getElementById('summary-description-short').textContent = shop.description.substring(0, 50) + '...';
    document.getElementById('summary-rating').textContent = shop.rating;
    
    summaryCard.classList.remove('-translate-y-full', 'opacity-0');
    summaryCard.classList.add('translate-y-0', 'opacity-100');
}

// Esconde la tarjeta de resumen del marcador
function hideMarkerSummary() {
    const summaryCard = document.getElementById('marker-summary');
    if (summaryCard) {
        summaryCard.classList.remove('translate-y-0', 'opacity-100');
        summaryCard.classList.add('-translate-y-full', 'opacity-0');
        selectedMarkerId = null;
    }
}

// Simula la funcionalidad de búsqueda
function performSearch(query) {
    const searchQuery = query || document.getElementById('search-input').value;
    const resultsTitle = document.getElementById('results-title');
    
    if (searchQuery) {
         // Simulamos un filtro por ciudad
        const filteredShops = Object.values(coffeeShops).filter(shop => shop.address.toLowerCase().includes(searchQuery.toLowerCase()));
        if (filteredShops.length > 0) {
             resultsTitle.textContent = `Resultados para "${searchQuery}"`;
             renderCoffeeShopCards('results-list', filteredShops);
        } else {
             resultsTitle.textContent = `No se encontraron resultados para "${searchQuery}"`;
             document.getElementById('results-list').innerHTML = `<p class="text-[var(--text-secondary)] text-center mt-8">Intenta con otra ciudad o un filtro diferente.</p>`;
        }
    } else {
        resultsTitle.textContent = 'Ingresa una ciudad para buscar';
        document.getElementById('results-list').innerHTML = '';
    }
    showScreen('resultsView');
}

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

// ===== INICIALIZACIÓN DE VISTAS =====

// Crea dinámicamente las vistas que no están en el HTML principal
function createViews() {
    const dynamicContent = document.getElementById('dynamic-content');
    if (!dynamicContent) {
        console.error('No se encontró el contenedor dynamic-content');
        return;
    }
    
    console.log('Creando vistas dinámicas...');
    
    // Vista del Mapa
    const mapView = document.createElement('div');
    mapView.id = 'mapView';
    mapView.className = 'screen absolute inset-0 w-full h-full flex flex-col';
    mapView.style.display = 'none';
    mapView.innerHTML = `
        <!-- Contenedor de la barra de búsqueda y filtros -->
        <div class="absolute top-4 w-full px-4 z-50 pointer-events-none">
            <div class="flex items-center space-x-2 pointer-events-auto">
                <button onclick="showScreen('listView')" class="w-10 h-10 flex items-center justify-center rounded-full bg-[var(--bg-surface)] shadow-lg hover:bg-[var(--accent-brown)] transition-colors focus:outline-none focus:ring-2 focus:ring-[var(--accent-brown)]">
                    <svg class="w-6 h-6 text-[var(--text-primary)]" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 19l-7-7 7-7"></path></svg>
                </button>
                <div class="flex-grow bg-[var(--bg-surface)] rounded-full shadow-lg p-3 flex items-center space-x-2">
                     <svg class="w-6 h-6 text-[var(--text-secondary)]" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z"></path></svg>
                     <button onclick="showScreen('searchView')" class="flex-grow bg-transparent text-[var(--text-secondary)] text-left focus:outline-none placeholder-[var(--text-secondary)]">
                         Buscar en el mapa...
                     </button>
                </div>
            </div>
            <!-- Filtros deslizable (movidos aquí) -->
            <div class="mt-4 overflow-x-auto whitespace-nowrap space-x-2 no-scrollbar pointer-events-auto">
                <button class="filter-pill">
                    <svg class="w-5 h-5 mr-2" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 5a2 2 0 012-2h10a2 2 0 012 2v16l-7-3.5L5 21V5z"></path></svg>
                    <span>Guardados</span>
                </button>
                <button class="filter-pill">
                    <svg class="w-5 h-5 mr-2" fill="none" stroke="currentColor" viewBox="0 0 24 24" stroke-width="2"><circle cx="12" cy="12" r="10"/><path d="M12 2v10h8"/></svg>
                    <span>Abierto ahora</span>
                </button>
                <button class="filter-pill">
                    <svg class="w-5 h-5 mr-2" fill="none" stroke="currentColor" viewBox="0 0 24 24" stroke-width="2"><path d="M12 2L12 12L2 12L12 22L22 12L12 12L12 2Z"/></svg>
                    <span>Breakfast</span>
                </button>
                <button class="filter-pill">
                    <svg class="w-5 h-5 mr-2" fill="none" stroke="currentColor" viewBox="0 0 24 24" stroke-width="2"><path d="M12 22a10 10 0 100-20 10 10 0 000 20zM12 15a3 3 0 100-6 3 3 0 000 6zM12 9V6M12 18v-3M18.3 18.3l-2.1-2.1M5.7 5.7l2.1 2.1"/></svg>
                    <span>Opciones veganas</span>
                </button>
            </div>
        </div>

        <!-- Iframe del mapa -->
        <div class="relative w-full h-full">
            <iframe src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d387191.03604130096!2d-74.25987158498863!3d40.69740344445388!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x89c24fa5d33f083b%3A0xc80b8f06e177fe62!2sNew%20York%2C%20NY%2C%20USA!5e0!3m2!1sen!2sco!4v1700599298218!5m2!1sen!2sco" width="100%" height="100%" style="border:0;" allowfullscreen="" loading="lazy" referrerpolicy="no-referrer-when-downgrade"></iframe>
            
            <!-- Marcadores de ejemplo sobre el iframe -->
            <div class="pointer-events-none absolute inset-0 z-40">
                 <!-- Marcador simulado 1: El Rincón del Café -->
                <div id="marker-1" class="map-marker bg-red-500 top-1/3 left-1/4 pointer-events-auto" onclick="showMarkerSummary('el-rincon-del-cafe')">
                    <svg class="w-5 h-5 text-white" fill="currentColor" viewBox="0 0 20 20"><path d="M10 20s-6-5.88-6-11a6 6 0 0 1 12 0c0 5.12-6 11-6 11zm0-7a3 3 0 1 0 0-6 3 3 0 0 0 0 6z"></path></svg>
                </div>
                
                <!-- Marcador simulado 2: La Molienda -->
                <div id="marker-2" class="map-marker bg-blue-500 top-1/2 left-3/4 pointer-events-auto" onclick="showMarkerSummary('la-molienda')">
                    <svg class="w-5 h-5 text-white" fill="currentColor" viewBox="0 0 20 20"><path d="M10 20s-6-5.88-6-11a6 6 0 0 1 12 0c0 5.12-6 11-6 11zm0-7a3 3 0 1 0 0-6 3 3 0 0 0 0 6z"></path></svg>
                </div>

                <!-- Marcador simulado 3: Coffee Lab -->
                <div id="marker-3" class="map-marker bg-green-500 top-2/3 left-1/2 pointer-events-auto" onclick="showMarkerSummary('coffee-lab')">
                    <svg class="w-5 h-5 text-white" fill="currentColor" viewBox="0 0 20 20"><path d="M10 20s-6-5.88-6-11a6 6 0 0 1 12 0c0 5.12-6 11-6 11zm0-7a3 3 0 1 0 0-6 3 3 0 0 0 0 6z"></path></svg>
                </div>
            </div>
        </div>
        <!-- Tarjeta flotante de resumen de marcador (ejemplo) -->
        <div id="marker-summary" class="absolute bottom-28 left-4 right-4 bg-white rounded-xl shadow-lg p-4 flex gap-4 transition-transform duration-300 ease-in-out -translate-y-full opacity-0 z-40 pointer-events-auto" onclick="hideMarkerSummary()">
             <img id="summary-img" src="" class="w-20 h-20 rounded-xl object-cover" alt="Foto de la cafetería">
             <div class="flex flex-col justify-center">
                 <h4 id="summary-name" class="font-bold text-gray-900"></h4>
                 <p id="summary-description-short" class="text-sm text-gray-600"></p>
                 <div class="flex items-center text-gray-500 text-xs mt-1">
                     <svg class="w-4 h-4 text-yellow-400" fill="currentColor" viewBox="0 0 20 20"><path d="M9.049 2.927c.3-.921 1.603-.921 1.902 0l1.07 3.292a1 1 0 00.95.69h3.462c.969 0 1.371 1.24.588 1.81l-2.8 2.034a1 1 0 00-.364 1.118l1.07 3.292c.3.921-.755 1.688-1.54 1.118l-2.8-2.034a1 1 0 00-1.175 0l-2.8 2.034c-.784.57-1.838-.197-1.539-1.118l1.07-3.292a1 1 0 00-.364-1.118L2.98 8.72c-.783-.57-.38-1.81.588-1.81h3.461a1 1 0 00.951-.69l1.07-3.292z"></path></svg>
                     <span id="summary-rating" class="ml-1"></span>
                 </div>
             </div>
        </div>
        <!-- Botones de zoom y ubicación -->
        <div class="absolute bottom-28 right-4 flex flex-col space-y-2 pointer-events-auto z-50">
            <button class="w-12 h-12 bg-[var(--bg-surface)] rounded-full shadow-lg flex items-center justify-center hover:bg-[var(--accent-brown)] transition-colors focus:outline-none focus:ring-2 focus:ring-[var(--accent-brown)]">
                <svg class="w-6 h-6 text-[var(--text-primary)]" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M17.657 16.657L13.414 20.9a1.998 1.998 0 01-2.827 0l-4.244-4.243a8 8 0 1111.314 0z"></path><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 11a3 3 0 11-6 0 3 3 0 016 0z"></path></svg>
            </button>
        </div>
    `;
    
    // Vista de Búsqueda
    const searchView = document.createElement('div');
    searchView.id = 'searchView';
    searchView.className = 'screen absolute inset-0 w-full h-full flex flex-col p-4 bg-[var(--bg-base)]';
    searchView.style.display = 'none';
    searchView.innerHTML = `
        <header class="w-full flex items-center mb-6 pt-4">
            <button onclick="showScreen('mapView')" class="w-10 h-10 flex items-center justify-center rounded-full text-[var(--text-secondary)] hover:bg-white/10 transition-colors focus:outline-none focus:ring-2 focus:ring-[var(--accent-brown)]">
                <svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 19l-7-7 7-7"></path></svg>
            </button>
            <input type="text" id="search-input" placeholder="Buscar por ciudad..." class="flex-grow mx-4 bg-[var(--bg-surface)] rounded-full px-4 py-2 text-[var(--text-primary)] placeholder-[var(--text-secondary)] focus:outline-none focus:ring-2 focus:ring-[var(--accent-brown)]">
            <button onclick="performSearch()" class="w-10 h-10 flex items-center justify-center rounded-full text-[var(--text-secondary)] hover:bg-white/10 transition-colors focus:outline-none focus:ring-2 focus:ring-[var(--accent-brown)]">
                 <svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z"></path></svg>
            </button>
        </header>
        <div class="text-left mt-4">
            <h2 class="text-xl font-bold text-[var(--text-primary)] mb-4">Ciudades populares</h2>
            <div class="flex flex-wrap gap-2">
                <button onclick="performSearch('Nueva York')" class="px-4 py-2 rounded-full bg-[var(--bg-surface)] text-[var(--text-secondary)] hover:bg-[var(--accent-brown)] hover:text-white transition-colors">Nueva York</button>
                <button onclick="performSearch('París')" class="px-4 py-2 rounded-full bg-[var(--bg-surface)] text-[var(--text-secondary)] hover:bg-[var(--accent-brown)] hover:text-white transition-colors">París</button>
                <button onclick="performSearch('Tokio')" class="px-4 py-2 rounded-full bg-[var(--bg-surface)] text-[var(--text-secondary)] hover:bg-[var(--accent-brown)] hover:text-white transition-colors">Tokio</button>
                <button onclick="performSearch('Londres')" class="px-4 py-2 rounded-full bg-[var(--bg-surface)] text-[var(--text-secondary)] hover:bg-[var(--accent-brown)] hover:text-white transition-colors">Londres</button>
            </div>
        </div>
    `;
    
    // Vista de Resultados de Búsqueda
    const resultsView = document.createElement('div');
    resultsView.id = 'resultsView';
    resultsView.className = 'screen absolute inset-0 w-full h-full flex flex-col p-4 bg-[var(--bg-base)] overflow-y-auto no-scrollbar';
    resultsView.style.display = 'none';
    resultsView.innerHTML = `
        <header class="w-full flex items-center mb-6 pt-4">
            <button onclick="showScreen('searchView')" class="w-10 h-10 flex items-center justify-center rounded-full text-[var(--text-secondary)] hover:bg-white/10 transition-colors focus:outline-none focus:ring-2 focus:ring-[var(--accent-brown)]">
                <svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 19l-7-7 7-7"></path></svg>
            </button>
            <h1 id="results-title" class="text-xl font-bold text-[var(--text-primary)] mx-auto">Resultados</h1>
        </header>
        <div id="results-list" class="flex-grow space-y-6">
            <!-- Las tarjetas de cafeterías se insertan aquí dinámicamente -->
        </div>
    `;
    
    // Vista de Favoritos y Mis Listas
    const favoritesView = document.createElement('div');
    favoritesView.id = 'favoritesView';
    favoritesView.className = 'screen absolute inset-0 w-full h-full flex flex-col p-4 bg-[var(--bg-base)] overflow-y-auto no-scrollbar';
    favoritesView.style.display = 'none';
    favoritesView.innerHTML = `
        <header class="w-full flex justify-between items-center mb-6 pt-4">
            <div class="text-left">
                <h1 class="text-3xl font-bold text-[var(--text-primary)]">Mis Favoritos</h1>
                <p class="text-sm text-[var(--text-secondary)] mt-1">Gestiona tus cafeterías guardadas</p>
            </div>
            <div class="flex space-x-2">
                <button onclick="showCreateListModal()" class="w-12 h-12 rounded-full bg-[var(--accent-brown)] flex items-center justify-center focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-offset-[var(--accent-brown)] focus:ring-white hover:bg-[var(--active-brown)] transition-all duration-200">
                    <svg class="w-6 h-6 text-white" fill="none" stroke="currentColor" viewBox="0 0 24 24" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M12 4v16m8-8H4"></path></svg>
                </button>
                <button onclick="toggleFavoritesView()" id="toggle-view-btn" class="w-12 h-12 rounded-full bg-[var(--bg-surface)] flex items-center justify-center focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-offset-[var(--bg-surface)] focus:ring-[var(--accent-brown)] hover:bg-[var(--accent-brown)] transition-all duration-200">
                    <svg class="w-6 h-6 text-[var(--text-primary)]" fill="none" stroke="currentColor" viewBox="0 0 24 24" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M3 4a1 1 0 011-1h16a1 1 0 011 1v2.586a1 1 0 01-.293.707l-6.414 6.034a1 1 0 00-.293.707V17l-4 4v-6.586a1 1 0 00-.293-.707L3.293 7.293A1 1 0 013 6.586V4z"></path></svg>
                </button>
            </div>
        </header>

        <!-- Estadísticas rápidas -->
        <div class="grid grid-cols-2 gap-4 mb-6">
            <div class="bg-gradient-to-br from-[var(--bg-surface)] to-[var(--accent-brown)] rounded-xl p-4 text-center">
                <div class="text-2xl font-bold text-[var(--text-primary)]" id="total-favorites">0</div>
                <div class="text-sm text-[var(--text-secondary)]">Cafeterías</div>
            </div>
            <div class="bg-gradient-to-br from-[var(--bg-surface)] to-[var(--accent-brown)] rounded-xl p-4 text-center">
                <div class="text-2xl font-bold text-[var(--text-primary)]" id="total-lists">0</div>
                <div class="text-sm text-[var(--text-secondary)]">Listas</div>
            </div>
        </div>

        <!-- Pestañas de vista -->
        <div class="flex space-x-1 bg-[var(--bg-surface)] rounded-lg p-1 mb-6">
            <button onclick="showFavoritesTab()" id="favorites-tab" class="flex-1 py-2 px-4 rounded-md bg-[var(--accent-brown)] text-white font-medium transition-all duration-200">
                <svg class="w-4 h-4 inline mr-2" fill="currentColor" viewBox="0 0 20 20"><path d="M9.049 2.927c.3-.921 1.603-.921 1.902 0l1.07 3.292a1 1 0 00.95.69h3.462c.969 0 1.371 1.24.588 1.81l-2.8 2.034a1 1 0 00-.364 1.118l1.07 3.292c.3.921-.755 1.688-1.54 1.118l-2.8-2.034a1 1 0 00-1.175 0l-2.8 2.034c-.784.57-1.838-.197-1.539-1.118l1.07-3.292a1 1 0 00-.364-1.118L2.98 8.72c-.783-.57-.38-1.81.588-1.81h3.461a1 1 0 00.951-.69l1.07-3.292z"></path></svg>
                Favoritos
            </button>
            <button onclick="showListsTab()" id="lists-tab" class="flex-1 py-2 px-4 rounded-md text-[var(--text-secondary)] hover:text-[var(--text-primary)] transition-all duration-200">
                <svg class="w-4 h-4 inline mr-2" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 5H7a2 2 0 00-2 2v10a2 2 0 002 2h8a2 2 0 002-2V7a2 2 0 00-2-2h-2M9 5a2 2 0 002 2h2a2 2 0 002-2M9 5a2 2 0 012-2h2a2 2 0 012 2"></path></svg>
                Mis Listas
            </button>
        </div>

        <!-- Contenido de Favoritos -->
        <div id="favorites-content" class="flex-grow">
            <div class="mb-6">
                <div class="flex items-center justify-between mb-4">
                    <h2 class="text-xl font-semibold text-[var(--text-primary)]">Cafeterías Guardadas</h2>
                    <div class="flex space-x-2">
                        <button onclick="sortFavorites('rating')" class="px-3 py-1 text-xs bg-[var(--bg-surface)] text-[var(--text-secondary)] rounded-full hover:bg-[var(--accent-brown)] hover:text-white transition-colors">
                            Por Rating
                        </button>
                        <button onclick="sortFavorites('distance')" class="px-3 py-1 text-xs bg-[var(--bg-surface)] text-[var(--text-secondary)] rounded-full hover:bg-[var(--accent-brown)] hover:text-white transition-colors">
                            Por Distancia
                        </button>
                    </div>
                </div>
                <div id="favorites-list" class="space-y-4">
                    <!-- Las cafeterías favoritas se insertan aquí dinámicamente -->
                </div>
            </div>
        </div>

        <!-- Contenido de Listas (inicialmente oculto) -->
        <div id="lists-content" class="flex-grow hidden">
            <div class="mb-6">
                <div class="flex items-center justify-between mb-4">
                    <h2 class="text-xl font-semibold text-[var(--text-primary)]">Mis Listas</h2>
                    <button onclick="showCreateListModal()" class="px-4 py-2 bg-[var(--accent-brown)] text-white rounded-lg text-sm font-medium hover:bg-[var(--active-brown)] transition-colors">
                        <svg class="w-4 h-4 inline mr-2" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 4v16m8-8H4"></path></svg>
                        Nueva Lista
                    </button>
                </div>
                <div id="custom-lists" class="space-y-4">
                    <!-- Las listas personalizadas se insertan aquí dinámicamente -->
                </div>
            </div>
        </div>
    `;
    
    // Vista de Perfil y Configuración
    const profileView = document.createElement('div');
    profileView.id = 'profileView';
    profileView.className = 'screen absolute inset-0 w-full h-full flex flex-col p-4 bg-[var(--bg-base)] overflow-y-auto no-scrollbar';
    profileView.style.display = 'none';
    profileView.innerHTML = `
        <header class="w-full flex justify-between items-center mb-6 pt-4">
            <div class="text-left">
                <h1 class="text-3xl font-bold text-[var(--text-primary)]">Mi Perfil</h1>
                <p class="text-sm text-[var(--text-secondary)] mt-1">Gestiona tu cuenta y preferencias</p>
            </div>
            <button onclick="editProfile()" class="w-12 h-12 rounded-full bg-[var(--accent-brown)] flex items-center justify-center focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-offset-[var(--accent-brown)] focus:ring-white hover:bg-[var(--active-brown)] transition-all duration-200">
                <svg class="w-6 h-6 text-white" fill="none" stroke="currentColor" viewBox="0 0 24 24" stroke-width="2"><path stroke-linecap="round" stroke-linejoin="round" d="M11 5H6a2 2 0 00-2 2v11a2 2 0 002 2h11a2 2 0 002-2v-5m-1.414-9.414a2 2 0 112.828 2.828L11.828 15H9v-2.828l8.586-8.586z"></path></svg>
            </button>
        </header>

        <!-- Información del Usuario -->
        <div class="config-card mb-6 relative overflow-hidden">
            <div class="absolute top-0 right-0 w-32 h-32 bg-gradient-to-br from-[var(--accent-brown)] to-transparent opacity-10 rounded-full transform translate-x-16 -translate-y-16"></div>
            <div class="relative z-10 flex items-center space-x-4">
                <div class="relative">
                    <div class="w-20 h-20 bg-gradient-to-br from-[var(--accent-brown)] to-[var(--active-brown)] rounded-full flex items-center justify-center shadow-lg">
                        <svg class="w-8 h-8 text-white" fill="none" stroke="currentColor" viewBox="0 0 24 24" stroke-width="2"><path stroke-linecap="round" stroke-linejoin="round" d="M16 7a4 4 0 11-8 0 4 4 0 018 0zM12 14a7 7 0 00-7 7h14a7 7 0 00-7-7z"></path></svg>
                    </div>
                    <button onclick="changeProfilePhoto()" class="absolute -bottom-1 -right-1 w-6 h-6 bg-[var(--accent-brown)] rounded-full flex items-center justify-center text-white text-xs hover:bg-[var(--active-brown)] transition-colors">
                        <svg class="w-3 h-3" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M3 9a2 2 0 012-2h.93a2 2 0 001.664-.89l.812-1.22A2 2 0 0110.07 4h3.86a2 2 0 011.664.89l.812 1.22A2 2 0 0018.07 7H19a2 2 0 012 2v9a2 2 0 01-2 2H5a2 2 0 01-2-2V9z"></path><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 13a3 3 0 11-6 0 3 3 0 016 0z"></path></svg>
                    </button>
                </div>
                <div class="flex-grow">
                    <h3 class="text-xl font-bold text-[var(--text-primary)]">Usuario CoffeeNav</h3>
                    <p class="text-sm text-[var(--text-secondary)]">usuario@coffeenav.com</p>
                    <div class="flex items-center mt-2">
                        <div class="w-2 h-2 bg-green-400 rounded-full mr-2"></div>
                        <span class="text-xs text-green-400 font-medium">En línea</span>
                    </div>
                </div>
                <div class="text-right">
                    <div class="text-sm text-[var(--text-secondary)]">Miembro desde</div>
                    <div class="text-sm font-medium text-[var(--text-primary)]">Enero 2024</div>
                </div>
            </div>
        </div>

        <!-- Estadísticas del usuario -->
        <div class="grid grid-cols-3 gap-4 mb-6">
            <div class="bg-gradient-to-br from-[var(--bg-surface)] to-[var(--accent-brown)] rounded-xl p-4 text-center">
                <div class="text-2xl font-bold text-[var(--text-primary)]" id="user-favorites">0</div>
                <div class="text-xs text-[var(--text-secondary)]">Favoritos</div>
            </div>
            <div class="bg-gradient-to-br from-[var(--bg-surface)] to-[var(--accent-brown)] rounded-xl p-4 text-center">
                <div class="text-2xl font-bold text-[var(--text-primary)]" id="user-lists">0</div>
                <div class="text-xs text-[var(--text-secondary)]">Listas</div>
            </div>
            <div class="bg-gradient-to-br from-[var(--bg-surface)] to-[var(--accent-brown)] rounded-xl p-4 text-center">
                <div class="text-2xl font-bold text-[var(--text-primary)]" id="user-visited">0</div>
                <div class="text-xs text-[var(--text-secondary)]">Visitadas</div>
            </div>
        </div>

        <!-- Configuración de Notificaciones -->
        <div class="config-card">
            <div class="flex items-center justify-between mb-4">
                <h3 class="text-lg font-semibold text-[var(--text-primary)]">Notificaciones</h3>
                <button onclick="toggleAllNotifications()" id="toggle-all-notifications" class="text-sm text-[var(--accent-brown)] hover:text-[var(--active-brown)] transition-colors">
                    Activar todas
                </button>
            </div>
            <div class="space-y-4">
                <div class="flex items-center justify-between p-3 bg-[var(--bg-base)] rounded-lg">
                    <div>
                        <p class="text-[var(--text-primary)] font-medium">Notificaciones push</p>
                        <p class="text-sm text-[var(--text-secondary)]">Recibe alertas sobre nuevas cafeterías</p>
                    </div>
                    <label class="switch">
                        <input type="checkbox" id="push-notifications" checked>
                        <span class="slider"></span>
                    </label>
                </div>
                <div class="flex items-center justify-between p-3 bg-[var(--bg-base)] rounded-lg">
                    <div>
                        <p class="text-[var(--text-primary)] font-medium">Notificaciones por email</p>
                        <p class="text-sm text-[var(--text-secondary)]">Resúmenes semanales de cafeterías</p>
                    </div>
                    <label class="switch">
                        <input type="checkbox" id="email-notifications">
                        <span class="slider"></span>
                    </label>
                </div>
                <div class="flex items-center justify-between p-3 bg-[var(--bg-base)] rounded-lg">
                    <div>
                        <p class="text-[var(--text-primary)] font-medium">Recordatorios de reservas</p>
                        <p class="text-sm text-[var(--text-secondary)]">Alertas antes de tus visitas</p>
                    </div>
                    <label class="switch">
                        <input type="checkbox" id="booking-reminders" checked>
                        <span class="slider"></span>
                    </label>
                </div>
            </div>
        </div>

        <!-- Configuración de Datos Offline -->
        <div class="config-card">
            <h3 class="text-lg font-semibold text-[var(--text-primary)] mb-4">Datos Offline</h3>
            <div class="space-y-4">
                <div class="flex items-center justify-between p-3 bg-[var(--bg-base)] rounded-lg">
                    <div>
                        <p class="text-[var(--text-primary)] font-medium">Descarga automática</p>
                        <p class="text-sm text-[var(--text-secondary)]">Sincroniza datos cuando hay WiFi</p>
                    </div>
                    <label class="switch">
                        <input type="checkbox" id="auto-download" checked>
                        <span class="slider"></span>
                    </label>
                </div>
                <div class="flex items-center justify-between p-3 bg-[var(--bg-base)] rounded-lg">
                    <div>
                        <p class="text-[var(--text-primary)] font-medium">Almacenamiento de fotos</p>
                        <p class="text-sm text-[var(--text-secondary)]">Guarda imágenes para uso offline</p>
                    </div>
                    <label class="switch">
                        <input type="checkbox" id="photo-storage">
                        <span class="slider"></span>
                    </label>
                </div>
                <div class="flex items-center justify-between p-3 bg-[var(--bg-base)] rounded-lg">
                    <div>
                        <p class="text-[var(--text-primary)] font-medium">Mapas offline</p>
                        <p class="text-sm text-[var(--text-secondary)]">Descarga mapas de áreas frecuentes</p>
                    </div>
                    <label class="switch">
                        <input type="checkbox" id="offline-maps">
                        <span class="slider"></span>
                    </label>
                </div>
            </div>
            <div class="mt-4 pt-4 border-t border-gray-700">
                <button onclick="downloadOfflineData()" class="w-full bg-[var(--accent-brown)] text-white py-3 px-4 rounded-lg font-medium hover:bg-[var(--active-brown)] transition-colors flex items-center justify-center">
                    <svg class="w-5 h-5 mr-2" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4 16v1a3 3 0 003 3h10a3 3 0 003-3v-1m-4-4l-4 4m0 0l-4-4m4 4V4"></path></svg>
                    Descargar Datos Offline
                </button>
                <div class="flex items-center justify-between mt-2 text-xs text-[var(--text-secondary)]">
                    <span>Espacio disponible: 2.3 GB</span>
                    <span>Última sincronización: Hace 2 horas</span>
                </div>
            </div>
        </div>

        <!-- Otras Configuraciones -->
        <div class="config-card">
            <h3 class="text-lg font-semibold text-[var(--text-primary)] mb-4">Otras Configuraciones</h3>
            <div class="space-y-4">
                <div class="flex items-center justify-between p-3 bg-[var(--bg-base)] rounded-lg">
                    <div>
                        <p class="text-[var(--text-primary)] font-medium">Modo oscuro</p>
                        <p class="text-sm text-[var(--text-secondary)]">Aplicar tema oscuro automáticamente</p>
                    </div>
                    <label class="switch">
                        <input type="checkbox" id="dark-mode">
                        <span class="slider"></span>
                    </label>
                </div>
                <div class="flex items-center justify-between p-3 bg-[var(--bg-base)] rounded-lg">
                    <div>
                        <p class="text-[var(--text-primary)] font-medium">Idioma</p>
                        <p class="text-sm text-[var(--text-secondary)]">Español</p>
                    </div>
                    <button onclick="changeLanguage()" class="text-[var(--accent-brown)] text-sm font-medium hover:text-[var(--active-brown)] transition-colors">Cambiar</button>
                </div>
                <div class="flex items-center justify-between p-3 bg-[var(--bg-base)] rounded-lg">
                    <div>
                        <p class="text-[var(--text-primary)] font-medium">Privacidad</p>
                        <p class="text-sm text-[var(--text-secondary)]">Gestionar datos personales</p>
                    </div>
                    <button onclick="managePrivacy()" class="text-[var(--accent-brown)] text-sm font-medium hover:text-[var(--active-brown)] transition-colors">Gestionar</button>
                </div>
            </div>
        </div>

        <!-- Botón de Cerrar Sesión -->
        <div class="mt-8">
            <button onclick="logout()" class="w-full bg-red-600 text-white py-3 px-4 rounded-lg font-medium hover:bg-red-700 transition-colors flex items-center justify-center">
                <svg class="w-5 h-5 mr-2" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M17 16l4-4m0 0l-4-4m4 4H7m6 4v1a3 3 0 01-3 3H6a3 3 0 01-3-3V7a3 3 0 013-3h4a3 3 0 013 3v1"></path></svg>
                Cerrar Sesión
            </button>
        </div>
    `;
    
    // Agregar las vistas al contenedor dinámico
    dynamicContent.appendChild(mapView);
    dynamicContent.appendChild(searchView);
    dynamicContent.appendChild(resultsView);
    dynamicContent.appendChild(favoritesView);
    dynamicContent.appendChild(profileView);
    
    console.log('Vistas creadas exitosamente:', {
        mapView: !!document.getElementById('mapView'),
        searchView: !!document.getElementById('searchView'),
        resultsView: !!document.getElementById('resultsView'),
        favoritesView: !!document.getElementById('favoritesView'),
        profileView: !!document.getElementById('profileView')
    });
}

// ===== INICIALIZACIÓN =====

// Renderiza las cafeterías iniciales al cargar
document.addEventListener('DOMContentLoaded', () => {
    // Crear las vistas dinámicamente
    createViews();
    
    // Renderizar la vista inicial
    renderCoffeeShopCards('coffee-list', Object.values(coffeeShops));
    
    // Oculta el resumen del marcador al inicio
    hideMarkerSummary();
    
    // Inicializa las vistas de favoritos y perfil
    setTimeout(() => {
        if (typeof renderFavoritesView === 'function') {
            renderFavoritesView();
        }
        if (typeof renderCustomLists === 'function') {
            renderCustomLists();
        }
        
        // Inicializa las funcionalidades del perfil
        initializeProfileFeatures();
        
        // Configuración inicial de la aplicación
        setupAppConfiguration();
        
        // Agregar event listeners para mejoras de UX
        addEventListeners();
    }, 100);
});

// Inicializa las funcionalidades del perfil
function initializeProfileFeatures() {
    // Inicializar switches
    if (typeof initializeSwitches === 'function') {
        initializeSwitches();
    }
    
    // Actualizar estadísticas del usuario
    if (typeof updateUserStats === 'function') {
        updateUserStats();
    }
    
    // Actualizar estadísticas de favoritos
    if (typeof updateFavoritesStats === 'function') {
        updateFavoritesStats();
    }
}

// Configuración inicial de la aplicación
function setupAppConfiguration() {
    // Configurar tema inicial
    const darkModeSwitch = document.getElementById('dark-mode');
    if (darkModeSwitch) {
        // Verificar preferencia del usuario
        const prefersDark = window.matchMedia('(prefers-color-scheme: dark)').matches;
        darkModeSwitch.checked = prefersDark;
        
        // Aplicar tema
        if (prefersDark) {
            document.documentElement.classList.add('dark');
        }
    }
    
    // Configurar notificaciones iniciales
    const pushNotif = document.getElementById('push-notifications');
    if (pushNotif) {
        // Verificar si el navegador soporta notificaciones
        if ('Notification' in window) {
            pushNotif.disabled = false;
        } else {
            pushNotif.disabled = true;
            pushNotif.checked = false;
        }
    }
}

// Agrega event listeners para mejoras de UX
function addEventListeners() {
    // Event listener para cambios de tema
    const darkModeSwitch = document.getElementById('dark-mode');
    if (darkModeSwitch) {
        darkModeSwitch.addEventListener('change', function() {
            if (this.checked) {
                document.documentElement.classList.add('dark');
                localStorage.setItem('darkMode', 'true');
            } else {
                document.documentElement.classList.remove('dark');
                localStorage.setItem('darkMode', 'false');
            }
        });
    }
    
    // Event listener para cambios de configuración offline
    const offlineSwitches = ['auto-download', 'photo-storage', 'offline-maps'];
    offlineSwitches.forEach(settingId => {
        const switchElement = document.getElementById(settingId);
        if (switchElement) {
            switchElement.addEventListener('change', function() {
                localStorage.setItem(`offline_${settingId}`, this.checked);
                
                // Mostrar feedback visual
                const feedback = document.createElement('div');
                feedback.className = 'fixed top-4 right-4 bg-green-500 text-white px-4 py-2 rounded-lg shadow-lg z-50 transform translate-x-full transition-transform duration-300';
                feedback.textContent = `Configuración ${this.checked ? 'activada' : 'desactivada'}`;
                document.body.appendChild(feedback);
                
                setTimeout(() => {
                    feedback.classList.remove('translate-x-full');
                }, 100);
                
                setTimeout(() => {
                    feedback.classList.add('translate-x-full');
                    setTimeout(() => {
                        document.body.removeChild(feedback);
                    }, 300);
                }, 2000);
            });
        }
    });
    
    // Event listener para búsqueda con Enter
    const searchInput = document.getElementById('search-input');
    if (searchInput) {
        searchInput.addEventListener('keypress', function(e) {
            if (e.key === 'Enter') {
                performSearch();
            }
        });
    }
    
    // Event listener para modal de crear lista
    const modal = document.getElementById('createListModal');
    if (modal) {
        // Cerrar modal al hacer clic fuera
        modal.addEventListener('click', function(e) {
            if (e.target === this) {
                hideCreateListModal();
            }
        });
        
        // Cerrar modal con Escape
        document.addEventListener('keydown', function(e) {
            if (e.key === 'Escape' && modal.style.display === 'flex') {
                hideCreateListModal();
            }
        });
    }
}

// Función para mostrar notificaciones del sistema
function showNotification(title, body, icon = null) {
    if ('Notification' in window && Notification.permission === 'granted') {
        new Notification(title, { body, icon });
    } else if ('Notification' in window && Notification.permission === 'default') {
        Notification.requestPermission().then(permission => {
            if (permission === 'granted') {
                new Notification(title, { body, icon });
            }
        });
    }
}

// Función para guardar estado de la aplicación
function saveAppState() {
    const appState = {
        favorites: Array.from(savedCoffeeShops),
        lists: customLists,
        settings: {
            darkMode: document.getElementById('dark-mode')?.checked || false,
            autoDownload: document.getElementById('auto-download')?.checked || false,
            photoStorage: document.getElementById('photo-storage')?.checked || false,
            offlineMaps: document.getElementById('offline-maps')?.checked || false
        },
        lastUpdated: new Date().toISOString()
    };
    
    localStorage.setItem('coffeenav_state', JSON.stringify(appState));
}

// Función para cargar estado de la aplicación
function loadAppState() {
    const savedState = localStorage.getItem('coffeenav_state');
    if (savedState) {
        try {
            const state = JSON.parse(savedState);
            
            // Restaurar favoritos
            if (state.favorites) {
                savedCoffeeShops.clear();
                state.favorites.forEach(id => savedCoffeeShops.add(id));
            }
            
            // Restaurar listas
            if (state.lists) {
                customLists.length = 0;
                state.lists.forEach(list => {
                    list.createdAt = new Date(list.createdAt);
                    customLists.push(list);
                });
            }
            
            // Restaurar configuraciones
            if (state.settings) {
                const darkModeSwitch = document.getElementById('dark-mode');
                if (darkModeSwitch && state.settings.darkMode) {
                    darkModeSwitch.checked = true;
                    document.documentElement.classList.add('dark');
                }
                
                const autoDownloadSwitch = document.getElementById('auto-download');
                if (autoDownloadSwitch) {
                    autoDownloadSwitch.checked = state.settings.autoDownload || false;
                }
                
                const photoStorageSwitch = document.getElementById('photo-storage');
                if (photoStorageSwitch) {
                    photoStorageSwitch.checked = state.settings.photoStorage || false;
                }
                
                const offlineMapsSwitch = document.getElementById('offline-maps');
                if (offlineMapsSwitch) {
                    offlineMapsSwitch.checked = state.settings.offlineMaps || false;
                }
            }
            
            console.log('Estado de la aplicación restaurado');
        } catch (error) {
            console.error('Error al restaurar el estado:', error);
        }
    }
}

// Guardar estado antes de cerrar la página
window.addEventListener('beforeunload', saveAppState);

// Cargar estado al iniciar
window.addEventListener('load', loadAppState); 
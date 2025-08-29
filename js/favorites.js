// ===== FUNCIONES PARA FAVORITOS Y LISTAS =====

// Renderiza la vista de favoritos
function renderFavoritesView() {
    const favoritesList = document.getElementById('favorites-list');
    if (!favoritesList) return;

    const savedShops = Array.from(savedCoffeeShops).map(id => coffeeShops[id]);
    
    // Actualizar estadísticas
    updateFavoritesStats();
    
    if (savedShops.length === 0) {
        favoritesList.innerHTML = `
            <div class="text-center py-12">
                <div class="w-24 h-24 bg-[var(--bg-surface)] rounded-full flex items-center justify-center mx-auto mb-4">
                    <svg class="w-12 h-12 text-[var(--text-secondary)]" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4.318 6.318a4.5 4.5 0 000 6.364L12 20.364l7.682-7.682a4.5 4.5 0 00-6.364-6.364L12 7.636l-1.318-1.318a4.5 4.5 0 00-6.364 0z"></path>
                    </svg>
                </div>
                <h3 class="text-xl font-semibold text-[var(--text-primary)] mb-2">No tienes cafeterías guardadas</h3>
                <p class="text-[var(--text-secondary)] mb-4">Guarda tus cafeterías favoritas para acceder rápidamente</p>
                <button onclick="showScreen('listView')" class="px-6 py-3 bg-[var(--accent-brown)] text-white rounded-lg font-medium hover:bg-[var(--active-brown)] transition-colors">
                    Explorar Cafeterías
                </button>
            </div>
        `;
        return;
    }

    favoritesList.innerHTML = '';
    savedShops.forEach(shop => {
        const card = createFavoriteCard(shop);
        favoritesList.appendChild(card);
    });
}

// Actualiza las estadísticas de favoritos
function updateFavoritesStats() {
    const totalFavorites = document.getElementById('total-favorites');
    const totalLists = document.getElementById('total-lists');
    const userFavorites = document.getElementById('user-favorites');
    const userLists = document.getElementById('user-lists');
    
    if (totalFavorites) totalFavorites.textContent = savedCoffeeShops.size;
    if (totalLists) totalLists.textContent = customLists.length;
    if (userFavorites) userFavorites.textContent = savedCoffeeShops.size;
    if (userLists) userLists.textContent = customLists.length;
}

// Crea una tarjeta de cafetería favorita mejorada
function createFavoriteCard(shop) {
    const card = document.createElement('div');
    card.classList.add('bg-[var(--bg-surface)]', 'rounded-xl', 'p-4', 'transform', 'hover:scale-105', 'transition-all', 'duration-300', 'cursor-pointer', 'border-l-4', 'border-[var(--accent-brown)]');
    card.onclick = () => showShopDetail(shop);
    
    card.innerHTML = `
        <div class="flex items-start space-x-4">
            <img src="${shop.photos[0]}" class="w-20 h-20 rounded-lg object-cover flex-shrink-0" alt="${shop.name}">
            <div class="flex-grow min-w-0">
                <div class="flex items-start justify-between mb-2">
                    <h3 class="font-bold text-lg text-[var(--text-primary)] truncate">${shop.name}</h3>
                    <button onclick="event.stopPropagation(); removeFromFavorites('${shop.id}')" class="text-red-400 hover:text-red-300 transition-colors p-1 rounded-full hover:bg-red-400/10">
                        <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M19 7l-.867 12.142A2 2 0 0116.138 21H7.862a2 2 0 01-1.995-1.858L5 7m5 4v6m4-6v6m1-10V4a1 1 0 00-1-1h-4a1 1 0 00-1 1v3M4 7h16"></path>
                        </svg>
                    </button>
                </div>
                <p class="text-sm text-[var(--text-secondary)] mb-3">${shop.address}</p>
                <div class="flex items-center justify-between">
                    <div class="flex items-center space-x-4">
                        <div class="flex items-center">
                            <svg class="w-4 h-4 text-yellow-400" fill="currentColor" viewBox="0 0 20 20">
                                <path d="M9.049 2.927c.3-.921 1.603-.921 1.902 0l1.07 3.292a1 1 0 00.95.69h3.462c.969 0 1.371 1.24.588 1.81l-2.8 2.034a1 1 0 00-.364 1.118l1.07 3.292c.3.921-.755 1.688-1.54 1.118l-2.8-2.034a1 1 0 00-1.175 0l-2.8 2.034c-.784.57-1.838-.197-1.539-1.118l1.07-3.292a1 1 0 00-.364-1.118L2.98 8.72c-.783-.57-.38-1.81.588-1.81h3.461a1 1 0 00.951-.69l1.07-3.292z"></path>
                            </svg>
                            <span class="ml-1 text-sm font-medium text-[var(--text-primary)]">${shop.rating}</span>
                        </div>
                        <span class="text-xs bg-[var(--accent-brown)] text-white px-2 py-1 rounded-full">${shop.distance}</span>
                    </div>
                    <div class="flex space-x-2">
                        ${shop.services.includes('WiFi gratis') || shop.services.includes('Wi-Fi') ? 
                            '<svg class="w-4 h-4 text-blue-400" fill="currentColor" viewBox="0 0 20 20"><path d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z"></path></svg>' : ''}
                        ${shop.services.includes('Pet-friendly') ? 
                            '<svg class="w-4 h-4 text-green-400" fill="currentColor" viewBox="0 0 20 20"><path d="M4.5 9.5a5.5 5.5 0 0011 0 5.5 5.5 0 00-11 0z"></path><path d="M9 12.5a3.5 3.5 0 117 0 3.5 3.5 0 01-7 0z"></path></svg>' : ''}
                    </div>
                </div>
            </div>
        </div>
        <div class="mt-3 pt-3 border-t border-gray-700">
            <div class="flex items-center justify-between text-xs text-[var(--text-secondary)]">
                <span>Horario: ${shop.hours}</span>
                <button onclick="event.stopPropagation(); addToList('${shop.id}')" class="text-[var(--accent-brown)] hover:text-[var(--active-brown)] transition-colors">
                    Agregar a lista
                </button>
            </div>
        </div>
    `;
    
    return card;
}

// Muestra el detalle de una cafetería
function showShopDetail(shop) {
    // Por ahora muestra un alert, pero se puede expandir a una vista completa
    const services = shop.services.join(', ');
    const methods = shop.brewingMethods.join(', ');
    
    alert(`Cafetería: ${shop.name}\n\nDirección: ${shop.address}\nHorario: ${shop.hours}\nRating: ${shop.rating}\nDistancia: ${shop.distance}\n\nServicios: ${services}\nMétodos de preparación: ${methods}\n\nDescripción: ${shop.description}`);
}

// Agrega una cafetería a una lista
function addToList(shopId) {
    if (customLists.length === 0) {
        if (confirm('No tienes listas personalizadas. ¿Quieres crear una nueva?')) {
            showCreateListModal();
        }
        return;
    }
    
    const listNames = customLists.map(list => list.name);
    const selectedList = prompt(`Selecciona una lista:\n\n${listNames.join('\n')}\n\nEscribe el nombre exacto de la lista:`);
    
    if (selectedList && listNames.includes(selectedList)) {
        const list = customLists.find(l => l.name === selectedList);
        if (list && !list.coffeeShops.includes(shopId)) {
            list.coffeeShops.push(shopId);
            renderCustomLists();
            alert(`¡${coffeeShops[shopId].name} agregada a "${list.name}"!`);
        } else if (list && list.coffeeShops.includes(shopId)) {
            alert('Esta cafetería ya está en la lista seleccionada.');
        }
    }
}

// Remueve una cafetería de favoritos
function removeFromFavorites(shopId) {
    if (confirm(`¿Estás seguro de que quieres quitar "${coffeeShops[shopId].name}" de tus favoritos?`)) {
        savedCoffeeShops.delete(shopId);
        renderFavoritesView();
        // También actualiza la vista principal si está visible
        if (document.getElementById('listView').style.display !== 'none') {
            renderCoffeeShopCards('coffee-list', Object.values(coffeeShops));
        }
    }
}

// Ordena los favoritos por criterio
function sortFavorites(criteria) {
    const favoritesList = document.getElementById('favorites-list');
    if (!favoritesList) return;
    
    const savedShops = Array.from(savedCoffeeShops).map(id => coffeeShops[id]);
    
    if (criteria === 'rating') {
        savedShops.sort((a, b) => b.rating - a.rating);
    } else if (criteria === 'distance') {
        savedShops.sort((a, b) => parseFloat(a.distance) - parseFloat(b.distance));
    }
    
    favoritesList.innerHTML = '';
    savedShops.forEach(shop => {
        const card = createFavoriteCard(shop);
        favoritesList.appendChild(card);
    });
}

// Muestra la pestaña de favoritos
function showFavoritesTab() {
    document.getElementById('favorites-content').classList.remove('hidden');
    document.getElementById('lists-content').classList.add('hidden');
    
    document.getElementById('favorites-tab').classList.add('bg-[var(--accent-brown)]', 'text-white');
    document.getElementById('favorites-tab').classList.remove('text-[var(--text-secondary)]');
    
    document.getElementById('lists-tab').classList.remove('bg-[var(--accent-brown)]', 'text-white');
    document.getElementById('lists-tab').classList.add('text-[var(--text-secondary)]');
}

// Muestra la pestaña de listas
function showListsTab() {
    document.getElementById('favorites-content').classList.add('hidden');
    document.getElementById('lists-content').classList.remove('hidden');
    
    document.getElementById('lists-tab').classList.add('bg-[var(--accent-brown)]', 'text-white');
    document.getElementById('lists-tab').classList.remove('text-[var(--text-secondary)]');
    
    document.getElementById('favorites-tab').classList.remove('bg-[var(--accent-brown)]', 'text-white');
    document.getElementById('favorites-tab').classList.add('text-[var(--text-secondary)]');
}

// Alterna entre vistas de favoritos
function toggleFavoritesView() {
    const currentView = document.getElementById('favorites-content').classList.contains('hidden') ? 'lists' : 'favorites';
    if (currentView === 'favorites') {
        showListsTab();
    } else {
        showFavoritesTab();
    }
}

// Renderiza las listas personalizadas
function renderCustomLists() {
    const customListsContainer = document.getElementById('custom-lists');
    if (!customListsContainer) return;

    if (customLists.length === 0) {
        customListsContainer.innerHTML = `
            <div class="text-center py-12">
                <div class="w-24 h-24 bg-[var(--bg-surface)] rounded-full flex items-center justify-center mx-auto mb-4">
                    <svg class="w-12 h-12 text-[var(--text-secondary)]" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 5H7a2 2 0 00-2 2v10a2 2 0 002 2h8a2 2 0 002-2V7a2 2 0 00-2-2h-2M9 5a2 2 0 002 2h2a2 2 0 002-2M9 5a2 2 0 012-2h2a2 2 0 012 2"></path>
                    </svg>
                </div>
                <h3 class="text-xl font-semibold text-[var(--text-primary)] mb-2">No tienes listas personalizadas</h3>
                <p class="text-[var(--text-secondary)] mb-4">Crea listas para organizar tus cafeterías favoritas</p>
                <button onclick="showCreateListModal()" class="px-6 py-3 bg-[var(--accent-brown)] text-white rounded-lg font-medium hover:bg-[var(--active-brown)] transition-colors">
                    Crear Primera Lista
                </button>
            </div>
        `;
        return;
    }

    customListsContainer.innerHTML = '';
    customLists.forEach(list => {
        const listCard = createCustomListCard(list);
        customListsContainer.appendChild(listCard);
    });
}

// Crea una tarjeta de lista personalizada mejorada
function createCustomListCard(list) {
    const card = document.createElement('div');
    card.classList.add('list-card', 'cursor-pointer', 'transform', 'hover:scale-105', 'transition-transform', 'duration-300');
    card.onclick = () => showListDetail(list);
    
    const coffeeShopsInList = list.coffeeShops.map(id => coffeeShops[id]).filter(Boolean);
    const totalShops = coffeeShopsInList.length;
    
    card.innerHTML = `
        <div class="relative z-10">
            <div class="flex items-start justify-between mb-3">
                <h3 class="text-xl font-bold text-[var(--text-primary)]">${list.name}</h3>
                <span class="text-sm text-[var(--text-secondary)] bg-black/20 px-3 py-1 rounded-full">
                    ${totalShops} ${totalShops === 1 ? 'cafetería' : 'cafeterías'}
                </span>
            </div>
            <p class="text-[var(--text-secondary)] mb-4">${list.description}</p>
            
            ${totalShops > 0 ? `
                <div class="mb-3">
                    <div class="flex -space-x-2 overflow-hidden">
                        ${coffeeShopsInList.slice(0, 3).map(shop => `
                            <img src="${shop.photos[0]}" class="w-8 h-8 rounded-full border-2 border-[var(--bg-surface)] object-cover" alt="${shop.name}" title="${shop.name}">
                        `).join('')}
                        ${totalShops > 3 ? `<div class="w-8 h-8 rounded-full bg-[var(--accent-brown)] flex items-center justify-center text-white text-xs font-bold border-2 border-[var(--bg-surface)]">+${totalShops - 3}</div>` : ''}
                    </div>
                </div>
            ` : ''}
            
            <div class="flex items-center justify-between">
                <span class="text-xs text-[var(--text-secondary)]">
                    Creada el ${list.createdAt.toLocaleDateString('es-ES')}
                </span>
                <div class="flex space-x-2">
                    <button onclick="event.stopPropagation(); editList('${list.id}')" class="text-blue-400 hover:text-blue-300 transition-colors p-1">
                        <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M11 5H6a2 2 0 00-2 2v11a2 2 0 002 2h11a2 2 0 002-2v-5m-1.414-9.414a2 2 0 112.828 2.828L11.828 15H9v-2.828l8.586-8.586z"></path>
                        </svg>
                    </button>
                    <button onclick="event.stopPropagation(); deleteCustomList('${list.id}')" class="text-red-400 hover:text-red-300 transition-colors p-1">
                        <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M19 7l-.867 12.142A2 2 0 0116.138 21H7.862a2 2 0 01-1.995-1.858L5 7m5 4v6m4-6v6m1-10V4a1 1 0 00-1-1h-4a1 1 0 00-1 1v3M4 7h16"></path>
                        </svg>
                    </button>
                </div>
            </div>
        </div>
    `;
    
    return card;
}

// Muestra el modal para crear lista
function showCreateListModal() {
    document.getElementById('createListModal').style.display = 'flex';
    document.getElementById('listNameInput').focus();
}

// Oculta el modal para crear lista
function hideCreateListModal() {
    document.getElementById('createListModal').style.display = 'none';
    document.getElementById('listNameInput').value = '';
    document.getElementById('listDescriptionInput').value = '';
}

// Crea una nueva lista personalizada
function createNewList() {
    const name = document.getElementById('listNameInput').value.trim();
    const description = document.getElementById('listDescriptionInput').value.trim();
    
    if (!name) {
        alert('Por favor ingresa un nombre para la lista');
        return;
    }
    
    const newList = {
        id: 'lista-' + Date.now(),
        name: name,
        description: description || 'Sin descripción',
        coffeeShops: [],
        createdAt: new Date()
    };
    
    customLists.push(newList);
    renderCustomLists();
    updateFavoritesStats();
    hideCreateListModal();
}

// Edita una lista existente
function editList(listId) {
    const list = customLists.find(l => l.id === listId);
    if (!list) return;
    
    const newName = prompt('Nuevo nombre de la lista:', list.name);
    if (newName && newName.trim()) {
        list.name = newName.trim();
        const newDescription = prompt('Nueva descripción de la lista:', list.description);
        if (newDescription !== null) {
            list.description = newDescription.trim();
        }
        renderCustomLists();
    }
}

// Elimina una lista personalizada
function deleteCustomList(listId) {
    const list = customLists.find(l => l.id === listId);
    if (!list) return;
    
    if (confirm(`¿Estás seguro de que quieres eliminar la lista "${list.name}"? Esta acción no se puede deshacer.`)) {
        customLists = customLists.filter(l => l.id !== listId);
        renderCustomLists();
        updateFavoritesStats();
    }
}

// Muestra el detalle de una lista
function showListDetail(list) {
    const coffeeShopsInList = list.coffeeShops.map(id => coffeeShops[id]).filter(Boolean);
    
    if (coffeeShopsInList.length === 0) {
        alert(`Lista: ${list.name}\n\nEsta lista está vacía. Agrega cafeterías desde la vista principal o desde tus favoritos.`);
        return;
    }
    
    const shopDetails = coffeeShopsInList.map((shop, index) => 
        `${index + 1}. ${shop.name} - ${shop.rating}⭐ (${shop.distance})`
    ).join('\n');
    
    alert(`Lista: ${list.name}\n\nDescripción: ${list.description}\n\nCafeterías incluidas:\n${shopDetails}\n\nTotal: ${coffeeShopsInList.length} cafetería${coffeeShopsInList.length === 1 ? '' : 's'}`);
} 
// ===== FUNCIONES PARA PERFIL Y CONFIGURACIÓN =====

// Descarga datos offline
function downloadOfflineData() {
    const button = event.target;
    const originalText = button.textContent;
    
    button.textContent = 'Descargando...';
    button.disabled = true;
    
    // Simula la descarga
    setTimeout(() => {
        button.textContent = '¡Descargado!';
        button.classList.remove('bg-[var(--accent-brown)]');
        button.classList.add('bg-green-600');
        
        setTimeout(() => {
            button.textContent = originalText;
            button.disabled = false;
            button.classList.remove('bg-green-600');
            button.classList.add('bg-[var(--accent-brown)]');
        }, 2000);
    }, 3000);
}

// Función de cerrar sesión
function logout() {
    if (confirm('¿Estás seguro de que quieres cerrar sesión?')) {
        // Aquí se implementaría la lógica de logout
        alert('Sesión cerrada exitosamente');
        showScreen('listView');
    }
}

// Edita el perfil del usuario
function editProfile() {
    const newName = prompt('Nuevo nombre de usuario:', 'Usuario CoffeeNav');
    if (newName && newName.trim()) {
        const nameElement = document.querySelector('#profileView h3');
        if (nameElement) {
            nameElement.textContent = newName.trim();
        }
        
        const newEmail = prompt('Nuevo email:', 'usuario@coffeenav.com');
        if (newEmail && newEmail.trim()) {
            const emailElement = document.querySelector('#profileView p');
            if (emailElement) {
                emailElement.textContent = newEmail.trim();
            }
        }
        
        alert('Perfil actualizado exitosamente');
    }
}

// Cambia la foto de perfil
function changeProfilePhoto() {
    // Simula la selección de una nueva foto
    const photoButton = event.target;
    const originalContent = photoButton.innerHTML;
    
    photoButton.innerHTML = `
        <svg class="w-3 h-3" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 13l4 4L19 7"></path>
        </svg>
    `;
    photoButton.classList.add('bg-green-500');
    
    setTimeout(() => {
        photoButton.innerHTML = originalContent;
        photoButton.classList.remove('bg-green-500');
        alert('Foto de perfil actualizada (simulado)');
    }, 1500);
}

// Activa/desactiva todas las notificaciones
function toggleAllNotifications() {
    const pushNotif = document.getElementById('push-notifications');
    const emailNotif = document.getElementById('email-notifications');
    const bookingNotif = document.getElementById('booking-reminders');
    const toggleButton = document.getElementById('toggle-all-notifications');
    
    if (!pushNotif || !emailNotif || !bookingNotif || !toggleButton) return;
    
    const allChecked = pushNotif.checked && emailNotif.checked && bookingNotif.checked;
    
    if (allChecked) {
        // Desactivar todas
        pushNotif.checked = false;
        emailNotif.checked = false;
        bookingNotif.checked = false;
        toggleButton.textContent = 'Activar todas';
    } else {
        // Activar todas
        pushNotif.checked = true;
        emailNotif.checked = true;
        bookingNotif.checked = true;
        toggleButton.textContent = 'Desactivar todas';
    }
}

// Cambia el idioma de la aplicación
function changeLanguage() {
    const languages = [
        { code: 'es', name: 'Español', flag: '🇪🇸' },
        { code: 'en', name: 'English', flag: '🇺🇸' },
        { code: 'fr', name: 'Français', flag: '🇫🇷' },
        { code: 'de', name: 'Deutsch', flag: '🇩🇪' },
        { code: 'it', name: 'Italiano', flag: '🇮🇹' }
    ];
    
    const languageList = languages.map(lang => `${lang.flag} ${lang.name}`).join('\n');
    const selectedLanguage = prompt(`Selecciona un idioma:\n\n${languageList}\n\nEscribe el nombre del idioma:`);
    
    if (selectedLanguage) {
        const lang = languages.find(l => l.name === selectedLanguage);
        if (lang) {
            const languageElement = document.querySelector('#profileView button[onclick="changeLanguage()"]').previousElementSibling.querySelector('p:last-child');
            if (languageElement) {
                languageElement.textContent = lang.name;
            }
            alert(`Idioma cambiado a ${lang.name}`);
        }
    }
}

// Gestiona la privacidad del usuario
function managePrivacy() {
    const privacyOptions = [
        'Perfil público',
        'Perfil privado',
        'Solo amigos',
        'Personalizado'
    ];
    
    const selectedOption = prompt(`Configuración de privacidad:\n\n${privacyOptions.join('\n')}\n\nEscribe la opción deseada:`);
    
    if (selectedOption && privacyOptions.includes(selectedOption)) {
        alert(`Privacidad configurada como: ${selectedOption}`);
    }
}

// Actualiza las estadísticas del usuario
function updateUserStats() {
    const userFavorites = document.getElementById('user-favorites');
    const userLists = document.getElementById('user-lists');
    const userVisited = document.getElementById('user-visited');
    
    if (userFavorites) userFavorites.textContent = savedCoffeeShops.size;
    if (userLists) userLists.textContent = customLists.length;
    if (userVisited) userVisited.textContent = Math.floor(Math.random() * 15) + 5; // Simulado
}

// Inicializa los switches de configuración
function initializeSwitches() {
    // Agregar event listeners a todos los switches
    const switches = document.querySelectorAll('.switch input[type="checkbox"]');
    switches.forEach(switchInput => {
        switchInput.addEventListener('change', function() {
            const settingName = this.id;
            const isEnabled = this.checked;
            
            // Aquí se implementaría la lógica para guardar la configuración
            console.log(`Configuración ${settingName} ${isEnabled ? 'activada' : 'desactivada'}`);
            
            // Actualizar el botón de "Activar todas" si es necesario
            if (settingName.includes('notifications')) {
                updateToggleAllButton();
            }
        });
    });
}

// Actualiza el estado del botón "Activar todas"
function updateToggleAllButton() {
    const pushNotif = document.getElementById('push-notifications');
    const emailNotif = document.getElementById('email-notifications');
    const bookingNotif = document.getElementById('booking-reminders');
    const toggleButton = document.getElementById('toggle-all-notifications');
    
    if (!pushNotif || !emailNotif || !bookingNotif || !toggleButton) return;
    
    const allChecked = pushNotif.checked && emailNotif.checked && bookingNotif.checked;
    toggleButton.textContent = allChecked ? 'Desactivar todas' : 'Activar todas';
}

// Función para exportar datos del usuario
function exportUserData() {
    const userData = {
        profile: {
            name: 'Usuario CoffeeNav',
            email: 'usuario@coffeenav.com',
            memberSince: 'Enero 2024'
        },
        favorites: Array.from(savedCoffeeShops),
        lists: customLists,
        settings: {
            notifications: {
                push: document.getElementById('push-notifications')?.checked || false,
                email: document.getElementById('email-notifications')?.checked || false,
                booking: document.getElementById('booking-reminders')?.checked || false
            },
            offline: {
                autoDownload: document.getElementById('auto-download')?.checked || false,
                photoStorage: document.getElementById('photo-storage')?.checked || false,
                offlineMaps: document.getElementById('offline-maps')?.checked || false
            }
        }
    };
    
    const dataStr = JSON.stringify(userData, null, 2);
    const dataBlob = new Blob([dataStr], { type: 'application/json' });
    
    const link = document.createElement('a');
    link.href = URL.createObjectURL(dataBlob);
    link.download = 'coffeenav-user-data.json';
    link.click();
    
    alert('Datos exportados exitosamente');
}

// Función para limpiar datos del usuario
function clearUserData() {
    if (confirm('¿Estás seguro de que quieres limpiar todos los datos? Esta acción no se puede deshacer.')) {
        if (confirm('Esto eliminará:\n- Todas las cafeterías favoritas\n- Todas las listas personalizadas\n- Configuraciones guardadas\n\n¿Continuar?')) {
            // Limpiar datos
            savedCoffeeShops.clear();
            customLists.length = 0;
            
            // Resetear switches
            const switches = document.querySelectorAll('.switch input[type="checkbox"]');
            switches.forEach(switchInput => {
                switchInput.checked = false;
            });
            
            // Actualizar estadísticas
            updateUserStats();
            updateToggleAllButton();
            
            alert('Todos los datos han sido limpiados');
        }
    }
} 
// Datos de prueba para simular cafeterías
const coffeeShops = {
    'el-rincon-del-cafe': {
        id: 'el-rincon-del-cafe',
        name: 'El Rincón del Café',
        address: '123 Calle del Café, Madrid',
        hours: 'Lunes a Sábado, 8:00 - 20:00',
        rating: 4.9,
        distance: '1.5 km',
        description: 'Un espacio acogedor con luz natural y WiFi rápido, ideal para teletrabajar. Ofrecemos una selección de café de especialidad tostado localmente y pasteles caseros.',
        services: ['WiFi gratis', 'Área de trabajo', 'Pet-friendly', 'Terraza'],
        brewingMethods: ['Espresso', 'V60', 'Chemex', 'Prensa Francesa'],
        photos: [
            'https://picsum.photos/id/1083/400/300',
            'https://picsum.photos/id/1069/400/300',
            'https://picsum.photos/id/1070/400/300'
        ]
    },
    'la-molienda': {
        id: 'la-molienda',
        name: 'La Molienda',
        address: '456 Avenida de la Molienda, Barcelona',
        hours: 'Martes a Domingo, 9:00 - 18:00',
        rating: 4.7,
        distance: '1.1 km',
        description: 'La Molienda es conocida por sus granos de café de origen único y postres exquisitos. Es un lugar tranquilo y perfecto para una escapada rápida o una tarde de lectura.',
        services: ['Wi-Fi', 'Solo efectivo'],
        brewingMethods: ['Espresso', 'Aeropress', 'Sifón'],
        photos: [
            'https://picsum.photos/id/119/400/300',
            'https://picsum.photos/id/1060/400/300',
            'https://picsum.photos/id/1062/400/300'
        ]
    },
    'coffee-lab': {
        id: 'coffee-lab',
        name: 'Coffee Lab',
        address: '789 Plaza Mayor, Valencia',
        hours: 'Lunes a Viernes, 7:00 - 19:00',
        rating: 4.5,
        distance: '0.8 km',
        description: 'Un laboratorio de sabores para los verdaderos entusiastas del café. Nuestro equipo de baristas experimenta con nuevos métodos y combinaciones para ofrecer una experiencia única.',
        services: ['WiFi gratis'],
        brewingMethods: ['Cold Brew', 'V60', 'Sifón'],
        photos: [
            'https://picsum.photos/id/1071/400/300',
            'https://picsum.photos/id/1072/400/300',
            'https://picsum.photos/id/1073/400/300'
        ]
    }
};

// Simulación de una lista de elementos guardados
// Se inicializa con una cafetería guardada para la demostración
let savedCoffeeShops = new Set(['el-rincon-del-cafe']);

// Listas personalizadas del usuario
let customLists = [
    {
        id: 'lista-1',
        name: 'Cafeterías para trabajar',
        description: 'Lugares ideales para teletrabajar con WiFi rápido',
        coffeeShops: ['el-rincon-del-cafe', 'coffee-lab'],
        createdAt: new Date('2024-01-15')
    },
    {
        id: 'lista-2',
        name: 'Cafeterías con terraza',
        description: 'Perfectas para días soleados',
        coffeeShops: ['el-rincon-del-cafe'],
        createdAt: new Date('2024-01-10')
    }
];

// Estado de la navegación
const screens = ['listView', 'mapView', 'searchView', 'resultsView', 'favoritesView', 'profileView'];
const navLinks = {
    'listView': 'nav-inicio',
    'favoritesView': 'nav-favoritos',
    'profileView': 'nav-ajustes'
};
let history = ['listView']; // Para manejar el botón de retroceso
let selectedMarkerId = null; 
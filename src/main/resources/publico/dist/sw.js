if('serviceWorker' in navigator) {
    window.addEventListener('load', () => {
        navigator.serviceWorker.register('sw.js')
            .then((registration) => {
                console.log("Service Worker Registrado Correctamente")
            }, (err) => {
                console.log("Registro de Service Worker Fallido")
            })
    })
}
let cache_name = 'mypag_v1'
let urls_to_cache = [
    '/',
    '/dist/index.html',
    '/dist/js/scripts.js',
    '/dist/404.html',
    '/dist/Logo_PUCMM.png',
    '/dist/css/styles.css',
    '/scripts/indexdDB.js',
    '/scripts/offline.js',
    '/scripts/clean.js'
]
self.addEventListener('install', (e) => {
    e.waitUntil(caches.open(cache_name).then((cache) => {
        return cache.addAll(urls_to_cache)
    }) )
})
self.addEventListener('fetch', (e) => {
    e.respondWith(caches.match(e.request).then((response) => {
        if(response)
            return response
        else
            return fetch(e.request)
    }) )
})
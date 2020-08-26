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
    '/js/scripts.js',
    '/404.html',
    '/Logo_PUCMM.png',
    '/css/styles.css',
    '/scripts/indexdDB.js',
    '/scripts/offline.js',
    '/sw.js',
    '/css/webcam-demo.css',
    '/js/app.js',
    '/js/webcamEasy.js',
    '/js/webpack.config.js',
    '/camera_flip_white.png'
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

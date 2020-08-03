
console.log("Script para detectar offline o online")

window.addEventListener('online',  updateOnlineStatus);
window.addEventListener('offline', updateOnlineStatus);

function updateOnlineStatus(event) {
    var condition = navigator.onLine ? "online" : "offline";
    document.body.className = condition;
}

function waiting(event) {
    var mirador = document.body.className


}
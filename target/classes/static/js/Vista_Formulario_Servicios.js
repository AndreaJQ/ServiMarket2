
  // Realizar una solicitud AJAX para obtener los datos del servidor
  fetch("/obtener_publicacion").then(function(response) {
    if (response.ok) {
        return response.json();
    } else {
        throw new Error("Error al obtener los datos");
    }
}).then(function(publicacion) {

    // Va a mostrar los datos recibidos en la página

 document.getElementById("id").innerText = publicacion.id;
 document.getElementById("titulo").innerText = publicacion.titulo;
 document.getElementById("rubro").innerText = publicacion.rubro;
 document.getElementById("descripcion").innerText = publicacion.descripcion;

 // Para mostrar la imagen
 var fotoUrl = publicacion.foto;
 if (fotoUrl) {
     document.getElementById("fotos").src = fotoUrl;
 }
}).catch(function(error) {
    console.log("Error:", error);
});
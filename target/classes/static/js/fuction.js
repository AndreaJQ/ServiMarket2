var images = [
  'img/portada2.webp',
  'img/portada3.webp',
  'img/portada4.webp',
  'img/portada5.webp'
]; // Lista de imágenes de fondo

    var index = 0; // Índice de la imagen actual

    function changeBackground() {
      index = (index + 1) % images.length; // Avanza al siguiente índice

      var imageUrl = images[index]; // Obtiene la URL de la imagen

      document.body.style.backgroundImage = 'linear-gradient(rgba(0,0,0,0.5), rgba(0,0,0,0.5)), url(' + imageUrl + ')'; // Establece el nuevo fondo

      setTimeout(changeBackground, 12000); // Llama a la función nuevamente después de 12 segundos
    }

    changeBackground(); // Inicia el cambio de fondo

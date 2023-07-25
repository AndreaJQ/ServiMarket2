var images = [
  'img/portada1.webp',
  'img/portada2.webp',
  'img/portada3.webp',
  'img/portada4.webp',
  'img/portada5.webp'
];

function changeBackground() {
  var randomIndex = Math.floor(Math.random() * images.length);
  var imageUrl = images[randomIndex];
  document.body.style.backgroundImage = 'url(' + imageUrl + ')';
  setTimeout(changeBackground, 9000); // Es para que cambie el fondo aleatoriamente en 9 segundos
}

changeBackground();   


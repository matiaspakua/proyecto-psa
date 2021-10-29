document.addEventListener('DOMContentLoaded', () => {
  
  // materialize setup
  M.AutoInit();
  var elems = document.querySelectorAll('.autocomplete');
  var instances = M.Autocomplete.init(elems, {
    data:{
      "Meli": './icons/mercadopago.png',
      "Brubank": './icons/brubank.png',
      "Galicia": './icons/galicia.png',
      "Uala": './icons/uala.jpg'
    }
  });

  // dom querys
  const buttonCreate = document.querySelector('#create-wallet');

  // listeners
  buttonCreate.addEventListener('click', e => {

    console.log('click')
    
  });

});
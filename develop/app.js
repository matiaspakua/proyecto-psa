document.addEventListener('DOMContentLoaded', () => {
  
  // materialize setup
  M.AutoInit();

  // dom querys
  const buttonCreate = document.querySelector('#create-wallet');

  // listeners
  buttonCreate.addEventListener('click', e => {

    console.log('click')
    
  });

});
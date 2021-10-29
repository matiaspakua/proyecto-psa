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
  const walletCollection = document.querySelector('#wallet-collection');
  const form = document.querySelector('form');

  // listeners
  buttonCreate.addEventListener('click', e => {

    console.log('click');
    createWallet('http://localhost:8080/accounts');
    showWallets('http://localhost:8080/accounts');
    
  });

  // functions
  const createWallet = (URI) => {

    const walletName = form['wallet-name'].value;
    const cbuOrId = form['cbu-or-id'].value;
    const currencySelection = form['currency-selection'].value;
    const currency = ["ARS", "USD", "BTC"]; // problema a resolver queda debilmente acomplado al form

    const body = {
      "balance": 0,
      "name": walletName,
      "cbu": cbuOrId,
      "currency": currency[currencySelection]
    };

    fetch(URI, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(body)
    }).then(resp => console.log(resp))
    .catch(err => console.log(err));
  };

  const showWallets = (URI) => {

    fetch(URI)
      .then(resp=> resp.json())
      .then(data => {
        data.forEach(arr => {
          const template = `
          <li class="collection-item avatar">
            <i class="material-icons circle">account_balance_wallet</i>
            <span class="title">${arr.name}</span>
            <p>${arr.balance} ${arr.currency}<br>
              --
            </p>
            <a href="#!" class="secondary-content"><i class="material-icons">grade</i></a>
          </li>
          `;
          walletCollection.innerHTML += template;
        })
      }).catch(err => console.log(err));
  };

  // app setup
  showWallets('http://localhost:8080/accounts');

});
export let dataHandler = {
    apiGet: function (url, callback) {
      fetch(url, {
          method:"GET",
          headers:{'Content-Type':'application/json'},
          credentials: "same-origin",
      })
          .then(response => response.json())
          .then(jsonResponse => callback(jsonResponse))
    },
    apiPost: function (url, data, callback) {
        fetch(url, {
            method: "POST",
            headers:{'Content-Type':'application/json'},
            credentials: 'same-origin',
            body:data
        })
            .then(response => response.json())
            .then(json_response => callback(json_response));
    },
    apiPut: function (url, data, callback) {
        fetch(url, {
            method: "PUT",
            headers:{'Content-Type':'application/json'},
            credentials: 'same-origin',
            body:data
        })
            .then(response => response.json())
            .then(json_response => callback(json_response));
    },
    apiDelete: function (url, data, callback) {
        fetch(url, {
            method: "DELETE",
            headers:{'Content-Type':'application/json'},
            credentials: 'same-origin',
            body:JSON.stringify(data)
        }).then(response => response.json())
            .then(json_response => callback(json_response));
    },
    getOrderProducts( callback){
        this.apiGet(`/cart`, (response)=>{
            callback(response)
        })
    },
    addProductToCart(id, callback){
        this.apiPut(`/cart`, id, (response)=>{
            callback(response)
        })
    },
    deleteProductFromCart(id, callback){
        this.apiDelete("/cart", id, (response)=>{
            callback(response)
        });
    }
};
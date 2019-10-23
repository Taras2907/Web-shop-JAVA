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
            body:data
        })
            .then(response => response.json())
            .then(json_response => callback(json_response));
    },
    getOrderProducts(id, callback){
        this.apiGet(`/cart?id=${id}`, (response)=>{  // ?id=1 & id2=2 for more parameters
            callback(response)
        })
    }
};
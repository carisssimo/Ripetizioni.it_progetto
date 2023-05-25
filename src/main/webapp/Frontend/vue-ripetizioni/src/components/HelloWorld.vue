<template>
  <div class="hello">
    <div class="title-container container">
      <h1 class="title-ripetizioni">Ripetizioni.it</h1>
      <p class="p-start">ripetizioni.it ti aiuta a prenotare ripetizioni con i migliori docenti della tua </p>
    </div>

    <!--FORM-->
    <div class="form-container container align-items-center">
      <form @submit.prevent="submitForm">
        <div class=" form-group">
          <label for="exampleInputEmail1">Email address</label>
          <input type="email" class="form-group-orange form-control" id="email" name="email" v-model="email"
                 aria-describedby="emailHelp">

        </div>
        <div class="form-group">
          <label for="exampleInputPassword1">Password</label>
          <input type="password" class="form-group-orange form-control" id="password" name="password" v-model="password">
        </div>
        <div class="form-group">
          <label for="exampleFormControlSelect1">Ruolo</label>
          <select class="form-group-orange form-control" id="role" name="role" v-model="role">
            <option>Utente</option>
            <option>Amministratore</option>
          </select>
        </div>

        <!--<button type="Login" class="btn-login btn btn-primary">Submit</button>-->
        <div class="btn-login btn btn-primary"><input type="submit" name="action" value="submitLogin"/></div>
      </form>
    </div>


  </div>
</template>

<script>
export default {
  name: 'HelloWorld',
  data() {
    return {
      email: '',
      password: '',
      role: '',
      action:'submitLogin'
    };
  },
  methods: {
    submitForm() {
      // Esegui la richiesta HTTP
      const url = 'http://localhost:8080/ServletJDBCmaven_war_exploded/HelloServlet';
      const params = new URLSearchParams();
      console.log(this.username);
      console.log(this.action);
      params.append('username', this.username);
      params.append('password', this.password);
      params.append('role', this.role);
      params.append('action', this.action);

      fetch(url, {
        method: 'POST',
        body: params
      })
          .then(response => {
            // Gestisci la risposta
            if (response.ok) {
              // La richiesta è stata completata con successo
              console.log('Richiesta inviata con successo');
            } else {
              // La richiesta ha generato un errore
              console.error('Errore durante l\'invio della richiesta');
            }
          })
          .catch(error => {
            // Gestisci gli errori di rete
            console.error('Errore di rete', error);
          });
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h3 {
  margin: 40px 0 0;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}
</style>

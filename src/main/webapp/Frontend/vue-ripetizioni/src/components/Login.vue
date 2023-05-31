<template>
  <div class="hello">
    <div class="title-container container">
      <h1 class="title-ripetizioni">Ripetizioni.it</h1>
      <p class="p-start">ripetizioni.it ti aiuta a prenotare ripetizioni con i migliori docenti della tua città</p>
    </div>

    <!--FORM-->
    <div class="form-container container align-items-center">
      <form v-on:submit.prevent="onSubmit" >
        <div class=" form-group">
          <label for="exampleInputEmail1">Email address</label>
          <input type="email" class="form-group-orange form-control" id="email" v-model="email"
                 aria-describedby="emailHelp" required>

        </div>
        <div class="form-group">
          <label for="exampleInputPassword1">Password</label>
          <input type="password" class="form-group-orange form-control" id="password" v-model="password" required>
        </div>
        <div class="form-group">
          <label for="exampleFormControlSelect1">Ruolo</label>
          <select class="form-group-orange form-control" id="role" v-model="role" required>
            <option>Utente</option>
            <option>Amministratore</option>
          </select>
        </div>



        <!--<button type="Login" class="btn-login btn btn-primary">Submit</button>-->
<!--        <div class="btn-login btn btn-primary"><input type="submit" name="action" value="submitLogin"/></div>-->
        <button  @click="submitForm" class="btn-login btn btn-primary">Accedi</button>
      </form>
    </div>

    <div class="button-container container align-items-center">
      <RouterLink to="/signin"><a class="btn-login btn btn-primary"  role="button">Registrati</a></RouterLink>
    </div>

    {{this.isLogged}}

  </div>
</template>

<script>


import axios from "axios";

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: 'Login',
  data() {
    return {
      email: '',
      password: '',
      role: '',
      isLogged:false,
    };
  },
  methods: {
    submitForm() {

      const url = 'http://localhost:8080/ServletJDBCmaven_war_exploded/HelloServlet';
      const params = {
        action: 'submitLogin',
        email:this.email,
        password:this.password,
        role:this.role
      };

        axios.get(url,{params}) /*prima effettuiamo la http request async*/
            .then(response => {         /*solo una volta eseguita la request passiamo a gestire la risposta*/
              if(response.data==="isLogged"){
                console.log(" loggato con successo")
                this.isLogged=true;
              }
              this.isLogged=true;
              console.log(response.data);
            })
            .catch(error => {

              console.error(error);
            });

    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>

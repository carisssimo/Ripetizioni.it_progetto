<template>
  <!--titolo e descrizione-->
  <div class="title-container container">
    <h1 class="title-ripetizioni">Ripetizioni.it</h1>
    <p class="p-start">ripetizioni.it ti aiuta a prenotare ripetizioni con i migliori docenti della tua città </p>
  </div>

  <!--FORM-->
  <div v-if="!isSigned" class="form-container container align-items-center">
    <form v-on:submit.prevent="onSubmit">
      <div class=" form-group">
        <label for="name">Name</label>
        <input type="text" class="form-group-orange form-control" id="name" v-model="name">

      </div>
      <div class="form-group">
        <label for="surname">Surname</label>
        <input type="text" class="form-group-orange form-control" id="surname" v-model="surname">
      </div>
      <div class="form-group">
        <label for="exampleInputPassword1">Password</label>
        <input type="password" class="form-group-orange form-control" id="exampleInputPassword1" v-model="password">
      </div>
      <div class="form-group">
        <label for="email">Email</label>
        <input type="email" class="form-group-orange form-control" id="email" v-model="email">
      </div>
      <div class="form-group">
        <label for="exampleFormControlSelect1">Ruolo</label>
        <select class="form-group-orange form-control" id="exampleFormControlSelect1" v-model="role">
          <option>Utente</option>
          <option>Amministratore</option>
        </select>
      </div>

      <button class="btn-login btn btn-primary" @click="signinForm">Registrati </button>
    </form>
  </div>

  <!--bottoni di registrazione e accesso per ospiti-->
  <div class="button-container container align-items-center">

    <div v-if="isSigned">
      <div class="logged-container title-container container">
        <h1 class="title-main">Ti sei correttamente registrato!</h1>
        <p class="p-start">Torna alla Home e poi effettua il login per prenotare una lezione! </p>
        <p class="p-start"><router-link to="/">Home</router-link> </p>
      </div>
    </div>


  </div>
</template>

<script>
import $ from 'jquery';

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Signin",
  data: function(){
    return{
      isSigned:false,
      action: '',
      name:'',
      surname:'',
      password:'',
      email:'',
      role:''
    }
  },
  methods:{
    signinForm(){
      const url = 'http://localhost:8080/ServletJDBCmaven_war_exploded/HelloServlet';
     /* const params = {
        action: 'submitRegistration',
        name:this.name,
        surname:this.surname,
        password: this.password,
        email: this.email,
        role: this.role
      };*/
      $.post(url, {action:'submitRegistration', name:this.name,
        surname:this.surname,
        password: this.password,
        email: this.email,
        role: this.role}) /*prima effettuiamo la http request async*/
          .then(response => {         /*solo una volta eseguita la request passiamo a gestire la risposta*/
            if (response=== "isSigned") {
              console.log(" registrato con successo")
              this.isSigned = true;
            } else {
              alert("problema di registrazione  ");
            }

          })
          .catch(error => {

            console.error(error);
          });
    }
  }
}
</script>

<style scoped>
.logged-container {
  padding-top: 50px;
  padding-bottom: 50px;
  border: solid orangered 2px;
  border-radius: 10px;
  margin-top: 50px;
  background-color: white;
}
</style>
<template>
  <nav class="navbar navbar-expand-lg navbar-light bg-light shadow-lg bg-white rounded fixed-top">
    <div class="container-fluid">
      <div class="container-logo-brand align-content-center">
        <img class="align-content-center" src="../assets/logo-fin.png" height="50" width="50">
        <a class="navbar-brand" style="font-weight: bold; color: orangered; font-size: 30px" href="#">Ripe.it</a>
      </div>
      <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
      </button>
      <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav me-auto mb-2 mb-lg-0">
          <li class="nav-item mx-3">
            <RouterLink to="/"><a class="nav-link active pe-auto" aria-current="page">Home</a></RouterLink>
          </li>
        </ul>
      </div>
    </div>
  </nav>

  <div class="container-full-screen">
    <div class="row w-100">
      <div class="col pt-6 justify-content-center">
        <h1 style="text-align: center; font-size: 60px; font-weight:bold; padding-top: 1rem;">Registrati</h1>
        <h4 style="text-align: center; margin-top: 2rem; margin-bottom: 1rem">Crea il tuo account per accedere ai servizi della piattaforma</h4>
        <img src="../images/img_hero.png" alt="">
      </div>
      <div class="col align-self-center px-5">
        <div v-if="!isSigned" class="form-container container align-items-center">
          <form v-on:submit.prevent="onSubmit">
            <div class=" form-group">
              <label for="name">Nome</label>
              <input type="text" class="form-group-orange form-control" id="name" v-model="name" placeholder="Inserisci il tuo nome:">

            </div>
            <div class="form-group">
              <label for="surname">Cognome</label>
              <input type="text" class="form-group-orange form-control" id="surname" v-model="surname" placeholder="Inserisci il tuo cognome:">
            </div>
            <div class="form-group">
              <label for="exampleInputPassword1">Password</label>
              <input type="password" class="form-group-orange form-control" id="exampleInputPassword1" v-model="password" placeholder="Scegli la tua password:">
            </div>
            <div class="form-group">
              <label for="email">Indirizzo email</label>
              <input type="email" class="form-group-orange form-control" id="email" v-model="email" placeholder="Inserisci il tuo indirizzo email:">
            </div>
            <div class="form-group">
              <label for="exampleFormControlSelect1">Ruolo</label>
              <select class="form-group-orange form-control" id="exampleFormControlSelect1" v-model="role" placeholder="Scegli il tuo ruolo:">
                <option>Utente</option>
                <option>admin</option>
              </select>
            </div>

            <div class="form-group">
              <button class="btn-login btn btn-primary" @click="signinForm">Registrati </button>
            </div>


            <RouterLink to="/login"><a style="color: orangered">Hai già un account? <strong>Clicca qui per accedere!</strong>!</a></RouterLink>
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
            if (response === "isSigned") {
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

.pt-6{
  padding-top: 6rem;
}

.container-full-screen{
  background-image: url("../images/bubbles.png");
  background-repeat: no-repeat;
  background-size: cover;
  height: 100%;
  margin-top: 3rem;
}

.container-full-screen img{
  width: 100%;
  height: 80%;
  margin-top: -6rem;
  padding-left: 5rem;
}
</style>
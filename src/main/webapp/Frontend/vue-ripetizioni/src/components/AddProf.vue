<template>
  <!--titolo e descrizione-->
  <div class="title-container container">
    <h1 class="title-ripetizioni">Ripetizioni.it</h1>
    <p class="p-start">Creazione Docente</p>
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
        <label for="email">Email</label>
        <input type="email" class="form-group-orange form-control" id="email" v-model="email">
      </div>

      <button class="btn-login btn btn-primary" @click="add">Aggiungi </button>
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
import axios from "axios";

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "AddProf",
  data: function(){
    return{
      isSigned:false,
      name:'',
      surname:'',
      email:'',

    }
  },
  methods:{
    add(){
      const url = 'http://localhost:8080/ServletJDBCmaven_war_exploded/HelloServlet';
      const params = {
        action: 'addProf',
        name:this.name,
        surname:this.surname,
        email: this.email,
      };
      axios.get(url, {params}) /*prima effettuiamo la http request async*/
          .then(response => {         /*solo una volta eseguita la request passiamo a gestire la risposta*/
            if (response.data === "Added") {
              console.log(" aggiunto con successo")
              this.name='';
              this.surname='';
              this.email='';
            } else {
              alert("problema aggiunta docente");
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
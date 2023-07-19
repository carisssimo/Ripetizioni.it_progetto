<template>
  <!--NAVBAR PRINCIPALE-->
  <!--<div class="container-fluid">-->
  <nav class="navbar navbar-light bg-light">
    <h1 class="title-ripetizioni">Ripetizioni.it</h1>
    <form class="form-inline">

    </form>
  </nav>
  <!--  navbar per la navigazione dell'amministratore loggato-->

  <ul class="title-main navbar-ripetizioni nav nav-tabs">
    <li class="nav-item">
      <RouterLink to="/Admin"><a class="nav-link " aria-current="page" href="#">Pagina Amministratore</a></RouterLink>
    </li>
    <li class="nav-item dropdown">
      <a class="nav-link dropdown-toggle" data-toggle="dropdown"  role="button" aria-expanded="false">Docenti</a>
      <ul class="dropdown-menu">
        <li><RouterLink to="/AddProf"><a class="dropdown-item" href="#">Aggiungi Docente</a></RouterLink></li>
        <li><RouterLink to="/DelProf"><a class="dropdown-item" href="#">Elimina Docente</a></RouterLink></li>
      </ul>
    </li>
    <li class="nav-item dropdown">
      <a class="nav-link dropdown-toggle" data-toggle="dropdown"  role="button" aria-expanded="false">Corsi</a>
      <ul class="dropdown-menu">
        <li><RouterLink to="/Addsub"><a class="dropdown-item" href="#">Aggiungi Corso</a></RouterLink></li>
        <li><RouterLink to="/RemoveSubject"><a class="dropdown-item" href="#">Elimina Corso</a></RouterLink></li>
      </ul>
    </li>
    <li class="nav-item dropdown">
      <a class="nav-link dropdown-toggle active" data-toggle="dropdown"  role="button" aria-expanded="false">Lezioni</a>
      <ul class="dropdown-menu">
        <li><RouterLink to="/AddLesson"><a class="dropdown-item" href="#">Aggiungi Lezione</a></RouterLink></li>
        <li><RouterLink to="/RemoveAvailability"><a class="dropdown-item" href="#">Elimina Lezione</a></RouterLink></li>
      </ul>
    </li>

  </ul>

  <div class="logged-container title-container container">
    <h1 class="title-main">Eliminazione disponibilità</h1>
  </div>

  <!--FORM-->
  <div v-if="!isSigned" class="form-container container align-items-center">
    <form v-on:submit.prevent="onSubmit">
      <div class=" form-group">
        <label for="name">ID_DOCENTE</label>
        <input type="text" class="form-group-orange form-control" id="name" v-model="id_prof">

      </div>
      <div class="form-group">
        <label for="surname">ID_CORSO</label>
        <input type="text" class="form-group-orange form-control" id="surname" v-model="id_sub">
      </div>

      <div class="form-group">
        <label for="name">GIORNO_ORA</label>
        <input type="text" class="form-group-orange form-control" v-model="dateAv">
      </div>

      <button class="btn-login btn btn-primary" @click="add">Rimuovi </button>
    </form>
  </div>


</template>

<script>
import axios from "axios";

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "RemoveAvailability",
  data: function(){
    return{

      id_prof:'',
      id_sub:'',
      dateAv:'',

    }
  },
  methods:{
    add(){
      const url = 'http://localhost:8080/ServletJDBCmaven_war_exploded/HelloServlet';
      const params = {
        action: 'removeAvailability',
        id_prof:this.id_prof,
        id_sub:this.id_sub,
        dateAv: this.dateAv,
      };
      axios.get(url, {params}) /*prima effettuiamo la http request async*/
          .then(response => {         /*solo una volta eseguita la request passiamo a gestire la risposta*/
            if (response.data === "Removed") {
              console.log("disponibilità rimossa con successo")
              this.id_prof='';
              this.id_sub='';
              this.dateAv='';
            } else {
              alert("problema rimozione disponibilità");
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

</style>
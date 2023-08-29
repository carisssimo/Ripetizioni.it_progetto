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
    <h1 class="title-main">Inserimento nuova lezione</h1>
  </div>

  <!--FORM-->
  <div class="form-container container align-items-center">
    <form v-on:submit.prevent="onSubmit">
      <div class=" form-group">
        <label for="name">ID_Docente</label>
        <input type="text" class="form-group-orange form-control" id="id_prof" v-model="id_prof">
      </div>
      <div class="form-group">
        <label for="surname">ID_Corso</label>
        <input type="text" class="form-group-orange form-control" id="id_sub" v-model="id_sub">
      </div>
      <div class="form-group">
        <label for="surname">Giorno e orario</label>
        <input type="text" class="form-group-orange form-control" id="daytime" v-model="daytime">
      </div>




      <button class="btn-login btn btn-primary" @click="add">Aggiungi </button>
    </form>
  </div>


</template>

<script>
//import axios from "axios";
import $ from 'jquery';
export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "AddLesson",
  data: function(){
    return{
      id_prof:'',
      id_sub:'',
      daytime:''
    }
  },
  methods:{
    add(){
      const url = 'http://localhost:8080/ServletJDBCmaven_war_exploded/HelloServlet';
     /* const params = {
        action: 'addLesson',
        id_prof:this.id_prof,
        id_sub:this.id_sub,
        daytime:this.daytime,

      };*/
      $.get(url, {action: 'addLesson', id_prof:this.id_prof, id_sub:this.id_sub, daytime:this.daytime,}) /*prima effettuiamo la http request async*/
          .then(response => {         /*solo una volta eseguita la request passiamo a gestire la risposta*/
            if (response === "Added") {
              console.log("lezione aggiunta con successo")
              this.id_prof='';
              this.id_sub='';
              this.daytime='';
            } else {
              alert("problema aggiunta lezione");
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
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
      <div class="collapse navbar-collapse" style="justify-content: space-between;" id="navbarSupportedContent">
        <ul class="navbar-nav me-auto mb-2 mb-lg-0 w-100">
          <li class="nav-item active">
            <RouterLink to="/Admin"><a class="nav-link active" aria-current="page" href="#">Pagina Amministratore</a></RouterLink>
          </li>
          <li class="nav-item dropdown active">
            <a class="nav-link dropdown-toggle" data-toggle="dropdown"  role="button" aria-expanded="false">Docenti</a>
            <ul class="dropdown-menu">
              <li><RouterLink to="/AddProf"><a class="dropdown-item" href="#">Aggiungi Docente</a></RouterLink></li>
              <li><RouterLink to="/DelProf"><a class="dropdown-item" href="#">Elimina Docente</a></RouterLink></li>
            </ul>
          </li>
          <li class="nav-item dropdown active">
            <a class="nav-link dropdown-toggle" data-toggle="dropdown"  role="button" aria-expanded="false">Corsi</a>
            <ul class="dropdown-menu">
              <li><RouterLink to="/Addsub"><a class="dropdown-item" href="#">Aggiungi Corso</a></RouterLink></li>
              <li><RouterLink to="/RemoveSubject"><a class="dropdown-item" href="#">Elimina Corso</a></RouterLink></li>
            </ul>
          </li>
          <li class="nav-item dropdown active">
            <a class="nav-link dropdown-toggle" data-toggle="dropdown"  role="button" aria-expanded="false">Lezioni</a>
            <ul class="dropdown-menu">
              <li><RouterLink to="/AddLesson"><a class="dropdown-item" href="#">Aggiungi Lezione</a></RouterLink></li>
              <li><RouterLink to="/RemoveAvailability"><a class="dropdown-item" href="#">Elimina Lezione</a></RouterLink></li>
            </ul>
          </li>
        </ul>
        <div class="container-right-btn d-inline-flex justify-content-end w-75">
          <RouterLink to="/">
            <a class="btn-login btn btn-primary" @click="Click" role="button">Logout</a>
          </RouterLink>
        </div>
      </div>
    </div>
  </nav>

  <div class="logged-container d-flex justify-content-center align-items-center mt-6">
    <h1 class="title-main text-center" style="font-size: 40px; margin-top: 0;">Aggiungi un corso:</h1>
  </div>

  <div v-if="!isSigned" class="form-container container rounded py-4 mt-4">
    <div class="container-btn-group">
      <div class="btn-group btn-group-justified" role="group">
        <div class="btn-group" role="group">
          <RouterLink :to="'/'+this.selected">
            <a class="btn-l btn-login btn btn-success" role="button">
              <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-chevron-left" viewBox="0 0 16 16">
                <path fill-rule="evenodd" d="M11.354 1.646a.5.5 0 0 1 0 .708L5.707 8l5.647 5.646a.5.5 0 0 1-.708.708l-6-6a.5.5 0 0 1 0-.708l6-6a.5.5 0 0 1 .708 0z"/>
              </svg>
            </a>
          </RouterLink>
        </div>
        <div class="btn-group" role="group">
          <RouterLink :to="'/'+this.selected">
            <a class="btn-l btn-login btn btn-success" role="button">
              <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-chevron-right" viewBox="0 0 16 16">
                <path fill-rule="evenodd" d="M4.646 1.646a.5.5 0 0 1 .708 0l6 6a.5.5 0 0 1 0 .708l-6 6a.5.5 0 0 1-.708-.708L10.293 8 4.646 2.354a.5.5 0 0 1 0-.708z"/>
              </svg>
            </a>
          </RouterLink>
        </div>
      </div>
    </div>
    <h3>Inserisci i dati del corso: </h3>
    <form v-on:submit.prevent="onSubmit" class="mt-4">
      <div class=" form-group">
        <label for="name">Nome del corso</label>
        <input type="text" class="form-group-orange form-control" id="name" v-model="name" placeholder="Inserisci il nome del corso:">
      </div>
      <div class="form-group">
        <label for="surname">Descrizione</label>
        <input type="text" class="form-group-orange form-control" id="surname" v-model="surname" placeholder="Inserisci la descrizione del corso:">
      </div>
      <button class="btn-login btn btn-primary" @click="add">Aggiungi </button>
    </form>
  </div>
</template>

<script>
//import axios from "axios";
import $ from 'jquery';
import Cookie from "vue-cookies";
import router from "@/router";

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "AddSubject",
  data: function(){
    return{
      isSigned:false,
      name:'',
      descp:'',


    }
  },
  methods:{
    Click() {
      console.log('logout')
      localStorage.removeItem('isLogged');
      localStorage.removeItem('admin');
      const url = 'http://localhost:8080/ServletJDBCmaven_war_exploded/HelloServlet';
      //cookieService.delete(localStorage.getItem('email'));
      const params = {
        action: 'logout',
      };
      $.get(url, {params}) /*prima effettuiamo la http request async*/
          .then(response => {         /*solo una volta eseguita la request passiamo a gestire la risposta*/
            if (response.data === "Logout") {
              console.log(" logout ")
              this.isLogged = false;
              localStorage.removeItem('isLogged');
            } else {
              alert("failed logout ");
            }

          })
          .catch(error => {

            console.error(error);
          });
    },
    add(){
      const url = 'http://localhost:8080/ServletJDBCmaven_war_exploded/HelloServlet';
      /*const params = {
        action: 'addSub',
        name:this.name,
        descp:this.descp,

      };*/
      if(localStorage.getItem("isLogged")==='true' && localStorage.getItem('admin')==='true') {
        $.get(url, {action: 'addSub', name: this.name, descp: this.descp,token: Cookie.get(localStorage.getItem("email"))}) /*prima effettuiamo la http request async*/
            .then(response => {         /*solo una volta eseguita la request passiamo a gestire la risposta*/
              if (response === "Added") {
                console.log(" aggiunto con successo")
                this.name = '';
                this.descp = '';
                alert("materia aggiunta con successo")
              }else if(response==='invalidSession'){
                alert("sessione invalida")
                router.push("/")
              }
              else {
                alert("problema aggiunta materia");
              }

            })
            .catch(error => {

              console.error(error);
            });
      }
    }
  }
}
</script>

<style scoped>
.nav-item{
  font-weight: bold;
  font-size: 18px;
}

.navbar-nav{
  justify-content: space-evenly;
}
</style>
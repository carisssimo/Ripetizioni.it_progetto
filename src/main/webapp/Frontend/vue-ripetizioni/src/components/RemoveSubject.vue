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
      <a class="nav-link dropdown-toggle active" data-toggle="dropdown"  role="button" aria-expanded="false">Corsi</a>
      <ul class="dropdown-menu">
        <li><RouterLink to="/Addsub"><a class="dropdown-item" href="#">Aggiungi Corso</a></RouterLink></li>
        <li><RouterLink to="/RemoveSubject"><a class="dropdown-item" href="#">Elimina Corso</a></RouterLink></li>
      </ul>
    </li>
    <li class="nav-item dropdown">
      <a class="nav-link dropdown-toggle" data-toggle="dropdown"  role="button" aria-expanded="false">Lezioni</a>
      <ul class="dropdown-menu">
        <li><RouterLink to="/AddLesson"><a class="dropdown-item" href="#">Aggiungi Lezione</a></RouterLink></li>
        <li><RouterLink to="/RemoveAvailability"><a class="dropdown-item" href="#">Elimina Lezione</a></RouterLink></li>
      </ul>
    </li>

  </ul>

  <div class="logged-container title-container container">
    <h1 class="title-main">Eliminazione Corso</h1>
  </div>

<!--  &lt;!&ndash;FORM&ndash;&gt;
  <div v-if="!isSigned" class="form-container container align-items-center">
    <form v-on:submit.prevent="onSubmit">
      <div class=" form-group">
        <label for="name">Nome</label>
        <input type="text" class="form-group-orange form-control" id="name" v-model="name">

      </div>
      <div class="form-group">
        <label for="surname">Descrizione</label>
        <input type="text" class="form-group-orange form-control" id="surname" v-model="descp">
      </div>



      <button class="btn-login btn btn-primary" @click="add">Rimuovi </button>
    </form>
  </div>-->

  <div class="form-container-selector container">
    <table class="table">
      <thead>
      <tr>
        <th scope="col">Nome</th>
        <th scope="col">Descrizione</th>
        <th scope="col"></th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="subject in subjects" :key="subject.subjectID">
        <td>{{ subject.subjectName }}</td>
        <td>{{ subject.description }}</td>
        <td><a class="btn-login btn btn-primary" role="button" @click="deleteSubject(subject.subjectID)">Elimina</a></td>
      </tr>

      </tbody>
    </table>
  </div>


</template>

<script>
import $ from 'jquery';
import {subjectsService} from "@/Service/subjectsService";

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "RemoveSubject",
  data: function(){
    return{
      isSigned:false,
      /*name:'',
      descp:'',*/
      subjects:{}


    }
  },
  created: async function() {
    try {
      this.loading = true;
      let response = await subjectsService.getAllSubjects();
      this.loading = false;
      this.subjects = response.data;
      console.log(this.subjects);
    } catch (e) {
      console.log(e);
    }
  },
  methods:{
    add(){
      const url = 'http://localhost:8080/ServletJDBCmaven_war_exploded/HelloServlet';
      const params = {
        action: 'RemoveSubject',
        /*name:this.name,
        descp:this.descp,*/

      };
      $.get(url, {params}) /*prima effettuiamo la http request async*/
          .then(response => {         /*solo una volta eseguita la request passiamo a gestire la risposta*/
            if (response.data === "Added") {
              console.log(" aggiunto con successo")
              this.name='';
              this.descp='';
            } else {
              alert("problema aggiunta materia");
            }

          })
          .catch(error => {

            console.error(error);
          });
    },
    deleteSubject(id){
      const url = 'http://localhost:8080/ServletJDBCmaven_war_exploded/HelloServlet';
      const params = {
        action: 'deleteSubject',
        subjectId:id
      };
      if(localStorage.getItem("isLogged")=="true"&&localStorage.getItem("admin")=="true") {
        $.get(url, {params}) /*prima effettuiamo la http request async*/
            .then(response => {         /*solo una volta eseguita la request passiamo a gestire la risposta*/
              if (response.data === "Added") {
                console.log(" eliminato con successo")
                const index = this.subjects.findIndex(subject => subject.subjectID === id);
                if (index !== -1) {
                  this.subjects.splice(index, 1); // Rimuovi la riga corrispondente dalla lista
                }

                this.teacherId = '';
              } else {
                alert("problema eliminazione corso");
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

</style>